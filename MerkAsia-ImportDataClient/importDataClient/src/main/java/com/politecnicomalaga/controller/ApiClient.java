package com.politecnicomalaga.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.politecnicomalaga.model.*;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

/**
 * Envía los datos del modelo al backend mediante OkHttp (POST JSON).
 *
 * URL base configurable. Adaptar según el dominio/puerto del servidor.
 */
public class ApiClient {

    private static final String BASE_URL  = "http://localhost:8080/tienda";
    private static final String ENDPOINT  = BASE_URL + "/importData";

    private final OkHttpClient httpClient = new OkHttpClient();
    private static final MediaType JSON   = MediaType.parse("application/json");

    // ── Métodos de envío ──────────────────────────────────────────────────────

    public void enviarClientes(List<Cliente> clientes) throws IOException {
        JsonArray array = new JsonArray();
        for (Cliente c : clientes) {
            JsonObject obj = new JsonObject();
            obj.put("dni",       c.getDni());
            obj.put("nombre",    c.getNombre());
            obj.put("apellidos", c.getApellidos());
            obj.put("email",     c.getEmail());
            obj.put("telefono",  c.getTelefono());
            obj.put("direccion", c.getDireccion());
            array.put(obj);
        }
        enviar("clientes", array);
    }

    public void enviarProductos(List<Producto> productos) throws IOException {
        JsonArray array = new JsonArray();
        for (Producto p : productos) {
            JsonObject obj = new JsonObject();
            obj.put("idProducto",    p.getIdProducto());
            obj.put("descripcion",   p.getDescripcion());
            obj.put("precioUnitario",p.getPrecioUnitario());
            array.put(obj);
        }
        enviar("productos", array);
    }

    public void enviarPedidos(List<Pedido> pedidos) throws IOException {
        JsonArray array = new JsonArray();
        for (Pedido p : pedidos) {
            JsonObject obj = new JsonObject();
            obj.put("idPedido",    p.getIdPedido());
            obj.put("dniCliente",  p.getDniCliente());
            obj.put("fechaPedido", p.getFechaPedido());
            obj.put("numLineas",   p.getNumLineas());
            obj.put("totalPedido", p.getTotalPedido());
            array.put(obj);
        }
        enviar("pedidos", array);
    }

    public void enviarLineas(List<LineaPedido> lineas) throws IOException {
        JsonArray array = new JsonArray();
        for (LineaPedido l : lineas) {
            JsonObject obj = new JsonObject();
            obj.add("idPedido",      l.getIdPedido());
            obj.put("idProducto",    l.getIdProducto());
            obj.put("cantidad",      l.getCantidad());
            obj.put("precioUnitario",l.getPrecioUnitario());
            // subtotal es columna generada en BD: no se envía
            array.put(obj);
        }
        enviar("lineas", array);
    }

    // ── Método genérico de POST ───────────────────────────────────────────────

    /**
     * Construye el payload {@code { "tipo": "...", "datos": [...] }}
     * y lo envía al servlet importData.
     *
     * Como se indica en el enunciado, al ser un POST sincrónico no hace
     * falta Callback ni enqueue: se usa {@code execute()} directamente.
     */
    private void enviar(String tipo, JsonArray datos) throws IOException {
        JsonObject payload = new JsonObject();
        payload.add("tipo",  tipo);
        payload.add("datos", datos);

        String json = payload.toString();

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
            .url(ENDPOINT)
            .post(body)
            .build();

        // POST síncrono: no hay Callback
        try (Response response = httpClient.newCall(request).execute()) {
            System.out.println("[" + tipo + "] HTTP " + response.code()
                + " → " + response.message());
        }
    }
}
