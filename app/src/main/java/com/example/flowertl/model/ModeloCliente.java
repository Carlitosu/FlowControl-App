package com.example.flowertl.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.flowertl.Config.AyudanteBaseDatos;
import com.example.flowertl.Empty.eCliente;
import java.util.ArrayList;
import java.util.List;

public class ModeloCliente {
    private AyudanteBaseDatos ayudante;

    public ModeloCliente(Context contexto) {
        ayudante = new AyudanteBaseDatos(contexto);
    }

    public long insertar(eCliente cliente) {
        SQLiteDatabase db = ayudante.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", cliente.obtenerNombre());
        valores.put("identidad", cliente.obtenerIdentidad());
        valores.put("telefono", cliente.obtenerTelefono());
        valores.put("direccion", cliente.obtenerDireccion());

        return db.insert(AyudanteBaseDatos.TABLA_CLIENTES, null, valores);
    }

    public List<eCliente> obtenerTodos() {
        List<eCliente> lista = new ArrayList<>();
        SQLiteDatabase db = ayudante.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + AyudanteBaseDatos.TABLA_CLIENTES, null);

        if (cursor.moveToFirst()) {
            do {
                lista.add(new eCliente(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }
}
