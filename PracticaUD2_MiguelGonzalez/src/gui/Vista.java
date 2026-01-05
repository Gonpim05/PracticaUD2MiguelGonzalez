package gui;

import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private final static String TITULO = "Aplicación Tienda Regalos Personalizados";

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

    // Default Table Models
    DefaultTableModel dtmCamisetas;
    DefaultTableModel dtmTazas;
    DefaultTableModel dtmLlaveros;
    DefaultTableModel dtmEnvios;

    // Menu y Diálogos
    JMenuItem itemOpciones;
    JMenuItem itemDesconectar;
    JMenuItem itemSalir;
    JDialog adminPasswordDialog;
    JButton btnValidate;
    JPasswordField adminPassword;

    public Vista() {
        // Llama al constructor de JFrame pasándole el título
        super(TITULO);
        initFrame();
    }

    public void initFrame() {
        // Conecta el panel principal creado en el Designer
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Inicializamos los modelos de tabla (DTM) antes de que se vea la ventana
        setTableModels();

        // Configuramos el menú
        setMenu();

        this.pack();
        this.setSize(new Dimension(1000, 700)); // Ajusta según el tamaño que necesites
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");

        itemOpciones = new JMenuItem("Opciones");
        itemOpciones.setActionCommand("Opciones");

        itemDesconectar = new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");

        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");

        menu.add(itemOpciones);
        menu.add(itemDesconectar);
        menu.add(itemSalir);
        mbBar.add(menu);
        this.setJMenuBar(mbBar);
    }

    private void setTableModels() {
        // Inicializamos cada modelo y lo asignamos a su tabla correspondiente
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