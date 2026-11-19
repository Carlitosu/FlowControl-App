package com.example.flowertl.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flowertl.R;
import com.example.flowertl.Controller.ControladorLogin;

public class ActividadRegistrarProducto extends AppCompatActivity {
    Button btnRegresar, btnCerrarSesion;
    ControladorLogin controladorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);

        controladorLogin = new ControladorLogin(this);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

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
