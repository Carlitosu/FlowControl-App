package com.example.flowertl.Controller;

import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Empty.eCliente;
import com.example.flowertl.Model.ModeloCliente;
import java.util.List;

public class ControladorCliente {
    private ModeloCliente modeloCliente = new ModeloCliente();

    public void registrarCliente(String nombre, String identidad, String telefono, String direccion,
                                 RespuestaCallback<IdRegistro> callback) {
        if (nombre.isEmpty() || identidad.isEmpty()) {
            callback.onError("El nombre y la identidad son obligatorios");
            return;
        }

        modeloCliente.insertar(new eCliente(0, nombre, identidad, telefono, direccion), callback);
    }

    public void listarClientes(RespuestaCallback<List<eCliente>> callback) {
        modeloCliente.obtenerTodos(callback);
    }
}
