<?php

    ini_set('display_errors', 0);
    date_default_timezone_set("America/Tegucigalpa");

    define("DS", DIRECTORY_SEPARATOR);
    define("ROOT", realpath(dirname(__FILE__)).DS);

    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");

    function getParam($param){
        return isset($_POST[$param]) ? trim($_POST[$param]) : "";
    }

    function responder($success, $message, $data = null, $codigo = 200){
        http_response_code($codigo);
        echo json_encode([
            "message" => $message,
            "success" => $success,
            "data" => $data
        ], JSON_UNESCAPED_UNICODE);
        exit;
    }

    require_once "Config/JRequest.php";
    require_once "Config/JRouter.php";
    require_once "Config/AutoLoad.php";
    require_once "Config/JConexion.php";
    require_once "Models/Api.php";

    if($_SERVER['REQUEST_METHOD'] != 'POST') {
        responder(false, "Metodo no valido, solo se permite POST", null, 405);
    }

    // Credenciales de la API (no del usuario de la app)
    $apiUser = getParam("username");
    $apiPass = getParam("password");

    if(empty($apiUser) || empty($apiPass)) {
        responder(false, "Se requiere autenticacion para acceder a la API", null, 401);
    }

    $api = new Models\Api();
    if(!$api->validar($apiUser, $apiPass)) {
        responder(false, "acceso denegado, credenciales de API incorrectas", null, 401);
    }

    Config\AutoLoad::run();
    Config\JRouter::run(new Config\JRequest());
?>
