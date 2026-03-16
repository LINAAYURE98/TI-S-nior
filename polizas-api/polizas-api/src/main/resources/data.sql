-- Pólizas de prueba
INSERT INTO polizas (id, tipo, estado, canon, prima, fecha_inicio, fecha_fin, tomador, beneficiario)
VALUES (1, 'INDIVIDUAL', 'ACTIVA', 1500000.00, 18000000.00, '2024-01-01', '2025-01-01', 'Carlos Pérez', 'Inmobiliaria XYZ');

INSERT INTO polizas (id, tipo, estado, canon, prima, fecha_inicio, fecha_fin, tomador, beneficiario)
VALUES (2, 'COLECTIVA', 'ACTIVA', 3000000.00, 36000000.00, '2024-01-01', '2025-01-01', 'Inmobiliaria ABC', 'Copropiedad Torres del Norte');

INSERT INTO polizas (id, tipo, estado, canon, prima, fecha_inicio, fecha_fin, tomador, beneficiario)
VALUES (3, 'INDIVIDUAL', 'CANCELADA', 2000000.00, 24000000.00, '2023-01-01', '2024-01-01', 'Ana Gómez', 'Propietario Sur');

INSERT INTO polizas (id, tipo, estado, canon, prima, fecha_inicio, fecha_fin, tomador, beneficiario)
VALUES (4, 'COLECTIVA', 'ACTIVA', 5000000.00, 60000000.00, '2024-06-01', '2025-06-01', 'Administración Copropiedades SA', 'Arrendadores Bloque B');

-- Riesgos de prueba
INSERT INTO riesgos (id, estado, descripcion, arrendatario, poliza_id)
VALUES (1, 'ACTIVO', 'Apartamento 101 - Calle 10 #5-20', 'Luis Martínez', 1);

INSERT INTO riesgos (id, estado, descripcion, arrendatario, poliza_id)
VALUES (2, 'ACTIVO', 'Local comercial Piso 1', 'Empresa Ventas SAS', 2);

INSERT INTO riesgos (id, estado, descripcion, arrendatario, poliza_id)
VALUES (3, 'ACTIVO', 'Apartamento 202 - Carrera 8 #12-40', 'María Torres', 2);

INSERT INTO riesgos (id, estado, descripcion, arrendatario, poliza_id)
VALUES (4, 'CANCELADO', 'Casa unifamiliar Sector Norte', 'Pedro Ramos', 3);
