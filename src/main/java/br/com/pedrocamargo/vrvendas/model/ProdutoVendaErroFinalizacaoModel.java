package br.com.pedrocamargo.vrvendas.model;

public class ProdutoVendaErroFinalizacaoModel {
    private Integer id;
    private Integer id_vendaproduto;
    private String motivoErro;
    private String descricao;
    private Integer quantidade;

    public ProdutoVendaErroFinalizacaoModel(Integer id, Integer id_vendaproduto, String motivoErro, String descricao, Integer quantidade) {
        this.id = id;
        this.id_vendaproduto = id_vendaproduto;
        this.motivoErro = motivoErro;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public Integer getId_vendaproduto() {
        return id_vendaproduto;
    }

    public String getMotivoErro() {
        return motivoErro;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
