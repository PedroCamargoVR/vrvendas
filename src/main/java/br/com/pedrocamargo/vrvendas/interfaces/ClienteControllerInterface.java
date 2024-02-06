package br.com.pedrocamargo.vrvendas.interfaces;

import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ClienteControllerInterface {
    
    public void salvarCliente(String id, String nome, String nomefantasia, String razaosocial, String cnpj) throws SQLException;
    
    public ResultSet getClientes() throws SQLException;
    
    public ClienteModel getClienteById(Integer id) throws SQLException;
    
    public ResultSet getClienteByCnpj(String cnpj) throws SQLException;
    
    public void removerCliente(Integer id) throws SQLException;
    
}
