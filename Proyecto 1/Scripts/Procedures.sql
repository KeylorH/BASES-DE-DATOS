--Persona-----------------------------------------------------------------------

CREATE OR REPLACE PACKAGE paquete_persona_edit AS
    PROCEDURE insertar_persona(
        p_isEmpleado IN persona.isEmpleado%TYPE,
        p_nombre IN persona.nombre%TYPE,
        p_apellido IN persona.apellido%TYPE,
        p_correo_electronico IN persona.correo_electronico%TYPE,
        p_password IN persona.password_persona%TYPE,
        p_id_distrito IN persona.id_distrito%TYPE,
        p_telefono IN telefono.numero%TYPE
    );

    PROCEDURE actualizar_persona(
        p_id_persona IN persona.id_persona%TYPE,
        p_isEmpleado IN persona.isEmpleado%TYPE,
        p_nombre IN persona.nombre%TYPE,
        p_apellido IN persona.apellido%TYPE,
        p_correo_electronico IN persona.correo_electronico%TYPE,
        p_password IN persona.password_persona%TYPE,
        p_id_distrito IN persona.id_distrito%TYPE,
        p_telefono IN telefono.numero%TYPE
    );

    PROCEDURE eliminar_persona(
        p_id_persona IN persona.id_persona%TYPE
    );
END paquete_persona_edit;
/

CREATE OR REPLACE PACKAGE BODY paquete_persona_edit AS

    PROCEDURE insertar_persona(
        p_isEmpleado IN persona.isEmpleado%TYPE,
        p_nombre IN persona.nombre%TYPE,
        p_apellido IN persona.apellido%TYPE,
        p_correo_electronico IN persona.correo_electronico%TYPE,
        p_password IN persona.password_persona%TYPE,
        p_id_distrito IN persona.id_distrito%TYPE,
        p_telefono IN telefono.numero%TYPE
    ) AS
        v_id_persona persona.id_persona%TYPE;
        v_id_telefono telefono.id_telefono%TYPE;
    BEGIN
        INSERT INTO persona(isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito)
        VALUES (p_isEmpleado, p_nombre, p_apellido, p_correo_electronico, p_password, p_id_distrito)
        RETURNING id_persona INTO v_id_persona;

        INSERT INTO telefono(numero)
        VALUES (p_telefono)
        RETURNING id_telefono INTO v_id_telefono;

        INSERT INTO persona_X_telefono(id_persona, id_telefono)
        VALUES (v_id_persona, v_id_telefono);
    END;

    PROCEDURE actualizar_persona(
        p_id_persona IN persona.id_persona%TYPE,
        p_isEmpleado IN persona.isEmpleado%TYPE,
        p_nombre IN persona.nombre%TYPE,
        p_apellido IN persona.apellido%TYPE,
        p_correo_electronico IN persona.correo_electronico%TYPE,
        p_password IN persona.password_persona%TYPE,
        p_id_distrito IN persona.id_distrito%TYPE,
        p_telefono IN telefono.numero%TYPE
    ) AS
        v_id_telefono telefono.id_telefono%TYPE;
    BEGIN
        UPDATE persona
        SET isEmpleado = p_isEmpleado,
            nombre = p_nombre,
            apellido = p_apellido,
            correo_electronico = p_correo_electronico,
            password_persona = p_password,
            id_distrito = p_id_distrito
        WHERE id_persona = p_id_persona;

        INSERT INTO telefono(numero)
        VALUES (p_telefono)
        RETURNING id_telefono INTO v_id_telefono;

        DELETE FROM persona_X_telefono
        WHERE id_persona = p_id_persona;

        INSERT INTO persona_X_telefono(id_persona, id_telefono)
        VALUES (p_id_persona, v_id_telefono);
    END;

    PROCEDURE eliminar_persona(
        p_id_persona IN persona.id_persona%TYPE
    ) AS
    BEGIN
        DELETE FROM telefono
        WHERE id_telefono IN (
            SELECT id_telefono
            FROM persona_X_telefono
            WHERE id_persona = p_id_persona
        );

        DELETE FROM persona_X_telefono
        WHERE id_persona = p_id_persona;

        DELETE FROM persona
        WHERE id_persona = p_id_persona;
    END;

END paquete_persona_edit;
/

