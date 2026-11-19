<?php
    namespace Controllers;
    use Models\Product as Product;

    class ProductController {
        private $data;

        public function __construct() {
            $this->data = new Product();
        }

        // POST Product/getProducts
        public function getProducts() {
            responder(true, "Consulta exitosa", $this->data->getAll());
        }

        // POST Product/insertProduct  (nombre, categoria, precio, existencias, descripcion)
        public function insertProduct() {
            $nombre = getParam("nombre");
            $categoria = getParam("categoria");
            $precio = getParam("precio");
            $existencias = getParam("existencias");
            $descripcion = getParam("descripcion");

            if (empty($nombre) || $precio === "" || $existencias === "") {
                responder(false, "Nombre, precio y existencias son obligatorios", null, 400);
            }
            if (!is_numeric($precio) || (float)$precio < 0) {
                responder(false, "El precio debe ser un numero mayor o igual a 0", null, 400);
            }
            if (!ctype_digit($existencias)) {
                responder(false, "Las existencias deben ser un numero entero mayor o igual a 0", null, 400);
            }

            try {
                $id = $this->data->insertar($nombre, $categoria, (float)$precio, (int)$existencias, $descripcion);
            } catch (\PDOException $e) {
                responder(false, "Error al registrar el producto: " . $e->getMessage(), null, 500);
            }

            responder(true, "Producto registrado correctamente", ["id" => (int)$id], 201);
        }
    }
?>
