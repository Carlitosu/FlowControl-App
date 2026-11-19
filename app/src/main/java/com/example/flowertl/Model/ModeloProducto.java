package com.example.flowertl.Model;

import com.example.flowertl.Config.ClienteApi;
import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Empty.eProducto;
import java.util.List;

public class ModeloProducto {

    public void obtenerTodos(RespuestaCallback<List<eProducto>> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().obtenerProductos(ClienteApi.parametros()),
            callback);
    }

    public void insertar(eProducto producto, RespuestaCallback<IdRegistro> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().registrarProducto(ClienteApi.parametros(
                "nombre", producto.obtenerNombre(),
                "categoria", producto.obtenerCategoria(),
                "precio", String.valueOf(producto.obtenerPrecio()),
                "existencias", String.valueOf(producto.obtenerExistencias()),
                "descripcion", producto.obtenerDescripcion())),
            callback);
    }
}
