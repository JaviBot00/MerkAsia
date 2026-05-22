package com.politecnicomalaga.merkasia.controller;

import com.google.gson.Gson;
import com.politecnicomalaga.merkasia.dataservice.DataRepository;
import com.politecnicomalaga.merkasia.dataservice.DatabaseAccess;

import java.sql.SQLException;
import java.util.Collections;

public class Controller implements DataAccess {

    private static final Gson GSON = new Gson();

    private final DataRepository repository;

    /** Production constructor — uses the shared {@link DatabaseAccess} singleton. */
    public Controller() {
        this(DatabaseAccess.getInstance());
    }

    /**
     * Injectable constructor for testing or alternative data sources.
     *
     * @param repository data access implementation to use
     */
    public Controller(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public String listProducts() {
        try {
            return GSON.toJson(repository.listProducts());
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("listProducts", e);
        }
    }

    @Override
    public String findProductCode(String code) {
        try {
            return GSON.toJson(repository.findProductCode(code));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("findProductCode", e);
        }
    }

    @Override
    public String findClientDNI(String dni) {
        try {
            return GSON.toJson(repository.findClientDNI(dni));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("findClientDNI", e);
        }
    }

    @Override
    public String listProductsOrder(String dni, String pedido) {
        try {
            return GSON.toJson(repository.listProductsOrder(dni, pedido));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("listProductsOrder", e);
        }
    }

    @Override
    public String importData(String jsonDataFromCSV) {
        try {
            repository.importData(jsonDataFromCSV);
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
