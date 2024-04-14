package com.example.integrador1.dto;

public class ClienteDTO {

    private Integer idCliente;
    private String mombre;
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Integer idCliente, String email, String mombre) {
        this.idCliente = idCliente;
        this.email = email;
        this.mombre = mombre;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getEmail() {
        return email;
    }

    public String getMombre() {
        return mombre;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "idCliente=" + idCliente +
                ", mombre='" + mombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
