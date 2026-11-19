package com.example.flowertl.Config;

public final class ApiConfig {

    // 10.0.2.2 es el localhost de la PC visto desde el emulador de Android.
    // Si se usa un celular fisico, cambiar por la IP de la PC en la red (ej. http://192.168.0.4/FlowControlAPI/)
    public static final String URL_BASE = "http://localhost:8080/FlowControlAPI/";

    // Credenciales de la aplicacion para la API (tabla API de la base FlowControl)
    public static final String API_USUARIO = "FlowControlApp";
    public static final String API_CONTRASENA = "flowcontrol123";

    private ApiConfig() {}
}
