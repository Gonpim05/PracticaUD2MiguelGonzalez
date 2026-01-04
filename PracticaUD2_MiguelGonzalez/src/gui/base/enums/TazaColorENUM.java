package gui.base.enums;

public enum TazaColorENUM {
    AMARILLO("Amarillo"),
    VERDE("Verde"),
    NARANJA("Naranja");

    private String valor;

    TazaColorENUM (String valor){
        this.valor=valor;
    }
    public String getValor(){
        return valor;
    }
}
