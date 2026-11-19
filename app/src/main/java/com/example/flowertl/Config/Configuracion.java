package com.example.flowertl.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class Configuracion {
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;
    private Context contexto;

    public Configuracion(String nombre, Context contexto) {
        this.contexto = contexto;
        this.preferencias = contexto.getSharedPreferences(nombre, Context.MODE_PRIVATE);
        this.editor = preferencias.edit();
    }

    public void establecerPreferencia(String clave, String valor) {
        editor.putString(clave, valor);
        editor.apply();
    }

    public String obtenerPreferencia(String clave) {
        return preferencias.getString(clave, "");
    }

    public void limpiar() {
        editor.clear();
        editor.apply();
    }
}
