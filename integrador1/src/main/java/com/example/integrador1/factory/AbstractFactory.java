package com.example.integrador1.factory;

import com.example.integrador1.dao.ClienteDAO;
import com.example.integrador1.dao.FacturaDAO;
import com.example.integrador1.dao.FacturaProductoDAO;
import com.example.integrador1.dao.ProductoDAO;
import com.example.integrador1.entity.Producto;

import java.sql.SQLException;

public abstract class AbstractFactory {
    public static final int MYSQL_JDBC = 1;
    public static final int DERBY_JDBC = 2;

    public abstract ClienteDAO getClienteDAO() throws SQLException;
    public abstract ProductoDAO getProductoDAO() throws SQLException;
    public abstract FacturaProductoDAO getFacturaProductoDAO() throws SQLException;
    public abstract FacturaDAO getFacturaDAO() throws SQLException;

    public static AbstractFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_JDBC : {
                return FactoryMySQL.getInstance();
            }
            case DERBY_JDBC: return null;
            default: return null;
        }
    }
}

