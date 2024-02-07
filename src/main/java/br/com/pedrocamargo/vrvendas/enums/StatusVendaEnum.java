package br.com.pedrocamargo.vrvendas.enums;

public enum StatusVendaEnum {
    DIGITANDO(1, "Digitando"),
    FINALIZADOPARCIAL(2, "Finalizado Parcial"),
    FINALIZADO(3, "Finalizado");

    private final int codigo;
    private final String descricao;

    StatusVendaEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public static StatusVendaEnum porCodigo(int codigo) {
        for (StatusVendaEnum status : StatusVendaEnum.values()) {
            if (status.getCodigo() == codigo) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}
