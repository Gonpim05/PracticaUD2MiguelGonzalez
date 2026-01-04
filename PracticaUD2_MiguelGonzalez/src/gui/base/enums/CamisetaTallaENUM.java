package gui.base.enums;

public enum CamisetaTallaENUM {
    S("S"),
    M("M"),
    L("L"),
    XL("XL");

    private String valor;

    CamisetaTallaENUM (String valor){
        this.valor=valor;
    }
    public String getValor(){
        return valor;
    }
}
