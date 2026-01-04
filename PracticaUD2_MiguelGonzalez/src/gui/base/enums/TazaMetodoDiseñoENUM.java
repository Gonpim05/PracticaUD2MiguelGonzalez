package gui.base.enums;

public enum TazaMetodoDiseñoENUM {
    FOTO("Foto"),
    IA("IA");

    private String valor;

    TazaMetodoDiseñoENUM(String valor){
        this.valor=valor;
    }

    public String getValor(){
        return valor;
    }
}
