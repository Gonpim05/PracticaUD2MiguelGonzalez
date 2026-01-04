package gui.base.enums;

public enum  LlaveroColorENUM {
    ROSA("Rosa"),
    MORADO("Morado"),
    GRANATE("Granate");

    private String valor;

    LlaveroColorENUM (String valor){
        this.valor=valor;
    }
    public String getValor(){
        return valor;
    }
}
