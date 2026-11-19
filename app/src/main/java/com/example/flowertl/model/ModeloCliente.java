package com.example.flowertl.Model;

import com.example.flowertl.Config.ClienteApi;
import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Empty.eCliente;
import java.util.List;

public class ModeloCliente {

    public void obtenerTodos(RespuestaCallback<List<eCliente>> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().obtenerClientes(ClienteApi.parametros()),
            callback);
    }

    public void insertar(eCliente cliente, RespuestaCallback<IdRegistro> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().registrarCliente(ClienteApi.parametros(
                "nombre", cliente.obtenerNombre(),
                "identidad", cliente.obtenerIdentidad(),
                "telefono", cliente.obtenerTelefono(),
                "direccion", cliente.obtenerDireccion())),
            callback);
    }
}
