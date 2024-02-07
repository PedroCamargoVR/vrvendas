package br.com.pedrocamargo.vrvendas.controller;

import br.com.pedrocamargo.vrvendas.interfaces.VendaControllerInterface;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.service.ProdutoService;
import br.com.pedrocamargo.vrvendas.service.VendaService;
import br.com.pedrocamargo.vrvendas.vo.VendaVO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendaController implements VendaControllerInterface {
    
    private ProdutoService produtoService;
    private VendaModel vendaAtual;
    private VendaService vendaService;
    
    public VendaController(){
        this.produtoService = new ProdutoService();
        this.vendaService = new VendaService();
    }
    
    public VendaController(VendaModel venda){
        this.produtoService = new ProdutoService();
        this.vendaService = new VendaService();
        this.vendaAtual = venda;
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
    public VendaModel getVendaById(Integer idVenda) throws SQLException {
        return vendaService.getVendaById(idVenda);
    }
    
    @Override
    public VendaVO getVendaVoById(Integer idVenda) throws SQLException {
        return vendaService.getVendaVoById(idVenda);
    }
    
    @Override
    public ResultSet getVendasByIdCliente(Integer idCliente) throws SQLException {
        return vendaService.getVendasByIdCliente(idCliente);
    }
    
    @Override
    public ArrayList<VendaVO> getVendasVoByIdStatus(Integer idStatus) throws SQLException {
        return vendaService.getVendasVoByIdStatus(idStatus);
    }

    @Override
    public ArrayList<VendaVO> getAllVendas() throws SQLException {
        return vendaService.getAllVendasVo();
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

    @Override
    public BigDecimal getValorTotalVenda() {
        return this.vendaAtual.getValortotal();
    }

    @Override
    public Integer salvarVenda(VendaModel venda) throws SQLException {
        return vendaService.salvarVenda(venda);
    }
}
