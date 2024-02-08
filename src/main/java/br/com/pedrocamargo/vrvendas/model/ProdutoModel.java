package br.com.pedrocamargo.vrvendas.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import br.com.pedrocamargo.vrvendas.util.ConverterTimeStampUtil;

public class ProdutoModel {
    private Integer id;
    private String descricao;
    private Integer estoque;
    private BigDecimal preco;
    private String unidade;
    private String ultimaAtualizacao;

    public ProdutoModel(Integer id, String descricao, Integer estoque, BigDecimal preco, String unidade, String ultimaAtualizacao) {
        this.id = id;
        this.descricao = descricao;
        this.estoque = estoque;
        this.preco = preco.setScale(2, RoundingMode.HALF_UP);
        this.unidade = unidade;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public BigDecimal getPreco() {
        return preco.setScale(2, RoundingMode.HALF_UP);
    }

    public String getUnidade() {
        return unidade;
    }

    public String getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }
    
    public Timestamp getUltimaAtualizacaoTimeStamp(){
        return Timestamp.valueOf(ConverterTimeStampUtil.converteTimeStampToLocalDateTime(this.getUltimaAtualizacao()));
    }

    public void setEstoque(Integer estoqueAtual) {
        this.estoque = estoqueAtual;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        ProdutoModel that = (ProdutoModel) o;
        return this.id == that.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
