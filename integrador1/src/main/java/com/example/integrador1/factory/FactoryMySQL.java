package com.example.integrador1.factory;

import com.example.integrador1.dao.ClienteDAO;
import com.example.integrador1.dao.FacturaDAO;
import com.example.integrador1.dao.FacturaProductoDAO;
import com.example.integrador1.dao.ProductoDAO;
import com.example.integrador1.entity.Factura;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryMySQL  extends AbstractFactory{

    private static FactoryMySQL instance = null;

    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String uri = "jdbc:mysql://localhost:3306/integrador1";
    public static Connection conn;

    private FactoryMySQL() {
    }

    public static synchronized FactoryMySQL getInstance() {
        if (instance == null) {
            instance = new FactoryMySQL();
        }
        return instance;
    }

    public static Connection createConnection() {
        if (conn != null) {
            return conn;
        }
        String driver = DRIVER;
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conn = DriverManager.getConnection(uri, "root", "");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClienteDAO getClienteDAO() {
        return new ClienteDAO(createConnection());
    }

    @Override
    public ProductoDAO getProductoDAO() {
        return new ProductoDAO(createConnection());
    }

    @Override
    public FacturaDAO getFacturaDAO() {
        return new FacturaDAO(createConnection());
    }

    @Override
    public FacturaProductoDAO getFacturaProductoDAO() {return new FacturaProductoDAO(createConnection());
    }
}
