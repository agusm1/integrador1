package com.example.integrador1.dto;

public class ProductoDTO {

    private Integer idProducto;
    private String nombre;
    private Integer valor;

    public ProductoDTO(Integer idProducto, String nombre, Integer valor) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.valor = valor;
    }

    public ProductoDTO() {
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", valor=" + valor +
                '}';
    }
}
