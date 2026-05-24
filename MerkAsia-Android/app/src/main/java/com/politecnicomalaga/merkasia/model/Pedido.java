package com.politecnicomalaga.merkasia.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private int idPedido;
    private String dniCliente;
    private String fechaPedido;
    private int numLineas;
    private double totalPedido;
    private List<LineaPedido> lineas;

    public Pedido() {
        this.lineas = new ArrayList<>();
    }

    public Pedido(int idPedido, String dniCliente, String fechaPedido,
                  int numLineas, double totalPedido) {
        this.idPedido = idPedido;
        this.dniCliente = dniCliente;
        this.fechaPedido = fechaPedido;
        this.numLineas = numLineas;
        this.totalPedido = totalPedido;
        this.lineas = new ArrayList<>();
    }

    public void addLineaPedido(LineaPedido linea) {
        this.lineas.add(linea);
    }

    public int calcularNumLineas() {
        return this.lineas.size();
    }

    public double calcularTotal() {
        return this.lineas.stream()
            .mapToDouble(l -> l.getCantidad() * l.getPrecioUnitario())
            .sum();
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getNumLineas() {
        return numLineas;
    }

    public void setNumLineas(int numLineas) {
        this.numLineas = numLineas;
    }

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaPedido> lineas) {
        this.lineas = lineas;
    }
}
