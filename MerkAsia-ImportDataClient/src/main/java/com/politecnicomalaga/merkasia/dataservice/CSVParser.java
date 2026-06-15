package com.politecnicomalaga.merkasia.dataservice;

import com.politecnicomalaga.merkasia.model.Cliente;
import com.politecnicomalaga.merkasia.model.LineaPedido;
import com.politecnicomalaga.merkasia.model.Pedido;
import com.politecnicomalaga.merkasia.model.Producto;

import java.io.*;
import java.util.*;

public class CSVParser {

    private final Map<String, Cliente>   clientes  = new LinkedHashMap<>();
    private final Map<Integer, Producto> productos = new LinkedHashMap<>();

    public List<Cliente> parsear(String rutaFichero) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(rutaFichero));
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaFichero), StandardCharsets.UTF_8));
        String linea;
        Cliente clienteActual = null;
        Pedido  pedidoActual  = null;

        while ((linea = br.readLine()) != null) {

            linea = linea.trim();

            if (linea.isEmpty()) {
                clienteActual = null;
                pedidoActual  = null;
                continue;
            }

            String[] campos = linea.split("#");

            if (campos[0].isEmpty()) {
                // --- LÍNEA DE PEDIDO ---
                int    idProducto = Integer.parseInt(campos[1].trim());
                String desc       = campos[2].trim();
                int    cantidad   = Integer.parseInt(campos[3].trim());
                double precio     = Double.parseDouble(campos[4].trim());

                productos.computeIfAbsent(idProducto, id -> new Producto(id, desc, precio));

                LineaPedido lp = new LineaPedido(
                    pedidoActual.getIdPedido(), idProducto, cantidad, precio
                );
                pedidoActual.addLineaPedido(lp);

            } else {
                // --- CABECERA CLIENTE + PEDIDO ---
                String nombre    = campos[0].trim();
                String apellidos = campos[1].trim();
                String dni       = campos[2].trim();
                String email     = campos[3].trim();
                String telefono  = campos[4].trim();
                String direccion = campos[5].trim();
                int    idPedido  = Integer.parseInt(campos[6].trim());
                String fecha     = campos[7].trim();
                int    numLineas = Integer.parseInt(campos[8].trim());
                double total     = Double.parseDouble(campos[9].trim());

                clienteActual = clientes.computeIfAbsent(
                    dni, d -> new Cliente(d, nombre, apellidos, email, telefono, direccion)
                );

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
