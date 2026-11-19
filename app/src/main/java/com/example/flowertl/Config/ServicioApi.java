package com.example.flowertl.Config;

import com.example.flowertl.Empty.eCliente;
import com.example.flowertl.Empty.ePedido;
import com.example.flowertl.Empty.eProducto;
import com.example.flowertl.Empty.eUsuario;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

// Cada endpoint de FlowControlAPI es un POST con parametros de formulario
public interface ServicioApi {

    @FormUrlEncoded
    @POST("User/login")
    Call<RespuestaApi<eUsuario>> iniciarSesion(@FieldMap Map<String, String> campos);

    @FormUrlEncoded
    @POST("User/insertUser")
    Call<RespuestaApi<IdRegistro>> registrarUsuario(@FieldMap Map<String, String> campos);

    @FormUrlEncoded
    @POST("Product/getProducts")
    Call<RespuestaApi<List<eProducto>>> obtenerProductos(@FieldMap Map<String, String> campos);

    @FormUrlEncoded
    @POST("Product/insertProduct")
    Call<RespuestaApi<IdRegistro>> registrarProducto(@FieldMap Map<String, String> campos);

    @FormUrlEncoded
    @POST("Customer/getCustomers")
    Call<RespuestaApi<List<eCliente>>> obtenerClientes(@FieldMap Map<String, String> campos);

    @FormUrlEncoded
    @POST("Customer/insertCustomer")
    Call<RespuestaApi<IdRegistro>> registrarCliente(@FieldMap Map<String, String> campos);

    @FormUrlEncoded
    @POST("Pedido/getPedidos")
    Call<RespuestaApi<List<ePedido>>> obtenerPedidos(@FieldMap Map<String, String> campos);

    @FormUrlEncoded
    @POST("Pedido/getPedidoById")
    Call<RespuestaApi<ePedido>> obtenerPedidoPorId(@FieldMap Map<String, String> campos);

    @FormUrlEncoded
    @POST("Pedido/insertPedido")
    Call<RespuestaApi<IdRegistro>> registrarPedido(@FieldMap Map<String, String> campos);

    @FormUrlEncoded
    @POST("Pedido/updateEstado")
    Call<RespuestaApi<Void>> actualizarEstadoPedido(@FieldMap Map<String, String> campos);

    @FormUrlEncoded
    @POST("Pedido/deletePedido")
    Call<RespuestaApi<Void>> eliminarPedido(@FieldMap Map<String, String> campos);
}
