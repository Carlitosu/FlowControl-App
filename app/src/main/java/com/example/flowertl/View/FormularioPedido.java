package com.example.flowertl.View;

import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.flowertl.R;
import com.example.flowertl.Config.IdRegistro;
import com.example.flowertl.Config.ItemPedidoEnvio;
import com.example.flowertl.Config.RespuestaCallback;
import com.example.flowertl.Controller.ControladorCliente;
import com.example.flowertl.Controller.ControladorLogin;
import com.example.flowertl.Controller.ControladorPedido;
import com.example.flowertl.Controller.ControladorProducto;
import com.example.flowertl.Empty.eCliente;
import com.example.flowertl.Empty.eProducto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

final class FormularioPedido {
    private final ActividadPedidos actividad;
    private final ControladorLogin sesion;
    private final ControladorPedido pedidos;
    private final Runnable actualizarLista;
    private boolean cargando;

    FormularioPedido(ActividadPedidos actividad, ControladorLogin sesion,
                     ControladorPedido pedidos, Runnable actualizarLista) {
        this.actividad = actividad;
        this.sesion = sesion;
        this.pedidos = pedidos;
        this.actualizarLista = actualizarLista;
    }

    void abrir() {
        if (cargando) return;
        cargando = true;
        new ControladorCliente().listarClientes(new RespuestaCallback<List<eCliente>>() {
            public void onExito(List<eCliente> clientes) {
                if (clientes == null || clientes.isEmpty()) {
                    errorCarga("Registra un cliente antes de crear un pedido");
                    return;
                }
                new ControladorProducto().listarProductos(new RespuestaCallback<List<eProducto>>() {
                    public void onExito(List<eProducto> productos) {
                        cargando = false;
                        if (actividad.isFinishing() || actividad.isDestroyed()) return;
                        List<eProducto> disponibles = new ArrayList<>();
                        if (productos != null) {
                            for (eProducto producto : productos) {
                                if (producto.obtenerExistencias() > 0) disponibles.add(producto);
                            }
                        }
                        if (disponibles.isEmpty()) {
                            mostrar("Agrega productos con existencias antes de crear un pedido");
                            return;
                        }
                        mostrarFormulario(clientes, disponibles);
                    }

                    public void onError(String mensaje) {
                        errorCarga(mensaje);
                    }
                });
            }

            public void onError(String mensaje) {
                errorCarga(mensaje);
            }
        });
    }

    private void mostrarFormulario(List<eCliente> clientes, List<eProducto> productos) {
        ScrollView scroll = new ScrollView(actividad);
        LinearLayout contenido = new LinearLayout(actividad);
        contenido.setOrientation(LinearLayout.VERTICAL);
        int margen = Math.round(24 * actividad.getResources().getDisplayMetrics().density);
        contenido.setPadding(margen, margen / 2, margen, margen / 2);
        scroll.addView(contenido);

        List<String> nombres = new ArrayList<>();
        for (eCliente cliente : clientes) {
            nombres.add(cliente.obtenerNombre() + " · " + cliente.obtenerIdentidad());
        }
        Spinner selector = new Spinner(actividad);
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(actividad,
            android.R.layout.simple_spinner_item, nombres);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selector.setAdapter(adaptador);
        etiqueta(contenido, "Cliente");
        contenido.addView(selector);

        List<EditText> cantidades = new ArrayList<>();
        etiqueta(contenido, "Indica la cantidad de cada producto que quieras pedir");
        for (eProducto producto : productos) {
            etiqueta(contenido, producto.obtenerNombre() + " · L. "
                + BigDecimal.valueOf(producto.obtenerPrecio()).setScale(2, java.math.RoundingMode.HALF_UP)
                + " · disponibles " + producto.obtenerExistencias());
            EditText cantidad = new EditText(actividad);
            cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
            cantidad.setHint("Cantidad");
            contenido.addView(cantidad);
            cantidades.add(cantidad);
        }

        AlertDialog dialogo = new AlertDialog.Builder(actividad)
            .setTitle(R.string.nuevo_pedido)
            .setView(scroll)
            .setNegativeButton(R.string.btn_cancelar, null)
            .setPositiveButton(R.string.btn_guardar, null)
            .show();
        Button guardar = dialogo.getButton(AlertDialog.BUTTON_POSITIVE);
        Button cancelar = dialogo.getButton(AlertDialog.BUTTON_NEGATIVE);
        guardar.setOnClickListener(v -> {
            List<ItemPedidoEnvio> items = new ArrayList<>();
            for (int i = 0; i < productos.size(); i++) {
                EditText campo = cantidades.get(i);
                String texto = campo.getText().toString().trim();
                if (texto.isEmpty()) continue;
                int cantidad;
                try {
                    cantidad = Integer.parseInt(texto);
                } catch (NumberFormatException e) {
                    campo.setError("Escribe una cantidad válida");
                    campo.requestFocus();
                    return;
                }
                eProducto producto = productos.get(i);
                if (cantidad < 0 || cantidad > producto.obtenerExistencias()) {
                    campo.setError("Cantidad fuera de las existencias disponibles");
                    campo.requestFocus();
                    return;
                }
                if (cantidad > 0) items.add(new ItemPedidoEnvio(producto.obtenerId(), cantidad));
            }
            guardar.setEnabled(false);
            cancelar.setEnabled(false);
            dialogo.setCancelable(false);
            int clienteId = clientes.get(selector.getSelectedItemPosition()).obtenerId();
            pedidos.registrar(clienteId, sesion.obtenerNombreUsuarioLogueado(), items,
                new RespuestaCallback<IdRegistro>() {
                    public void onExito(IdRegistro resultado) {
                        if (actividad.isFinishing() || actividad.isDestroyed()) return;
                        dialogo.dismiss();
                        mostrar("Pedido registrado correctamente");
                        actualizarLista.run();
                    }

                    public void onError(String mensaje) {
                        if (actividad.isFinishing() || actividad.isDestroyed()) return;
                        guardar.setEnabled(true);
                        cancelar.setEnabled(true);
                        dialogo.setCancelable(true);
                        mostrar(mensaje);
                    }
                });
        });
    }

    private void etiqueta(LinearLayout contenido, String texto) {
        TextView etiqueta = new TextView(actividad);
        etiqueta.setText(texto);
        contenido.addView(etiqueta);
    }

    private void errorCarga(String mensaje) {
        cargando = false;
        mostrar(mensaje);
    }

    private void mostrar(String mensaje) {
        if (!actividad.isFinishing() && !actividad.isDestroyed()) {
            Toast.makeText(actividad, mensaje, Toast.LENGTH_LONG).show();
        }
    }
}
