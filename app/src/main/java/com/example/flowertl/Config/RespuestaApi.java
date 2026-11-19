package com.example.flowertl.Config;

// Formato de respuesta de todos los endpoints: {"message": "...", "success": true, "data": ...}
public class RespuestaApi<T> {
    public String message;
    public boolean success;
    public T data;
}
