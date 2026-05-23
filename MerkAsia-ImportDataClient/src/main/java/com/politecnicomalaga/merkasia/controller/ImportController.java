package com.politecnicomalaga.merkasia.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportController {

    private static final String BASE_URL  = "http://localhost:8080/tienda";
    private static final String ENDPOINT  = BASE_URL + "/importData";
    private final OkHttpClient  httpClient = new OkHttpClient();
    private static final MediaType JSON    = MediaType.parse("application/json");

    private final CSVParser parser = new CSVParser();
    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    public void importar(String rutaCSV) {
        try {
            // 1. Parsear el CSV
            List<Cliente> clientes = parser.parsear(rutaCSV);
            Map<Integer, Producto> productos = parser.getProductos();

            // 2. Construir el objeto a serializar
            // Se manda todo junto en un único JSON para que el servlet lo procese
            Map<String, Object> payload = new HashMap<>();
            payload.put("clientes", clientes);
            payload.put("productos", productos.values());

            String json = gson.toJson(payload);

            System.out.println("JSON generado:\n" + json);

            // 3. Enviar por HTTP POST
            enviar(json);

        } catch (IOException e) {
            System.err.println("Error al importar: " + e.getMessage());
        }
    }

    private void enviar(String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
            .url(ENDPOINT)
            .post(body)
            .build();

        Response response = httpClient.newCall(request).execute();

        System.out.println("Respuesta del servidor: " + response.code());
        response.close();
    }
}
