package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.dao.ProdutoDao;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        ResultSet rs =  produtoDao.getProdutoById(id);
        if(rs.next()){
            return new ProdutoModel(rs.getInt("id"), rs.getString("descricao"), rs.getDouble("estoque"), rs.getBigDecimal("preco"), rs.getString("unidade"), rs.getString("ultimaAtualizacao"));
        }
        return null;
    }

    public ResultSet getProdutoByDescricao(String descricao) throws SQLException {
        return produtoDao.getProdutoByDescricao(descricao);
    }
    
}
