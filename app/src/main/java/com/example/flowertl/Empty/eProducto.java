package com.example.flowertl.Empty;

public class eProducto {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int existencias;
    private String descripcion;

    public eProducto() {}

    public eProducto(int id, String nombre, String categoria, double precio, int existencias, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.existencias = existencias;
        this.descripcion = descripcion;
    }

    public int obtenerId() { return id; }
    public void establecerId(int id) { this.id = id; }

    public String obtenerNombre() { return nombre; }
    public void establecerNombre(String nombre) { this.nombre = nombre; }

    public String obtenerCategoria() { return categoria; }
    public void establecerCategoria(String categoria) { this.categoria = categoria; }

    public double obtenerPrecio() { return precio; }
    public void establecerPrecio(double precio) { this.precio = precio; }

    public int obtenerExistencias() { return existencias; }
    public void establecerExistencias(int existencias) { this.existencias = existencias; }

    public String obtenerDescripcion() { return descripcion; }
    public void establecerDescripcion(String descripcion) { this.descripcion = descripcion; }
}
