package com.politecnicomalaga.merkasia.dataservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.politecnicomalaga.merkasia.model.Cliente;
import com.politecnicomalaga.merkasia.model.LineaPedido;
import com.politecnicomalaga.merkasia.model.Pedido;
import com.politecnicomalaga.merkasia.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess implements DataRepository {

    //Aquí los métodos necesarios para CRUD de datos en la BBDD
    private static final String TABLE_CLIENTES = "clientes";
    private static final String TABLE_PRODUCTOS = "productos";
    private static final String TABLE_PEDIDOS = "pedidos";
    private static final String TABLE_LINEA_PEDIDO = "lineas_pedido";

    private static DatabaseAccess instance;

    public DatabaseAccess() {}

    public static synchronized DatabaseAccess getInstance() {
        if (instance == null) instance = new DatabaseAccess();
        return instance;
    }

    @Override
    public List<Producto> listProducts() throws SQLException, ClassNotFoundException {
        List<Producto> results = new ArrayList<>();
        String sql = "SELECT id_producto, descripcion, precio_unitario FROM " + TABLE_PRODUCTOS;
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) results.add(mapRowProducto(rs));
            }
        }
        return results;
    }

    @Override
    public List<Producto> findProductCode(String code) throws SQLException, ClassNotFoundException {
        List<Producto> results = new ArrayList<>();
        String sql = "SELECT id_producto, descripcion, precio_unitario FROM " + TABLE_PRODUCTOS + " WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) results.add(mapRowProducto(rs));
            }
        }
        return results;
    }

    @Override
    public List<Cliente> findClientDNI(String dni) throws SQLException, ClassNotFoundException {
        List<Cliente> results = new ArrayList<>();
        String sql = "SELECT dni, nombre, apellidos, email, telefono, direccion FROM " + TABLE_CLIENTES + " WHERE dni = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) results.add(mapRowCliente(rs));
            }
        }
        return results;
    }

    @Override
    public List<Pedido> listProductsOrder(String dni, String pedido) throws SQLException, ClassNotFoundException {
        List<Pedido> results = new ArrayList<>();
        String sql = "SELECT id_pedido, dni_cliente, fecha_pedido, num_lineas, total_pedido FROM " + TABLE_PEDIDOS
            + " WHERE dni_cliente = ? AND id_pedido = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.setString(2, pedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) results.add(mapRowPedido(rs));
            }
        }
        return results;
    }

    @Override
    public void importData(String jsonDataFromCSV) throws SQLException, ClassNotFoundException {
        Gson gson = new Gson();
        JsonObject root = JsonParser.parseString(jsonDataFromCSV).getAsJsonObject();

        // Parsear listas del JSON
        List<Producto> productos = gson.fromJson(root.get("productos"), new TypeToken<List<Producto>>(){}.getType());
        List<Cliente>  clientes  = gson.fromJson(root.get("clientes"),  new TypeToken<List<Cliente>>(){}.getType());

        try (Connection conn = DatabaseConnection.getConnection()) {
//            conn.setAutoCommit(false); // transacción: o todo o nada
            try {
                insertarProductos(conn, productos);
                for (Cliente cliente : clientes) {
                    insertarCliente(conn, cliente);
                    for (Pedido pedido : cliente.getPedidos()) {
                        insertarPedido(conn, pedido);
                        for (LineaPedido linea : pedido.getLineas()) {
                            insertarLinea(conn, linea);
                        }
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
            }
        }
    }

    // --- Métodos auxiliares INSERT---

    private void insertarProductos(Connection conn, List<Producto> productos) throws SQLException {
        String sql = "INSERT IGNORE INTO " + TABLE_PRODUCTOS + " (id_producto, descripcion, precio_unitario) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Producto p : productos) {
                ps.setInt(1, p.getIdProducto());
                ps.setString(2, p.getDescripcion());
                ps.setDouble(3, p.getPrecioUnitario());
                ps.executeUpdate();
            }
        }
    }

    private void insertarCliente(Connection conn, Cliente c) throws SQLException {
        String sql = "INSERT IGNORE INTO " + TABLE_CLIENTES + " (dni, nombre, apellidos, email, telefono, direccion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellidos());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getTelefono());
            ps.setString(6, c.getDireccion());
            ps.executeUpdate();
        }
    }

    private void insertarPedido(Connection conn, Pedido p) throws SQLException {
        String sql = "INSERT IGNORE INTO " + TABLE_PEDIDOS + " (id_pedido, dni_cliente, fecha_pedido, num_lineas, total_pedido) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdPedido());
            ps.setString(2, p.getDniCliente());
            ps.setString(3, p.getFechaPedido());
            ps.setInt(4, p.getNumLineas());
            ps.setDouble(5, p.getTotalPedido());
            ps.executeUpdate();
        }
    }

    private void insertarLinea(Connection conn, LineaPedido l) throws SQLException {
        String sql = "INSERT INTO " + TABLE_LINEA_PEDIDO + " (id_pedido, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, l.getIdPedido());
            ps.setInt(2, l.getIdProducto());
            ps.setInt(3, l.getCantidad());
            ps.setDouble(4, l.getPrecioUnitario());
            ps.executeUpdate();
        }
    }

    // --- Métodos auxiliares PARSER---

    private Producto mapRowProducto(ResultSet rs) throws SQLException {
        return new Producto(rs.getInt("id_producto"),
            rs.getString("descripcion"),
            rs.getDouble("precio_unitario"));
    }

    private Cliente mapRowCliente(ResultSet rs) throws SQLException {
        return new Cliente(rs.getString("dni"),
            rs.getString("nombre"),
            rs.getString("apellidos"),
            rs.getString("email"),
            rs.getString("telefono"),
            rs.getString("direccion"));
    }

    private Pedido mapRowPedido(ResultSet rs) throws SQLException {
        return new Pedido(rs.getInt("id_pedido"),
            rs.getString("dni_cliente"),
            rs.getString("fecha_pedido"),
            rs.getInt("num_lineas"),
            rs.getDouble("total_pedido"));
    }
}
