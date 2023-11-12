package com.example.ignaciobalbontin;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Lista extends AppCompatActivity {

    daoAnimal dao;
    Adaptador adapter;
    ArrayList<Animal> lista;
    Animal c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        dao = new daoAnimal(Lista.this);
        lista = dao.verTodo();
        adapter= new Adaptador(this,lista,dao);
        ListView list = findViewById(R.id.lista);
        Button insertar = findViewById(R.id.btn_insertar);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Animal animalSeleccionado = lista.get(i);

                // Abre un cuadro de diálogo de confirmación
                mostrarConfirmacionBorrar(animalSeleccionado);
            }
        });
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Lista.this);
                dialog.setTitle("Nuevo Registro");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogo);
                dialog.show();
                final EditText nombre = dialog.findViewById(R.id.et_nombre);
                final EditText edad = dialog.findViewById(R.id.et_edad);
                final EditText raza = dialog.findViewById(R.id.et_raza);
                final EditText telefono = dialog.findViewById(R.id.et_telefono);
                final EditText tipoDeAnimal = dialog.findViewById(R.id.et_tipoDeAnimal);
                Button guardar = dialog.findViewById(R.id.btn_agregar);
                guardar.setText("Agregar");
                Button cancelar = dialog.findViewById(R.id.btn_cancelar);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            c = new Animal(nombre.getText().toString(),
                                    edad.getText().toString(),
                                    raza.getText().toString(),
                                    telefono.getText().toString(),
                                    tipoDeAnimal.getText().toString());
                            dao.insertar(c);
                            lista = dao.verTodo();
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }catch (Exception e){
                            Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void mostrarConfirmacionBorrar(final Animal animal) {
        final Dialog confirmDialog = new Dialog(Lista.this);
        confirmDialog.setTitle("Confirmar Borrado");
        confirmDialog.setCancelable(true);
        confirmDialog.setContentView(R.layout.item);
        confirmDialog.show();

        Button btnConfirmar = confirmDialog.findViewById(R.id.btn_eliminar);
        Button btnCancelar = confirmDialog.findViewById(R.id.btn_cancelar);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Borrar el elemento de la base de datos
                dao.eliminar(animal.getId());

                // Actualizar la lista y cerrar el cuadro de diálogo
                lista = dao.verTodo();
                adapter.notifyDataSetChanged();
                confirmDialog.dismiss();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
            }
        });
    }
}