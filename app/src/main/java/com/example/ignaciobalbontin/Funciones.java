package com.example.ignaciobalbontin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Funciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funciones);
    }

    public void verAnimales(View view){
        Intent intent = new Intent(Funciones.this, Lista.class);
        startActivity(intent);
    }
    public void verDireccion(View view){
        Intent intent = new Intent(Funciones.this, Direccion.class);
        startActivity(intent);
    }

}