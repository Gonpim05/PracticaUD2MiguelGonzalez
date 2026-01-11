package gui;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class Modelo {
    private String ip;
    private String user;
    private String password;
    private String adminPassword;
    private String dbName = "bdd_regalosPers";

    private Connection conexion;

    public Modelo() {
        getPropValues();
    }

    public String getIp() {
        return ip; }
    public String getUser() {
        return user; }
    public String getPassword() {
        return password; }
    public String getAdminPassword() {
        return adminPassword; }
    public Connection getConexion() {
        return conexion; }

    void conectar() {
        String params = "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/" + dbName + params, user, password);
            System.out.println("Conectado con éxito a " + dbName);
        } catch (SQLException sqle) {
            System.out.println("La base de datos no existe. Intentando crearla...");
            try {
                // Conectamos a la raíz de MySQL para ejecutar el script SQL
                conexion = DriverManager.getConnection(
                        "jdbc:mysql://" + ip + ":3306/" + params, user, password);

                String code = leerFichero();
                String[] queries = code.split(";");
                Statement statement = conexion.createStatement();

                for (String aQuery : queries) {
                    if (!aQuery.trim().isEmpty()) {
                        statement.executeUpdate(aQuery);
                    }
                }

                statement.close();
                conexion.setCatalog(dbName);
                System.out.println("Base de datos y tablas creadas con éxito.");
            } catch (SQLException | IOException e) {
                System.out.println("ERROR CRÍTICO: No se pudo conectar ni crear la DB.");
                e.printStackTrace();
            }
        }
    }

    private String leerFichero() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("bdd_regalosPers.sql"));
        String linea;
        StringBuilder stringBuilder = new StringBuilder();
        while ((linea = reader.readLine()) != null) {
            stringBuilder.append(linea);
            stringBuilder.append(" ");
        }
        reader.close();
        return stringBuilder.toString();
    }

    void desconectar() {
        try {
            if (conexion != null) {
                conexion.close();
                conexion = null;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }



    public int insertarEnvio(String dni, String nombre, String tel, String coment, java.sql.Date fecha) {
        int idGenerado = -1;

        String sql = "INSERT INTO envios (dni_cliente, nombre_completo, telefono, comentario, fecha_pedido) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, tel);
            ps.setString(4, coment);
            ps.setDate(5, fecha); // Enviamos la fecha elegida

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) idGenerado = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGenerado;
    }

    void insertarCamiseta(int idEnvio, String color, String material, String talla,
                          boolean conTexto, String disenoTexto, int cantidad, float precio) {
        String sentenciaSql = "INSERT INTO camiseta (idenvio, color, material, talla, con_texto, diseno_texto, cantidad, precio_camisetas) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idEnvio);
            sentencia.setString(2, color);
            sentencia.setString(3, material);
            sentencia.setString(4, talla);
            sentencia.setBoolean(5, conTexto);
            sentencia.setString(6, disenoTexto);
            sentencia.setInt(7, cantidad);
            sentencia.setFloat(8, precio);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    void insertarTaza(int idEnvio, String color, String material, String tamano, String tipo_diseno, String metodo_diseno, int cantidad, float precio) {

        if (metodo_diseno.equalsIgnoreCase("Foto") || metodo_diseno.toLowerCase().contains("foto")) {
            metodo_diseno = "Foto";
        } else {
            metodo_diseno = "IA";
        }

        if (tipo_diseno.equalsIgnoreCase("Texto") || tipo_diseno.toLowerCase().contains("texto")) {
            tipo_diseno = "Texto";
        } else {
            tipo_diseno = "Dibujo";
        }

        String sentenciaSql = "INSERT INTO taza (idenvio, color, material, tamano, tipo_diseno, metodo_diseno, cantidad, precio_tazas) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    void insertarLlavero(int idEnvio, String color, String material, String forma, int cantidad, float precio) {
        String sentenciaSql = "INSERT INTO llavero (idenvio, color, material, forma, cantidad, precio_llaveros) VALUES (?, ?, ?, ?, ?, ?)";
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
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }



    void modificarEnvio(int idEnvio, String dni, String nombre, String tfn, String comentario) {
        String sentenciaSql = "UPDATE envios SET dni_cliente = ?, nombre_completo = ?, telefono = ?, comentario = ? WHERE idenvio = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, dni);
            sentencia.setString(2, nombre);
            sentencia.setString(3, tfn);
            sentencia.setString(4, comentario);
            sentencia.setInt(5, idEnvio);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    void modificarCamiseta(String color, String material, String talla, boolean conTexto, String diseno_texto, int cantidad, float precio, int idCamiseta) {
        String sentenciaSql = "UPDATE camiseta SET color = ?, material = ?, talla = ?, con_texto = ?, diseno_texto = ?, cantidad = ?, precio_camisetas = ? WHERE idcamiseta = ?";
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
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    void modificarTaza(String color, String material, String tamano, String tipo_diseno, String metodo_diseno, int cantidad, float precio_taza, int idTaza){
        String sentenciaSql = "UPDATE taza SET color = ?, material = ?, tamano = ?, tipo_diseno = ?,  metodo_diseno = ?, cantidad = ?, precio_tazas = ? WHERE idtaza = ?";
        PreparedStatement sentencia = null;
        try {
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
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null){
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    void modificarLlavero(String color, String material, String forma, int cantidad, float precio_llavero, int idLlavero){
        String sentenciaSql = "UPDATE llavero SET color = ?, material = ?, forma = ?, cantidad = ?, precio_llaveros = ? WHERE idllavero = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1,color);
            sentencia.setString(2,material);
            sentencia.setString(3,forma);
            sentencia.setInt(4,cantidad);
            sentencia.setFloat(5,precio_llavero);
            sentencia.setInt(6,idLlavero);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null){
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }



    void eliminarEnvio(int idEnvio) {
        String sentenciaSql = "DELETE FROM envios WHERE idenvio = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idEnvio);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null) {
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    void eliminarCamiseta(int idCamiseta){
        String sentenciaSql = "DELETE FROM camiseta WHERE idcamiseta = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1,idCamiseta);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null){
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    void eliminarTaza(int idTaza){
        String sentenciaSql = "DELETE FROM taza WHERE idtaza = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1,idTaza);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null){
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    void eliminarLlavero(int idLlavero){
        String sentenciaSql = "DELETE FROM llavero WHERE idllavero = ?";
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1,idLlavero);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null){
                try { sentencia.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
    // consulta

    ResultSet consultarEnvios() throws SQLException {
        String sentenciaSql = "SELECT * FROM envios";
        PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
        return sentencia.executeQuery();
    }

    ResultSet consultarResumenPedidos() throws SQLException {
        String sentenciaSql = "SELECT * FROM envios";
        PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
        return sentencia.executeQuery();
    }

    ResultSet consultarCamisetas() throws SQLException {
        String sentenciaSql = "SELECT * FROM camiseta";
        PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
        return sentencia.executeQuery();
    }

    ResultSet consultarTazas() throws SQLException {
        String sentenciaSql = "SELECT * FROM taza" ;

        PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
        return sentencia.executeQuery();
    }

    ResultSet consultarLlaveros() throws SQLException {
        String sentenciaSql = "SELECT * FROM llavero";

        PreparedStatement sentencia = conexion.prepareStatement(sentenciaSql);
        return sentencia.executeQuery();
    }

    //configuracion

    private void getPropValues() {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            inputStream = new FileInputStream("config.properties");
            prop.load(inputStream);
            ip = prop.getProperty("ip");
            user = prop.getProperty("user");
            password = prop.getProperty("pass");
            adminPassword = prop.getProperty("admin");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try { if (inputStream != null) inputStream.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    void setPropValues(String ip, String user, String pass, String adminPass) {
        try {
            Properties prop = new Properties();
            prop.setProperty("ip", ip);
            prop.setProperty("user", user);
            prop.setProperty("pass", pass);
            prop.setProperty("admin", adminPass);
            OutputStream out = new FileOutputStream("config.properties");
            prop.store(out, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.ip = ip; this.user = user; this.password = pass; this.adminPassword = adminPass;
    }

    public float obtenerPresupuestoTotal(int idEnvio) {
        float total = 0;
        String sql = "{? = call calcular_total_envio(?)}";

        try (CallableStatement cs = conexion.prepareCall(sql)) {
            // El parámetro 1 es el valor de RETORNO (el total)
            cs.registerOutParameter(1, java.sql.Types.DECIMAL);

            // El parámetro 2 es la entrada (el ID)
            cs.setInt(2, idEnvio);

            cs.execute();

            // Recogemos el resultado del parámetro 1
            total = cs.getFloat(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public String[] obtenerDatosTicket(int idEnvio) {

        String[] datos = new String[4];


        String sqlDatos = "SELECT dni_cliente, nombre_completo, fecha_pedido FROM envios WHERE idenvio = ?";

        try {

            PreparedStatement ps = conexion.prepareStatement(sqlDatos);
            ps.setInt(1, idEnvio);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                datos[0] = rs.getString("dni_cliente");
                datos[1] = rs.getString("nombre_completo");
                datos[2] = rs.getString("fecha_pedido");


                float totalCalculado = obtenerPresupuestoTotal(idEnvio);
                datos[3] = String.format("%.2f", totalCalculado);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener datos para el ticket: " + e.getMessage());
            e.printStackTrace();
        }

        return datos;
    }
}