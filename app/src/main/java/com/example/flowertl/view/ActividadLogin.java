package com.example.flowertl.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flowertl.R;
import com.example.flowertl.controller.ControladorLogin;

public class ActividadLogin extends AppCompatActivity {
    EditText txtNombreUsuario, txtContrasena;
    Button btnIniciarSesion;
    ControladorLogin controladorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtContrasena = findViewById(R.id.txtContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        controladorLogin = new ControladorLogin(this);

        btnIniciarSesion.setOnClickListener(v -> {
            String usuario = txtNombreUsuario.getText().toString();
            String clave = txtContrasena.getText().toString();

            if (usuario.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else if (controladorLogin.autenticar(usuario, clave)) {
                Intent intent = new Intent(this, ActividadMenu.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
