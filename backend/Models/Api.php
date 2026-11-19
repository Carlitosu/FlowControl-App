<?php
    namespace Models;
    use Config\JConexion;

    class Api {
        private $db;

        function __construct() {
            $this->db = new JConexion();
        }

        // Valida las credenciales de la aplicacion cliente (tabla API, password en MD5)
        function validar($username, $password) {
            $sql = "SELECT Id FROM API WHERE UserName = :username AND Password = MD5(:password)";
            $params = [
                ":username" => $username,
                ":password" => $password
            ];
            return $this->db->fetch($sql, $params) !== false;
        }
    }
?>
