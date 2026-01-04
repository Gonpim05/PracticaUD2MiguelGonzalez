package gui.base.enums;

public enum CamisetaColorENUM {
    NEGRO("Negro"),
    AZUL("Azul"),
    ROJO("Rojo");

    private String valor;

    CamisetaColorENUM(String valor) {this.valor=valor;

    }

    public String getValor(){

        return valor;
    }
}
