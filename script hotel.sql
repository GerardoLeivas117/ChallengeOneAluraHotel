create database hotel;
use hotel;

CREATE TABLE credential_user (
    id VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

-- Insertar el usuario "admin" con contraseña "admin"
INSERT INTO credential_user (id, password)
VALUES ('admin', 'admin');

-- Insertar el usuario "recepcion" con contraseña "1234"
INSERT INTO credential_user (id, password)
VALUES ('recepcion', '1234');

-- Crear la tabla 'reservas'
CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    fecha_entrada DATE NOT NULL,
    fecha_salida DATE NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    forma_pago VARCHAR(50) NOT NULL
);

-- Crear la tabla 'huespedes'
CREATE TABLE huespedes (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    nacionalidad VARCHAR(50) NOT NULL,
    telefono VARCHAR(50) NOT NULL,
    id_reserva INT,
    FOREIGN KEY (id_reserva) REFERENCES reservas(id)    
);
