package br.com.pedrocamargo.vrvendas.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class VendaProdutoModel {
    private Integer id;
    private Integer id_venda;
    private Integer id_produto;
    private BigDecimal valorprodutonavenda;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Integer quantidade;

    public VendaProdutoModel(Integer id, Integer id_venda, Integer id_produto, BigDecimal valorprodutonavenda, Timestamp created_at, Timestamp updated_at, Integer quantidade) {
        this.id = id;
        this.id_venda = id_venda;
        this.id_produto = id_produto;
        this.valorprodutonavenda = valorprodutonavenda;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public Integer getId_venda() {
        return id_venda;
    }

    public Integer getId_produto() {
        return id_produto;
    }

    public BigDecimal getValorprodutonavenda() {
        return valorprodutonavenda;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
    
    
    
}
