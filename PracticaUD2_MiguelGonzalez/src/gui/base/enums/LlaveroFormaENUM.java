package gui.base.enums;

public enum LlaveroFormaENUM {
    REDONDO("Redondo"),
    CUADRADO("Cuadrado");

    private String valor;

    LlaveroFormaENUM(String valor){
        this.valor=valor;
    }
    public String getValor(){
        return valor;
    }
}
