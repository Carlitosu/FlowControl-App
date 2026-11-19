package com.example.flowertl.Config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AyudanteBaseDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "FlowControl.db";
    private static final int VERSION_BASE_DATOS = 2;

    // Tablas
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String TABLA_PRODUCTOS = "productos";
    public static final String TABLA_CLIENTES = "clientes";
    public static final String TABLA_PEDIDOS = "pedidos";

    public AyudanteBaseDatos(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_BASE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla usuarios
        db.execSQL("CREATE TABLE " + TABLA_USUARIOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre_usuario TEXT, " +
                "contrasena TEXT, " +
                "rol TEXT, " +
                "correo TEXT)");

        // Crear tabla productos
        db.execSQL("CREATE TABLE " + TABLA_PRODUCTOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "categoria TEXT, " +
                "precio REAL, " +
                "stock INTEGER, " +
                "descripcion TEXT)");

        // Crear tabla clientes
        db.execSQL("CREATE TABLE " + TABLA_CLIENTES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "identidad TEXT, " +
                "telefono TEXT, " +
                "direccion TEXT)");

        // Crear tabla pedidos
        db.execSQL("CREATE TABLE " + TABLA_PEDIDOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_cliente INTEGER, " +
                "fecha TEXT, " +
                "total REAL, " +
                "estado TEXT, " +
                "FOREIGN KEY(id_cliente) REFERENCES " + TABLA_CLIENTES + "(id))");

        // Insertar usuario administrador por defecto
        db.execSQL("INSERT INTO " + TABLA_USUARIOS + " (nombre_usuario, contrasena, rol, correo) VALUES ('admin', 'admin123', 'Administrador', 'admin@flowers.lt')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PEDIDOS);
        onCreate(db);
    }
}
