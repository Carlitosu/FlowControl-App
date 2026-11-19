package com.example.flowertl.Config;

public interface RespuestaCallback<T> {
    void onExito(T resultado);
    void onError(String mensaje);
}
