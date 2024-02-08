package br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models;

public class ErrorDetailModel {
    private Integer estoqueAtual;
    private Integer quantidade;
    private String error;
    
    public ErrorDetailModel(Integer estoqueAtual, Integer quantidade, String error){
        this.estoqueAtual = estoqueAtual;
        this.quantidade = quantidade;
        this.error = error;
    }
    
    public ErrorDetailModel(String error){
        this.estoqueAtual = 0;
        this.quantidade = 0;
        this.error = error;
    }
   
    public Integer getEstoqueAtual() {
        return estoqueAtual;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
    
    public String getError() {
        return error;
    }
}
