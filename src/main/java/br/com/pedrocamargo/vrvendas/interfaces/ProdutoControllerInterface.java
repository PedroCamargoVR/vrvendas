package br.com.pedrocamargo.vrvendas.interfaces;

import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import java.sql.SQLException;
import java.util.List;

public interface ProdutoControllerInterface {
    public void salvarProdutoLote(ProdutoModel[] produtos) throws SQLException;
    
    public void sincronizarProdutosApiBdLocal() throws SQLException;
    
    public ProdutoModel getProdutoById(Integer id) throws SQLException;
    
    public List<ProdutoModel> getProdutoByDescricao(String descricao) throws SQLException;
}
