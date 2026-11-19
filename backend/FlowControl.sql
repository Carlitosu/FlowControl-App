-- Base de datos de la app FlowControl (adaptada de la version SQLite)
-- Ejecutar completo en SQLyog (XAMPP MySQL en puerto 3307, usuario root sin contrasena)

DROP DATABASE IF EXISTS FlowControl;
CREATE DATABASE FlowControl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE FlowControl;

-- Categorias de usuario (rol)
CREATE TABLE UserCategory (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Description VARCHAR(120) NOT NULL UNIQUE
);

-- Usuarios de la aplicacion
CREATE TABLE `User` (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    UserName VARCHAR(120) NOT NULL UNIQUE,
    Password VARCHAR(120) NOT NULL,
    FullName VARCHAR(120) NOT NULL,
    Email VARCHAR(120),
    UserCategory_Id INT,
    FOREIGN KEY (UserCategory_Id) REFERENCES UserCategory(Id)
);

-- Productos
CREATE TABLE Product (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(120) NOT NULL,
    Category VARCHAR(120),
    Price DECIMAL(10,2) NOT NULL,
    Stock INT NOT NULL DEFAULT 0,
    Description TEXT
);

-- Clientes
CREATE TABLE Customer (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(150) NOT NULL,
    Identity VARCHAR(20) UNIQUE,
    Phone VARCHAR(20),
    Address TEXT
);

-- Credenciales de las aplicaciones que consumen la API (misma idea que la tabla API de EndPoint)
CREATE TABLE API (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    UserName VARCHAR(60) NOT NULL UNIQUE,
    Password VARCHAR(120) NOT NULL,
    Description VARCHAR(120) NOT NULL
);

-- Datos iniciales
INSERT INTO UserCategory (Description) VALUES ('Administrador');

INSERT INTO `User` (UserName, Password, FullName, Email, UserCategory_Id)
VALUES ('admin', 'admin123', 'Administrador', 'admin@flowers.lt', 1);

-- Credencial de la app Android: usuario FlowControlApp / contrasena flowcontrol123
INSERT INTO API (UserName, Password, Description)
VALUES ('FlowControlApp', MD5('flowcontrol123'), 'Aplicacion Android FlowControl');
