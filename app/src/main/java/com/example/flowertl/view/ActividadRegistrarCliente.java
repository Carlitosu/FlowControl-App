package com.example.flowertl.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flowertl.R;
import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Controller.ControladorCliente;
import com.example.flowertl.Controller.ControladorLogin;

public class ActividadRegistrarCliente extends AppCompatActivity {
    Button btnGuardar, btnRegresar, btnCerrarSesion;
    EditText txtNombre, txtIdentidad, txtTelefono, txtDireccion;
    ControladorLogin controladorLogin;
    ControladorCliente controladorCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);

        controladorLogin = new ControladorLogin(this);
        controladorCliente = new ControladorCliente();

        txtNombre = findViewById(R.id.txtNombreCliente);
        txtIdentidad = findViewById(R.id.txtIdentidadCliente);
        txtTelefono = findViewById(R.id.txtTelefonoCliente);
        txtDireccion = findViewById(R.id.txtDireccionCliente);
        btnGuardar = findViewById(R.id.btnGuardarCliente);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnGuardar.setOnClickListener(v -> {
            btnGuardar.setEnabled(false);
            controladorCliente.registrarCliente(
                txtNombre.getText().toString().trim(),
                txtIdentidad.getText().toString().trim(),
                txtTelefono.getText().toString().trim(),
                txtDireccion.getText().toString().trim(),
                new RespuestaCallback<IdRegistro>() {
                    @Override
                    public void onExito(IdRegistro resultado) {
                        btnGuardar.setEnabled(true);
                        Toast.makeText(ActividadRegistrarCliente.this, "Cliente registrado correctamente", Toast.LENGTH_SHORT).show();
                        txtNombre.setText("");
                        txtIdentidad.setText("");
                        txtTelefono.setText("");
                        txtDireccion.setText("");
                    }

                    @Override
                    public void onError(String mensaje) {
                        btnGuardar.setEnabled(true);
                        Toast.makeText(ActividadRegistrarCliente.this, mensaje, Toast.LENGTH_SHORT).show();
                    }
                });
        });

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
