package gui;

import com.github.lgooddatepicker.components.DatePicker;
import gui.base.enums.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private final static String TITULO = "Aplicación Tienda Regalos Personalizados";

    public OptionDialog optionDialog;

    // Camiseta
    JPanel JPanelCamiseta;
    JRadioButton siRadioButton;
    JRadioButton noRadioButton;
    JTextField dieseñoJTextField;
    JComboBox colorCamisetaCB;
    JComboBox materialCamisetaCB;
    JComboBox tallaCamisetaCB;
    JSlider slider1;
    JButton añadirCamisetaButton;
    JButton modificarCamisetaButton;
    JButton eliminarCamisetaButton;
    JTable tablaCamiseta;

    // Taza
    JPanel JPanelTaza;
    JRadioButton textoRadioButton;
    JRadioButton dibujoRadioButton;
    JRadioButton fotoRadioButton;
    JRadioButton inteligenciaArtificialRadioButton;
    JComboBox colorTazaCB;
    JComboBox materialTazaCB;
    JComboBox tamañoTazaCB;
    JSlider slider2;
    JButton añadirTazaButton;
    JButton modificarTazaButton;
    JButton eliminarTazaButton;
    JTable tablaTaza;

    // Llavero
    JPanel JPanelLlavero;
    JComboBox colorLlaveroCB;
    JComboBox materialLlaveroCB;
    JComboBox formaLlaveroCB;
    JSlider slider3;
    JButton añadirLlaveroButton;
    JButton modificarLlaveroButton;
    JButton eliminarLlaveroButton;
    JTable tablaLlavero;

    // Envio
    JPanel envio;
    JTextField DNITextField;
    JTextField nombreTextField;
    JTextField tfnTextField;
    JTextField comentarioTextField;
    JButton añadirEnvioButton;
    JButton modificarEnvioButton;
    JButton eliminarEnvioButton;
    JTable tablaEnvio;
    DatePicker datePicker;
    JLabel precioCamiseta;
    JLabel precioTaza;
    JLabel precioLlavero;

    // Default Table Models
    DefaultTableModel dtmCamisetas;
    DefaultTableModel dtmTazas;
    DefaultTableModel dtmLlaveros;
    DefaultTableModel dtmEnvios;

    // Menu y Diálogos
    JMenuItem itemOpciones;
    JMenuItem itemTicket;
    JMenuItem itemDesconectar;
    JMenuItem itemSalir;
    JDialog adminPasswordDialog;
    JButton btnValidate;
    JPasswordField adminPassword;



    public Vista() {
        super(TITULO);
        initFrame();
    }

    public void initFrame() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        this.optionDialog = new OptionDialog(this);


        this.adminPasswordDialog = new JDialog(this, "Validación Admin", true);
        this.adminPassword = new JPasswordField(20);
        this.btnValidate = new JButton("Entrar");
        this.btnValidate.setActionCommand("abrirOpciones");

        JPanel p = new JPanel(new FlowLayout());
        p.add(new JLabel("Contraseña:"));
        p.add(adminPassword);
        p.add(btnValidate);

        adminPasswordDialog.add(p);
        adminPasswordDialog.pack();
        adminPasswordDialog.setLocationRelativeTo(this);


        rellenarCombos();

        setTableModels();
        setMenu();

        this.pack();
        this.setSize(new Dimension(1000, 700));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void rellenarCombos() {
        // Camisetas
        colorCamisetaCB.setModel(new DefaultComboBoxModel<>(CamisetaColorENUM.values()));
        materialCamisetaCB.setModel(new DefaultComboBoxModel<>(CamisetaMaterialENUM.values()));
        tallaCamisetaCB.setModel(new DefaultComboBoxModel<>(CamisetaTallaENUM.values()));

        // Tazas
        colorTazaCB.setModel(new DefaultComboBoxModel<>(TazaColorENUM.values()));
        materialTazaCB.setModel(new DefaultComboBoxModel<>(TazaMaterialENUM.values()));
        tamañoTazaCB.setModel(new DefaultComboBoxModel<>(TazaTamañoENUM.values()));

        // Llaveros
        colorLlaveroCB.setModel(new DefaultComboBoxModel<>(LlaveroColorENUM.values()));
        materialLlaveroCB.setModel(new DefaultComboBoxModel<>(LlaveroMaterialENUM.values()));
        formaLlaveroCB.setModel(new DefaultComboBoxModel<>(LlaveroFormaENUM.values()));
    }

    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");

        itemOpciones = new JMenuItem("Opciones");
        itemOpciones.setActionCommand("Opciones");

        itemDesconectar = new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");

        itemTicket = new JMenuItem("Ticket");
        itemTicket.setActionCommand("Ticket");

        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");

        menu.add(itemOpciones);
        menu.add(itemDesconectar);
        menu.add(itemTicket);
        menu.add(itemSalir);
        mbBar.add(menu);
        this.setJMenuBar(mbBar);
    }

    private void setTableModels() {
        this.dtmCamisetas = new DefaultTableModel();
        this.tablaCamiseta.setModel(dtmCamisetas);

        this.dtmTazas = new DefaultTableModel();
        this.tablaTaza.setModel(dtmTazas);

        this.dtmLlaveros = new DefaultTableModel();
        this.tablaLlavero.setModel(dtmLlaveros);

        this.dtmEnvios = new DefaultTableModel();
        this.tablaEnvio.setModel(dtmEnvios);
    }
}