package com.politecnicomalaga.merkasia.controller;

public interface ControllerContract {
    String listProducts();
    String findProductCode(String code);
    String findClientDNI(String dni);
    String listClients();
    String listProductsOrder(String dni, String pedido);
    String importData(String jsonDataFromCSV);
}
