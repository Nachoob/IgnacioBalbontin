package com.example.ignaciobalbontin;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Registro extends AppCompatActivity {

    private daoUsuario dao;
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Obtener la instancia de SQLiteDatabase
        bd = openOrCreateDatabase("BDUsuarios", MODE_PRIVATE, null);

        // Inicializar la clase daoUsuario con la instancia de SQLiteDatabase
        dao = new daoUsuario(bd);

        // Configurar el botón de registro
        Button btnRegistrarse = findViewById(R.id.btn_registrarse);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        // Obtener los datos de usuario y contraseña desde los EditText
        EditText usernameEditText = findViewById(R.id.registro_username);
        EditText passwordEditText = findViewById(R.id.registro_password);

        String usuario = usernameEditText.getText().toString();
        String contrasena = passwordEditText.getText().toString();

        // Validar que se hayan ingresado datos
        if (!usuario.isEmpty() && !contrasena.isEmpty()) {
            // Verificar si el usuario ya existe
            if (dao.verificarUsuario(usuario, contrasena)) {
                Toast.makeText(Registro.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
            } else {
                // Registrar el nuevo usuario
                boolean registrado = dao.registrarUsuario(usuario, contrasena);

                if (registrado) {
                    Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                    // Iniciar la actividad principal después del registro
                    Intent intent = new Intent(Registro.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Esto evitará que el usuario vuelva a esta actividad presionando el botón "Atrás"
                } else {
                    Toast.makeText(Registro.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(Registro.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bd != null && bd.isOpen()) {
            bd.close();
        }
    }
}