CREATE OR REPLACE PACKAGE paquete_persona_view AS
    PROCEDURE obtener_persona_por_id(
        p_id_persona IN persona.id_persona%TYPE,
        p_isEmpleado OUT persona.isEmpleado%TYPE,
        p_nombre OUT persona.nombre%TYPE,
        p_apellido OUT persona.apellido%TYPE,
        p_correo_electronico OUT persona.correo_electronico%TYPE,
        p_id_distrito OUT persona.id_distrito%TYPE,
        p_telefono OUT telefono.numero%TYPE
    );

    PROCEDURE obtener_todas_personas(
        p_cursor OUT SYS_REFCURSOR
    );
    
    FUNCTION contar_personas RETURN NUMBER;

END paquete_persona_view;
/

CREATE OR REPLACE PACKAGE BODY paquete_persona_view AS
    PROCEDURE obtener_persona_por_id(
        p_id_persona IN persona.id_persona%TYPE,
        p_isEmpleado OUT persona.isEmpleado%TYPE,
        p_nombre OUT persona.nombre%TYPE,
        p_apellido OUT persona.apellido%TYPE,
        p_correo_electronico OUT persona.correo_electronico%TYPE,
        p_id_distrito OUT persona.id_distrito%TYPE,
        p_telefono OUT telefono.numero%TYPE
    ) AS
    BEGIN
        SELECT 
            p.isEmpleado,
            p.nombre,
            p.apellido,
            p.correo_electronico,
            p.id_distrito,
            t.numero
        INTO 
            p_isEmpleado,
            p_nombre,
            p_apellido,
            p_correo_electronico,
            p_id_distrito,
            p_telefono
        FROM
            persona p
        LEFT JOIN persona_X_telefono pt ON p.id_persona = pt.id_persona
        LEFT JOIN telefono t ON pt.id_telefono = t.id_telefono
        WHERE p.id_persona = p_id_persona;
    END obtener_persona_por_id;
    
    PROCEDURE obtener_todas_personas(
        p_cursor OUT SYS_REFCURSOR
    ) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT 
            p.id_persona,
            p.isEmpleado,
            p.nombre,
            p.apellido,
            p.correo_electronico,
            p.id_distrito,
            t.numero AS telefono
        FROM 
            persona p
        LEFT JOIN persona_X_telefono pt ON p.id_persona = pt.id_persona
        LEFT JOIN telefono t ON pt.id_telefono = t.id_telefono;
    END obtener_todas_personas;
    
    FUNCTION contar_personas RETURN NUMBER IS
        v_total NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_total FROM persona;
        RETURN v_total;
    END contar_personas;
 
END paquete_persona_view;
/
--Categoria---------------------------------------------------------------------

CREATE OR REPLACE PACKAGE paquete_categoria_edit AS
    PROCEDURE insertar_categoria(
        p_nombre IN categoria.nombre%TYPE,
        p_descripcion IN categoria.descripcion%TYPE,
        p_impuesto IN categoria.impuesto%TYPE
    );

    PROCEDURE actualizar_categoria(
        p_id_categoria IN categoria.id_categoria%TYPE,
        p_nombre IN categoria.nombre%TYPE,
        p_descripcion IN categoria.descripcion%TYPE,
        p_impuesto IN categoria.impuesto%TYPE
    );

    PROCEDURE eliminar_categoria(
        p_id_categoria IN categoria.id_categoria%TYPE
    );

    PROCEDURE obtener_categoria_por_id(
        p_id_categoria IN categoria.id_categoria%TYPE,
        p_nombre OUT categoria.nombre%TYPE,
        p_descripcion OUT categoria.descripcion%TYPE,
        p_impuesto OUT categoria.impuesto%TYPE
    );

    PROCEDURE obtener_todas_categorias(
        p_cursor OUT SYS_REFCURSOR
    );
END paquete_categoria_edit;
/

