package com.example.flowertl.Model;

import com.example.flowertl.Config.ClienteApi;
import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.ItemPedidoEnvio;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Empty.ePedido;
import com.google.gson.Gson;
import java.util.List;

public class ModeloPedido {

    public void obtenerTodos(RespuestaCallback<List<ePedido>> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().obtenerPedidos(ClienteApi.parametros()),
            callback);
    }

    public void obtenerPorId(int id, RespuestaCallback<ePedido> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().obtenerPedidoPorId(ClienteApi.parametros("id", String.valueOf(id))),
            callback);
    }

    public void insertar(int clienteId, String nombreUsuario, List<ItemPedidoEnvio> items,
                         RespuestaCallback<IdRegistro> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().registrarPedido(ClienteApi.parametros(
                "clienteId", String.valueOf(clienteId),
                "nombreUsuario", nombreUsuario,
                "productos", new Gson().toJson(items))),
            callback);
    }

    public void actualizarEstado(int id, String estado, RespuestaCallback<Void> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().actualizarEstadoPedido(ClienteApi.parametros(
                "id", String.valueOf(id),
                "estado", estado)),
            callback);
    }

    public void eliminar(int id, RespuestaCallback<Void> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().eliminarPedido(ClienteApi.parametros("id", String.valueOf(id))),
            callback);
    }
}
