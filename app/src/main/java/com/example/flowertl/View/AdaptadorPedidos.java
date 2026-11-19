package com.example.flowertl.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flowertl.R;
import com.example.flowertl.Empty.ePedido;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.VistaPedido> {

    public interface AlElegirPedido {
        void onElegir(ePedido pedido);
    }

    private final AlElegirPedido alElegirPedido;
    private List<ePedido> pedidos = new ArrayList<>();

    public AdaptadorPedidos(AlElegirPedido alElegirPedido) {
        this.alElegirPedido = alElegirPedido;
    }

    public void establecerDatos(List<ePedido> nuevosPedidos) {
        pedidos = nuevosPedidos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VistaPedido onCreateViewHolder(@NonNull ViewGroup padre, int tipo) {
        View vista = LayoutInflater.from(padre.getContext()).inflate(R.layout.item_pedido, padre, false);
        return new VistaPedido(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull VistaPedido vista, int posicion) {
        ePedido pedido = pedidos.get(posicion);
        vista.lblCliente.setText(pedido.obtenerClienteNombre());
        vista.lblFecha.setText("#" + pedido.obtenerId() + "  ·  " + pedido.obtenerFecha());
        vista.lblTotal.setText(String.format(Locale.US, "L. %.2f", pedido.obtenerTotal()));
        vista.lblEstado.setText(pedido.obtenerEstado());
        vista.lblEstado.setTextColor(colorDeEstado(vista.itemView, pedido.obtenerEstado()));
        vista.itemView.setOnClickListener(v -> alElegirPedido.onElegir(pedido));
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    private int colorDeEstado(View vista, String estado) {
        if ("Entregado".equals(estado)) {
            return ContextCompat.getColor(vista.getContext(), R.color.verde_oscuro);
        }
        if ("Cancelado".equals(estado)) {
            return ContextCompat.getColor(vista.getContext(), R.color.rojo_error);
        }
        return ContextCompat.getColor(vista.getContext(), R.color.rosa_oscuro);
    }

    static class VistaPedido extends RecyclerView.ViewHolder {
        TextView lblCliente, lblFecha, lblTotal, lblEstado;

        VistaPedido(@NonNull View vista) {
            super(vista);
            lblCliente = vista.findViewById(R.id.lblClientePedido);
            lblFecha = vista.findViewById(R.id.lblFechaPedido);
            lblTotal = vista.findViewById(R.id.lblTotalPedido);
            lblEstado = vista.findViewById(R.id.lblEstadoPedido);
        }
    }
}
