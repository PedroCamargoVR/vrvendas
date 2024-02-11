package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.dao.ProdutoDao;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import java.sql.SQLException;
import java.util.List;

public class ProdutoService {

    ProdutoDao produtoDao;
    
    public ProdutoService(){
        this.produtoDao = new ProdutoDao();
    }
    
    public void salvarProdutoLote(ProdutoModel[] produtos) throws SQLException {
        produtoDao.salvarProdutoLote(produtos);
    }

    public void sincronizarProdutosApiBdLocal() throws SQLException {
        produtoDao.sincronizarProdutosApiBdLocal();
    }

    public ProdutoModel getProdutoById(Integer id) throws SQLException {
        return produtoDao.getProdutoById(id);
    }

    public List<ProdutoModel> getProdutoByDescricao(String descricao) throws SQLException {
        return produtoDao.getProdutoByDescricao(descricao);
    }
    
    public void atualizaEstoqueProduto(ProdutoModel produto) throws SQLException{
        Integer estoqueAtual = produtoDao.getEstoqueProduto(produto.getId());
        if(estoqueAtual != -1){
            produto.setEstoque(estoqueAtual);
        }
    }
}
