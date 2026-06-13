--Paises------------------------------------------------------------------------
INSERT INTO pais (nombre_pais) VALUES ('Costa Rica');
INSERT INTO pais (nombre_pais) VALUES ('Nicaragua');
INSERT INTO pais (nombre_pais) VALUES ('Panamá');
INSERT INTO pais (nombre_pais) VALUES ('Honduras');
INSERT INTO pais (nombre_pais) VALUES ('Guatemala');
INSERT INTO pais (nombre_pais) VALUES ('El Salvador');
INSERT INTO pais (nombre_pais) VALUES ('México');
INSERT INTO pais (nombre_pais) VALUES ('Colombia');
INSERT INTO pais (nombre_pais) VALUES ('Perú');
INSERT INTO pais (nombre_pais) VALUES ('Argentina');

--Provincias--------------------------------------------------------------------
INSERT INTO provincia (nombre_provincia, id_pais) VALUES ('San José', 1);
INSERT INTO provincia (nombre_provincia, id_pais) VALUES ('Panamá Oeste', 3);
INSERT INTO provincia (nombre_provincia, id_pais) VALUES ('León', 2);
INSERT INTO provincia (nombre_provincia, id_pais) VALUES ('Cortés', 4);
INSERT INTO provincia (nombre_provincia, id_pais) VALUES ('San Salvador', 6);
INSERT INTO provincia (nombre_provincia, id_pais) VALUES ('Guatemala', 5);
INSERT INTO provincia (nombre_provincia, id_pais) VALUES ('Jalisco', 7);
INSERT INTO provincia (nombre_provincia, id_pais) VALUES ('Antioquia', 8);
INSERT INTO provincia (nombre_provincia, id_pais) VALUES ('Lima', 9);
INSERT INTO provincia (nombre_provincia, id_pais) VALUES ('Buenos Aires', 10);

--Provincias--------------------------------------------------------------------
INSERT INTO canton (nombre_canton, id_provincia) VALUES ('Central', 1);
INSERT INTO canton (nombre_canton, id_provincia) VALUES ('Arraiján', 2);
INSERT INTO canton (nombre_canton, id_provincia) VALUES ('León Centro', 3);
INSERT INTO canton (nombre_canton, id_provincia) VALUES ('San Pedro Sula', 4);
INSERT INTO canton (nombre_canton, id_provincia) VALUES ('Mejicanos', 5);
INSERT INTO canton (nombre_canton, id_provincia) VALUES ('Mixco', 6);
INSERT INTO canton (nombre_canton, id_provincia) VALUES ('Guadalajara', 7);
INSERT INTO canton (nombre_canton, id_provincia) VALUES ('Medellín', 8);
INSERT INTO canton (nombre_canton, id_provincia) VALUES ('Miraflores', 9);
INSERT INTO canton (nombre_canton, id_provincia) VALUES ('San Telmo', 10);

--Distritos---------------------------------------------------------------------
INSERT INTO distrito (nombre_distrito, id_canton) VALUES ('Carmen', 1);
INSERT INTO distrito (nombre_distrito, id_canton) VALUES ('Burunga', 2);
INSERT INTO distrito (nombre_distrito, id_canton) VALUES ('Subtiava', 3);
INSERT INTO distrito (nombre_distrito, id_canton) VALUES ('Chamelecón', 4);
INSERT INTO distrito (nombre_distrito, id_canton) VALUES ('Cuscatancingo', 5);
INSERT INTO distrito (nombre_distrito, id_canton) VALUES ('Zona 1', 6);
INSERT INTO distrito (nombre_distrito, id_canton) VALUES ('Zapopan', 7);
INSERT INTO distrito (nombre_distrito, id_canton) VALUES ('El Poblado', 8);
INSERT INTO distrito (nombre_distrito, id_canton) VALUES ('Surco', 9);
INSERT INTO distrito (nombre_distrito, id_canton) VALUES ('Barracas', 10);

--Personas----------------------------------------------------------------------
INSERT INTO persona (isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito) 
VALUES (0, 'Laura', 'Jiménez', 'laura.jimenez@mail.com', 'pass1', 1);
INSERT INTO persona (isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito) 
VALUES (1, 'Carlos', 'Martínez', 'carlos.martinez@mail.com', 'pass2', 2);
INSERT INTO persona (isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito) 
VALUES (0, 'María', 'Gómez', 'maria.gomez@mail.com', 'pass3', 3);
INSERT INTO persona (isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito) 
VALUES (1, 'José', 'Rodríguez', 'jose.rodriguez@mail.com',  'pass4', 4);
INSERT INTO persona (isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito) 
VALUES (0, 'Andrea', 'Morales', 'andrea.morales@mail.com',  'pass5', 5);
INSERT INTO persona (isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito) 
VALUES (1, 'Luis', 'Castro', 'luis.castro@mail.com',  'pass6', 6);
INSERT INTO persona (isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito) 
VALUES (0, 'Sofía', 'Vargas', 'sofia.vargas@mail.com',  'pass7', 7);
INSERT INTO persona (isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito) 
VALUES (1, 'Diego', 'López', 'diego.lopez@mail.com',  'pass8', 8);
INSERT INTO persona (isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito) 
VALUES (0, 'Ana', 'Fernández', 'ana.fernandez@mail.com',  'pass9', 9);
INSERT INTO persona (isEmpleado, nombre, apellido, correo_electronico, password_persona, id_distrito) 
VALUES (1, 'Pedro', 'Ramírez', 'pedro.ramirez@mail.com',  'pass10', 10);

