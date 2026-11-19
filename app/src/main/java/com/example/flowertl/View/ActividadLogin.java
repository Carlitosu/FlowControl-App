package com.example.flowertl.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flowertl.R;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Controller.ControladorLogin;
import com.example.flowertl.Empty.eUsuario;

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
            } else {
                btnIniciarSesion.setEnabled(false);
                controladorLogin.autenticar(usuario, clave, new RespuestaCallback<eUsuario>() {
                    @Override
                    public void onExito(eUsuario resultado) {
                        startActivity(new Intent(ActividadLogin.this, ActividadMenu.class));
                        finish();
                    }

                    @Override
                    public void onError(String mensaje) {
                        btnIniciarSesion.setEnabled(true);
                        Toast.makeText(ActividadLogin.this, mensaje, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
