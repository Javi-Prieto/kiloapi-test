INSERT INTO clase(id, nombre, tutor) VALUES (1, '1 DAM', 'Juan');
INSERT INTO clase(id, nombre, tutor) VALUES (2, '2 DAM', 'Luis');
INSERT INTO clase(id, nombre, tutor) VALUES (3, '1 AYF', 'Pepe');

INSERT INTO tipo_alimento(nombre, id) VALUES ('Pasta', 1);

INSERT INTO aportacion(clase_id, id, fecha) VALUES (1, 1, '2022-12-13');
INSERT INTO aportacion(clase_id, id, fecha) VALUES (1, 2, '2022-12-13');
INSERT INTO aportacion(clase_id, id, fecha) VALUES (2, 3, '2022-12-13');

INSERT INTO detalle_aportacion (cantidad_kg, tipo_alimento, aportacion_id, linea_id) VALUES (8, 1, 1, 1);
INSERT INTO detalle_aportacion (cantidad_kg, tipo_alimento, aportacion_id, linea_id) VALUES (10, 1, 2, 2);
INSERT INTO detalle_aportacion (cantidad_kg, tipo_alimento, aportacion_id, linea_id) VALUES (12, 1, 3, 3);
