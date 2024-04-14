package com.example.integrador1.dao;

import com.example.integrador1.entity.FacturaProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturaProductoDAO {
    private Connection conn;

    public FacturaProductoDAO(Connection conn) {
        this.conn = conn;
    }
    public void insertFacturaProducto(FacturaProducto facturaProducto) {
        String query = "INSERT INTO FacturaProducto (idFactura, idProducto,cantidad) VALUES (?, ?,?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, facturaProducto.getIdFactura()); // idcliente
            ps.setInt(2, facturaProducto.getIdProducto()); // nombre
            ps.setInt(2, facturaProducto.getCantidad()); // nombre
            ps.executeUpdate();
            System.out.println("factura insertada exitosamente.");
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

}
