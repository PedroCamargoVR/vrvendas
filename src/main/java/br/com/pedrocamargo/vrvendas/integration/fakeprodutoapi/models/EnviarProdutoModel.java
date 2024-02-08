package br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models;


public class EnviarProdutoModel {
    
    private Integer id;
    private Integer quantidade;

    public EnviarProdutoModel(Integer id, Integer quantidade) {
        this.id = id;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                '}';
    }
    
}
