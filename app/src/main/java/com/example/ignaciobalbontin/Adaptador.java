package com.example.ignaciobalbontin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Animal> lista;
    daoAnimal dao;
    Animal a;
    Activity activity;
    private int selectedPosition = -1;

    public Adaptador(Activity activity, ArrayList<Animal> lista, daoAnimal dao) {
        this.lista = lista;
        this.dao = dao;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        a = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        a = lista.get(i);
        return a.getId();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public long getSelectedId() {
        return getItemId(selectedPosition);
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item, null);
        }
        a = lista.get(posicion);
        TextView nombre = v.findViewById(R.id.nombre);
        TextView edad = v.findViewById(R.id.edad);
        TextView tipoDeAnimal = v.findViewById(R.id.tipoDeAnimal);
        TextView raza = v.findViewById(R.id.raza);
        TextView telefono = v.findViewById(R.id.telefono);
        Button editar = v.findViewById(R.id.btn_editar);
        Button eliminar = v.findViewById(R.id.btn_eliminar);
        nombre.setText(a.getNombre());
        edad.setText(String.valueOf(a.getEdad()));
        tipoDeAnimal.setText(a.getTipoDeAnimal());
        raza.setText(a.getRaza());
        telefono.setText(a.getTelefono());
        editar.setTag(posicion);
        eliminar.setTag(posicion);

        // ...

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                setSelectedPosition(pos);

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                final View dialogView = activity.getLayoutInflater().inflate(R.layout.dialogo, null);
                dialogBuilder.setView(dialogView);

                final EditText nombre = dialogView.findViewById(R.id.et_nombre);
                final EditText edad = dialogView.findViewById(R.id.et_edad);
                final EditText tipoDeAnimal = dialogView.findViewById(R.id.et_tipoDeAnimal);
                final EditText raza = dialogView.findViewById(R.id.et_raza);
                final EditText telefono = dialogView.findViewById(R.id.et_telefono);

                nombre.setText(a.getNombre());
                edad.setText(String.valueOf(a.getEdad()));
                tipoDeAnimal.setText(a.getTipoDeAnimal());
                raza.setText(a.getRaza());
                telefono.setText(a.getTelefono());

                // Obtener el botón personalizado del cuadro de diálogo
                Button btnConfirmar = dialogView.findViewById(R.id.btn_agregar);
                Button btnCancel = dialogView.findViewById(R.id.btn_cancelar);

                // Crear el cuadro de diálogo fuera del bloque del botón "Cancelar"
                final AlertDialog editarDialog = dialogBuilder.create();

                // Personalizar el botón "Confirmar"
                btnConfirmar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            a = new Animal((int) getSelectedId(), nombre.getText().toString(),
                                    edad.getText().toString(),
                                    tipoDeAnimal.getText().toString(), raza.getText().toString(),
                                    telefono.getText().toString());
                            dao.editar(a);
                            lista = dao.verTodo();
                            notifyDataSetChanged();
                            Toast.makeText(activity, "Registro editado con éxito", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(activity, "Error al editar el registro", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Personalizar el botón "Cancelar"
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Cerrar el cuadro de diálogo cuando se hace clic en "Cancelar"
                        editarDialog.dismiss();
                    }
                });

                editarDialog.show();
            }
        });

// ...


        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                setSelectedPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("¿Estás seguro de eliminar?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dao.eliminar((int) getSelectedId());
                                lista = dao.verTodo();
                                notifyDataSetChanged();
                                Toast.makeText(activity, "Registro eliminado con éxito", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // No hacer nada si el usuario elige no eliminar
                            }
                        }).show();
            }
        });

        return v;
    }
}
