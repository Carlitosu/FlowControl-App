package com.example.flowertl.Empty;

public class eCliente {
    private int id;
    private String nombre;
    private String identidad;
    private String telefono;
    private String direccion;

    public eCliente() {}

    public eCliente(int id, String nombre, String identidad, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.identidad = identidad;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int obtenerId() { return id; }
    public void establecerId(int id) { this.id = id; }

    public String obtenerNombre() { return nombre; }
    public void establecerNombre(String nombre) { this.nombre = nombre; }

    public String obtenerIdentidad() { return identidad; }
    public void establecerIdentidad(String identidad) { this.identidad = identidad; }

    public String obtenerTelefono() { return telefono; }
    public void establecerTelefono(String telefono) { this.telefono = telefono; }

    public String obtenerDireccion() { return direccion; }
    public void establecerDireccion(String direccion) { this.direccion = direccion; }
}
