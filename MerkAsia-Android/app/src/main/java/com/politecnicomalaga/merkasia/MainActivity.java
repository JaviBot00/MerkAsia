package com.politecnicomalaga.merkasia;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.politecnicomalaga.merkasia.controller.ClienteRVAdapter;
import com.politecnicomalaga.merkasia.controller.ProductoRVAdapter;
import com.politecnicomalaga.merkasia.model.Cliente;
import com.politecnicomalaga.merkasia.model.Producto;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.1.139:8888/MerkAsia-Servlets";
    private static final String URL_CLIENTES = BASE_URL + "/find-cliente";
    private static final String URL_PRODUCTOS = BASE_URL + "/list-productos";
    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnClients).setOnClickListener(v -> cargarClientes());
        findViewById(R.id.btnProducts).setOnClickListener(v -> cargarProductos());

    }

    // --- Clientes ---

    private void cargarClientes() {
        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(URL_CLIENTES).get().build();
                Response response = httpClient.newCall(request).execute();
                String json = response.body().string();
                Log.d("RESPUESTA", json); // ver en Logcat qué devuelve el servidor

                List<Cliente> clientes = gson.fromJson(json, new TypeToken<List<Cliente>>() {
                }.getType());

                runOnUiThread(() -> {
                    recyclerView.setAdapter(new ClienteRVAdapter(clientes));
                });

            } catch (IOException e) {
                runOnUiThread(() ->
                    Toast.makeText(this, "Error al cargar clientes", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    // --- Productos ---

    private void cargarProductos() {
        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(URL_PRODUCTOS).get().build();
                Response response = httpClient.newCall(request).execute();
                String json = response.body().string();

                List<Producto> productos = gson.fromJson(json, new TypeToken<List<Producto>>() {
                }.getType());

                runOnUiThread(() -> {
                    recyclerView.setAdapter(new ProductoRVAdapter(productos));
                });

            } catch (IOException e) {
                runOnUiThread(() ->
                    Toast.makeText(this, "Error al cargar productos", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}
