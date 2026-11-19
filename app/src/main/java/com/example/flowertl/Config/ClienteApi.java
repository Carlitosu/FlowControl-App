package com.example.flowertl.Config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteApi {

    private static ServicioApi servicio;

    public static ServicioApi obtenerServicio() {
        if (servicio == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConfig.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            servicio = retrofit.create(ServicioApi.class);
        }
        return servicio;
    }

    // Arma los parametros de una llamada: credenciales de la API + pares clave, valor
    public static Map<String, String> parametros(String... pares) {
        Map<String, String> mapa = new HashMap<>();
        mapa.put("username", ApiConfig.API_USUARIO);
        mapa.put("password", ApiConfig.API_CONTRASENA);
        for (int i = 0; i < pares.length; i += 2) {
            mapa.put(pares[i], pares[i + 1] == null ? "" : pares[i + 1]);
        }
        return mapa;
    }

    public static <T> void ejecutar(Call<RespuestaApi<T>> llamada, RespuestaCallback<T> callback) {
        llamada.enqueue(new Callback<RespuestaApi<T>>() {
            @Override
            public void onResponse(Call<RespuestaApi<T>> call, Response<RespuestaApi<T>> respuesta) {
                RespuestaApi<T> cuerpo = respuesta.body();
                if (cuerpo == null) {
                    callback.onError(mensajeDeError(respuesta));
                } else if (cuerpo.success) {
                    callback.onExito(cuerpo.data);
                } else {
                    callback.onError(cuerpo.message);
                }
            }

            @Override
            public void onFailure(Call<RespuestaApi<T>> call, Throwable t) {
                callback.onError("No se pudo conectar con el servidor");
            }
        });
    }

    // Cuando el servidor responde con error HTTP (401, 409, etc.) el mensaje viene en el JSON del cuerpo
    private static String mensajeDeError(Response<?> respuesta) {
        try {
            if (respuesta.errorBody() != null) {
                JsonObject json = new Gson().fromJson(respuesta.errorBody().string(), JsonObject.class);
                if (json != null && json.has("message")) {
                    return json.get("message").getAsString();
                }
            }
        } catch (Exception e) {
            // Si el cuerpo no es JSON se usa el mensaje generico
        }
        return "Error del servidor (codigo " + respuesta.code() + ")";
    }
}
