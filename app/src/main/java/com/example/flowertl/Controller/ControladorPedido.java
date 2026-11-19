package com.example.flowertl.Controller;

import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.ItemPedidoEnvio;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Empty.ePedido;
import com.example.flowertl.Model.ModeloPedido;
import java.util.List;

public class ControladorPedido {
    private ModeloPedido modeloPedido = new ModeloPedido();

    public void listar(RespuestaCallback<List<ePedido>> callback) {
        modeloPedido.obtenerTodos(callback);
    }

    public void obtenerDetalle(int id, RespuestaCallback<ePedido> callback) {
        modeloPedido.obtenerPorId(id, callback);
    }

    public void registrar(int clienteId, String nombreUsuario, List<ItemPedidoEnvio> items,
                          RespuestaCallback<IdRegistro> callback) {
        if (items == null || items.isEmpty()) {
            callback.onError("Agregue al menos un producto al pedido");
            return;
        }
        modeloPedido.insertar(clienteId, nombreUsuario, items, callback);
    }

    public void cambiarEstado(int id, String estado, RespuestaCallback<Void> callback) {
        modeloPedido.actualizarEstado(id, estado, callback);
    }

    public void eliminar(int id, RespuestaCallback<Void> callback) {
        modeloPedido.eliminar(id, callback);
    }
}
