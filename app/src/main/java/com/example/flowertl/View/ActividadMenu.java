package com.example.flowertl.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flowertl.R;
import com.example.flowertl.Controller.ControladorLogin;

public class ActividadMenu extends AppCompatActivity {
    LinearLayout btnRegProducto, btnRegCliente, btnCarrito, btnPerfil, btnRegUsuario, btnAcercaDe;
    Button btnCerrarSesion;
    ControladorLogin controladorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        controladorLogin = new ControladorLogin(this);

        btnRegProducto = findViewById(R.id.btnRegProducto);
        btnRegCliente = findViewById(R.id.btnRegCliente);
        btnCarrito = findViewById(R.id.btnCarrito);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnRegUsuario = findViewById(R.id.btnRegUsuario);
        btnAcercaDe = findViewById(R.id.btnAcercaDe);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnRegProducto.setOnClickListener(v -> startActivity(new Intent(this, ActividadRegistrarProducto.class)));
        btnRegCliente.setOnClickListener(v -> startActivity(new Intent(this, ActividadRegistrarCliente.class)));
        btnCarrito.setOnClickListener(v -> startActivity(new Intent(this, ActividadPedidos.class)));
        btnPerfil.setOnClickListener(v -> startActivity(new Intent(this, ActividadPerfilUsuario.class)));
        btnRegUsuario.setOnClickListener(v -> startActivity(new Intent(this, ActividadRegistrarUsuario.class)));
        btnAcercaDe.setOnClickListener(v -> startActivity(new Intent(this, ActividadAcercaDe.class)));

        btnCerrarSesion.setOnClickListener(v -> {
            controladorLogin.cerrarSesion();
            Intent intent = new Intent(this, ActividadLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
