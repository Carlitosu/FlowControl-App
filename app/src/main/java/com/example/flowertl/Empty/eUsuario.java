package com.example.flowertl.Empty;

public class eUsuario {
    private int id;
    private String nombreUsuario;
    private String nombreCompleto;
    private String contrasena;
    private String rol; // Administrador, Cliente, Usuario
    private String correo;

    public eUsuario() {}

    public eUsuario(int id, String nombreUsuario, String contrasena, String rol, String correo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.correo = correo;
    }

    public int obtenerId() { return id; }
    public void establecerId(int id) { this.id = id; }

    public String obtenerNombreUsuario() { return nombreUsuario; }
    public void establecerNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String obtenerNombreCompleto() { return nombreCompleto; }
    public void establecerNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String obtenerContrasena() { return contrasena; }
    public void establecerContrasena(String contrasena) { this.contrasena = contrasena; }

    public String obtenerRol() { return rol; }
    public void establecerRol(String rol) { this.rol = rol; }

    public String obtenerCorreo() { return correo; }
    public void establecerCorreo(String correo) { this.correo = correo; }
}
