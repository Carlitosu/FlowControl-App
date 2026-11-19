package com.example.flowertl.Empty;

import java.util.List;

public class ePedido {
    private int id;
    private int clienteId;
    private String clienteNombre;
    private String fecha;
    private double total;
    private String estado; // Pendiente, En proceso, Entregado, Cancelado
    private List<eDetallePedido> detalles;

    public int obtenerId() { return id; }

    public int obtenerClienteId() { return clienteId; }

    public String obtenerClienteNombre() { return clienteNombre; }

    public String obtenerFecha() { return fecha; }

    public double obtenerTotal() { return total; }

    public String obtenerEstado() { return estado; }

    public List<eDetallePedido> obtenerDetalles() { return detalles; }
}
