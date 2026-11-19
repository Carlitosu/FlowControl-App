<?php
    namespace Models;
    use Config\JConexion;

    class User {
        private $db;

        function __construct() {
            $this->db = new JConexion();
        }

        // Devuelve el usuario si la contrasena coincide, o false
        function login($nombreUsuario, $contrasena) {
            $sql = "SELECT u.Id AS id, u.UserName AS nombreUsuario, u.FullName AS nombreCompleto,
                           c.Description AS rol, u.Email AS correo
                    FROM User u
                    INNER JOIN UserCategory c ON c.Id = u.UserCategory_Id
                    WHERE u.UserName = :nombreUsuario AND u.Password = :contrasena";
            $params = [
                ":nombreUsuario" => $nombreUsuario,
                ":contrasena" => $contrasena
            ];
            return $this->db->fetch($sql, $params);
        }

        function getIdByNombreUsuario($nombreUsuario) {
            $fila = $this->db->fetch("SELECT Id FROM User WHERE UserName = :nombreUsuario", [":nombreUsuario" => $nombreUsuario]);
            return $fila ? $fila->Id : null;
        }

        function existeNombreUsuario($nombreUsuario) {
            $sql = "SELECT Id FROM User WHERE UserName = :nombreUsuario";
            return $this->db->fetch($sql, [":nombreUsuario" => $nombreUsuario]) !== false;
        }

        // Busca la categoria por descripcion; si no existe la crea
        function obtenerIdCategoria($rol) {
            $this->db->query("INSERT IGNORE INTO UserCategory (Description) VALUES (:rol)", [":rol" => $rol]);
            $fila = $this->db->fetch("SELECT Id FROM UserCategory WHERE Description = :rol", [":rol" => $rol]);
            return $fila->Id;
        }

        function insertar($nombreUsuario, $contrasena, $nombreCompleto, $correo, $rol) {
            $idCategoria = $this->obtenerIdCategoria($rol);
            $sql = "INSERT INTO User (UserName, Password, FullName, Email, UserCategory_Id)
                    VALUES (:nombreUsuario, :contrasena, :nombreCompleto, :correo, :idCategoria)";
            $params = [
                ":nombreUsuario" => $nombreUsuario,
                ":contrasena" => $contrasena,
                ":nombreCompleto" => $nombreCompleto,
                ":correo" => $correo,
                ":idCategoria" => $idCategoria
            ];
            $this->db->query($sql, $params);
            return $this->db->getConn()->lastInsertId();
        }
    }
?>
