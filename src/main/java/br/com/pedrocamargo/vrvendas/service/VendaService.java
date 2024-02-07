package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.dao.ProdutoDao;
import br.com.pedrocamargo.vrvendas.dao.VendaDao;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.vo.VendaVO;
import java.math.BigDecimal;
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
            VendaModel vendaModel = new VendaModel(rs.getInt("id"),rs.getInt("id_cliente"),rs.getInt("id_status"),new BigDecimal(0),rs.getTimestamp("created_at"),rs.getTimestamp("updated_at"));
            
            ResultSet rsProdutos = produtoDao.getProdutosByVendaId(vendaModel.getId());
            
            while(rsProdutos.next()){
                vendaModel.adicionarProduto(new ProdutoModel(
                    rsProdutos.getInt("id"),
                    rsProdutos.getString("descricao"),
                    rsProdutos.getInt("estoque"),
                    rsProdutos.getBigDecimal("valorprodutonavenda"),
                    rsProdutos.getString("unidade"),
                    rsProdutos.getString("ultimaatualizacao")
                ), rsProdutos.getInt("quantidade"));
            }
            
            return vendaModel;
        }
        return null;
    }
    
    public ResultSet getVendasByIdCliente(Integer idCliente) throws SQLException {
        return vendaDao.getVendasByIdCliente(idCliente);
    }
    
    public ArrayList<VendaVO> getVendasVoByIdStatus(Integer idStatus) throws SQLException {
        ResultSet rs = vendaDao.getVendasVoByStatus(idStatus);
        ArrayList<VendaVO> vendas = new ArrayList<>();
        while(rs.next()){
           vendas.add(new VendaVO(
                   rs.getInt("id_venda"),
                   rs.getInt("id_status"), 
                   new ClienteModel(rs.getInt("id_cliente"),
                            rs.getString("nome"),
                            rs.getString("nomefantasia"),
                            rs.getString("razaosocial"),
                            rs.getString("cnpj")
                        ), 
                   rs.getBigDecimal("valortotal"), 
                   rs.getTimestamp("created_at"), 
                   rs.getTimestamp("updated_at")
                )
           );
        }
        
        return vendas;
    }

    public ArrayList<VendaVO> getAllVendasVo() throws SQLException {
        ResultSet rs = vendaDao.getAllVendasVo();
        ArrayList<VendaVO> vendas = new ArrayList<>();
        while(rs.next()){
           vendas.add(new VendaVO(
                   rs.getInt("id_venda"),
                   rs.getInt("id_status"), 
                   new ClienteModel(rs.getInt("id_cliente"),
                            rs.getString("nome"),
                            rs.getString("nomefantasia"),
                            rs.getString("razaosocial"),
                            rs.getString("cnpj")
                        ), 
                   rs.getBigDecimal("valortotal"), 
                   rs.getTimestamp("created_at"), 
                   rs.getTimestamp("updated_at")
                )
           );
        }
        
        return vendas;
    }

    public VendaVO getVendaVoById(Integer idVenda) throws SQLException {
        ResultSet rs = vendaDao.getVendaVoById(idVenda);
        VendaVO vendaVo = null;
        if(rs.next()){
            vendaVo = new VendaVO(
                   rs.getInt("id_venda"),
                   rs.getInt("id_status"), 
                   new ClienteModel(rs.getInt("id_cliente"),
                            rs.getString("nome"),
                            rs.getString("nomefantasia"),
                            rs.getString("razaosocial"),
                            rs.getString("cnpj")
                        ), 
                   rs.getBigDecimal("valortotal"), 
                   rs.getTimestamp("created_at"), 
                   rs.getTimestamp("updated_at")
           );
        }
        return vendaVo;
    }
}
