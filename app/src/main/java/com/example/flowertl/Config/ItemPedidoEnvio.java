package com.example.flowertl.Config;

// Un producto dentro de un pedido. Se envia a la API como JSON: [{"productoId": 1, "cantidad": 2}]
public class ItemPedidoEnvio {
    public int productoId;
    public int cantidad;

    public ItemPedidoEnvio(int productoId, int cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }
}
