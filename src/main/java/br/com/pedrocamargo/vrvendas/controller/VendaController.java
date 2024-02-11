package br.com.pedrocamargo.vrvendas.controller;

import br.com.pedrocamargo.vrvendas.interfaces.VendaControllerInterface;
import br.com.pedrocamargo.vrvendas.model.ProdutoVendaErroFinalizacaoModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.model.VendaProdutoModel;
import br.com.pedrocamargo.vrvendas.service.ProdutoService;
import br.com.pedrocamargo.vrvendas.service.VendaService;
import br.com.pedrocamargo.vrvendas.store.VendaStorage;
import br.com.pedrocamargo.vrvendas.vo.VendaVO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class VendaController implements VendaControllerInterface {
    
    private ProdutoService produtoService;
    private VendaService vendaService;
    private VendaStorage vendaStorage;
    
    public VendaController(){
        this.produtoService = new ProdutoService();
        this.vendaService = new VendaService();
        this.vendaStorage = new VendaStorage();
    }
    
    public VendaController(VendaModel venda){
        this.produtoService = new ProdutoService();
        this.vendaService = new VendaService();
        this.vendaStorage = new VendaStorage(venda);
    }

    @Override
    public void criarNovaVenda() {
        this.vendaStorage.criarNovaVenda();
    }
    
    @Override
    public VendaModel getVendaAtual() {
        return this.vendaStorage.getVendaAtual();
    }
    
    @Override
    public BigDecimal getValorTotalVenda() {
        return this.vendaStorage.getValorTotalVenda();
    }
    
    @Override
    public VendaModel getVendaById(Integer idVenda) throws SQLException {
        return vendaService.getVendaById(idVenda);
    }
    
    @Override
    public VendaVO getVendaVoById(Integer idVenda) throws SQLException {
        return vendaService.getVendaVoById(idVenda);
    }
    
    @Override
    public List<VendaVO> getVendasVoByIdStatus(Integer idStatus) throws SQLException {
        return vendaService.getVendasVoByIdStatus(idStatus);
    }
    
    @Override
    public List<VendaModel> getVendasByIdCliente(Integer idCliente) throws SQLException {
        return vendaService.getVendasByIdCliente(idCliente);
    }
    
    @Override
    public List<VendaVO> getAllVendasVo() throws SQLException {
        return vendaService.getAllVendasVo();
    }
    
    @Override
    public List<VendaProdutoModel> getProdutosVendaByIdVenda(Integer idVenda) throws SQLException{
        return vendaService.getProdutosVendaByIdVenda(idVenda);
    }
    
    @Override
    public void atualizaEstoqueProdutosVendaAtual() throws SQLException {
        try{
            this.vendaStorage.getVendaAtual().getProdutosVenda().forEach((produto,quantidade) -> {
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

    @Override
    public Integer salvarVenda(VendaModel venda) throws SQLException {
        return vendaService.salvarVenda(venda);
    }

    @Override
    public void excluirVenda(Integer id) throws SQLException {
        vendaService.excluirVenda(id);
    }
    
    @Override
    public Integer finalizarVenda(VendaModel vendaAtual) throws Exception {
        return vendaService.finalizarVenda(vendaAtual);
    }

    @Override
    public void insertProdutoVendaProdutoErro(Integer idVendaProduto, String motivo) throws SQLException {
        vendaService.insertProdutoVendaProdutoErro(idVendaProduto,motivo);
    }

    @Override
    public List<ProdutoVendaErroFinalizacaoModel> getProdutosVendaErroFinalizacao(Integer idVenda) throws SQLException {
        return vendaService.getProdutosVendaErroFinalizacao(idVenda);
    }
}