CREATE OR REPLACE PACKAGE BODY paquete_categoria_edit AS

    PROCEDURE insertar_categoria(
        p_nombre IN categoria.nombre%TYPE,
        p_descripcion IN categoria.descripcion%TYPE,
        p_impuesto IN categoria.impuesto%TYPE
    ) AS
    BEGIN
        INSERT INTO categoria(nombre, descripcion, impuesto)
        VALUES (p_nombre, p_descripcion, p_impuesto);
    END insertar_categoria;

    PROCEDURE actualizar_categoria(
        p_id_categoria IN categoria.id_categoria%TYPE,
        p_nombre IN categoria.nombre%TYPE,
        p_descripcion IN categoria.descripcion%TYPE,
        p_impuesto IN categoria.impuesto%TYPE
    ) AS
    BEGIN
        UPDATE categoria
        SET nombre = p_nombre,
            descripcion = p_descripcion,
            impuesto = p_impuesto
        WHERE id_categoria = p_id_categoria;
    END actualizar_categoria;

    PROCEDURE eliminar_categoria(
        p_id_categoria IN categoria.id_categoria%TYPE
    ) AS
    BEGIN
        DELETE FROM categoria
        WHERE id_categoria = p_id_categoria;
    END eliminar_categoria;

    PROCEDURE obtener_categoria_por_id(
        p_id_categoria IN categoria.id_categoria%TYPE,
        p_nombre OUT categoria.nombre%TYPE,
        p_descripcion OUT categoria.descripcion%TYPE,
        p_impuesto OUT categoria.impuesto%TYPE
    ) AS
    BEGIN
        SELECT nombre, descripcion, impuesto
        INTO p_nombre, p_descripcion, p_impuesto
        FROM categoria
        WHERE id_categoria = p_id_categoria;
    END obtener_categoria_por_id;

    PROCEDURE obtener_todas_categorias(
        p_cursor OUT SYS_REFCURSOR
    ) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT * FROM categoria;
    END obtener_todas_categorias;

END paquete_categoria_edit;
/

CREATE OR REPLACE PACKAGE paquete_categoria_view AS
    PROCEDURE obtener_categoria_por_id(
        p_id_categoria IN categoria.id_categoria%TYPE,
        p_nombre OUT categoria.nombre%TYPE,
        p_descripcion OUT categoria.descripcion%TYPE,
        p_impuesto OUT categoria.impuesto%TYPE
    );

    PROCEDURE obtener_todas_categorias(
        p_cursor OUT SYS_REFCURSOR
    );
END paquete_categoria_view;
/

CREATE OR REPLACE PACKAGE BODY paquete_categoria_view AS

    PROCEDURE obtener_categoria_por_id(
        p_id_categoria IN categoria.id_categoria%TYPE,
        p_nombre OUT categoria.nombre%TYPE,
        p_descripcion OUT categoria.descripcion%TYPE,
        p_impuesto OUT categoria.impuesto%TYPE
    ) AS
    BEGIN
        SELECT nombre, descripcion, impuesto
        INTO p_nombre, p_descripcion, p_impuesto
        FROM categoria
        WHERE id_categoria = p_id_categoria;
    END obtener_categoria_por_id;

    PROCEDURE obtener_todas_categorias(
        p_cursor OUT SYS_REFCURSOR
    ) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT * FROM categoria;
    END obtener_todas_categorias;

END paquete_categoria_view;
/

--Lugares-----------------------------------------------------------------------

CREATE OR REPLACE PACKAGE paquete_lugares_edit AS
    PROCEDURE insertar_pais(
        p_nombre IN pais.nombre_pais%TYPE
    );
    PROCEDURE actualizar_pais(
        p_id IN pais.id_pais%TYPE, 
        p_nombre IN pais.nombre_pais%TYPE
    );
    PROCEDURE eliminar_pais(
        p_id IN pais.id_pais%TYPE
    );

    PROCEDURE insertar_provincia(
        p_nombre IN provincia.nombre_provincia%TYPE, 
        p_id_pais IN provincia.id_pais%TYPE
    );
    PROCEDURE actualizar_provincia(
        p_id IN provincia.id_provincia%TYPE, 
        p_nombre IN provincia.nombre_provincia%TYPE, 
        p_id_pais IN provincia.id_pais%TYPE
    );
    PROCEDURE eliminar_provincia(
        p_id IN provincia.id_provincia%TYPE
    );
    PROCEDURE insertar_canton(
        p_nombre IN canton.nombre_canton%TYPE, 
        p_id_provincia IN canton.id_provincia%TYPE
    );
    PROCEDURE actualizar_canton(
        p_id IN canton.id_canton%TYPE, 
        p_nombre IN canton.nombre_canton%TYPE, 
        p_id_provincia IN canton.id_provincia%TYPE
    );
    PROCEDURE eliminar_canton(
        p_id IN canton.id_canton%TYPE
    );
    
    PROCEDURE insertar_distrito(
        p_nombre IN distrito.nombre_distrito%TYPE, 
        p_id_canton IN distrito.id_canton%TYPE
    );
    PROCEDURE actualizar_distrito(
        p_id IN distrito.id_distrito%TYPE, 
        p_nombre IN distrito.nombre_distrito%TYPE, 
        p_id_canton IN distrito.id_canton%TYPE
    );
    PROCEDURE eliminar_distrito(
        p_id IN distrito.id_distrito%TYPE
    );
