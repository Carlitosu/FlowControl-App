package com.example.flowertl.Controller;

import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Empty.eProducto;
import com.example.flowertl.Model.ModeloProducto;

public class ControladorProducto {
    private ModeloProducto modeloProducto = new ModeloProducto();

    public void registrarProducto(String nombre, String categoria, String precioTexto,
                                  String existenciasTexto, String descripcion,
                                  RespuestaCallback<IdRegistro> callback) {
        if (nombre.isEmpty() || precioTexto.isEmpty() || existenciasTexto.isEmpty()) {
            callback.onError("El nombre, el precio y las existencias son obligatorios");
            return;
        }

        double precio;
        int existencias;
        try {
            precio = Double.parseDouble(precioTexto);
            existencias = Integer.parseInt(existenciasTexto);
        } catch (NumberFormatException e) {
            callback.onError("El precio o las existencias no son validos");
            return;
        }

        if (precio < 0 || existencias < 0) {
            callback.onError("El precio y las existencias no pueden ser negativos");
            return;
        }

        modeloProducto.insertar(new eProducto(0, nombre, categoria, precio, existencias, descripcion), callback);
    }
}
