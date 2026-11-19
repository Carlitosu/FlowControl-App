<?php
    namespace Models;
    use Config\JConexion;

    class Customer {
        private $db;

        function __construct() {
            $this->db = new JConexion();
        }

        function getAll() {
            $sql = "SELECT Id AS id, Name AS nombre, Identity AS identidad,
                           Phone AS telefono, Address AS direccion
                    FROM Customer";
            return $this->db->fetchAll($sql);
        }

        function insertar($nombre, $identidad, $telefono, $direccion) {
            $sql = "INSERT INTO Customer (Name, Identity, Phone, Address)
                    VALUES (:nombre, :identidad, :telefono, :direccion)";
            $params = [
                ":nombre" => $nombre,
                ":identidad" => $identidad,
                ":telefono" => $telefono,
                ":direccion" => $direccion
            ];
            $this->db->query($sql, $params);
            return $this->db->getConn()->lastInsertId();
        }
    }
?>
