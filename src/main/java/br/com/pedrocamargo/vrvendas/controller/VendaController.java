package br.com.pedrocamargo.vrvendas.controller;

import br.com.pedrocamargo.vrvendas.interfaces.VendaControllerInterface;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.service.ProdutoService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendaController implements VendaControllerInterface {
    
    private ProdutoService produtoService;
    private VendaModel vendaAtual;
    
    public VendaController(){
        this.produtoService = new ProdutoService();
    }

    @Override
    public void criarNovaVenda() {
        this.vendaAtual = new VendaModel(0, 0, 0, new BigDecimal(0));
    }
    
    @Override
    public VendaModel getVendaAtual() {
        return vendaAtual;
    }

    @Override
    public void getVendaExistente(Integer idVenda) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizaEstoqueProdutosVendaAtual() throws SQLException {
        try{
            this.vendaAtual.getProdutosSelecionados().forEach((produto,quantidade) -> {
                try{
                    produtoService.atualizaEstoqueProduto(produto);
                } catch(SQLException e){
                    throw new RuntimeException(e.getMessage());
                }
            });
        }catch(RuntimeException ex){
            throw new SQLException(ex.getMessage());
        }
        
    }

    
    
}
