package com.politecnicomalaga.tienda.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int idPedido;
    private String dni_cliente;
    private LocalDate fechaPedido;
    private int numLineas;
    private Double totalPedido;
    private List<LineaPedido> lineas;

    public Pedido() {
        this.lineas = new ArrayList<>();
    }

    public Pedido(int idPedido, String dni_cliente, LocalDate fechaPedido, int numLineas, Double totalPedido) {
        this.idPedido = idPedido;
        this.dni_cliente = dni_cliente;
        this.fechaPedido = fechaPedido;
        this.numLineas = numLineas;
        this.totalPedido = totalPedido;
        this.lineas = new ArrayList<>();
    }

    public void addLinea(LineaPedido linea) {
        lineas.add(linea);
        linea.setPedido(this);
        numLineas = lineas.size();
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getNumLineas() {
        return numLineas;
    }

    public void setNumLineas(int numLineas) {
        this.numLineas = numLineas;
    }

    public Double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaPedido> lineas) {
        this.lineas = lineas;
    }
}
