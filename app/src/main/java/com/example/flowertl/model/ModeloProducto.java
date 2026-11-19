package com.example.flowertl.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.flowertl.Config.AyudanteBaseDatos;
import com.example.flowertl.Empty.eProducto;
import java.util.ArrayList;
import java.util.List;

public class ModeloProducto {
    private AyudanteBaseDatos ayudante;

    public ModeloProducto(Context contexto) {
        ayudante = new AyudanteBaseDatos(contexto);
    }

    public long insertar(eProducto producto) {
        SQLiteDatabase db = ayudante.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", producto.obtenerNombre());
        valores.put("categoria", producto.obtenerCategoria());
        valores.put("precio", producto.obtenerPrecio());
        valores.put("stock", producto.obtenerExistencias());
        valores.put("descripcion", producto.obtenerDescripcion());

        return db.insert(AyudanteBaseDatos.TABLA_PRODUCTOS, null, valores);
    }

    public List<eProducto> obtenerTodos() {
        List<eProducto> lista = new ArrayList<>();
        SQLiteDatabase db = ayudante.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + AyudanteBaseDatos.TABLA_PRODUCTOS, null);

        if (cursor.moveToFirst()) {
            do {
                lista.add(new eProducto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getInt(4),
                    cursor.getString(5)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }
}
