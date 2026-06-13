CREATE OR REPLACE TYPE t_factura_ids AS TABLE OF NUMBER;
/
CREATE OR REPLACE TRIGGER tiud_prod_fact
    FOR INSERT OR UPDATE OR DELETE 
    ON producto_x_factura
    
    COMPOUND TRIGGER
         v_ids t_factura_ids := t_factura_ids();
    
    AFTER EACH ROW IS
    BEGIN   
        
        IF INSERTING THEN
            v_ids.EXTEND;
            v_ids(v_ids.COUNT) := :NEW.id_factura;

        ELSIF UPDATING THEN
            v_ids.EXTEND;
            v_ids(v_ids.COUNT) := :NEW.id_factura;

        IF :OLD.id_factura != :NEW.id_factura THEN
            v_ids.EXTEND;
            v_ids(v_ids.COUNT) := :OLD.id_factura;
        END IF;

        ELSIF DELETING THEN
            v_ids.EXTEND;
            v_ids(v_ids.COUNT) := :OLD.id_factura;
        END IF;
    END AFTER EACH ROW;
    
    AFTER STATEMENT IS
    BEGIN
        FOR rec IN (
            SELECT DISTINCT COLUMN_VALUE AS id_factura
            FROM TABLE(v_ids)
        ) LOOP
            DECLARE
                v_total NUMBER(10, 2);
            BEGIN
                SELECT NVL(SUM(
                    pxf.cantidad_producto * (pxf.precio_unitario + pxf.precio_unitario * c.impuesto)
                ), 0)
                INTO v_total
                FROM producto_x_factura pxf
                JOIN producto p ON pxf.id_producto = p.id_producto
                JOIN categoria c ON p.id_categoria = c.id_categoria
                WHERE pxf.id_factura = rec.id_factura;

                UPDATE factura
                SET total = v_total
                WHERE id_factura = rec.id_factura;
            END;
        END LOOP;
    END AFTER STATEMENT;
END tiud_prod_fact;
/

CREATE OR REPLACE TRIGGER tiud_bitacora
AFTER INSERT OR UPDATE OF precio OR DELETE
ON producto
FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO bitacora (id_producto, precio_anterior, precio_nuevo, fecha_cambio)
        VALUES (:NEW.id_producto, NULL, :NEW.precio, SYSDATE);

    ELSIF UPDATING THEN
        INSERT INTO bitacora (id_producto, precio_anterior, precio_nuevo, fecha_cambio)
        VALUES (:NEW.id_producto, :OLD.precio, :NEW.precio, SYSDATE);

    ELSIF DELETING THEN
        INSERT INTO bitacora (id_producto, precio_anterior, precio_nuevo, fecha_cambio)
        VALUES (:OLD.id_producto, :OLD.precio, NULL, SYSDATE);
    END IF;
END;
/
