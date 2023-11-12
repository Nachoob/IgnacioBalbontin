package com.example.ignaciobalbontin;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class daoUsuario {

    private SQLiteDatabase bd;

    // Constructor de la clase
    public daoUsuario(SQLiteDatabase bd) {
        this.bd = bd;
        // Crear tabla de usuarios si no existe
        String tablaUsuarios = "create table if not exists usuario(id integer primary key autoincrement, nombre text, contrasena text)";
        bd.execSQL(tablaUsuarios);
    }

    // Método para verificar si un usuario existe en la base de datos
    public boolean verificarUsuario(String nombre, String contrasena) {
        Cursor cursor = bd.rawQuery("SELECT * FROM usuario WHERE nombre = ? AND contrasena = ?", new String[]{nombre, contrasena});
        return cursor.getCount() > 0;
    }

    // Método para registrar un nuevo usuario en la base de datos
    public boolean registrarUsuario(String nombreUsuario, String contrasena) {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", nombreUsuario);
        contenedor.put("contrasena", contrasena);
        return (bd.insert("usuario", null, contenedor)) > 0;
    }
}
