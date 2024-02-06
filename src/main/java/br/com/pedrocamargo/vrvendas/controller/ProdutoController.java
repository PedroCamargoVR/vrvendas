package br.com.pedrocamargo.vrvendas.controller;

import br.com.pedrocamargo.vrvendas.interfaces.ProdutoControllerInterface;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import br.com.pedrocamargo.vrvendas.service.ProdutoService;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoController implements ProdutoControllerInterface{

    ProdutoService produtoService;
    
    public ProdutoController(){
        this.produtoService = new ProdutoService();
    }
    
    @Override
    public void salvarProdutoLote(ProdutoModel[] produtos) throws SQLException {
        produtoService.salvarProdutoLote(produtos);
    }

    @Override
    public void sincronizarProdutosApiBdLocal() throws SQLException {
        produtoService.sincronizarProdutosApiBdLocal();
    }

    @Override
    public ProdutoModel getProdutoById(Integer id) throws SQLException {
        return produtoService.getProdutoById(id);
    }

    @Override
    public ResultSet getProdutoByDescricao(String descricao) throws SQLException {
        return produtoService.getProdutoByDescricao(descricao);
    }
    
}
