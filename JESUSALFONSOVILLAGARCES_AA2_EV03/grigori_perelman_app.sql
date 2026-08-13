-- ======================================================
-- Base de datos para GrigoriPerelmánApp
-- Proyecto: Aplicación de resolución de problemas matemáticos paso a paso
-- Autor: Jesús Alfonso Villa Garcés
-- Ficha: 3118497
-- Fecha: 30/05/2026
-- ======================================================

-- 1. Creación de la base de datos (si no existe)
CREATE DATABASE IF NOT EXISTS grigori_perelman_app
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- 2. Seleccionar la base de datos
USE grigori_perelman_app;

-- ======================================================
-- 3. Eliminación de tablas en orden inverso (por si ya existen, para evitar errores)
-- ======================================================
DROP TABLE IF EXISTS problemas;
DROP TABLE IF EXISTS usuarios;

-- ======================================================
-- 4. Creación de tablas con restricciones
-- ======================================================

-- 4.1 Tabla: usuarios
-- Almacena la información de los usuarios registrados
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único del usuario',
    nombre VARCHAR(100) NOT NULL COMMENT 'Nombre completo del usuario',
    email VARCHAR(100) NOT NULL COMMENT 'Correo electrónico (único)',
    password_hash VARCHAR(255) NOT NULL COMMENT 'Hash de la contraseña (bcrypt, SHA-256, etc.)',
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha de creación de la cuenta',
    rol ENUM('estudiante', 'docente', 'profesional') NOT NULL DEFAULT 'estudiante' COMMENT 'Rol del usuario para control de acceso',
    PRIMARY KEY (id_usuario),
    CONSTRAINT uk_usuario_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Usuarios registrados en la aplicación';

-- 4.2 Tabla: problemas
-- Almacena el historial de problemas matemáticos resueltos por los usuarios
CREATE TABLE IF NOT EXISTS problemas (
    id_problema INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único del problema',
    usuario_id INT NOT NULL COMMENT 'Referencia al usuario que resolvió el problema',
    ecuacion TEXT NOT NULL COMMENT 'Ecuación ingresada (formato LaTeX)',
    solucion TEXT NOT NULL COMMENT 'Explicación paso a paso de la solución (puede incluir HTML/LaTeX)',
    tipo_problema VARCHAR(50) DEFAULT NULL COMMENT 'Clasificación del problema (ej: ecuacion_cuadratica, calculo_integral)',
    dificultad ENUM('basico', 'intermedio', 'avanzado', 'experto') NOT NULL DEFAULT 'intermedio' COMMENT 'Nivel de dificultad',
    fecha_resolucion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha y hora de resolución',
    PRIMARY KEY (id_problema),
    CONSTRAINT fk_problemas_usuario FOREIGN KEY (usuario_id) 
        REFERENCES usuarios(id_usuario) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_usuario_id (usuario_id) COMMENT 'Índice para consultas rápidas por usuario'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Historial de problemas resueltos';

-- ======================================================
-- 5. Restricciones adicionales (opcionales, mayor integridad)
-- ======================================================

-- Asegurar que el campo email tenga un formato válido (MySQL 8+)
-- ALTER TABLE usuarios ADD CONSTRAINT chk_email_format CHECK (email LIKE '%_@_%.__%');
-- Nota: La restricción CHECK se puede habilitar si se usa MySQL 8.0.16+.
-- Por simplicidad, se valida en la aplicación.

-- Asegurar que nombre no contenga solo espacios (se puede hacer en aplicación)
-- Asegurar que ecuacion y solucion no estén vacíos (ya son NOT NULL)

-- ======================================================
-- 6. Datos de prueba (opcional, para demostrar funcionalidad)
-- ======================================================

-- Insertar usuarios de ejemplo
INSERT INTO usuarios (nombre, email, password_hash, rol) VALUES
('Jesús Alfonso Villa Garcés', 'jesus.villa@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'estudiante'),
('María López', 'maria.lopez@example.com', 'hash_demo_maria', 'docente'),
('Carlos Mendoza', 'carlos.m@example.com', 'hash_demo_carlos', 'estudiante'),
('Ana Rodríguez', 'ana.rodriguez@example.com', 'hash_demo_ana', 'profesional');

-- Insertar problemas asociados a usuarios (usando los id generados automáticamente)
-- Se obtienen los id de usuarios mediante subconsultas
INSERT INTO problemas (usuario_id, ecuacion, solucion, tipo_problema, dificultad) VALUES
(
    (SELECT id_usuario FROM usuarios WHERE email = 'jesus.villa@example.com'),
    'x^2 - 5x + 6 = 0',
    'Paso 1: Identificar coeficientes a=1, b=-5, c=6.\nPaso 2: Calcular discriminante Δ = b² - 4ac = 25 - 24 = 1.\nPaso 3: Aplicar fórmula cuadrática x = [-b ± √Δ] / (2a).\nPaso 4: Soluciones: x₁ = (5 + 1)/2 = 3, x₂ = (5 - 1)/2 = 2.',
    'ecuacion_cuadratica',
    'intermedio'
),
(
    (SELECT id_usuario FROM usuarios WHERE email = 'jesus.villa@example.com'),
    '2x + 3 = 7',
    'Paso 1: Restar 3 a ambos lados: 2x = 4.\nPaso 2: Dividir entre 2: x = 2.',
    'ecuacion_lineal',
    'basico'
),
(
    (SELECT id_usuario FROM usuarios WHERE email = 'maria.lopez@example.com'),
    '∫ x² dx',
    'La integral de x² con respecto a x es x³/3 + C, donde C es la constante de integración.',
    'calculo_integral',
    'avanzado'
),
(
    (SELECT id_usuario FROM usuarios WHERE email = 'carlos.m@example.com'),
    'dy/dx = 2x',
    'Solución: y = x² + C, donde C es una constante.',
    'ecuacion_diferencial',
    'avanzado'
),
(
    (SELECT id_usuario FROM usuarios WHERE email = 'ana.rodriguez@example.com'),
    'lim_{x→0} (sen x)/x',
    'El límite es 1, conocido como límite fundamental trigonométrico.',
    'limite',
    'experto'
);

-- ======================================================
-- 7. Consultas de verificación (comentadas, opcionales)
-- ======================================================
-- SELECT * FROM usuarios;
-- SELECT * FROM problemas;
-- 
-- -- Listar problemas con nombre del usuario (vista recomendada)
-- CREATE OR REPLACE VIEW vista_problemas_usuario AS
-- SELECT p.id_problema, u.nombre AS usuario, p.ecuacion, p.solucion, p.tipo_problema, p.dificultad, p.fecha_resolucion
-- FROM problemas p
-- JOIN usuarios u ON p.usuario_id = u.id_usuario
-- ORDER BY p.fecha_resolucion DESC;

-- ======================================================
-- 8. Fin del script
-- ======================================================