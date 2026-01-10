package gui;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import gui.OptionDialog;
import util.Util;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Vector;
import java.io.IOException;

public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {

    private Modelo modelo;
    private Vista vista;
    boolean refrescar;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo=modelo;
        this.vista=vista;
        modelo.conectar();

        if (modelo.getConexion() != null) {
            setOptions();
            addActionListeners(this);
            addItemListeners(this);
            addWindowListeners(this);
            refrescarTodo();
            iniciar();
        } else {
            Util.showErrorAlert("Error de conexión con la base de datos.");
        }
    }

    private void addActionListeners(ActionListener listener) {
        vista.añadirCamisetaButton.addActionListener(listener);
        vista.añadirCamisetaButton.setActionCommand("anadirCamiseta");
        vista.añadirTazaButton.addActionListener(listener);
        vista.añadirTazaButton.setActionCommand("anadirTaza");
        vista.añadirLlaveroButton.addActionListener(listener);
        vista.añadirLlaveroButton.setActionCommand("anadirLlavero");
        vista.añadirEnvioButton.addActionListener(listener);
        vista.añadirEnvioButton.setActionCommand("anadirEnvio");
        vista.eliminarCamisetaButton.addActionListener(listener);
        vista.eliminarCamisetaButton.setActionCommand("eliminarCamiseta");
        vista.eliminarTazaButton.addActionListener(listener);
        vista.eliminarTazaButton.setActionCommand("eliminarTaza");
        vista.eliminarLlaveroButton.addActionListener(listener);
        vista.eliminarLlaveroButton.setActionCommand("eliminarLlavero");
        vista.eliminarEnvioButton.addActionListener(listener);
        vista.eliminarEnvioButton.setActionCommand("eliminarEnvio");
        vista.modificarCamisetaButton.addActionListener(listener);
        vista.modificarCamisetaButton.setActionCommand("modificarCamiseta");
        vista.modificarTazaButton.addActionListener(listener);
        vista.modificarTazaButton.setActionCommand("modificarTaza");
        vista.modificarLlaveroButton.addActionListener(listener);
        vista.modificarLlaveroButton.setActionCommand("modificarLlavero");
        vista.modificarEnvioButton.addActionListener(listener);
        vista.modificarEnvioButton.setActionCommand("modificarEnvio");
        vista.optionDialog.guardarButton.addActionListener(listener);
        vista.optionDialog.guardarButton.setActionCommand("guardarOpciones");
        vista.itemOpciones.addActionListener(listener);
        vista.itemDesconectar.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);
        vista.btnValidate.addActionListener(listener);
        vista.itemTicket.addActionListener(listener);
    }

    private void addWindowListeners(WindowListener listener){
        vista.addWindowListener(listener);
    }

    void iniciar() {
        vista.tablaCamiseta.setCellSelectionEnabled(true);
        vista.tablaTaza.setCellSelectionEnabled(true);
        vista.tablaLlavero.setCellSelectionEnabled(true);
        vista.tablaEnvio.setCellSelectionEnabled(true);

        vista.tablaCamiseta.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vista.tablaTaza.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vista.tablaLlavero.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vista.tablaEnvio.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        vista.slider1.setMinimum(1);
        vista.slider1.setMaximum(10);
        vista.slider1.setMajorTickSpacing(1);
        vista.slider1.setPaintTicks(true);
        vista.slider1.setPaintLabels(true);

        vista.slider2.setMinimum(1);
        vista.slider2.setMaximum(10);
        vista.slider2.setMajorTickSpacing(1);
        vista.slider2.setPaintTicks(true);
        vista.slider2.setPaintLabels(true);

        vista.slider3.setMinimum(1);
        vista.slider3.setMaximum(10);
        vista.slider3.setMajorTickSpacing(1);
        vista.slider3.setPaintTicks(true);
        vista.slider3.setPaintLabels(true);

        // --- LÓGICA DE MULTIPLICACIÓN SIMPLE PARA SLIDERS ---
        vista.slider1.addChangeListener(e -> {
            float total = 15.0f * vista.slider1.getValue();
            vista.precioCamiseta.setText(String.valueOf(total));
        });

        vista.slider2.addChangeListener(e -> {
            float total = 10.0f * vista.slider2.getValue();
            vista.precioTaza.setText(String.valueOf(total));
        });

        vista.slider3.addChangeListener(e -> {
            float total = 5.0f * vista.slider3.getValue();
            vista.precioLlavero.setText(String.valueOf(total));
        });

        ListSelectionListener selectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablaCamiseta.getSelectionModel())) {
                        int row = vista.tablaCamiseta.getSelectedRow();
                        vista.colorCamisetaCB.setSelectedItem(vista.tablaCamiseta.getValueAt(row, 2).toString());
                        vista.materialCamisetaCB.setSelectedItem(vista.tablaCamiseta.getValueAt(row, 3).toString());
                        vista.tallaCamisetaCB.setSelectedItem(vista.tablaCamiseta.getValueAt(row, 4).toString());
                        boolean tieneTexto = Boolean.parseBoolean(vista.tablaCamiseta.getValueAt(row, 5).toString());
                        if (tieneTexto) vista.siRadioButton.setSelected(true); else vista.noRadioButton.setSelected(true);
                        vista.dieseñoJTextField.setText(vista.tablaCamiseta.getValueAt(row, 6).toString());
                        vista.slider1.setValue(Integer.parseInt(vista.tablaCamiseta.getValueAt(row, 7).toString()));
                        vista.precioCamiseta.setText(vista.tablaCamiseta.getValueAt(row, 8).toString());
                    }
                    if (e.getSource().equals(vista.tablaTaza.getSelectionModel())) {
                        int row = vista.tablaTaza.getSelectedRow();
                        vista.colorTazaCB.setSelectedItem(vista.tablaTaza.getValueAt(row, 2).toString());
                        vista.materialTazaCB.setSelectedItem(vista.tablaTaza.getValueAt(row, 3).toString());
                        vista.tamañoTazaCB.setSelectedItem(vista.tablaTaza.getValueAt(row, 4).toString());
                        String tipoDiseno = vista.tablaTaza.getValueAt(row, 5).toString();
                        if(tipoDiseno.equals("Texto")) vista.textoRadioButton.setSelected(true);
                        else if(tipoDiseno.equals("Dibujo")) vista.dibujoRadioButton.setSelected(true);
                        else if(tipoDiseno.equals("Foto")) vista.fotoRadioButton.setSelected(true);
                        vista.slider2.setValue(Integer.parseInt(vista.tablaTaza.getValueAt(row, 7).toString()));
                        vista.precioTaza.setText(vista.tablaTaza.getValueAt(row, 8).toString());
                    }
                    if (e.getSource().equals(vista.tablaLlavero.getSelectionModel())) {
                        int row = vista.tablaLlavero.getSelectedRow();
                        vista.colorLlaveroCB.setSelectedItem(vista.tablaLlavero.getValueAt(row, 2).toString());
                        vista.materialLlaveroCB.setSelectedItem(vista.tablaLlavero.getValueAt(row, 3).toString());
                        vista.formaLlaveroCB.setSelectedItem(vista.tablaLlavero.getValueAt(row, 4).toString());
                        vista.slider3.setValue(Integer.parseInt(vista.tablaLlavero.getValueAt(row, 5).toString()));
                        vista.precioLlavero.setText(vista.tablaLlavero.getValueAt(row, 6).toString());
                    }
                    if (e.getSource().equals(vista.tablaEnvio.getSelectionModel())) {
                        int row = vista.tablaEnvio.getSelectedRow();
                        vista.DNITextField.setText(vista.tablaEnvio.getValueAt(row, 1).toString());
                        vista.nombreTextField.setText(vista.tablaEnvio.getValueAt(row, 2).toString());
                        vista.tfnTextField.setText(vista.tablaEnvio.getValueAt(row, 3).toString());
                    }
                }
            }
        };
        vista.tablaCamiseta.getSelectionModel().addListSelectionListener(selectionListener);
        vista.tablaTaza.getSelectionModel().addListSelectionListener(selectionListener);
        vista.tablaLlavero.getSelectionModel().addListSelectionListener(selectionListener);
        vista.tablaEnvio.getSelectionModel().addListSelectionListener(selectionListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Opciones":
                vista.adminPasswordDialog.setVisible(true);
                break;

            case "abrirOpciones":
                if (String.valueOf(vista.adminPassword.getPassword()).equals(modelo.getAdminPassword())) {
                    vista.adminPassword.setText("");
                    vista.adminPasswordDialog.dispose();
                    vista.optionDialog.setVisible(true);
                } else {
                    Util.showErrorAlert("Contraseña incorrecta.");
                }
                break;

            case "guardarOpciones":
                modelo.setPropValues(vista.optionDialog.ipJTextField.getText(), vista.optionDialog.usuarioJTextField.getText(),
                        String.valueOf(vista.optionDialog.contraseñaJPasswordField.getPassword()),
                        String.valueOf(vista.optionDialog.contraseñaAdminJPasswordField.getPassword()));
                vista.optionDialog.dispose();
                break;

            case "anadirEnvio":
                java.time.LocalDate fechaLocal = vista.datePicker.getDate();
                if (comprobarEnvioVacio()) {
                    Util.showErrorAlert("Rellena todos los campos del cliente.");
                } else if (fechaLocal == null) {
                    Util.showErrorAlert("Por favor, selecciona una fecha para el pedido.");
                } else {

                    java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaLocal);

                    modelo.insertarEnvio(
                            vista.DNITextField.getText(),
                            vista.nombreTextField.getText(),
                            vista.tfnTextField.getText(),
                            vista.comentarioTextField.getText(),
                            fechaSQL
                    );

                    refrescarEnvio();
                    borrarCamposEnvio();

                    vista.datePicker.clear();
                }
                break;


            case "modificarEnvio":
                try {
                    int id = (Integer) vista.tablaEnvio.getValueAt(vista.tablaEnvio.getSelectedRow(), 0);
                    modelo.modificarEnvio(id, vista.DNITextField.getText(), vista.nombreTextField.getText(), vista.tfnTextField.getText(), vista.comentarioTextField.getText());
                    refrescarEnvio();
                } catch (Exception ex) { Util.showErrorAlert("Selecciona un envío"); }
                break;

            case "eliminarEnvio":
                try {
                    int id = (Integer) vista.tablaEnvio.getValueAt(vista.tablaEnvio.getSelectedRow(), 0);
                    modelo.eliminarEnvio(id);
                    refrescarTodo();
                } catch (Exception ex) { Util.showErrorAlert("Selecciona un envío"); }
                break;

            case "anadirCamiseta":
                try {
                    if (comprobarCamisetaVacia()) throw new IOException("Rellena los campos");
                    int fila = vista.tablaEnvio.getSelectedRow();
                    if (fila == -1) throw new IOException("Selecciona un envío");
                    int idEnvio = (Integer) vista.tablaEnvio.getValueAt(fila, 0);
                    float precio = Float.parseFloat(vista.precioCamiseta.getText().replace(",", "."));
                    modelo.insertarCamiseta(idEnvio, String.valueOf(vista.colorCamisetaCB.getSelectedItem()), String.valueOf(vista.materialCamisetaCB.getSelectedItem()),
                            String.valueOf(vista.tallaCamisetaCB.getSelectedItem()), vista.siRadioButton.isSelected(), vista.dieseñoJTextField.getText(), vista.slider1.getValue(), precio);
                    refrescarCamisetas();
                    borrarCamposCamiseta();
                } catch (IOException ex) { Util.showErrorAlert(ex.getMessage());
                } catch (Exception ex) { Util.showErrorAlert("Error de precio"); }
                break;

            case "modificarCamiseta":
                try {
                    int id = (Integer) vista.tablaCamiseta.getValueAt(vista.tablaCamiseta.getSelectedRow(), 0);
                    float p = Float.parseFloat(vista.precioCamiseta.getText().replace(",", "."));
                    modelo.modificarCamiseta(String.valueOf(vista.colorCamisetaCB.getSelectedItem()), String.valueOf(vista.materialCamisetaCB.getSelectedItem()),
                            String.valueOf(vista.tallaCamisetaCB.getSelectedItem()), vista.siRadioButton.isSelected(), vista.dieseñoJTextField.getText(), vista.slider1.getValue(), p, id);
                    refrescarCamisetas();
                } catch (Exception ex) { Util.showErrorAlert("Selecciona una camiseta"); }
                break;

            case "eliminarCamiseta":
                try {
                    int id = (Integer) vista.tablaCamiseta.getValueAt(vista.tablaCamiseta.getSelectedRow(), 0);
                    modelo.eliminarCamiseta(id);
                    refrescarCamisetas();
                } catch (Exception ex) { Util.showErrorAlert("Selecciona una camiseta"); }
                break;

            case "anadirTaza":
                try {
                    if (comprobarTazaVacia()) throw new IOException("Rellena los campos");
                    int fila = vista.tablaEnvio.getSelectedRow();
                    if (fila == -1) throw new IOException("Selecciona un envío");
                    float p = Float.parseFloat(vista.precioTaza.getText().replace(",", "."));
                    modelo.insertarTaza((Integer)vista.tablaEnvio.getValueAt(fila,0), String.valueOf(vista.colorTazaCB.getSelectedItem()), String.valueOf(vista.materialTazaCB.getSelectedItem()),
                            String.valueOf(vista.tamañoTazaCB.getSelectedItem()), obtenerTipoDisenoTaza(), "Sublimación", vista.slider2.getValue(), p);
                    refrescarTazas();
                    borrarCamposTaza();
                } catch (IOException ex) { Util.showErrorAlert(ex.getMessage());
                } catch (Exception ex) { Util.showErrorAlert("Error de precio"); }
                break;

            case "modificarTaza":
                try {
                    int id = (Integer) vista.tablaTaza.getValueAt(vista.tablaTaza.getSelectedRow(), 0);
                    float p = Float.parseFloat(vista.precioTaza.getText().replace(",", "."));
                    modelo.modificarTaza(String.valueOf(vista.colorTazaCB.getSelectedItem()), String.valueOf(vista.materialTazaCB.getSelectedItem()),
                            String.valueOf(vista.tamañoTazaCB.getSelectedItem()), obtenerTipoDisenoTaza(), "Sublimación", vista.slider2.getValue(), p, id);
                    refrescarTazas();
                } catch (Exception ex) { Util.showErrorAlert("Selecciona una taza"); }
                break;

            case "eliminarTaza":
                try {
                    int id = (Integer) vista.tablaTaza.getValueAt(vista.tablaTaza.getSelectedRow(), 0);
                    modelo.eliminarTaza(id);
                    refrescarTazas();
                } catch (Exception ex) { Util.showErrorAlert("Selecciona una taza"); }
                break;

            case "anadirLlavero":
                try {
                    if (comprobarLlaveroVacio()) throw new IOException("Rellena los campos");
                    int fila = vista.tablaEnvio.getSelectedRow();
                    if (fila == -1) throw new IOException("Selecciona un envío");
                    float p = Float.parseFloat(vista.precioLlavero.getText().replace(",", "."));
                    modelo.insertarLlavero((Integer)vista.tablaEnvio.getValueAt(fila,0), String.valueOf(vista.colorLlaveroCB.getSelectedItem()), String.valueOf(vista.materialLlaveroCB.getSelectedItem()),
                            String.valueOf(vista.formaLlaveroCB.getSelectedItem()), vista.slider3.getValue(), p);
                    refrescarLlaveros();
                    borrarCamposLlavero();
                } catch (IOException ex) { Util.showErrorAlert(ex.getMessage());
                } catch (Exception ex) { Util.showErrorAlert("Error de precio"); }
                break;

            case "modificarLlavero":
                try {
                    int id = (Integer) vista.tablaLlavero.getValueAt(vista.tablaLlavero.getSelectedRow(), 0);
                    float p = Float.parseFloat(vista.precioLlavero.getText().replace(",", "."));
                    modelo.modificarLlavero(String.valueOf(vista.colorLlaveroCB.getSelectedItem()), String.valueOf(vista.materialLlaveroCB.getSelectedItem()),
                            String.valueOf(vista.formaLlaveroCB.getSelectedItem()), vista.slider3.getValue(), p, id);
                    refrescarLlaveros();
                } catch (Exception ex) { Util.showErrorAlert("Selecciona un llavero"); }
                break;

            case "eliminarLlavero":
                try {
                    int id = (Integer) vista.tablaLlavero.getValueAt(vista.tablaLlavero.getSelectedRow(), 0);
                    modelo.eliminarLlavero(id);
                    refrescarLlaveros();
                } catch (Exception ex) { Util.showErrorAlert("Selecciona un llavero"); }
                break;

            case "Desconectar":
                modelo.desconectar();
                break;

            case "Salir":
                System.exit(0);
                break;

            case "Ticket":
                int filaSeleccionada = vista.tablaEnvio.getSelectedRow();

                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "Selecciona un envio de la tabla para generar su ticket.");
                } else {
                    try {
                        int idEnvio = (int) vista.tablaEnvio.getValueAt(filaSeleccionada, 0);
                        String[] datosTicket = modelo.obtenerDatosTicket(idEnvio);
                        PrintWriter writer = new PrintWriter("Ticket_Pedido_" + idEnvio + ".txt");

                        writer.println("       TICKET DE VENTA     ");
                        writer.println("ID PEDIDO: " + idEnvio);
                        writer.println("FECHA:     " + datosTicket[2]);
                        writer.println("CLIENTE:   " + datosTicket[1]);
                        writer.println("DNI:       " + datosTicket[0]);
                        writer.println("TOTAL " + datosTicket[3] + " €");
                        writer.close();

                        // 5. Avisar al usuario
                        JOptionPane.showMessageDialog(null, "Ticket generado con éxito en la carpeta del proyecto.");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al generar el ticket: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                break;
        }
    }

    // --- MÉTODOS DE REFRESCO Y APOYO ---
    private void refrescarTodo() {
     refrescarEnvio();
    refrescarCamisetas();
    refrescarTazas();
    refrescarLlaveros(); }
    private void refrescarEnvio() {
        try { vista.tablaEnvio.setModel(construirTableModel(modelo.consultarEnvios(), vista.dtmEnvios));
        } catch (SQLException e) {

        }
    }
    private void refrescarCamisetas() { try { vista.tablaCamiseta.setModel(construirTableModel(modelo.consultarCamisetas(), vista.dtmCamisetas)); } catch (SQLException e) {} }
    private void refrescarTazas() { try { vista.tablaTaza.setModel(construirTableModel(modelo.consultarTazas(), vista.dtmTazas)); } catch (SQLException e) {} }
    private void refrescarLlaveros() { try { vista.tablaLlavero.setModel(construirTableModel(modelo.consultarLlaveros(), vista.dtmLlaveros)); } catch (SQLException e) {} }

    private DefaultTableModel construirTableModel(ResultSet rs, DefaultTableModel dtm) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i <= metaData.getColumnCount(); i++) columnNames.add(metaData.getColumnName(i));
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) vector.add(rs.getObject(i));
            data.add(vector);
        }
        dtm.setDataVector(data, columnNames);
        return dtm;
    }

    private void setOptions() {
        vista.optionDialog.ipJTextField.setText(modelo.getIp());
        vista.optionDialog.usuarioJTextField.setText(modelo.getUser());
    }

    private void borrarCamposEnvio() { vista.DNITextField.setText(""); vista.nombreTextField.setText(""); vista.tfnTextField.setText(""); vista.comentarioTextField.setText(""); }
    private void borrarCamposCamiseta() { vista.dieseñoJTextField.setText(""); vista.slider1.setValue(1); vista.precioCamiseta.setText(""); }
    private void borrarCamposTaza() { vista.slider2.setValue(1); vista.precioTaza.setText(""); }
    private void borrarCamposLlavero() { vista.slider3.setValue(1); vista.precioLlavero.setText(""); }

    private boolean comprobarEnvioVacio() { return vista.DNITextField.getText().isEmpty(); }
    private boolean comprobarCamisetaVacia() { return vista.precioCamiseta.getText().isEmpty(); }
    private boolean comprobarTazaVacia() { return vista.precioTaza.getText().isEmpty(); }
    private boolean comprobarLlaveroVacio() { return vista.precioLlavero.getText().isEmpty(); }

    private String obtenerTipoDisenoTaza() {
        if (vista.textoRadioButton.isSelected()) return "Texto";
        if (vista.dibujoRadioButton.isSelected()) return "Dibujo";
        return "Foto";
    }

    @Override public void windowClosing(WindowEvent e) { System.exit(0); }
    @Override public void itemStateChanged(ItemEvent e) {}
    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}
    @Override public void valueChanged(ListSelectionEvent e) {}
    private void addItemListeners(Controlador c) {}
    private void addWindowListeners(Controlador c) {}
}