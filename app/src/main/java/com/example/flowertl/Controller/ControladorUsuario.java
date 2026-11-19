package com.example.flowertl.Controller;

import android.util.Patterns;
import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Empty.eUsuario;
import com.example.flowertl.Model.ModeloUsuario;

public class ControladorUsuario {
    private ModeloUsuario modeloUsuario = new ModeloUsuario();

    public void registrarUsuario(String nombreUsuario, String contrasena, String nombreCompleto,
                                 String correo, String rol, RespuestaCallback<IdRegistro> callback) {
        if (nombreUsuario.isEmpty() || contrasena.isEmpty() || nombreCompleto.isEmpty() || rol.isEmpty()) {
            callback.onError("Usuario, contrasena, nombre completo y rol son obligatorios");
            return;
        }
        if (!correo.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            callback.onError("El correo no tiene un formato valido");
            return;
        }

        eUsuario usuario = new eUsuario();
        usuario.establecerNombreUsuario(nombreUsuario);
        usuario.establecerContrasena(contrasena);
        usuario.establecerNombreCompleto(nombreCompleto);
        usuario.establecerCorreo(correo);
        usuario.establecerRol(rol);
        modeloUsuario.registrar(usuario, callback);
    }
}
