<?php
    namespace Models;
    use Config\JConexion;

    class Product {
        private $db;

        function __construct() {
            $this->db = new JConexion();
        }

        function getAll() {
            $sql = "SELECT Id AS id, Name AS nombre, Category AS categoria, Price AS precio,
                           Stock AS existencias, Description AS descripcion
                    FROM Product";
            return $this->db->fetchAll($sql);
        }

        function insertar($nombre, $categoria, $precio, $existencias, $descripcion) {
            $sql = "INSERT INTO Product (Name, Category, Price, Stock, Description)
                    VALUES (:nombre, :categoria, :precio, :existencias, :descripcion)";
            $params = [
                ":nombre" => $nombre,
                ":categoria" => $categoria,
                ":precio" => $precio,
                ":existencias" => $existencias,
                ":descripcion" => $descripcion
            ];
            $this->db->query($sql, $params);
            return $this->db->getConn()->lastInsertId();
        }
    }
?>
