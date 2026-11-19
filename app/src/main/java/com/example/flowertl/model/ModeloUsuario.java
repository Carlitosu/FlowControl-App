package com.example.flowertl.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.flowertl.Config.AyudanteBaseDatos;
import com.example.flowertl.Empty.eUsuario;

public class ModeloUsuario {
    private AyudanteBaseDatos ayudante;

    public ModeloUsuario(Context contexto) {
        ayudante = new AyudanteBaseDatos(contexto);
    }

    public boolean iniciarSesion(String nombreUsuario, String contrasena) {
        SQLiteDatabase db = ayudante.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + AyudanteBaseDatos.TABLA_USUARIOS +
                " WHERE nombre_usuario=? AND contrasena=?", new String[]{nombreUsuario, contrasena});

        boolean exito = cursor.getCount() > 0;
        cursor.close();
        return exito;
    }

    public eUsuario obtenerUsuarioPorNombre(String nombreUsuario) {
        SQLiteDatabase db = ayudante.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + AyudanteBaseDatos.TABLA_USUARIOS +
                " WHERE nombre_usuario=?", new String[]{nombreUsuario});

        if (cursor.moveToFirst()) {
            eUsuario usuario = new eUsuario(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
            );
            cursor.close();
            return usuario;
        }
        cursor.close();
        return null;
    }

    public long registrar(eUsuario usuario) {
        SQLiteDatabase db = ayudante.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre_usuario", usuario.obtenerNombreUsuario());
        valores.put("contrasena", usuario.obtenerContrasena());
        valores.put("rol", usuario.obtenerRol());
        valores.put("correo", usuario.obtenerCorreo());

        return db.insert(AyudanteBaseDatos.TABLA_USUARIOS, null, valores);
    }
}
