package gui.base.enums;

public enum tazaDiseñoENUM {
    TEXTO("Texto"),
    DIBUJO("Dibujo");

    private String valor;

    tazaDiseñoENUM(String valor){
        this.valor=valor;

    }
    public String getValor(){
        return valor;
    }
}
