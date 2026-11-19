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
import com.example.flowertl.Controller.ControladorLogin;
import com.example.flowertl.Controller.ControladorProducto;

public class ActividadRegistrarProducto extends AppCompatActivity {
    Button btnGuardar, btnRegresar, btnCerrarSesion;
    EditText txtNombre, txtCategoria, txtPrecio, txtExistencias, txtDescripcion;
    ControladorLogin controladorLogin;
    ControladorProducto controladorProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);

        controladorLogin = new ControladorLogin(this);
        controladorProducto = new ControladorProducto();

        txtNombre = findViewById(R.id.txtNombreProducto);
        txtCategoria = findViewById(R.id.txtCategoriaProducto);
        txtPrecio = findViewById(R.id.txtPrecioProducto);
        txtExistencias = findViewById(R.id.txtExistenciasProducto);
        txtDescripcion = findViewById(R.id.txtDescripcionProducto);
        btnGuardar = findViewById(R.id.btnGuardarProducto);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnGuardar.setOnClickListener(v -> {
            btnGuardar.setEnabled(false);
            controladorProducto.registrarProducto(
                txtNombre.getText().toString().trim(),
                txtCategoria.getText().toString().trim(),
                txtPrecio.getText().toString().trim(),
                txtExistencias.getText().toString().trim(),
                txtDescripcion.getText().toString().trim(),
                new RespuestaCallback<IdRegistro>() {
                    @Override
                    public void onExito(IdRegistro resultado) {
                        btnGuardar.setEnabled(true);
                        Toast.makeText(ActividadRegistrarProducto.this, "Producto registrado correctamente", Toast.LENGTH_SHORT).show();
                        txtNombre.setText("");
                        txtCategoria.setText("");
                        txtPrecio.setText("");
                        txtExistencias.setText("");
                        txtDescripcion.setText("");
                    }

                    @Override
                    public void onError(String mensaje) {
                        btnGuardar.setEnabled(true);
                        Toast.makeText(ActividadRegistrarProducto.this, mensaje, Toast.LENGTH_SHORT).show();
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
