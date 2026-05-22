package com.politecnicomalaga.controller;

import com.politecnicomalaga.model.Cliente;
import com.politecnicomalaga.model.LineaPedido;
import com.politecnicomalaga.model.Pedido;
import com.politecnicomalaga.model.Producto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void importDataFromCSV(String csvFilePath) throws IOException {

        Map<String, Cliente> clientes = new HashMap<>();
        Map<String, Producto> productos = new HashMap<>();
        List<Pedido> pedidos = new ArrayList<>();
        List<LineaPedido> lineasPedido = new ArrayList<>();

        Pedido pedidoActual = null;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {

            String linea;
            while ((linea = br.readLine()) != null) {

                // Ignorar líneas vacías o solo espacios
                if (linea.isBlank()) continue;

                boolean esLineaProducto = linea.startsWith("#");

                // ── Línea de cliente + cabecera de pedido ─────────────────────
                // Formato: nombre#apellidos#dni#email#tlf#dir#idPedido#fecha#numLineas#total
                // Índices:   0      1        2   3     4   5   6        7     8         9
                if (!esLineaProducto) {
                    String[] datos = linea.split("#");

                    String nombre = datos[0];
                    String apellidos = datos[1];
                    String dni = datos[2];
                    String email = datos[3];
                    String telefono = datos[4];
                    String direccion = datos[5];

                    int idPedido = Integer.parseInt(datos[6]);
                    LocalDate fecha = LocalDate.parse(datos[7]);
                    int numLineas = Integer.parseInt(datos[8]);
                    double totalPedido = Double.parseDouble(datos[9]);

                    // Reutilizar cliente si ya existe (mismo DNI → varios pedidos)
                    Cliente cliente = clientes.computeIfAbsent(dni, d -> new Cliente(d, nombre, apellidos, email, telefono, direccion));

                    // Crear el pedido y vincularlo al cliente
                    pedidoActual = new Pedido(idPedido, dni, fecha, numLineas, totalPedido);
                    pedidos.add(pedidoActual);
                    cliente.addPedido(pedidoActual);

                    // ── Línea de producto / línea de pedido ───────────────────────
                    // Formato: #idProducto#descripcion#cantidad#precioUnitario
                    // Índices:  0(vacío)   1            2         3       4
                } else {
                    String[] datos = linea.split("#");
                    // datos[0] está vacío porque la línea empieza por '#'

                    int idProducto = Integer.parseInt(datos[1]);
                    String descripcion = datos[2];
                    int cantidad = Integer.parseInt(datos[3]);
                    double precioUnit = Double.parseDouble(datos[4]);

                    // Reutilizar producto si ya existe en el catálogo
                    Producto producto = productos.computeIfAbsent(String.valueOf(idProducto), id -> new Producto(idProducto, descripcion, precioUnit));

                    // Crear la línea y asociarla al pedido actual
                    if (pedidoActual != null) {
                        LineaPedido lp = new LineaPedido(pedidoActual, producto, cantidad, precioUnit);
                        lineasPedido.add(lp);
                        pedidoActual.addLinea(lp);
                    }
                }
            }
        }
        // El try-with-resources cierra el BufferedReader automáticamente

        // ── Envío al backend ──────────────────────────────────────────────────
        ApiClient api = new ApiClient();
        api.enviarClientes(new ArrayList<>(clientes.values()));
        api.enviarProductos(new ArrayList<>(productos.values()));
        api.enviarPedidos(pedidos);
        api.enviarLineas(lineasPedido);
    }
}
