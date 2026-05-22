package com.politecnicomalaga.merkasia.dataservice;


import com.politecnicomalaga.merkasia.model.Cliente;
import com.politecnicomalaga.merkasia.model.Pedido;
import com.politecnicomalaga.merkasia.model.Producto;

import java.sql.SQLException;
import java.util.List;

/**
 * Contract for all data persistence operations.
 * Decouples the {@link com.politecnicomalaga.merkasia.controller.Controller}
 * from any concrete storage implementation.
 */
public interface DataRepository {

    List<Producto> listProducts() throws SQLException, ClassNotFoundException;
    List<Producto> findProductCode(String code) throws SQLException, ClassNotFoundException;
    List<Cliente> findClientDNI(String dni) throws SQLException, ClassNotFoundException;
    List<Pedido> listProductsOrder(String dni, String pedido) throws SQLException, ClassNotFoundException;
    String importData(String jsonDataFromCSV) throws SQLException, ClassNotFoundException;
}