END paquete_lugares_edit;
/

CREATE OR REPLACE PACKAGE BODY paquete_lugares_edit AS

    PROCEDURE insertar_pais(
        p_nombre IN pais.nombre_pais%TYPE
    ) AS
    BEGIN
        INSERT INTO pais(nombre_pais) VALUES (p_nombre);
    END;

    PROCEDURE actualizar_pais(
        p_id IN pais.id_pais%TYPE, 
        p_nombre IN pais.nombre_pais%TYPE
    ) AS
    BEGIN
        UPDATE pais SET nombre_pais = p_nombre WHERE id_pais = p_id;
    END;

    PROCEDURE eliminar_pais(p_id IN pais.id_pais%TYPE) AS
    BEGIN
        DELETE FROM pais WHERE id_pais = p_id;
    END;

    PROCEDURE insertar_provincia(
        p_nombre IN provincia.nombre_provincia%TYPE, 
        p_id_pais IN provincia.id_pais%TYPE
    ) AS
    BEGIN
        INSERT INTO provincia(nombre_provincia, id_pais) VALUES (p_nombre, p_id_pais);
    END;

    PROCEDURE actualizar_provincia(
        p_id IN provincia.id_provincia%TYPE, 
        p_nombre IN provincia.nombre_provincia%TYPE, 
        p_id_pais IN provincia.id_pais%TYPE
    ) AS
    BEGIN
        UPDATE provincia
        SET nombre_provincia = p_nombre, id_pais = p_id_pais
        WHERE id_provincia = p_id;
    END;

    PROCEDURE eliminar_provincia(
        p_id IN provincia.id_provincia%TYPE
    ) AS
    BEGIN
        DELETE FROM provincia WHERE id_provincia = p_id;
    END;

    PROCEDURE insertar_canton(
        p_nombre IN canton.nombre_canton%TYPE, 
        p_id_provincia IN canton.id_provincia%TYPE
    ) AS
    BEGIN
        INSERT INTO canton(nombre_canton, id_provincia) VALUES (p_nombre, p_id_provincia);
    END;

    PROCEDURE actualizar_canton(
        p_id IN canton.id_canton%TYPE, 
        p_nombre IN canton.nombre_canton%TYPE, 
        p_id_provincia IN canton.id_provincia%TYPE
    ) AS
    BEGIN
        UPDATE canton
        SET nombre_canton = p_nombre, id_provincia = p_id_provincia
        WHERE id_canton = p_id;
    END;

    PROCEDURE eliminar_canton(
        p_id IN canton.id_canton%TYPE
    ) AS
    BEGIN
        DELETE FROM canton WHERE id_canton = p_id;
    END;
    
    PROCEDURE insertar_distrito(
        p_nombre IN distrito.nombre_distrito%TYPE, 
        p_id_canton IN distrito.id_canton%TYPE
    ) AS
    BEGIN
        INSERT INTO distrito(nombre_distrito, id_canton) VALUES (p_nombre, p_id_canton);
    END;

    PROCEDURE actualizar_distrito(
       p_id IN distrito.id_distrito%TYPE, 
        p_nombre IN distrito.nombre_distrito%TYPE, 
        p_id_canton IN distrito.id_canton%TYPE
    ) AS
    BEGIN
        UPDATE distrito
        SET nombre_distrito = p_nombre, 
            id_canton = p_id_canton
        WHERE id_distrito = p_id;
    END;

    PROCEDURE eliminar_distrito(
        p_id IN distrito.id_distrito%TYPE
    ) AS
    BEGIN
        DELETE FROM distrito WHERE id_distrito = p_id;
    END;

END paquete_lugares_edit;
/

