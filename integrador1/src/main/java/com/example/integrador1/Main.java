package com.example.integrador1;

import com.example.integrador1.dao.ClienteDAO;
import com.example.integrador1.dao.FacturaDAO;
import com.example.integrador1.dao.FacturaProductoDAO;
import com.example.integrador1.dao.ProductoDAO;
import com.example.integrador1.dto.ProductoDTO;
import com.example.integrador1.entity.Cliente;
import com.example.integrador1.utils.HelperMySQL;
import com.example.integrador1.factory.AbstractFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        /*HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();
        dbMySQL.createTables();
        dbMySQL.populateDB();
        dbMySQL.closeConnection();*/

        AbstractFactory chosenFactory = AbstractFactory.getDAOFactory(1);
        ClienteDAO cliente = chosenFactory.getClienteDAO();
        ProductoDAO producto = chosenFactory.getProductoDAO();
        FacturaDAO factura = chosenFactory.getFacturaDAO();
        FacturaProductoDAO facturaProducto = chosenFactory.getFacturaProductoDAO();
        System.out.println();
        System.out.println("////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////");
        ProductoDTO producMasVendido = producto.productoConMasRecaudacion();
        System.out.println(producMasVendido);
        System.out.println("////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////");
        List<Cliente> clientesxFacturacion = cliente.obtenerClientesOrdenadosPorFacturacion();
        for (Cliente clienteOrdenado : clientesxFacturacion) {
            System.out.println(clienteOrdenado);
            System.out.println("////////////////////////////////////////////");
        }

    }

}
