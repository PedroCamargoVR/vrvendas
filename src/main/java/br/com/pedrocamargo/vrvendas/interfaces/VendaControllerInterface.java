package br.com.pedrocamargo.vrvendas.interfaces;

import br.com.pedrocamargo.vrvendas.model.VendaModel;
import java.sql.SQLException;

public interface VendaControllerInterface {
    
    public void criarNovaVenda();
    
    public void getVendaExistente(Integer idVenda);
    
    public VendaModel getVendaAtual();
    
    public void atualizaEstoqueProdutosVendaAtual() throws SQLException;
}
