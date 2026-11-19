<?php
    namespace Controllers;
    use Models\Pedido as Pedido;
    use Models\User as User;

    class PedidoController {
        private $data;

        public function __construct() {
            $this->data = new Pedido();
        }

        // POST Pedido/getPedidos  (clienteId opcional)
        public function getPedidos() {
            responder(true, "Consulta exitosa", $this->data->getAll(getParam("clienteId")));
        }

        // POST Pedido/getPedidoById  (id)
        public function getPedidoById() {
            $id = getParam("id");
            $pedido = $id !== "" ? $this->data->getById($id) : false;
            if (!$pedido) {
                responder(false, "El pedido no existe", null, 404);
            }
            $pedido->detalles = $this->data->getDetalles($id);
            responder(true, "Consulta exitosa", $pedido);
        }

        // POST Pedido/insertPedido  (clienteId, nombreUsuario, productos)
        // productos: JSON [{"productoId": 1, "cantidad": 2}, ...]
        public function insertPedido() {
            $clienteId = getParam("clienteId");
            $nombreUsuario = getParam("nombreUsuario");
            $productosJson = getParam("productos");

            if ($clienteId === "" || $productosJson === "") {
                responder(false, "El cliente y al menos un producto son obligatorios", null, 400);
            }

            $items = json_decode($productosJson, true);
            if (!is_array($items) || count($items) === 0) {
                responder(false, "La lista de productos no es valida", null, 400);
            }
            foreach ($items as $item) {
                if (!isset($item["productoId"], $item["cantidad"]) || !ctype_digit((string)$item["cantidad"]) || (int)$item["cantidad"] < 1) {
                    responder(false, "Cada producto necesita un id y una cantidad mayor a 0", null, 400);
                }
            }

            $usuarioId = $nombreUsuario !== "" ? (new User())->getIdByNombreUsuario($nombreUsuario) : null;

            try {
                $id = $this->data->crear($clienteId, $usuarioId, $items);
            } catch (\Exception $e) {
                responder(false, "No se pudo registrar el pedido: " . $e->getMessage(), null, 409);
            }

            responder(true, "Pedido registrado correctamente", ["id" => (int)$id], 201);
        }

        // POST Pedido/updateEstado  (id, estado)
        public function updateEstado() {
            $id = getParam("id");
            $estado = getParam("estado");

            if ($id === "" || !in_array($estado, Pedido::ESTADOS, true)) {
                responder(false, "Id y un estado valido son obligatorios", null, 400);
            }
            if (!$this->data->existe($id)) {
                responder(false, "El pedido no existe", null, 404);
            }

            try {
                $this->data->cambiarEstado($id, $estado);
            } catch (\Exception $e) {
                responder(false, "No se pudo actualizar el pedido: " . $e->getMessage(), null, 500);
            }

            responder(true, "Estado actualizado a " . $estado);
        }

        // POST Pedido/deletePedido  (id)
        public function deletePedido() {
            $id = getParam("id");
            if ($id === "" || !$this->data->existe($id)) {
                responder(false, "El pedido no existe", null, 404);
            }

            try {
                $this->data->eliminar($id);
            } catch (\Exception $e) {
                responder(false, "No se pudo eliminar el pedido: " . $e->getMessage(), null, 500);
            }

            responder(true, "Pedido eliminado correctamente");
        }
    }
?>
