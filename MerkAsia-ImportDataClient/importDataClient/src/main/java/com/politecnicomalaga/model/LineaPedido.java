package com.politecnicomalaga.model;

public class LineaPedido {

    private int idLinea;
    private Pedido pedido;
    private Producto producto;
    private int cantidad;
    private Double precioUnitario;

    public LineaPedido() {
    }

    public LineaPedido(Pedido pedido, Producto producto, int cantidad, Double precioUnitario) {
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public LineaPedido(Pedido pedido, Producto producto, int cantidad, Double precioUnitario, int idLinea) {
        this(pedido, producto, cantidad, precioUnitario);
        this.idLinea = idLinea;
    }

    public int getIdPedido() {
        return pedido != null ? pedido.getIdPedido() : 0;
    }

    public int getIdProducto() {
        return producto != null ? producto.getIdProducto() : 0;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
