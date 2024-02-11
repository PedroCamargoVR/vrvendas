package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.dao.ProdutoDao;
import br.com.pedrocamargo.vrvendas.dao.VendaDao;
import br.com.pedrocamargo.vrvendas.dao.VendaProdutoDao;
import br.com.pedrocamargo.vrvendas.dao.VendaProdutoErroFinalizacaoDao;
import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.FakeProdutoAPIService;
import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models.EnviarProdutoModel;
import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models.ErroFinalizacaoResponseModel;
import br.com.pedrocamargo.vrvendas.model.ProdutoVendaErroFinalizacaoModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.model.VendaProdutoModel;
import br.com.pedrocamargo.vrvendas.vo.ProdutoQuantidadeVO;
import br.com.pedrocamargo.vrvendas.vo.VendaVO;
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
        
        VendaModel venda = vendaDao.getVendaById(idVenda);
        
        if(venda != null){            
            List<ProdutoQuantidadeVO> produtos = produtoDao.getProdutosByVendaId(venda.getId());
            
            produtos.forEach((produtoVo) -> {
                venda.adicionarProduto(produtoVo.getProduto(),produtoVo.getQuantidade());
            });
            
            return venda;
        }
        return null;
    }
    
    public List<VendaModel> getVendasByIdCliente(Integer idCliente) throws SQLException {
        return vendaDao.getVendasByIdCliente(idCliente);
    }
    
    public List<VendaVO> getAllVendasVo() throws SQLException {
        return vendaDao.getAllVendasVo();
    }
    
    public List<VendaVO> getVendasVoByIdStatus(Integer idStatus) throws SQLException {
        return vendaDao.getVendasVoByStatus(idStatus);
    }

    public VendaVO getVendaVoById(Integer idVenda) throws SQLException {        
        return vendaDao.getVendaVoById(idVenda);
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
            List<ProdutoVendaErroFinalizacaoModel> produtosErro = vendaProdutoErroFinalizacaoDao.getProdutosVendaErroFinalizacao(venda.getId());
            produtosErro.forEach((produtoErro) -> {
                vendaProdutoErroIds.add(produtoErro.getId_vendaproduto());
                produtosParaEnvio.add(new EnviarProdutoModel(produtoErro.getId(),produtoErro.getQuantidade()));
            });
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
                
                List<VendaProdutoModel> idsVendaProduto = vendaProdutoDao.getVendaProdutoByIdVenda(venda.getId());
                List<ProdutoVendaErroFinalizacaoModel> idsProdutoErro = vendaProdutoErroFinalizacaoDao.getProdutosVendaErroFinalizacao(venda.getId());
                
                Map<Integer,Integer> idProdutoIdVendaProduto = new HashMap<>();
                Map<Integer,Integer> idProdutoIdVendaProdutoError = new HashMap<>();
                
                idsVendaProduto.forEach((vendaProduto) -> {
                    idProdutoIdVendaProduto.put(vendaProduto.getId_produto(),vendaProduto.getId());
                });
                
                idsProdutoErro.forEach((produtoErro) -> {
                    idProdutoIdVendaProdutoError.put(produtoErro.getId(),produtoErro.getId_vendaproduto());
                });
                
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

    public List<VendaProdutoModel> getProdutosVendaByIdVenda(Integer idVenda) throws SQLException {
        return vendaProdutoDao.getVendaProdutoByIdVenda(idVenda);
    }

    public List<ProdutoVendaErroFinalizacaoModel> getProdutosVendaErroFinalizacao(Integer idVenda) throws SQLException {
        return vendaProdutoErroFinalizacaoDao.getProdutosVendaErroFinalizacao(idVenda);
    }
}
