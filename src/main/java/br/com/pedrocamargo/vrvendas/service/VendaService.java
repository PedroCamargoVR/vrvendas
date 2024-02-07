package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.dao.ProdutoDao;
import br.com.pedrocamargo.vrvendas.dao.VendaDao;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class VendaService {
    
    private ProdutoDao produtoDao;
    private VendaDao vendaDao;
    
    public VendaService(){
        this.vendaDao = new VendaDao();
        this.produtoDao = new ProdutoDao();
    }

    public Integer salvarVenda(VendaModel venda) throws SQLException {
        return vendaDao.salvarVenda(venda);
    }
    
    public VendaModel getVendaById(Integer idVenda) throws SQLException{
        
        ResultSet rs = vendaDao.getVendaById(idVenda);
        
        if(rs.next()){
            VendaModel vendaModel = new VendaModel(rs.getInt("id"),rs.getInt("id_cliente"),rs.getInt("id_status"),rs.getBigDecimal("valortotal"),rs.getTimestamp("created_at"),rs.getTimestamp("updated_at"));
            
            ResultSet rsProdutos = produtoDao.getProdutosByVendaId(vendaModel.getId());
            
            while(rsProdutos.next()){
                vendaModel.adicionarProduto(new ProdutoModel(
                    rsProdutos.getInt("id"),
                    rsProdutos.getString("descricao"),
                    rsProdutos.getInt("estoque"),
                    rsProdutos.getBigDecimal("preco"),
                    rsProdutos.getString("unidade"),
                    rsProdutos.getString("ultimaatualizacao")
                ), rsProdutos.getInt("quantidade"));
            }
            
            return vendaModel;
        }
        return null;
    }
    
}
