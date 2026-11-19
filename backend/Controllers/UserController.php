<?php
    namespace Controllers;
    use Models\User as User;

    class UserController {
        private $data;

        public function __construct() {
            $this->data = new User();
        }

        // POST User/login  (nombreUsuario, contrasena)
        public function login() {
            $nombreUsuario = getParam("nombreUsuario");
            $contrasena = getParam("contrasena");

            if (empty($nombreUsuario) || empty($contrasena)) {
                responder(false, "Usuario o contrasena vacios", null, 400);
            }

            $usuario = $this->data->login($nombreUsuario, $contrasena);
            if (!$usuario) {
                responder(false, "Nombre de usuario o contrasena incorrectos", null, 401);
            }

            responder(true, "Inicio de sesion exitoso", $usuario);
        }

        // POST User/insertUser  (nombreUsuario, contrasena, nombreCompleto, correo, rol)
        public function insertUser() {
            $nombreUsuario = getParam("nombreUsuario");
            $contrasena = getParam("contrasena");
            $nombreCompleto = getParam("nombreCompleto");
            $correo = getParam("correo");
            $rol = getParam("rol");

            if (empty($nombreUsuario) || empty($contrasena) || empty($nombreCompleto) || empty($rol)) {
                responder(false, "Nombre de usuario, contrasena, nombre completo y rol son obligatorios", null, 400);
            }
            if (!empty($correo) && !filter_var($correo, FILTER_VALIDATE_EMAIL)) {
                responder(false, "El correo no tiene un formato valido", null, 400);
            }
            if ($this->data->existeNombreUsuario($nombreUsuario)) {
                responder(false, "El nombre de usuario ya existe", null, 409);
            }

            try {
                $id = $this->data->insertar($nombreUsuario, $contrasena, $nombreCompleto, $correo, $rol);
            } catch (\PDOException $e) {
                responder(false, "Error al registrar el usuario: " . $e->getMessage(), null, 500);
            }

            responder(true, "Usuario registrado correctamente", ["id" => (int)$id], 201);
        }
    }
?>
