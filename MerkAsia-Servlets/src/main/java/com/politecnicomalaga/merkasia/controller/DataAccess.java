package com.politecnicomalaga.merkasia.controller;

public interface DataAccess {
    String listProducts();
    String findProductCode(String code);
    String findClientDNI(String dni);
    String listProductsOrder(String dni, String pedido);
    String importData(String jsonDataFromCSV);
}
