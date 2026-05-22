package com.politecnicomalaga.tienda.controller;

import com.google.gson.Gson;
import com.politecnicomalaga.tienda.dataservice.BBDDAccess;

import java.sql.SQLException;
import java.util.Collections;

public class Controlador implements DataAccess {

    private static final Gson GSON = new Gson();

    private final BBDDAccess miBBDD;

    public Controlador() {
        miBBDD = new BBDDAccess();
    }

    //Implementar lógica definida en el interfaz DataAccess para que los Servlets soliciten lo que quieran

    @Override
    public String listAllProductos() {
        try {
            return GSON.toJson(miBBDD.listAllProductos());
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("listAllProductos", e);
        }
    }

    @Override
    public String findProductoXCodigo(String code) {
        try {
            return GSON.toJson(miBBDD.findProductoXCodigo(code));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("findProductoXCodigo", e);
        }
    }

    @Override
    public String findClienteXDNI(String dni) {
        try {
            return GSON.toJson(miBBDD.findClienteXDNI(dni));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("findClienteXDNI", e);
        }
    }

    @Override
    public String listProductosXPedido(String dni, String pedido) {
        try {
            return GSON.toJson(miBBDD.listProductosXPedido(dni, pedido));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("listProductosXPedido", e);
        }
    }

    @Override
    public String importData(String jsonDataFromCSV) {
        try {
            miBBDD.importData(jsonDataFromCSV);
            return okJson();
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("importData", e);
        }
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private String okJson() {
        return GSON.toJson(Collections.singletonMap("status", "OK"));
    }

    private String errorJson(String operation, Exception e) {
        return GSON.toJson(Collections.singletonMap("error", operation + ": " + e.getMessage()));
    }

}
