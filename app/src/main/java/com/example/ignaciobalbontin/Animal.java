package com.example.ignaciobalbontin;
public class Animal {
    int id;
    String nombre;
    String edad;
    String tipoDeAnimal;
    String raza;
    String telefono;

    public Animal() {
    }

    public Animal(String nombre, String edad, String tipoDeAnimal, String raza, String telefono) {
        this.nombre = nombre;
        this.edad = edad;
        this.tipoDeAnimal = tipoDeAnimal;
        this.raza = raza;
        this.telefono = telefono;
    }

    public Animal(int id, String nombre, String edad, String tipoDeAnimal, String raza, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.tipoDeAnimal = tipoDeAnimal;
        this.raza = raza;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTipoDeAnimal() {
        return tipoDeAnimal;
    }

    public void setTipoDeAnimal(String tipoDeAnimal) {
        this.tipoDeAnimal = tipoDeAnimal;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

