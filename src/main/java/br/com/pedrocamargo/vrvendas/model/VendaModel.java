package br.com.pedrocamargo.vrvendas.model;

import br.com.pedrocamargo.vrvendas.enums.StatusVendaEnum;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class VendaModel {
    Integer id;
    Integer id_cliente;
    Integer id_status;
    BigDecimal valortotal;
    private Map<ProdutoModel, Integer> produtos;
    Timestamp created_at;
    Timestamp updated_at;

    public VendaModel(Integer id, Integer id_cliente, Integer id_status, BigDecimal valortotal) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_status = id_status;
        this.valortotal = valortotal;
        this.produtos = new LinkedHashMap<>();
    }
    
    public VendaModel(Integer id, Integer id_cliente, Integer id_status, BigDecimal valortotal, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_status = id_status;
        this.valortotal = valortotal;
        this.produtos = new LinkedHashMap<>();
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
    
    public void adicionarProduto(ProdutoModel produto, Integer quantidade) {
        this.valortotal = valortotal.add(produto.getPreco().multiply(new BigDecimal(quantidade)));
        if(produtos.containsKey(produto)){
            Integer qtde = produtos.get(produto);
            produtos.put(produto,quantidade+qtde);
        }else{
            produtos.put(produto,quantidade);   
        }
    }

    public void removerProduto(ProdutoModel produto) {
        Integer quantidade = produtos.get(produto);
        this.valortotal = valortotal.subtract(produto.getPreco().multiply(new BigDecimal(quantidade)));
        produtos.remove(produto);
    }
    
    public void editarProduto(ProdutoModel produtoAnterior, ProdutoModel produtoNovo, Integer quantidade) {
       this.removerProduto(produtoAnterior);
       this.adicionarProduto(produtoNovo, quantidade);
    }
    
    public void editarQuantidadeProduto(ProdutoModel produto, Integer quantidadeNova) {
       Integer qtdeAtual = produtos.get(produto);
       this.valortotal = valortotal.subtract(produto.getPreco().multiply(new BigDecimal(qtdeAtual)));
       this.valortotal = valortotal.add(produto.getPreco().multiply(new BigDecimal(quantidadeNova)));
       produtos.put(produto,quantidadeNova);   
    }

    public Map<ProdutoModel, Integer> getProdutosSelecionados() {
        return produtos;
    }
    
    public ArrayList<Integer> getIdsProdutosSelecionados(){
        ArrayList<Integer> ids = new ArrayList<>();
        
        produtos.forEach((produto,quantidade)->{
            ids.add(produto.getId());
        });
        
        return ids;
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
        return valortotal.setScale(2, RoundingMode.HALF_UP);
    }
    
    public String getStatus() {
        return StatusVendaEnum.porCodigo(this.id_status).getDescricao();
    }
    
    public void setIdCliente(Integer idCliente) {
        this.id_cliente = idCliente;
    }

    public void setIdStatus(Integer idStatus) {
        this.id_status = idStatus;
    }
    
    public void finalizarVenda() {
        // TODO Implementação da lógica para gravar no banco de dados
    }
}
