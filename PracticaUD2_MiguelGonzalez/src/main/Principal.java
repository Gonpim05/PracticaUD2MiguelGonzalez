package main;
import gui.Controlador;
import gui.Modelo;
import gui.Vista;

public class Principal {
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo,vista);

        vista.setVisible(true);
    }
}
