package br.com.pedrocamargo.vrvendas.interfaces;

import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import java.sql.SQLException;
import java.util.List;

public interface ClienteControllerInterface {
    
    public void salvarCliente(String id, String nome, String nomefantasia, String razaosocial, String cnpj) throws SQLException;
    
    public List<ClienteModel> getClientes() throws SQLException ;
    
    public ClienteModel getClienteById(Integer id) throws SQLException;
    
    public List<ClienteModel> getClienteByCnpj(String cnpj) throws SQLException;
    
    public void removerCliente(Integer id) throws SQLException;
    
}
