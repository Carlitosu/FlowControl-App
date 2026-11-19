package com.example.flowertl.Model;

import com.example.flowertl.Config.ClienteApi;
import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Empty.eUsuario;

public class ModeloUsuario {

    public void iniciarSesion(String nombreUsuario, String contrasena, RespuestaCallback<eUsuario> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().iniciarSesion(
                ClienteApi.parametros("nombreUsuario", nombreUsuario, "contrasena", contrasena)),
            callback);
    }

    public void registrar(eUsuario usuario, RespuestaCallback<IdRegistro> callback) {
        ClienteApi.ejecutar(
            ClienteApi.obtenerServicio().registrarUsuario(ClienteApi.parametros(
                "nombreUsuario", usuario.obtenerNombreUsuario(),
                "contrasena", usuario.obtenerContrasena(),
                "nombreCompleto", usuario.obtenerNombreCompleto(),
                "correo", usuario.obtenerCorreo(),
                "rol", usuario.obtenerRol())),
            callback);
    }
}
