package com.example.integrador1.dto;

public class ClienteDTO {

    private Integer idCliente;
    private String nombre;
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Integer idCliente, String nombre, String email) {
        this.idCliente = idCliente;
        this.email = email;
        this.nombre = nombre;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getEmail() {
        return email;
    }

    public String getMombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "idCliente=" + idCliente +
                ", mombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
