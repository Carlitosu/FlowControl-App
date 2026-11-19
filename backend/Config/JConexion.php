<?php
namespace Config;

use PDO;

class JConexion {

    private PDO $conn;

    public function __construct() {
        $this->conn = new PDO("mysql:host=localhost;dbname=FlowControl;port=3307;charset=utf8mb4", "root", "");

        $this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }

    public function query(string $sql, array $params = []) {
        $stmt = $this->conn->prepare($sql);
        $stmt->execute($params);
        return $stmt;
    }

    public function getConn(): PDO {
        return $this->conn;
    }

    public function fetchAll(string $sql, array $params = []) {
        $stmt = $this->query($sql, $params);
        return $stmt->fetchAll(PDO::FETCH_OBJ);
    }

    public function fetch(string $sql, array $params = []) {
        $stmt = $this->query($sql, $params);
        return $stmt->fetch(PDO::FETCH_OBJ);
    }

}
?>
