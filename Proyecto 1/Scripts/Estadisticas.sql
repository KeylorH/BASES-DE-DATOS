CREATE OR REPLACE PACKAGE paquete_reportes_view AS
    PROCEDURE reporte_ventas_por_provincia(
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE reporte_productos_y_bitacora(
        p_cursor OUT SYS_REFCURSOR
    );
END paquete_reportes_view;
/

CREATE OR REPLACE PACKAGE BODY paquete_reportes_view AS

    PROCEDURE reporte_ventas_por_provincia(
        p_cursor OUT SYS_REFCURSOR
    ) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT pr.nombre_provincia, SUM(f.total) AS total_ventas
        FROM factura f
        INNER JOIN persona p ON f.id_persona = p.id_persona
        INNER JOIN distrito d ON p.id_distrito = d.id_distrito
        INNER JOIN canton c ON d.id_canton = c.id_canton
        INNER JOIN provincia pr ON c.id_provincia = pr.id_provincia
        GROUP BY pr.nombre_provincia;
    END;

    PROCEDURE reporte_productos_y_bitacora(
        p_cursor OUT SYS_REFCURSOR
    ) AS
    BEGIN
        OPEN p_cursor FOR
        SELECT p.id_producto, p.nombre, c.nombre AS categoria, b.fecha_cambio, b.precio_anterior, b.precio_nuevo
        FROM producto p
        INNER JOIN categoria c ON p.id_categoria = c.id_categoria
        LEFT JOIN bitacora b ON p.id_producto = b.id_producto;
    END;

END paquete_reportes_view;
/
