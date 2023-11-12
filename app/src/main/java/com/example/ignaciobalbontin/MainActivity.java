package com.example.ignaciobalbontin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pb1;
    private int contador = 0;
    private Timer timer;

    daoAnimal dao;
    daoUsuario dao2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb1 = findViewById(R.id.progressBar);

        // Inicializar daoAnimal
        dao = new daoAnimal(MainActivity.this);

        // Crear o abrir la base de datos
        SQLiteDatabase bd = openOrCreateDatabase("BDUsuarios", MODE_PRIVATE, null);

        // Inicializar daoUsuario con la instancia de SQLiteDatabase
        dao2 = new daoUsuario(bd);

        // Configuración del botón de inicio de sesión
        Button loginButton = findViewById(R.id.loginbtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Muestra el ProgressBar
                pb1.setVisibility(View.VISIBLE);

                // Inicializa el contador
                contador = 0;

                // Inicializa el Timer
                timer = new Timer();

                // Programa la tarea del Timer
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contador++;
                                pb1.setProgress(contador);

                                if (contador == 50) {
                                    // Cancela el Timer después de 5 segundos
                                    timer.cancel();

                                    // Oculta el ProgressBar
                                    pb1.setVisibility(View.GONE);

                                    // Continúa con la lógica de verificar usuario
                                    verificarUsuario();
                                }
                            }
                        });
                    }
                }, 100, 100);
            }
        });

        // Configuración del botón de registro
        Button registerButton = findViewById(R.id.registrarbtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Agregar lógica para abrir la actividad de registro
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });
    }

    private void verificarUsuario() {
        // Obtener el nombre de usuario y la contraseña desde los EditText
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        String usuario = usernameEditText.getText().toString();
        String contrasena = passwordEditText.getText().toString();

        // Verificar el usuario en la base de datos
        boolean usuarioValido = dao2.verificarUsuario(usuario, contrasena);

        // Si el usuario es válido, iniciar la actividad de la lista
        if (usuarioValido) {
            Intent intent = new Intent(MainActivity.this, Funciones.class);
            startActivity(intent);
            finish();  // Esto evitará que el usuario vuelva a esta actividad presionando el botón "Atrás"
        } else {
            // Mostrar un mensaje de error si las credenciales son inválidas
            Toast.makeText(MainActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
        }
    }
}

