<?php
    namespace Models;
    use Config\JConexion;

    class Pedido {
        private $db;

        public const ESTADOS = ["Pendiente", "En proceso", "Entregado", "Cancelado"];

        function __construct() {
            $this->db = new JConexion();
        }

        function getAll($clienteId = null) {
            $sql = "SELECT p.Id AS id, p.Customer_Id AS clienteId, c.Name AS clienteNombre,
                           p.Fecha AS fecha, p.Total AS total, p.Estado AS estado
                    FROM Pedido p
                    INNER JOIN Customer c ON c.Id = p.Customer_Id";
            $params = [];
            if (!empty($clienteId)) {
                $sql .= " WHERE p.Customer_Id = :clienteId";
                $params[":clienteId"] = $clienteId;
            }
            $sql .= " ORDER BY p.Fecha DESC, p.Id DESC";
            return $this->db->fetchAll($sql, $params);
        }

        function getById($id) {
            $sql = "SELECT p.Id AS id, p.Customer_Id AS clienteId, c.Name AS clienteNombre,
                           p.Fecha AS fecha, p.Total AS total, p.Estado AS estado
                    FROM Pedido p
                    INNER JOIN Customer c ON c.Id = p.Customer_Id
                    WHERE p.Id = :id";
            return $this->db->fetch($sql, [":id" => $id]);
        }

        function getDetalles($pedidoId) {
            $sql = "SELECT d.Product_Id AS productoId, pr.Name AS nombreProducto, d.Cantidad AS cantidad,
                           d.PrecioUnitario AS precioUnitario, d.Subtotal AS subtotal
                    FROM DetallePedido d
                    INNER JOIN Product pr ON pr.Id = d.Product_Id
                    WHERE d.Pedido_Id = :pedidoId";
            return $this->db->fetchAll($sql, [":pedidoId" => $pedidoId]);
        }

        function existe($id) {
            return $this->db->fetch("SELECT Id FROM Pedido WHERE Id = :id", [":id" => $id]) !== false;
        }

        function getEstado($id) {
            $fila = $this->db->fetch("SELECT Estado FROM Pedido WHERE Id = :id", [":id" => $id]);
            return $fila ? $fila->Estado : null;
        }

        // Crea el pedido, descuenta el stock y calcula el total con los precios de la base.
        // Devuelve el id del pedido o lanza una excepcion con el motivo.
        function crear($clienteId, $usuarioId, $items) {
            $conn = $this->db->getConn();
            $conn->beginTransaction();
            try {
                $total = 0;
                $detalles = [];
                foreach ($items as $item) {
                    $producto = $this->db->fetch(
                        "SELECT Id, Name, Price, Stock FROM Product WHERE Id = :id FOR UPDATE",
                        [":id" => $item["productoId"]]);
                    if (!$producto) {
                        throw new \Exception("El producto " . $item["productoId"] . " no existe");
                    }
                    if ($producto->Stock < $item["cantidad"]) {
                        throw new \Exception("Stock insuficiente para " . $producto->Name .
                            " (disponible: " . $producto->Stock . ")");
                    }
                    $subtotal = round($producto->Price * $item["cantidad"], 2);
                    $total += $subtotal;
                    $detalles[] = [
                        "productoId" => $producto->Id,
                        "cantidad" => $item["cantidad"],
                        "precio" => $producto->Price,
                        "subtotal" => $subtotal
                    ];
                }

                $this->db->query(
                    "INSERT INTO Pedido (Customer_Id, User_Id, Total, Estado) VALUES (:cliente, :usuario, :total, 'Pendiente')",
                    [":cliente" => $clienteId, ":usuario" => $usuarioId, ":total" => $total]);
                $pedidoId = $conn->lastInsertId();

                foreach ($detalles as $d) {
                    $this->db->query(
                        "INSERT INTO DetallePedido (Pedido_Id, Product_Id, Cantidad, PrecioUnitario, Subtotal)
                         VALUES (:pedido, :producto, :cantidad, :precio, :subtotal)",
                        [":pedido" => $pedidoId, ":producto" => $d["productoId"], ":cantidad" => $d["cantidad"],
                         ":precio" => $d["precio"], ":subtotal" => $d["subtotal"]]);
                    $this->ajustarStock($d["productoId"], -$d["cantidad"]);
                }

                $conn->commit();
                return $pedidoId;
            } catch (\Exception $e) {
                $conn->rollBack();
                throw $e;
            }
        }

        function cambiarEstado($id, $estado) {
            $conn = $this->db->getConn();
            $conn->beginTransaction();
            try {
                $anterior = $this->getEstado($id);
                // Al cancelar se devuelve el stock, pero solo una vez
                if ($estado === "Cancelado" && $anterior !== "Cancelado") {
                    $this->devolverStock($id);
                }
                // Si se reactiva un pedido cancelado, se vuelve a descontar el stock
                if ($anterior === "Cancelado" && $estado !== "Cancelado") {
                    $this->descontarStock($id);
                }
                $this->db->query("UPDATE Pedido SET Estado = :estado WHERE Id = :id",
                    [":estado" => $estado, ":id" => $id]);
                $conn->commit();
            } catch (\Exception $e) {
                $conn->rollBack();
                throw $e;
            }
        }

        function eliminar($id) {
            $conn = $this->db->getConn();
            $conn->beginTransaction();
            try {
                if ($this->getEstado($id) !== "Cancelado") {
                    $this->devolverStock($id);
                }
                $this->db->query("DELETE FROM Pedido WHERE Id = :id", [":id" => $id]);
                $conn->commit();
            } catch (\Exception $e) {
                $conn->rollBack();
                throw $e;
            }
        }

        private function devolverStock($pedidoId) {
            foreach ($this->getDetalles($pedidoId) as $d) {
                $this->ajustarStock($d->productoId, $d->cantidad);
            }
        }

        private function descontarStock($pedidoId) {
            foreach ($this->getDetalles($pedidoId) as $d) {
                $this->ajustarStock($d->productoId, -$d->cantidad);
            }
        }

        private function ajustarStock($productoId, $cambio) {
            $this->db->query("UPDATE Product SET Stock = Stock + :cambio WHERE Id = :id",
                [":cambio" => $cambio, ":id" => $productoId]);
        }
    }
?>
