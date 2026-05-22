package com.politecnicomalaga.model;

public class Producto {

    private int idProducto;
    private String descripcion;
    private Double precioUnitario;

    public Producto() {
    }

    public Producto(int idProducto, String descripcion, Double precioUnitario) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int id) {
        this.idProducto = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
