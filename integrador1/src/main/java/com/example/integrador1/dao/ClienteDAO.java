package com.example.integrador1.dao;

import com.example.integrador1.entity.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {
    private Connection conn;

    public ClienteDAO(Connection conn) {
        this.conn = conn;
    }
    public void insertCliente(Cliente cliente) {
        String query = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, cliente.getIdCliente()); // idcliente
            ps.setString(2, cliente.getNombre()); // nombre
            ps.setString(3, cliente.getEmail()); // edad
            ps.executeUpdate();
            System.out.println("cliente insertada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<Cliente> obtenerClientesOrdenadosPorFacturacion() {
        String query = "SELECT c.*, SUM(fp.cantidad) as total_facturacion " +
                "FROM Cliente c " +
                "JOIN Factura f ON c.idCliente = f.idCliente " +
                "JOIN FacturaProducto fp ON f.idFactura = fp.idFactura " +
                "GROUP BY c.idCliente " +
                "ORDER BY total_facturacion DESC" +
                "LIMIT: 10";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Cliente> clientesOrdenados = new ArrayList<>();

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");

                // Construir un objeto Cliente con los datos recuperados de la consulta
                Cliente cliente = new Cliente(idCliente, nombre, email);
                clientesOrdenados.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clientesOrdenados;
    }
}
