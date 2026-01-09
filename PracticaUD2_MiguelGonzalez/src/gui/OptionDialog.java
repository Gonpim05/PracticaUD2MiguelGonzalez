package gui;

import javax.swing.*;
import java.awt.*;

public class OptionDialog extends JDialog {
    private JPanel panelPrincipal; 
    JTextField ipJTextField;
    JTextField usuarioJTextField;
    JPasswordField contraseñaJPasswordField;
    JPasswordField contraseñaAdminJPasswordField;
    JButton guardarButton;

    private Frame owner;

    public OptionDialog(Frame owner) {
        super(owner, "Configuración de Conexión", true);
        this.owner = owner;
        initDialog();
    }

    private void initDialog() {
        this.setContentPane(panelPrincipal);
        this.panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth() + 100, this.getHeight()));
        this.setLocationRelativeTo(owner);
    }
}