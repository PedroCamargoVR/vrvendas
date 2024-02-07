package br.com.pedrocamargo.vrvendas.interfaces;

import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.vo.VendaVO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface VendaControllerInterface {
    
    public void criarNovaVenda();
    
    public VendaModel getVendaAtual();
    
    public VendaModel getVendaById(Integer idVenda) throws SQLException;
    
    public VendaVO getVendaVoById(Integer idVenda) throws SQLException;
    
    public ResultSet getVendasByIdCliente(Integer idCliente) throws SQLException;
    
    public ArrayList<VendaVO> getVendasVoByIdStatus(Integer idStatus) throws SQLException;
    
    public ArrayList<VendaVO> getAllVendas() throws SQLException;
    
    public void atualizaEstoqueProdutosVendaAtual() throws SQLException;
    
    public BigDecimal getValorTotalVenda();
    
    public Integer salvarVenda(VendaModel venda) throws SQLException ;
    
   
}
