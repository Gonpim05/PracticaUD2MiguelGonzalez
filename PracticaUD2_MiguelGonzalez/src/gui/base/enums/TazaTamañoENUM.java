package gui.base.enums;

public enum TazaTamañoENUM {
    PEQUEÑA("Pequeña"),
    MEDIANA("Mediana"),
    GRANDE("Grande");

    private String valor;

    TazaTamañoENUM(String valor){
        this.valor=valor;
    }
    public String getValor(){
        return valor;
    }
}
