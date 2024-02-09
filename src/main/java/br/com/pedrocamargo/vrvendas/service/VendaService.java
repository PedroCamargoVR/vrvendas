package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.dao.ProdutoDao;
import br.com.pedrocamargo.vrvendas.dao.VendaDao;
import br.com.pedrocamargo.vrvendas.dao.VendaProdutoDao;
import br.com.pedrocamargo.vrvendas.dao.VendaProdutoErroFinalizacaoDao;
import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.FakeProdutoAPIService;
import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models.EnviarProdutoModel;
import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models.ErroFinalizacaoResponseModel;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.vo.VendaVO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VendaService {
    
    private ProdutoDao produtoDao;
    private VendaDao vendaDao;
    private VendaProdutoDao vendaProdutoDao;
    private VendaProdutoErroFinalizacaoDao vendaProdutoErroFinalizacaoDao;
    private FakeProdutoAPIService fakeProdutoApiService;
    
    public VendaService(){
        this.vendaDao = new VendaDao();
        this.produtoDao = new ProdutoDao();
        this.fakeProdutoApiService = new FakeProdutoAPIService();
        this.vendaProdutoDao = new VendaProdutoDao();
        this.vendaProdutoErroFinalizacaoDao = new VendaProdutoErroFinalizacaoDao();
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
    
    public List<VendaModel> getVendasByIdCliente(Integer idCliente) throws SQLException {
        return vendaDao.getVendasByIdCliente(idCliente);
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

    public void excluirVenda(Integer id) throws SQLException {
        vendaDao.excluirVenda(id);
    }
    
    public Integer finalizarVenda(VendaModel venda) throws Exception{
        List<EnviarProdutoModel> produtosParaEnvio = new ArrayList<>();
        List<Integer> vendaProdutoErroIds = new ArrayList<>();
        
        if(venda.getId_status() == 1){
            venda.getProdutosVenda().forEach((produto,quantidade) -> {
                produtosParaEnvio.add(new EnviarProdutoModel(produto.getId(),quantidade));
            });
        }else{
            ResultSet rsProdutos = vendaProdutoErroFinalizacaoDao.getProdutosVendaErroFinalizacao(venda.getId());
            while(rsProdutos.next()){
                vendaProdutoErroIds.add(rsProdutos.getInt("id_vendaproduto"));
                produtosParaEnvio.add(new EnviarProdutoModel(rsProdutos.getInt("id"),rsProdutos.getInt("quantidade")));
            }
        }
        
        Map<Integer,String> responseApi = fakeProdutoApiService.postProdutosApi(produtosParaEnvio);
        
        if(responseApi.containsKey(200)){
            try{
                vendaDao.atualizarStatusVenda(venda.getId(),3);
                vendaProdutoErroIds.forEach((p) -> {
                    try {
                        vendaProdutoErroFinalizacaoDao.removeProdutoVendaProdutoErro(p);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex.getMessage());
                    }
                });
                return 200;
            }catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }else if(responseApi.containsKey(207)){
            try{
                vendaDao.atualizarStatusVenda(venda.getId(),2);
                ErroFinalizacaoResponseModel erroFinalizacao = fakeProdutoApiService.obterObjetoErro(responseApi.get(207));
                
                ResultSet idsVendaProduto = vendaProdutoDao.getVendaProdutoByIdVenda(venda.getId());
                ResultSet idsProdutoErro = vendaProdutoErroFinalizacaoDao.getProdutosVendaErroFinalizacao(venda.getId());
                
                Map<Integer,Integer> idProdutoIdVendaProduto = new HashMap<>();
                Map<Integer,Integer> idProdutoIdVendaProdutoError = new HashMap<>();
                
                while(idsVendaProduto.next()){
                    idProdutoIdVendaProduto.put(idsVendaProduto.getInt("id_produto"),idsVendaProduto.getInt("id"));
                }
                
                while(idsProdutoErro.next()){
                    idProdutoIdVendaProdutoError.put(idsProdutoErro.getInt("id"),idsProdutoErro.getInt("id_vendaproduto"));
                } 
                
                erroFinalizacao.getErrors().forEach((id,erro) -> {
                    try{
                        insertProdutoVendaProdutoErro(idProdutoIdVendaProduto.get(id), erro.getError());
                    }catch(SQLException e){
                        throw new RuntimeException(e.getMessage());
                    }
                });
                
                idProdutoIdVendaProdutoError.forEach((idProduto,idVendaProduto) -> {
                    if(!erroFinalizacao.getErrors().containsKey(idProduto)){
                        try {
                            vendaProdutoErroFinalizacaoDao.removeProdutoVendaProdutoErro(idVendaProduto);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex.getMessage());
                        }
                    }
                });
                
                return 207;
            }catch(SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }else if(responseApi.containsKey(400)){
            return 400;
        }else if(responseApi.containsKey(500)){
            return 500;
        }
        
        return 0;
    }

    public void insertProdutoVendaProdutoErro(Integer idVendaProduto, String motivo) throws SQLException {
        vendaProdutoErroFinalizacaoDao.inserirProdutoVendaProdutoErro(idVendaProduto, motivo);
    }

    public ResultSet getProdutosVendaByIdVenda(Integer idVenda) throws SQLException {
        return vendaProdutoDao.getVendaProdutoByIdVenda(idVenda);
    }

    public ResultSet getProdutosVendaErroFinalizacao(Integer idVenda) throws SQLException {
        return vendaProdutoErroFinalizacaoDao.getProdutosVendaErroFinalizacao(idVenda);
    }
}
