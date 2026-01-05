package gui;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class Modelo {
    private String ip;
    private String user;
    private String password;
    private String adminPassword;
    private String dbName; // Añadimos esta para tu BD específica

    private Connection conexion;

    public Modelo() {
        // Llamamos al método para cargar los datos del config.properties
        getPropValues();
    }

    // --- BLOQUE DE PROPIEDADES ---

    private void getPropValues() {
        Properties prop = new Properties();
        // Intentamos leer el archivo físico
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            prop.load(fis);

            // Asignamos los valores del archivo a tus variables
            this.ip = prop.getProperty("ip");
            this.user = prop.getProperty("user");
            this.password = prop.getProperty("pass");
            this.adminPassword = prop.getProperty("admin");
            this.dbName = prop.getProperty("db_name");

        } catch (IOException e) {
            System.err.println("Error: No se pudo leer config.properties");
            e.printStackTrace();
        }
    }

    // --- BLOQUE DE CONEXIÓN ---

    void conectar() {
        try {
            // INTENTO A: Conectar a la base de datos que ya debería existir
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/" + dbName, user, password);
            System.out.println("Conectado con éxito a " + dbName);

        } catch (SQLException sqle) {
            // INTENTO B: Si falla (porque no existe), conectamos a la raíz y la creamos
            try {
                conexion = DriverManager.getConnection(
                        "jdbc:mysql://" + ip + ":3306/", user, password);

                PreparedStatement statement = null;

                // Leemos tu archivo específico
                String code = leerFichero();

                // Separamos por los guiones como hace tu profesora
                String[] query = code.split("--");

                for (String aQuery : query) {
                    if (!aQuery.trim().isEmpty()) {
                        statement = conexion.prepareStatement(aQuery);
                        statement.executeUpdate();
                    }
                }

                if (statement != null) {
                    statement.close();
                }

                // Una vez creadas las tablas, entramos en la base de datos
                conexion.setCatalog(dbName);
                System.out.println("Base de datos creada desde el script.");

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String leerFichero() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("bdd_regalosPers.sql"));
        String linea;
        StringBuilder stringBuilder = new StringBuilder();
        while ((linea=reader.readLine())!=null){
            stringBuilder.append(linea);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }


    public String getIp() { return ip; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
    public String getAdminPassword() { return adminPassword; }
    public Connection getConexion() { return conexion; }
}