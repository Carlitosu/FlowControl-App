package com.example.flowertl.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flowertl.R;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Controller.ControladorLogin;
import com.example.flowertl.Controller.ControladorPedido;
import com.example.flowertl.Empty.eDetallePedido;
import com.example.flowertl.Empty.ePedido;
import java.util.List;
import java.util.Locale;

public class ActividadPedidos extends AppCompatActivity {
    Button btnRegresar, btnCerrarSesion;
    RecyclerView rvPedidos;
    TextView lblVacio;
    ControladorLogin controladorLogin;
    ControladorPedido controladorPedido;
    AdaptadorPedidos adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        controladorLogin = new ControladorLogin(this);
        controladorPedido = new ControladorPedido();

        rvPedidos = findViewById(R.id.rvPedidos);
        lblVacio = findViewById(R.id.lblPedidosVacios);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        adaptador = new AdaptadorPedidos(this::mostrarOpciones);
        rvPedidos.setLayoutManager(new LinearLayoutManager(this));
        rvPedidos.setAdapter(adaptador);

        btnRegresar.setOnClickListener(v -> finish());

        btnCerrarSesion.setOnClickListener(v -> {
            controladorLogin.cerrarSesion();
            Intent intent = new Intent(this, ActividadLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarPedidos();
    }

    private void cargarPedidos() {
        controladorPedido.listar(new RespuestaCallback<List<ePedido>>() {
            @Override
            public void onExito(List<ePedido> pedidos) {
                adaptador.establecerDatos(pedidos);
                lblVacio.setVisibility(pedidos.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onError(String mensaje) {
                mostrar(mensaje);
            }
        });
    }

    private void mostrarOpciones(ePedido pedido) {
        String[] opciones = {
            getString(R.string.opcion_ver_detalle),
            getString(R.string.opcion_cambiar_estado),
            getString(R.string.opcion_eliminar)
        };
        new AlertDialog.Builder(this)
            .setTitle("Pedido #" + pedido.obtenerId())
            .setItems(opciones, (dialogo, posicion) -> {
                if (posicion == 0) {
                    verDetalle(pedido.obtenerId());
                } else if (posicion == 1) {
                    elegirEstado(pedido.obtenerId());
                } else {
                    confirmarEliminar(pedido.obtenerId());
                }
            })
            .setNegativeButton(R.string.btn_cancelar, null)
            .show();
    }

    private void verDetalle(int id) {
        controladorPedido.obtenerDetalle(id, new RespuestaCallback<ePedido>() {
            @Override
            public void onExito(ePedido pedido) {
                new AlertDialog.Builder(ActividadPedidos.this)
                    .setTitle(R.string.titulo_detalle_pedido)
                    .setMessage(construirDetalle(pedido))
                    .setPositiveButton(R.string.btn_cerrar, null)
                    .show();
            }

            @Override
            public void onError(String mensaje) {
                mostrar(mensaje);
            }
        });
    }

    private String construirDetalle(ePedido pedido) {
        StringBuilder texto = new StringBuilder();
        texto.append("Cliente: ").append(pedido.obtenerClienteNombre()).append("\n");
        texto.append("Fecha: ").append(pedido.obtenerFecha()).append("\n");
        texto.append("Estado: ").append(pedido.obtenerEstado()).append("\n\n");
        if (pedido.obtenerDetalles() != null) {
            for (eDetallePedido d : pedido.obtenerDetalles()) {
                texto.append(d.obtenerNombreProducto())
                     .append("  x")
                     .append(d.obtenerCantidad())
                     .append("  =  L. ")
                     .append(String.format(Locale.US, "%.2f", d.obtenerSubtotal()))
                     .append("\n");
            }
        }
        texto.append("\nTotal: L. ").append(String.format(Locale.US, "%.2f", pedido.obtenerTotal()));
        return texto.toString();
    }

    private void elegirEstado(int id) {
        new AlertDialog.Builder(this)
            .setTitle(R.string.opcion_cambiar_estado)
            .setItems(R.array.estados_pedido, (dialogo, posicion) -> {
                String estado = getResources().getStringArray(R.array.estados_pedido)[posicion];
                controladorPedido.cambiarEstado(id, estado, new RespuestaCallback<Void>() {
                    @Override
                    public void onExito(Void resultado) {
                        mostrar("Estado actualizado a " + estado);
                        cargarPedidos();
                    }

                    @Override
                    public void onError(String mensaje) {
                        mostrar(mensaje);
                    }
                });
            })
            .setNegativeButton(R.string.btn_cancelar, null)
            .show();
    }

    private void confirmarEliminar(int id) {
        new AlertDialog.Builder(this)
            .setTitle(R.string.titulo_eliminar_pedido)
            .setMessage(R.string.mensaje_eliminar_pedido)
            .setPositiveButton(R.string.btn_eliminar, (dialogo, which) ->
                controladorPedido.eliminar(id, new RespuestaCallback<Void>() {
                    @Override
                    public void onExito(Void resultado) {
                        mostrar("Pedido eliminado");
                        cargarPedidos();
                    }

                    @Override
                    public void onError(String mensaje) {
                        mostrar(mensaje);
                    }
                }))
            .setNegativeButton(R.string.btn_cancelar, null)
            .show();
    }

    private void mostrar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
