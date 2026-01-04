package gui.base.enums;

public enum LlaveroMaterialENUM {
    PLASTICO("Plastico"),
    METAL("Metal");

    private String valor;

    LlaveroMaterialENUM(String valor){
        this.valor=valor;
    }
    public String getValor(){
        return valor;
    }
}
