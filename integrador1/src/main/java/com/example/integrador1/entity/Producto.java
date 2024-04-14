package com.example.integrador1.entity;

public class Producto {

    private Integer idProducto;
    private String nombre;
    private Integer valor;

    public Producto(Integer idProducto, String nombre, Integer valor) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.valor = valor;
    }

    public Producto() {
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", valor=" + valor +
                '}';
    }
}
