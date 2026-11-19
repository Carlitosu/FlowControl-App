package com.example.flowertl.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flowertl.R;
import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Controller.ControladorLogin;
import com.example.flowertl.Controller.ControladorUsuario;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class ActividadRegistrarUsuario extends AppCompatActivity {
    Button btnGuardar, btnRegresar, btnCerrarSesion;
    EditText txtUsuario, txtContrasena, txtNombreCompleto, txtCorreo;
    MaterialAutoCompleteTextView ddRol;
    ControladorLogin controladorLogin;
    ControladorUsuario controladorUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        controladorLogin = new ControladorLogin(this);
        controladorUsuario = new ControladorUsuario();

        txtUsuario = findViewById(R.id.txtNuevoUsuario);
        txtContrasena = findViewById(R.id.txtNuevaContrasena);
        txtNombreCompleto = findViewById(R.id.txtNombreCompletoUsuario);
        txtCorreo = findViewById(R.id.txtCorreoUsuario);
        ddRol = findViewById(R.id.ddRolUsuario);
        btnGuardar = findViewById(R.id.btnGuardarUsuario);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        String[] roles = getResources().getStringArray(R.array.roles_usuario);
        ddRol.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roles));
        ddRol.setText(roles[1], false);

        btnGuardar.setOnClickListener(v -> {
            btnGuardar.setEnabled(false);
            controladorUsuario.registrarUsuario(
                txtUsuario.getText().toString().trim(),
                txtContrasena.getText().toString(),
                txtNombreCompleto.getText().toString().trim(),
                txtCorreo.getText().toString().trim(),
                ddRol.getText().toString().trim(),
                new RespuestaCallback<IdRegistro>() {
                    @Override
                    public void onExito(IdRegistro resultado) {
                        btnGuardar.setEnabled(true);
                        Toast.makeText(ActividadRegistrarUsuario.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        txtUsuario.setText("");
                        txtContrasena.setText("");
                        txtNombreCompleto.setText("");
                        txtCorreo.setText("");
                        ddRol.setText(roles[1], false);
                    }

                    @Override
                    public void onError(String mensaje) {
                        btnGuardar.setEnabled(true);
                        Toast.makeText(ActividadRegistrarUsuario.this, mensaje, Toast.LENGTH_SHORT).show();
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
