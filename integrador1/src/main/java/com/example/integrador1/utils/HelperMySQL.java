package com.example.integrador1.utils;

import com.example.integrador1.entity.Cliente;
import com.example.integrador1.entity.Factura;
import com.example.integrador1.entity.FacturaProducto;
import com.example.integrador1.entity.Producto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelperMySQL {

    private Connection conn = null;

    public HelperMySQL() throws Exception{//Constructor
        String driver = "com.mysql.cj.jdbc.Driver";
        String uri = "jdbc:mysql://localhost:3306/integrador1";

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (conn != null){
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void dropTables() throws SQLException {
        String dropFacturaProducto = "DROP TABLE IF EXISTS FacturaProducto";
        this.conn.prepareStatement(dropFacturaProducto).execute();
        this.conn.commit();

        String dropFactura = "DROP TABLE IF EXISTS Factura";
        this.conn.prepareStatement(dropFactura).execute();
        this.conn.commit();

        String dropProducto = "DROP TABLE IF EXISTS Producto";
        this.conn.prepareStatement(dropProducto).execute();
        this.conn.commit();

        String dropCliente = "DROP TABLE IF EXISTS Cliente";
        this.conn.prepareStatement(dropCliente).execute();
        this.conn.commit();

    }

    public void createTables() throws SQLException {
        String tableCliente = "CREATE TABLE IF NOT EXISTS Cliente(" +
                "idCliente INT NOT NULL, " +
                "Nombre VARCHAR(500), " +
                "email VARCHAR(500), " +
                "CONSTRAINT Cliente_pk PRIMARY KEY (idCliente));" ;
        this.conn.prepareStatement(tableCliente).execute();
        this.conn.commit();

        String tableProducto = "CREATE TABLE IF NOT EXISTS Producto(" +
                "idProducto INT NOT NULL, " +
                "nombre VARCHAR(500), " +
                "valor INT NOT NULL, " +
                "CONSTRAINT Producto_pk PRIMARY KEY (idProducto))";
        this.conn.prepareStatement(tableProducto).execute();
        this.conn.commit();

        String tableFactura = "CREATE TABLE IF NOT EXISTS Factura(" +
                "idFactura INT NOT NULL, " +
                "idCliente INT NOT NULL, " +
                "CONSTRAINT Factura_pk PRIMARY KEY (idFactura), "+
                "CONSTRAINT FK_idCliente FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente))";
        this.conn.prepareStatement(tableFactura).execute();
        this.conn.commit();

        String tableFacturaProducto = "CREATE TABLE IF NOT EXISTS FacturaProducto(" +
                "idFactura INT NOT NULL, " +
                "cantidad INT NOT NULL, " +
                "idProducto INT NOT NULL, " +
                "CONSTRAINT Factura_pk PRIMARY KEY (idFactura), "+
                "CONSTRAINT FK_idProducto FOREIGN KEY (idProducto) REFERENCES Producto (idProducto))";
        this.conn.prepareStatement(tableFacturaProducto).execute();
        this.conn.commit();
    }

    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "src\\main\\resources\\" + archivo;
        Reader in = new FileReader(path);
        String[] header = {};  // Puedes configurar tu encabezado personalizado aquí si es necesario
        CSVParser csvParser = CSVFormat.EXCEL.withHeader(header).parse(in);

        Iterable<CSVRecord> records = csvParser.getRecords();
        return records;
    }

    public void populateDB() throws Exception {
        try {
            System.out.println("Populating DB...");

            for(CSVRecord row : getData("clientes.csv")) {
                if(row.size() >= 3) { // Verificar que hay al menos 4 campos en el CSVRecord
                    String idCliente = row.get(0);
                    String nombre = row.get(1);
                    String email = row.get(2);
                    if(!idCliente.isEmpty() && !nombre.isEmpty() && !email.isEmpty()) {
                        try {
                            int id = Integer.parseInt(idCliente);
                            Cliente cliente = new Cliente(id, nombre, email);
                            insertCliente(cliente, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de dirección: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Clientes insertadas");

            for (CSVRecord row : getData("productos.csv")) {
                if (row.size() >= 3) { // Verificar que hay al menos 4 campos en el CSVRecord
                    String idProducto = row.get(0);
                    String nombre = row.get(1);
                    String valor = row.get(2);

                    if (!idProducto.isEmpty() && !nombre.isEmpty() && !valor.isEmpty()) {
                        try {
                            int id = Integer.parseInt(idProducto);
                            int valorProducto = Integer.parseInt(valor);

                            Producto producto = new Producto(id, nombre, valorProducto);
                            insertProducto(producto, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de persona: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Productos insertados");

            for (CSVRecord row : getData("facturas.csv")) {
                if (row.size() >= 2) { // Verificar que hay al menos 4 campos en el CSVRecord
                    String idFactura = row.get(0);
                    String idCliente = row.get(1);

                    if (!idFactura.isEmpty() && !idCliente.isEmpty()) {
                        try {
                            int idfactura = Integer.parseInt(idFactura);
                            int cliente = Integer.parseInt(idCliente);

                            Factura factura = new Factura(idfactura, cliente);
                            insertFactura(factura, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de persona: " + e.getMessage());
                        }
                    }
                }

            }

            System.out.println("Facturas insertadas");

            for (CSVRecord row : getData("facturas-productos.csv")) {
                if (row.size() >= 3) { // Verificar que hay al menos 4 campos en el CSVRecord
                    String idFactura = row.get(0);
                    String idCliente = row.get(1);
                    String cantidad = row.get(2);
                    if (!idFactura.isEmpty() && !idCliente.isEmpty()) {
                        try {
                            int idfactura = Integer.parseInt(idFactura);
                            int cliente = Integer.parseInt(idCliente);
                            int cantidades = Integer.parseInt(cantidad);
                            FacturaProducto facturaProducto = new FacturaProducto(idfactura, cliente, cantidades);
                            insertFacturaProducto(facturaProducto, conn);
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato en datos de persona: " + e.getMessage());
                        }
                    }
                }

            }

            System.out.println("Personas insertadas");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int insertFactura (Factura factura, Connection conn) throws Exception{
        String insert = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1,factura.getIdFactura());
            ps.setInt(2, factura.getIdCliente());

            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private int insertFacturaProducto (FacturaProducto facturaProducto, Connection conn) throws Exception{
        String insert = "INSERT INTO FacturaProducto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1,facturaProducto.getIdFactura());
            ps.setInt(2, facturaProducto.getIdProducto());
            ps.setInt(3, facturaProducto.getCantidad());

            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }


    private int insertProducto (Producto producto, Connection conn) throws Exception{
        String insert = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1,producto.getIdProducto());
            ps.setString(2, producto.getNombre());
            ps.setInt(3,producto.getValor());

            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }


    private int insertCliente(Cliente cliente, Connection conn) throws Exception {

        String insert = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insert);
            ps.setInt(1,cliente.getIdCliente());
            ps.setString(2, cliente.getNombre());
            ps.setString(3,cliente.getEmail());
            if (ps.executeUpdate() == 0) {
                throw new Exception("No se pudo insertar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePsAndCommit(conn, ps);
        }
        return 0;
    }

    private void closePsAndCommit(Connection conn, PreparedStatement ps) {
        if (conn != null){
            try {
                ps.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