CREATE OR REPLACE PACKAGE paquete_lugares_view AS

    PROCEDURE obtener_todos_paises(p_cursor OUT SYS_REFCURSOR);
    PROCEDURE obtener_pais_por_id(p_id IN pais.id_pais%TYPE, p_nombre OUT pais.nombre_pais%TYPE);

    PROCEDURE obtener_provincias_por_pais(p_id_pais IN provincia.id_pais%TYPE, p_cursor OUT SYS_REFCURSOR);
    PROCEDURE obtener_provincia_por_id(p_id IN provincia.id_provincia%TYPE, p_nombre OUT provincia.nombre_provincia%TYPE, p_id_pais OUT provincia.id_pais%TYPE);

    PROCEDURE obtener_cantones_por_provincia(p_id_provincia IN canton.id_provincia%TYPE, p_cursor OUT SYS_REFCURSOR);
    PROCEDURE obtener_canton_por_id(p_id IN canton.id_canton%TYPE, p_nombre OUT canton.nombre_canton%TYPE, p_id_provincia OUT canton.id_provincia%TYPE);

    PROCEDURE obtener_distritos_por_canton(p_id_canton IN distrito.id_canton%TYPE, p_cursor OUT SYS_REFCURSOR);
    PROCEDURE obtener_distrito_por_id(p_id IN distrito.id_distrito%TYPE, p_nombre OUT distrito.nombre_distrito%TYPE, p_id_canton OUT distrito.id_canton%TYPE);
END paquete_lugares_view;
/

