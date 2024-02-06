package br.com.pedrocamargo.vrvendas.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import util.ConverterTimeStampUtil;

public class ProdutoModel {
    private Integer id;
    private String descricao;
    private Double estoque;
    private BigDecimal preco;
    private String unidade;
    private String ultimaAtualizacao;

    public ProdutoModel(Integer id, String descricao, Double estoque, BigDecimal preco, String unidade, String ultimaAtualizacao) {
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

    public Double getEstoque() {
        return estoque;
    }

    public BigDecimal getPreco() {
        return preco;
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
}
