-- MySQL dump 10.13  Distrib 8.0.45, for Linux (x86_64)
--
-- Host: localhost    Database: merkasia
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `merkasia`
--

/*!40000 DROP DATABASE IF EXISTS `merkasia`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `merkasia` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `merkasia`;

-- =====================================================
-- TABLA CLIENTES
-- =====================================================
DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE clientes (
    dni               VARCHAR(20) PRIMARY KEY,
    nombre            VARCHAR(80) NOT NULL,
    apellidos         VARCHAR(120) NOT NULL,
    email             VARCHAR(150) NOT NULL,
    telefono          VARCHAR(30),
    direccion         VARCHAR(255)
);


-- =====================================================
-- TABLA PRODUCTOS
-- Catálogo general de productos
-- El código viene del CSV (10001, 40002, etc.)
-- =====================================================
CREATE TABLE productos (
    id_producto       INT PRIMARY KEY,
    descripcion       VARCHAR(200) NOT NULL,
    precio_unitario   DECIMAL(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;



-- =====================================================
-- TABLA PEDIDOS
-- =====================================================
DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE pedidos (
    id_pedido         INT PRIMARY KEY,
    dni_cliente        VARCHAR(20) NOT NULL,
    fecha_pedido      DATE NOT NULL,
    num_lineas        INT NOT NULL,
    total_pedido      DECIMAL(12,2) NOT NULL,

    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (dni_cliente)
        REFERENCES clientes(dni)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =====================================================
-- TABLA LINEAS_PEDIDO
-- Relación pedido-producto
-- =====================================================
DROP TABLE IF EXISTS `lineas_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE lineas_pedido (
    id_linea          INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido         INT NOT NULL,
    id_producto       INT NOT NULL,
    cantidad          INT NOT NULL,
    precio_unitario   DECIMAL(10,2) NOT NULL,
    subtotal          DECIMAL(12,2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,

    CONSTRAINT fk_linea_pedido
        FOREIGN KEY (id_pedido)
        REFERENCES pedidos(id_pedido)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_linea_producto
        FOREIGN KEY (id_producto)
        REFERENCES productos(id_producto)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-19 12:00:44
