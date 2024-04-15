package com.example.integrador1.dao;

import com.example.integrador1.dto.ProductoDTO;
import com.example.integrador1.entity.Producto;
import com.example.integrador1.factory.FactoryMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO {
    private Connection conn;

    public ProductoDAO(Connection conn) {
        this.conn = conn;
    }
    public void insertProducto(Producto producto) throws Exception{
        String query = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?,?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, producto.getIdProducto()); // idcliente
            ps.setString(2, producto.getNombre()); // nombre
            ps.setInt(2, producto.getValor()); // nombre
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
    public ProductoDTO productoConMasRecaudacion() throws SQLException{
        ProductoDTO productoDTO = null;
        this.conn = FactoryMySQL.createConnection();
        String query = "SELECT p.*, SUM(p.valor * fp.cantidad) as total " +
                "FROM Producto p JOIN FacturaProducto fp ON (p.idProducto = fp.idProducto) "+
                "WHERE p.idProducto = fp.idProducto "+
                "GROUP BY p.idProducto "+
                "ORDER BY total DESC "+
                "LIMIT 1";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            productoDTO = new ProductoDTO(rs.getInt(1), rs.getString(2), rs.getInt(3));
        }
        //this.conn.close();
        return productoDTO;
    }
}
