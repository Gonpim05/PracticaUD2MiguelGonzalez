package gui.base.enums;

public enum TazaMaterialENUM {
    CERAMICA("Cerámica"),
    CRISTAL("Cristal");

    private String valor;

    TazaMaterialENUM(String valor){
        this.valor=valor;
    }
    public String getValor(){
        return valor;
    }
}
