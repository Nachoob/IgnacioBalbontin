package com.example.ignaciobalbontin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoAnimal {
    SQLiteDatabase bd;
    ArrayList<Animal> lista = new ArrayList<Animal>();
    Animal a;
    Context ct;
    String nombreBd = "BDAnimales";
    String tabla = "create table if not exists animal(id integer primary key autoincrement, nombre text, edad text, tipoDeAnimal text, raza text, telefono text)";

    public daoAnimal(Context c) {
        this.ct = c;
        bd = c.openOrCreateDatabase(nombreBd, Context.MODE_PRIVATE, null);

        // Crear tabla de animales
        bd.execSQL(tabla);


    }






    public boolean insertar(Animal a) {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", a.getNombre());
        contenedor.put("edad", a.getEdad());
        contenedor.put("tipoDeAnimal", a.getTipoDeAnimal());
        contenedor.put("raza", a.getRaza());
        contenedor.put("telefono", a.getTelefono());
        return (bd.insert("animal", null, contenedor)) > 0;
    }

    public boolean eliminar(int id) {
        return (bd.delete("animal", "id=" + id, null)) > 0;
    }

    public boolean editar(Animal a) {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", a.getNombre());
        contenedor.put("edad", a.getEdad());
        contenedor.put("tipoDeAnimal", a.getTipoDeAnimal());
        contenedor.put("raza", a.getRaza());
        contenedor.put("telefono", a.getTelefono());
        return (bd.update("animal", contenedor, "id=" + a.getId(), null)) > 0;
    }

    public ArrayList<Animal> verTodo() {
        lista.clear();
        Cursor cursor = bd.rawQuery("select * from animal", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                lista.add(new Animal(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5)));

            } while (cursor.moveToNext());
        }
        return lista;
    }

    public Animal verUno(int posicion) {
        Cursor cursor = bd.rawQuery("select * from animal", null);
        cursor.moveToPosition(posicion);
        a = new Animal(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5));
        return a;
    }
}

