package com.example.flowertl.controller;

import android.content.Context;
import com.example.flowertl.model.ModeloUsuario;
import com.example.flowertl.Config.Configuracion;
import com.example.flowertl.Empty.eUsuario;

public class ControladorLogin {
    private ModeloUsuario modeloUsuario;
    private Configuracion configuracion;

    public ControladorLogin(Context contexto) {
        modeloUsuario = new ModeloUsuario(contexto);
        configuracion = new Configuracion("SesionUsuario", contexto);
    }

    public boolean autenticar(String nombreUsuario, String contrasena) {
        if (modeloUsuario.iniciarSesion(nombreUsuario, contrasena)) {
            eUsuario usuario = modeloUsuario.obtenerUsuarioPorNombre(nombreUsuario);
            if (usuario != null) {
                configuracion.establecerPreferencia("nombreUsuario", usuario.obtenerNombreUsuario());
                configuracion.establecerPreferencia("rol", usuario.obtenerRol());
                return true;
            }
        }
        return false;
    }

    public String obtenerRolUsuarioLogueado() {
        return configuracion.obtenerPreferencia("rol");
    }

    public void cerrarSesion() {
        configuracion.limpiar();
    }
}