--Telefonos---------------------------------------------------------------------
INSERT INTO telefono (numero) VALUES ('+50660000001');
INSERT INTO telefono (numero) VALUES ('+50660000002');
INSERT INTO telefono (numero) VALUES ('+50660000003');
INSERT INTO telefono (numero) VALUES ('+50660000004');
INSERT INTO telefono (numero) VALUES ('+50660000005');
INSERT INTO telefono (numero) VALUES ('+50660000006');
INSERT INTO telefono (numero) VALUES ('+50660000007');
INSERT INTO telefono (numero) VALUES ('+50660000008');
INSERT INTO telefono (numero) VALUES ('+50660000009');
INSERT INTO telefono (numero) VALUES ('+50660000010');

--PersonaXTelefono--------------------------------------------------------------
INSERT INTO persona_X_telefono (id_persona, id_telefono) VALUES (1, 1);
INSERT INTO persona_X_telefono (id_persona, id_telefono) VALUES (2, 2);
INSERT INTO persona_X_telefono (id_persona, id_telefono) VALUES (3, 3);
INSERT INTO persona_X_telefono (id_persona, id_telefono) VALUES (4, 4);
INSERT INTO persona_X_telefono (id_persona, id_telefono) VALUES (5, 5);
INSERT INTO persona_X_telefono (id_persona, id_telefono) VALUES (6, 6);
INSERT INTO persona_X_telefono (id_persona, id_telefono) VALUES (7, 7);
INSERT INTO persona_X_telefono (id_persona, id_telefono) VALUES (8, 8);
INSERT INTO persona_X_telefono (id_persona, id_telefono) VALUES (9, 9);
INSERT INTO persona_X_telefono (id_persona, id_telefono) VALUES (10, 10);

--Metodos de Pago---------------------------------------------------------------
INSERT INTO metodo_pago (nombre) VALUES ('Tarjeta');
INSERT INTO metodo_pago (nombre) VALUES ('Efectivo');
INSERT INTO metodo_pago (nombre) VALUES ('SINPE');
INSERT INTO metodo_pago (nombre) VALUES ('Metodo4');
INSERT INTO metodo_pago (nombre) VALUES ('Metodo5');
INSERT INTO metodo_pago (nombre) VALUES ('Metodo6');
INSERT INTO metodo_pago (nombre) VALUES ('Metodo7');
INSERT INTO metodo_pago (nombre) VALUES ('Metodo8');
INSERT INTO metodo_pago (nombre) VALUES ('Metodo9');
INSERT INTO metodo_pago (nombre) VALUES ('Metodo10');

--Categorias--------------------------------------------------------------------
INSERT INTO categoria (nombre, descripcion, impuesto) 
VALUES ('Laptops', 'Computadoras portátiles y notebooks', 0.13);
INSERT INTO categoria (nombre, descripcion, impuesto) 
VALUES ('Desktops', 'Computadoras de escritorio y estaciones de trabajo', 0.13);
INSERT INTO categoria (nombre, descripcion, impuesto) 
VALUES ('Componentes', 'Procesadores, tarjetas madre, RAM, discos duros', 0.13);
INSERT INTO categoria (nombre, descripcion, impuesto) 
VALUES ('Periféricos', 'Teclados, mouse, cámaras, monitores', 0.13);
INSERT INTO categoria (nombre, descripcion, impuesto) 
VALUES ('Accesorios', 'Bolsos, soportes, fundas y adaptadores', 0.13);
INSERT INTO categoria (nombre, descripcion, impuesto) 
VALUES ('Redes', 'Routers, switches, cables y tarjetas de red', 0.13);
INSERT INTO categoria (nombre, descripcion, impuesto) 
VALUES ('Software', 'Licencias de sistemas operativos y aplicaciones', 0.13);
INSERT INTO categoria (nombre, descripcion, impuesto) 
VALUES ('Gaming', 'Consolas, mandos, sillas y accesorios gamer', 0.13);
INSERT INTO categoria (nombre, descripcion, impuesto) 
VALUES ('Audio', 'Audífonos, bocinas, micrófonos y sonido profesional', 0.13);
INSERT INTO categoria (nombre, descripcion, impuesto) 
VALUES ('Almacenamiento', 'Discos externos, USB, SSD y tarjetas SD', 0.13);

