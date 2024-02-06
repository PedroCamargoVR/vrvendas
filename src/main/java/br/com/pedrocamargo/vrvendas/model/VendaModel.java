package br.com.pedrocamargo.vrvendas.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class VendaModel {
    Integer id;
    Integer id_cliente;
    Integer id_status;
    BigDecimal valortotal;
    private Map<ProdutoModel, Integer> produtos;

    public VendaModel(Integer id, Integer id_cliente, Integer id_status, BigDecimal valortotal) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_status = id_status;
        this.valortotal = valortotal.setScale(2, RoundingMode.HALF_UP);
        this.produtos = new LinkedHashMap<>();
    }
    
    public void adicionarProduto(ProdutoModel produto, Integer quantidade) {
        valortotal.add(produto.getPreco());
        produtos.put(produto,quantidade);
    }

    public void removerProduto(ProdutoModel produto, Integer quantidade) {
        valortotal.subtract(produto.getPreco());
        produtos.remove(produto,quantidade);
    }

    public Map<ProdutoModel, Integer> getProdutosSelecionados() {
        return produtos;
    }

    public Integer getId() {
        return id;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public Integer getId_status() {
        return id_status;
    }

    public BigDecimal getValortotal() {
        return valortotal;
    }
    
    // TODO Método para gravar os IDs no banco de dados ao finalizar a venda
    public void finalizarVenda() {
        // TODO Implementação da lógica para gravar no banco de dados
    }

    
}
