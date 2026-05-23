package com.politecnicomalaga.merkasia.dataservice;

import com.google.gson.Gson;
import com.politecnicomalaga.merkasia.model.Cliente;
import com.politecnicomalaga.merkasia.model.LineaPedido;
import com.politecnicomalaga.merkasia.model.Pedido;
import com.politecnicomalaga.merkasia.model.Producto;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class CSVParser {

    // Mapa para evitar duplicados de clientes y productos
    private final Map<String, Cliente>  clientes  = new LinkedHashMap<>();
    private final Map<Integer, Producto> productos = new LinkedHashMap<>();

    public List<Cliente> parsear(String rutaFichero) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(rutaFichero));
        String linea;
        Cliente clienteActual = null;
        Pedido pedidoActual  = null;

        while ((linea = br.readLine()) != null) {

            linea = linea.trim();

            // Línea vacía: fin del bloque del cliente actual
            if (linea.isEmpty()) {
                clienteActual = null;
                pedidoActual  = null;
                continue;
            }

            String[] campos = linea.split("#");

            if (campos[0].isEmpty()) {
                // --- LÍNEA DE PEDIDO ---
                // Formato: #id_producto#descripcion#cantidad#precio
                int    idProducto = Integer.parseInt(campos[1].trim());
                String desc       = campos[2].trim();
                int    cantidad   = Integer.parseInt(campos[3].trim());
                double precio     = Double.parseDouble(campos[4].trim());

                // Registrar producto si es nuevo
                Producto producto = productos.computeIfAbsent(
                    idProducto,
                    id -> new Producto(id, desc, precio)
                );

                // Crear linea y añadirla al pedido actual
                LineaPedido lineaPedido = new LineaPedido(pedidoActual, producto, cantidad, precio);
                pedidoActual.addLineaPedido(lineaPedido);

            } else {
                // --- CABECERA DE PEDIDO / CLIENTE ---
                // Formato: nombre#apellidos#dni#email#telefono#direccion
                //          #id_pedido#fecha#num_lineas#total
                String nombre    = campos[0].trim();
                String apellidos = campos[1].trim();
                String dni       = campos[2].trim();
                String email     = campos[3].trim();
                String telefono  = campos[4].trim();
                String direccion = campos[5].trim();
                int    idPedido  = Integer.parseInt(campos[6].trim());
                String fecha  = campos[7].trim();
                int    numLineas = Integer.parseInt(campos[8].trim());
                double total     = Double.parseDouble(campos[9].trim());

                // Registrar cliente si es nuevo (clave = DNI)
                clienteActual = clientes.computeIfAbsent(
                    dni,
                    d -> new Cliente(d, nombre, apellidos, email, telefono, direccion)
                );

                // Crear pedido y añadirlo al cliente
                pedidoActual = new Pedido(idPedido, dni, fecha, numLineas, total);
                clienteActual.addPedido(pedidoActual);
            }
        }

        br.close();
        return new ArrayList<>(clientes.values());
    }

    public Map<Integer, Producto> getProductos() {
        return productos;
    }
}
