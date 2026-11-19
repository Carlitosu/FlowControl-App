package com.example.flowertl.Empty;

public class eDetallePedido {
    private int productoId;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public int obtenerProductoId() { return productoId; }

    public String obtenerNombreProducto() { return nombreProducto; }

    public int obtenerCantidad() { return cantidad; }

    public double obtenerPrecioUnitario() { return precioUnitario; }

    public double obtenerSubtotal() { return subtotal; }
}
