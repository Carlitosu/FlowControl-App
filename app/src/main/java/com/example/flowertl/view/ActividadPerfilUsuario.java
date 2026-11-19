package com.example.flowertl.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flowertl.R;
import com.example.flowertl.controller.ControladorLogin;

import android.widget.TextView;
import com.example.flowertl.Config.Configuracion;

public class ActividadPerfilUsuario extends AppCompatActivity {
    Button btnRegresar, btnCerrarSesion;
    TextView lblNombreUsuario, lblRolUsuario;
    ControladorLogin controladorLogin;
    Configuracion configuracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        controladorLogin = new ControladorLogin(this);
        configuracion = new Configuracion("SesionUsuario", this);

        btnRegresar = findViewById(R.id.btnRegresar);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        lblNombreUsuario = findViewById(R.id.lblNombreUsuario);
        lblRolUsuario = findViewById(R.id.lblRolUsuario);

        // Mostrar datos de sesión
        lblNombreUsuario.setText(configuracion.obtenerPreferencia("nombreUsuario"));
        lblRolUsuario.setText("Rol: " + configuracion.obtenerPreferencia("rol"));

        btnRegresar.setOnClickListener(v -> finish());

        btnCerrarSesion.setOnClickListener(v -> {
            controladorLogin.cerrarSesion();
            Intent intent = new Intent(this, ActividadLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
