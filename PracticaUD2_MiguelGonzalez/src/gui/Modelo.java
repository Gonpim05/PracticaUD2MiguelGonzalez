package gui;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class Modelo {
    private String ip;
    private String user;
    private String password;
    private String adminPassword;
    private String dbName;

    private Connection conexion;

    public Modelo() {
        getPropValues();
    }

    public String getIp() { return ip; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
    public String getAdminPassword() { return adminPassword; }
    public Connection getConexion() { return conexion; }


    private void getPropValues() {
        Properties prop = new Properties();
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



    void conectar() {
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/" + dbName, user, password);
            System.out.println("Conectado con éxito a " + dbName);

        } catch (SQLException sqle) {

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

                // entrada base de datos
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

    void desconectar(){
        try {
            conexion.close();
            conexion=null;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    void insertarCamiseta(int idEnvio, String color, String material, String talla,
                                 boolean conTexto, String diseñoTexto, int cantidad, float precio) {

        // 1. Añadimos precio_camiseta a la lista de columnas y un ? más en VALUES
        String sentenciaSql = "INSERT INTO camiseta (idenvio, color, material, talla, con_texto, diseno_texto, cantidad, precio_camiseta) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);

            // 2. Asignamos los valores (ahora son 8 parámetros)
            sentencia.setInt(1, idEnvio);
            sentencia.setString(2, color);
            sentencia.setString(3, material);
            sentencia.setString(4, talla);
            sentencia.setBoolean(5, conTexto);
            sentencia.setString(6, diseñoTexto);
            sentencia.setInt(7, cantidad);
            sentencia.setFloat(8, precio);

            sentencia.executeUpdate();
            System.out.println("Camiseta añadida correctamente.");

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
    }

    void insertarTaza(int idEnvio, String color, String material, String tamano, String tipo_diseno, String metodo_diseno, int cantidad, float precio) {
        String sentenciaSql = "INSERT INTO taza (idenvio, color, material, tamano, tipo_diseno, metodo_diseno, cantidad, precio_taza) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idEnvio);
            sentencia.setString(2, color);
            sentencia.setString(3, material);
            sentencia.setString(4, tamano);
            sentencia.setString(5, tipo_diseno);
            sentencia.setString(6, metodo_diseno);
            sentencia.setInt(7, cantidad);
            sentencia.setFloat(8, precio);

            sentencia.executeUpdate();
            System.out.println("Taza añadida correctamente.");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
    }

    void insertarLlavero(int idEnvio, String color, String material, String forma, int cantidad, float precio) {
        String sentenciaSql = "INSERT INTO llavero (idenvio, color, material, forma, cantidad, precio_llavero) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idEnvio);
            sentencia.setString(2, color);
            sentencia.setString(3, material);
            sentencia.setString(4, forma);
            sentencia.setInt(5, cantidad);
            sentencia.setFloat(6, precio);

            sentencia.executeUpdate();
            System.out.println("Llavero añadido correctamente");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
    }

    void modificarCamiseta(String color, String material, String talla, boolean conTexto, String diseno_texto, int cantidad, float precio, int idCamiseta) {

        String sentenciaSql = "UPDATE camiseta SET color = ?, material = ?, talla = ?, con_texto = ?, diseno_texto = ?, cantidad = ?, precio_camiseta = ? " +
                "WHERE idcamiseta = ?";

        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, color);
            sentencia.setString(2, material);
            sentencia.setString(3, talla);
            sentencia.setBoolean(4, conTexto);
            sentencia.setString(5, diseno_texto);
            sentencia.setInt(6, cantidad);
            sentencia.setFloat(7, precio);
            sentencia.setInt(8, idCamiseta);

            sentencia.executeUpdate();
            System.out.println("Camiseta modificada correctamente.");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        }
    }

    void modificarTaza(String color, String material, String tamano, String tipo_diseno, String metodo_diseno, int cantidad, float precio_taza, int idTaza){
        String sentenciaSql = "UPDATE taza SET color = ?, material = ?, tamano = ?, tipo_diseno = ?,  metodo_diseno = ?, cantidad = ?, precio_taza = ?" + " WHERE idTaza = ?";

        PreparedStatement sentencia = null;

        try{
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1,color);
            sentencia.setString(2,material);
            sentencia.setString(3,tamano);
            sentencia.setString(4,tipo_diseno);
            sentencia.setString(5,metodo_diseno);
            sentencia.setInt(6,cantidad);
            sentencia.setFloat(7,precio_taza);
            sentencia.setInt(8, idTaza);

            sentencia.executeUpdate();
            System.out.println("Taza modificada correctamente");

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }finally {
            if (sentencia != null){
                try {
                    sentencia.close();
                } catch (SQLException sqle){
                    sqle.printStackTrace();
                }
            }
        }
    }

    void modificarLlavero(String color, String material, String forma, int cantidad, float precio_llavero, int idLlavero){
        String sentenciaSql = "UPDATE llavero SET color = ?, material = ?, forma = ?, cantidad = ?, precio_llavero = ?" + "WHERE idLlavero = ?";

        PreparedStatement sentencia = null;

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1,color);
            sentencia.setString(2,material);
            sentencia.setString(3,forma);
            sentencia.setInt(4,cantidad);
            sentencia.setFloat(5,precio_llavero);
            sentencia.setInt(6,idLlavero);

            sentencia.executeUpdate();
            System.out.println("Llavero modificado correctamente");

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }finally {
            if (sentencia != null){
              try {
                  sentencia.close();
              }catch (SQLException sqle){
                  sqle.printStackTrace();
              }
            }
        }
    }

    void eliminarCamiseta(int idCamiseta){
        String sentenciaSql = "DELETE FROM camiseta WHERE idCamiseta = ?";
        PreparedStatement sentencia = null;

        try{
        sentencia =conexion.prepareStatement(sentenciaSql);
        sentencia.setInt(1,idCamiseta);
        sentencia.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }finally {
            if (sentencia!=null){
                try{
                    sentencia.close();
                }catch (SQLException sqle){
                    sqle.printStackTrace();
                }
            }
        }
    }

    void eliminarTaza(int idTaza){
        String sentenciaSql = "DELETE FROM taza WHERE idTaza = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1,idTaza);
            sentencia.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }finally {
            if (sentencia!=null){
                try {
                    sentencia.close();
                }catch (SQLException sqle){
                    sqle.printStackTrace();
                }
            }
        }
    }

    void eliminarLlavero(int idLlavero){
        String sentenciaSql = "DELETE FROM llavero WHERE idLlavero = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia=conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1,idLlavero);
            sentencia.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }finally {
            if (sentencia!=null){
                try {
                    sentencia.close();
                }catch (SQLException sqle){
                    sqle.printStackTrace();
                }
            }
        }
    }




}