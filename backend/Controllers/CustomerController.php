<?php
    namespace Controllers;
    use Models\Customer as Customer;

    class CustomerController {
        private $data;

        public function __construct() {
            $this->data = new Customer();
        }

        // POST Customer/getCustomers
        public function getCustomers() {
            responder(true, "Consulta exitosa", $this->data->getAll());
        }

        // POST Customer/insertCustomer  (nombre, identidad, telefono, direccion)
        public function insertCustomer() {
            $nombre = getParam("nombre");
            $identidad = getParam("identidad");
            $telefono = getParam("telefono");
            $direccion = getParam("direccion");

            if (empty($nombre) || empty($identidad)) {
                responder(false, "Nombre e identidad son obligatorios", null, 400);
            }

            try {
                $id = $this->data->insertar($nombre, $identidad, $telefono, $direccion);
            } catch (\PDOException $e) {
                // 23000 = violacion de clave unica (identidad repetida)
                if ($e->getCode() == "23000") {
                    responder(false, "Ya existe un cliente con esa identidad", null, 409);
                }
                responder(false, "Error al registrar el cliente: " . $e->getMessage(), null, 500);
            }

            responder(true, "Cliente registrado correctamente", ["id" => (int)$id], 201);
        }
    }
?>
