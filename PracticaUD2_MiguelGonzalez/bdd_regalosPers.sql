DROP DATABASE IF EXISTS bdd_regalosPers;
CREATE DATABASE bdd_regalosPers;
USE bdd_regalosPers;

CREATE TABLE envios (
    idenvio INT AUTO_INCREMENT PRIMARY KEY,
    dni_cliente VARCHAR(9) NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    comentario TEXT,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('Pendiente', 'Enviado', 'Entregado') DEFAULT 'Pendiente' -- Añadido para que la vista funcione
);

-- (Tus tablas de camiseta, taza y llavero se quedan igual que las tenías)
CREATE TABLE camiseta (
    idcamiseta INT AUTO_INCREMENT PRIMARY KEY,
    idenvio INT,
    color ENUM('Rojo','Negro','Azul') NOT NULL,
    material ENUM('Algodón','Poliester') NOT NULL,
    talla ENUM('S','M','L','XL') NOT NULL,
    con_texto BOOLEAN, 
    diseno_texto VARCHAR(255), 
    cantidad INT NOT NULL, 
    precio_camiseta DECIMAL(8,2) DEFAULT 15.50,
    FOREIGN KEY (idenvio) REFERENCES envios(idenvio) ON DELETE CASCADE
);

CREATE TABLE taza (
    idtaza INT AUTO_INCREMENT PRIMARY KEY,
    idenvio INT,
    color ENUM('Amarillo','Verde','Naranja') NOT NULL,
    material ENUM('Cerámica','Cristal') NOT NULL,
    tamano ENUM('Pequeña', 'Mediana', 'Grande'), 
    tipo_diseno ENUM('Texto', 'Dibujo') NOT NULL,
    metodo_diseno ENUM('Foto', 'IA') NOT NULL,
    cantidad INT NOT NULL, 
    precio_taza DECIMAL(8,2) DEFAULT 10.00,
    FOREIGN KEY (idenvio) REFERENCES envios(idenvio) ON DELETE CASCADE
);

CREATE TABLE llavero (
    idllavero INT AUTO_INCREMENT PRIMARY KEY,
    idenvio INT,
    color ENUM('Rosa','Morado','Granate') NOT NULL,
    material ENUM('Plastico','Metal') NOT NULL,
    forma ENUM('Redondo','Cuadrado') NOT NULL,
    cantidad INT NOT NULL, 
    precio_llavero DECIMAL(8,2) DEFAULT 5.00,
    FOREIGN KEY (idenvio) REFERENCES envios(idenvio) ON DELETE CASCADE
);

-- Tu función (Perfecta)
DELIMITER //
CREATE FUNCTION calcular_total_envio(p_idenvio INT) 
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE total DECIMAL(10,2) DEFAULT 0;
    DECLARE t_cam, t_taz, t_lla DECIMAL(10,2) DEFAULT 0;
    
    SELECT IFNULL(SUM(cantidad * precio_camiseta), 0) INTO t_cam FROM camiseta WHERE idenvio = p_idenvio;
    SELECT IFNULL(SUM(cantidad * precio_taza), 0) INTO t_taz FROM taza WHERE idenvio = p_idenvio;
    SELECT IFNULL(SUM(cantidad * precio_llavero), 0) INTO t_lla FROM llavero WHERE idenvio = p_idenvio;
    
    SET total = t_cam + t_taz + t_lla;
    RETURN total;
END //
DELIMITER ;

-- Tu procedimiento (Perfecto)
DELIMITER //
CREATE PROCEDURE sp_registrar_envio_basico(
    IN p_dni VARCHAR(9), 
    IN p_nombre VARCHAR(100), 
    IN p_tel VARCHAR(15)
)
BEGIN
    INSERT INTO envios (dni_cliente, nombre_completo, telefono) 
    VALUES (p_dni, p_nombre, p_tel);
END //
DELIMITER ;

CREATE OR REPLACE VIEW vista_resumen_pedidos AS
SELECT 
    e.idenvio as 'ID', 
    e.nombre_completo as 'Cliente', 
    e.dni_cliente as 'DNI',
    calcular_total_envio(e.idenvio) AS 'Total a Pagar'
FROM envios e;