--Productos---------------------------------------------------------------------
INSERT INTO producto (nombre, precio, cantidad, id_categoria) 
VALUES ('Laptop Dell Inspiron', 799.99, 20, 1);
INSERT INTO producto (nombre, precio, cantidad, id_categoria) 
VALUES ('PC Gamer Ryzen', 1200.00, 10, 2);
INSERT INTO producto (nombre, precio, cantidad, id_categoria) 
VALUES ('SSD 1TB Samsung', 109.50, 30, 3);
INSERT INTO producto (nombre, precio, cantidad, id_categoria) 
VALUES ('Monitor LG 24"', 159.99, 25, 4);
INSERT INTO producto (nombre, precio, cantidad, id_categoria) 
VALUES ('Maletín para laptop', 35.00, 50, 5);
INSERT INTO producto (nombre, precio, cantidad, id_categoria) 
VALUES ('Router TP-Link AC1200', 49.99, 40, 6);
INSERT INTO producto (nombre, precio, cantidad, id_categoria) 
VALUES ('Windows 11 Pro', 145.00, 15, 7);
INSERT INTO producto (nombre, precio, cantidad, id_categoria) 
VALUES ('Control Xbox Series X', 65.00, 35, 8);
INSERT INTO producto (nombre, precio, cantidad, id_categoria) 
VALUES ('Audífonos Logitech G435', 79.90, 45, 9);
INSERT INTO producto (nombre, precio, cantidad, id_categoria) 
VALUES ('USB Kingston 64GB', 9.99, 100, 10);

--Factura-----------------------------------------------------------------------
INSERT INTO factura (id_persona, id_metodo, total, fecha) 
VALUES (1, 1, 0, SYSDATE);
INSERT INTO factura (id_persona, id_metodo, total, fecha) 
VALUES (2, 2, 0, SYSDATE);
INSERT INTO factura (id_persona, id_metodo, total, fecha) 
VALUES (3, 3, 0, SYSDATE);
INSERT INTO factura (id_persona, id_metodo, total, fecha) 
VALUES (4, 4, 0, SYSDATE);
INSERT INTO factura (id_persona, id_metodo, total, fecha) 
VALUES (5, 5, 0, SYSDATE);
INSERT INTO factura (id_persona, id_metodo, total, fecha) 
VALUES (6, 6, 0, SYSDATE);
INSERT INTO factura (id_persona, id_metodo, total, fecha) 
VALUES (7, 7, 0, SYSDATE);
INSERT INTO factura (id_persona, id_metodo, total, fecha) 
VALUES (8, 8, 0, SYSDATE);
INSERT INTO factura (id_persona, id_metodo, total, fecha) 
VALUES (9, 9, 0, SYSDATE);
INSERT INTO factura (id_persona, id_metodo, total, fecha) 
VALUES (10, 10, 0, SYSDATE);

--ProdXFactura------------------------------------------------------------------

select * from factura;


-- Factura 1
BEGIN
    paquete_factura_edit.agregar_producto_a_factura(1, 1, 1); -- Laptop
    paquete_factura_edit.agregar_producto_a_factura(1, 5, 2); -- Maletín
END;
/

-- Factura 2
BEGIN
    paquete_factura_edit.agregar_producto_a_factura(2, 2, 1); -- PC Gamer
END;
/

-- Factura 3
BEGIN
    paquete_factura_edit.agregar_producto_a_factura(3, 3, 2);  -- SSD
    paquete_factura_edit.agregar_producto_a_factura(3, 10, 3); -- USB
END;
/

-- Factura 4
BEGIN
    paquete_factura_edit.agregar_producto_a_factura(4, 4, 1); -- Monitor
    paquete_factura_edit.agregar_producto_a_factura(4, 9, 2); -- Audífonos
END;
/

-- Factura 5
BEGIN
    paquete_factura_edit.agregar_producto_a_factura(5, 6, 1); -- Router
    paquete_factura_edit.agregar_producto_a_factura(5, 8, 1); -- Control Xbox
END;
/

-- Factura 6
BEGIN
    paquete_factura_edit.agregar_producto_a_factura(6, 7, 1); -- Windows
    paquete_factura_edit.agregar_producto_a_factura(6, 10, 2); -- USB
END;
/

-- Factura 7
BEGIN
    paquete_factura_edit.agregar_producto_a_factura(7, 1, 1); -- Laptop
END;
/

-- Factura 8
BEGIN
    paquete_factura_edit.agregar_producto_a_factura(8, 2, 1); -- PC Gamer
    paquete_factura_edit.agregar_producto_a_factura(8, 4, 1); -- Monitor
END;
/

-- Factura 9
BEGIN
    paquete_factura_edit.agregar_producto_a_factura(9, 3, 1); -- SSD
    paquete_factura_edit.agregar_producto_a_factura(9, 5, 1); -- Maletín
END;
/

-- Factura 10
BEGIN
    paquete_factura_edit.agregar_producto_a_factura(10, 6, 2); -- Router
    paquete_factura_edit.agregar_producto_a_factura(10, 8, 2); -- Control Xbox
END;
/

select * from factura;

--
