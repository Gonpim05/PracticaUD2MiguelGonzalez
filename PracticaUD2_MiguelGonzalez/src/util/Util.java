package util;

import javax.swing.*;

public class Util {
    //mensaje de error
    public static void showErrorAlert(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje,"Error",JOptionPane.ERROR_MESSAGE);
    }
    //mensaje de aviso
    public static void showWarningAlert(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje,"Aviso",JOptionPane.WARNING_MESSAGE);
    }
    //mensaje de info
    public static void showInfoAlert(String mensaje) {
        JOptionPane.showMessageDialog(null,mensaje,"Información",JOptionPane.INFORMATION_MESSAGE);
    }
}