CREATE OR REPLACE PACKAGE BODY paquete_lugares_view AS

    -- Países
    PROCEDURE obtener_todos_paises(p_cursor OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT * FROM pais;
    END;

    PROCEDURE obtener_pais_por_id(p_id IN pais.id_pais%TYPE, p_nombre OUT pais.nombre_pais%TYPE) AS
    BEGIN
        SELECT nombre_pais INTO p_nombre FROM pais WHERE id_pais = p_id;
    END;

    -- Provincias
    PROCEDURE obtener_provincias_por_pais(p_id_pais IN provincia.id_pais%TYPE, p_cursor OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT * FROM provincia WHERE id_pais = p_id_pais;
    END;

    PROCEDURE obtener_provincia_por_id(p_id IN provincia.id_provincia%TYPE, p_nombre OUT provincia.nombre_provincia%TYPE, p_id_pais OUT provincia.id_pais%TYPE) AS
    BEGIN
        SELECT nombre_provincia, id_pais
        INTO p_nombre, p_id_pais
        FROM provincia
        WHERE id_provincia = p_id;
    END;

    -- Cantones
    PROCEDURE obtener_cantones_por_provincia(p_id_provincia IN canton.id_provincia%TYPE, p_cursor OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT * FROM canton WHERE id_provincia = p_id_provincia;
    END;

    PROCEDURE obtener_canton_por_id(p_id IN canton.id_canton%TYPE, p_nombre OUT canton.nombre_canton%TYPE, p_id_provincia OUT canton.id_provincia%TYPE) AS
    BEGIN
        SELECT nombre_canton, id_provincia
        INTO p_nombre, p_id_provincia
        FROM canton
        WHERE id_canton = p_id;
    END;

    -- Distritos
    PROCEDURE obtener_distritos_por_canton(p_id_canton IN distrito.id_canton%TYPE, p_cursor OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT * FROM distrito WHERE id_canton = p_id_canton;
    END;

    PROCEDURE obtener_distrito_por_id(p_id IN distrito.id_distrito%TYPE, p_nombre OUT distrito.nombre_distrito%TYPE, p_id_canton OUT distrito.id_canton%TYPE) AS
    BEGIN
        SELECT nombre_distrito, id_canton
        INTO p_nombre, p_id_canton
        FROM distrito
        WHERE id_distrito = p_id;
    END;

END paquete_lugares_view;
/

--Ventas------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE paquete_factura_edit AS
    PROCEDURE crear_factura(
        p_id_persona IN factura.id_persona%TYPE,
        p_id_metodo IN factura.id_metodo%TYPE,
        p_fecha IN DATE,
        p_id_factura OUT factura.id_factura%TYPE
    );

    PROCEDURE crear_producto(
        p_nombre IN producto.nombre%TYPE,
        p_precio IN producto.precio%TYPE,
        p_cantidad IN producto.cantidad%TYPE,
        p_id_categoria IN producto.id_categoria%TYPE,
        p_id_producto OUT producto.id_producto%TYPE
    );

    PROCEDURE agregar_producto_a_factura(
        p_id_factura IN producto_X_factura.id_factura%TYPE,
        p_id_producto IN producto_X_factura.id_producto%TYPE,
        p_cantidad_producto IN producto_X_factura.cantidad_producto%TYPE
    );

    PROCEDURE eliminar_producto_de_factura(
        p_id_factura IN producto_X_factura.id_factura%TYPE,
        p_id_producto IN producto_X_factura.id_producto%TYPE
    );

    PROCEDURE eliminar_producto(
        p_id_producto IN producto.id_producto%TYPE
    );

    PROCEDURE eliminar_factura(
        p_id_factura IN factura.id_factura%TYPE
    );
END paquete_factura_edit;
/

CREATE OR REPLACE PACKAGE BODY paquete_factura_edit AS

    PROCEDURE crear_factura(
        p_id_persona IN factura.id_persona%TYPE,
        p_id_metodo IN factura.id_metodo%TYPE,
        p_fecha IN DATE,
        p_id_factura OUT factura.id_factura%TYPE
    ) AS
    BEGIN
        INSERT INTO factura(id_persona, id_metodo, total, fecha)
        VALUES (p_id_persona, p_id_metodo, 0, p_fecha)
        RETURNING id_factura INTO p_id_factura;
    END;

    PROCEDURE crear_producto(
        p_nombre IN producto.nombre%TYPE,
        p_precio IN producto.precio%TYPE,
        p_cantidad IN producto.cantidad%TYPE,
        p_id_categoria IN producto.id_categoria%TYPE,
        p_id_producto OUT producto.id_producto%TYPE
    ) AS
    BEGIN
        INSERT INTO producto(nombre, precio, cantidad, id_categoria)
        VALUES (p_nombre, p_precio, p_cantidad, p_id_categoria)
        RETURNING id_producto INTO p_id_producto;
    END;

    PROCEDURE agregar_producto_a_factura(
        p_id_factura IN producto_X_factura.id_factura%TYPE,
        p_id_producto IN producto_X_factura.id_producto%TYPE,
        p_cantidad_producto IN producto_X_factura.cantidad_producto%TYPE
    ) AS
        v_precio_unitario producto.precio%TYPE;
        v_subtotal NUMBER;
    BEGIN
        SELECT precio INTO v_precio_unitario FROM producto WHERE id_producto = p_id_producto;

        INSERT INTO producto_X_factura(id_producto, id_factura, cantidad_producto, precio_unitario)
        VALUES (p_id_producto, p_id_factura, p_cantidad_producto, v_precio_unitario);

        v_subtotal := p_cantidad_producto * v_precio_unitario;

        UPDATE factura
        SET total = total + v_subtotal
        WHERE id_factura = p_id_factura;
    END;

    PROCEDURE eliminar_producto_de_factura(
        p_id_factura IN producto_X_factura.id_factura%TYPE,
        p_id_producto IN producto_X_factura.id_producto%TYPE
    ) AS
        v_subtotal NUMBER;
    BEGIN
        SELECT cantidad_producto * precio_unitario
        INTO v_subtotal
        FROM producto_X_factura
        WHERE id_factura = p_id_factura AND id_producto = p_id_producto;

        DELETE FROM producto_X_factura
        WHERE id_factura = p_id_factura AND id_producto = p_id_producto;

        UPDATE factura
        SET total = total - v_subtotal
        WHERE id_factura = p_id_factura;
    END;

    PROCEDURE eliminar_producto(
        p_id_producto IN producto.id_producto%TYPE
    ) AS
        v_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_count
        FROM producto_X_factura
        WHERE id_producto = p_id_producto;

        IF v_count > 0 THEN
            RAISE_APPLICATION_ERROR(-20001, 'No se puede eliminar el producto: está asociado a una factura.');
        END IF;

        DELETE FROM producto
        WHERE id_producto = p_id_producto;
    END;

    PROCEDURE eliminar_factura(
        p_id_factura IN factura.id_factura%TYPE
    ) AS
    BEGIN
        DELETE FROM producto_X_factura WHERE id_factura = p_id_factura;

        DELETE FROM factura WHERE id_factura = p_id_factura;
    END;

END paquete_factura_edit;
/

CREATE OR REPLACE PACKAGE paquete_factura_view AS
    -- Facturas
    PROCEDURE obtener_todas_facturas(p_cursor OUT SYS_REFCURSOR);
    PROCEDURE obtener_facturas_por_cliente(p_id_persona IN factura.id_persona%TYPE, p_cursor OUT SYS_REFCURSOR);
    PROCEDURE obtener_facturas_por_producto(p_id_producto IN producto.id_producto%TYPE, p_cursor OUT SYS_REFCURSOR);

    -- Productos
    PROCEDURE obtener_todos_productos(p_cursor OUT SYS_REFCURSOR);
    PROCEDURE obtener_productos_por_categoria(p_id_categoria IN producto.id_categoria%TYPE, p_cursor OUT SYS_REFCURSOR);
    PROCEDURE obtener_productos_en_factura(p_id_factura IN factura.id_factura%TYPE, p_cursor OUT SYS_REFCURSOR);
END paquete_factura_view;
/

CREATE OR REPLACE PACKAGE BODY paquete_factura_view AS

    PROCEDURE obtener_todas_facturas(p_cursor OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT f.id_factura, f.id_persona, f.id_metodo, f.total, f.fecha,
               p.nombre AS cliente, m.nombre AS metodo_pago
        FROM factura f
        JOIN persona p ON f.id_persona = p.id_persona
        JOIN metodo_pago m ON f.id_metodo = m.id_metodo;
    END;

    PROCEDURE obtener_facturas_por_cliente(p_id_persona IN factura.id_persona%TYPE, p_cursor OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT f.id_factura, f.total, f.fecha, m.nombre AS metodo_pago
        FROM factura f
        JOIN metodo_pago m ON f.id_metodo = m.id_metodo
        WHERE f.id_persona = p_id_persona;
    END;

    PROCEDURE obtener_facturas_por_producto(p_id_producto IN producto.id_producto%TYPE, p_cursor OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT DISTINCT f.id_factura, f.total, f.fecha, f.id_persona
        FROM factura f
        JOIN producto_X_factura pf ON f.id_factura = pf.id_factura
        WHERE pf.id_producto = p_id_producto;
    END;

    PROCEDURE obtener_todos_productos(p_cursor OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT p.id_producto, p.nombre, p.precio, p.cantidad,
               c.nombre AS categoria
        FROM producto p
        JOIN categoria c ON p.id_categoria = c.id_categoria;
    END;

    PROCEDURE obtener_productos_por_categoria(p_id_categoria IN producto.id_categoria%TYPE, p_cursor OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT id_producto, nombre, precio, cantidad
        FROM producto
        WHERE id_categoria = p_id_categoria;
    END;

    PROCEDURE obtener_productos_en_factura(p_id_factura IN factura.id_factura%TYPE, p_cursor OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT p.id_producto, p.nombre, pf.cantidad_producto, pf.precio_unitario,
               (pf.cantidad_producto * pf.precio_unitario) AS subtotal
        FROM producto_X_factura pf
        JOIN producto p ON pf.id_producto = p.id_producto
        WHERE pf.id_factura = p_id_factura;
    END;

END paquete_factura_view;
/

--Metodos de Pago---------------------------------------------------------------

CREATE OR REPLACE PACKAGE paquete_metodo_pago_edit AS
    PROCEDURE insertar_metodo_pago(
        p_nombre IN metodo_pago.nombre%TYPE
    );

    PROCEDURE actualizar_metodo_pago(
        p_id_metodo IN metodo_pago.id_metodo%TYPE,
        p_nombre IN metodo_pago.nombre%TYPE
    );

    PROCEDURE eliminar_metodo_pago(
        p_id_metodo IN metodo_pago.id_metodo%TYPE
    );
END paquete_metodo_pago_edit;
/

CREATE OR REPLACE PACKAGE paquete_metodo_pago_view AS
    PROCEDURE obtener_todos_metodos_pago(
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE obtener_metodo_pago_por_id(
        p_id_metodo IN metodo_pago.id_metodo%TYPE,
        p_nombre OUT metodo_pago.nombre%TYPE
    );
END paquete_metodo_pago_view;
/

