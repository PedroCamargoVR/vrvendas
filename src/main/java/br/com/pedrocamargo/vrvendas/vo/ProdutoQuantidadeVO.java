package br.com.pedrocamargo.vrvendas.vo;

import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import java.math.BigDecimal;


public class ProdutoQuantidadeVO {
    
    private ProdutoModel produto;
    private Integer quantidade;
    
    public ProdutoQuantidadeVO(ProdutoModel produto, Integer quantidade){
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    } 
}
