package com.example.flowertl.Controller;

import android.content.Context;
import com.example.flowertl.Config.Configuracion;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Empty.eUsuario;
import com.example.flowertl.Model.ModeloUsuario;

public class ControladorLogin {
    private ModeloUsuario modeloUsuario;
    private Configuracion configuracion;

    public ControladorLogin(Context contexto) {
        modeloUsuario = new ModeloUsuario();
        configuracion = new Configuracion("SesionUsuario", contexto);
    }

    // La respuesta llega de forma asincrona desde la API
    public void autenticar(String nombreUsuario, String contrasena, RespuestaCallback<eUsuario> callback) {
        modeloUsuario.iniciarSesion(nombreUsuario, contrasena, new RespuestaCallback<eUsuario>() {
            @Override
            public void onExito(eUsuario usuario) {
                configuracion.establecerPreferencia("nombreUsuario", usuario.obtenerNombreUsuario());
                configuracion.establecerPreferencia("rol", usuario.obtenerRol());
                callback.onExito(usuario);
            }

            @Override
            public void onError(String mensaje) {
                callback.onError(mensaje);
            }
        });
    }

    public String obtenerRolUsuarioLogueado() {
        return configuracion.obtenerPreferencia("rol");
    }

    public String obtenerNombreUsuarioLogueado() {
        return configuracion.obtenerPreferencia("nombreUsuario");
    }

    public void cerrarSesion() {
        configuracion.limpiar();
    }
}
