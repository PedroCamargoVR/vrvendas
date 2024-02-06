package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.dao.ClienteDao;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteService {
    
    private ClienteModel clienteModel;
    private ClienteDao clienteDao;
    
    public ClienteService(){
        clienteDao = new ClienteDao();
    }
    
    public void salvarCliente(String id, String nome, String nomefantasia, String razaosocial, String cnpj) throws SQLException{
        if(id.isEmpty()){
            clienteModel = new ClienteModel(0, nome, nomefantasia, razaosocial, cnpj);
        }else{
            clienteModel = new ClienteModel(Integer.parseInt(id), nome, nomefantasia, razaosocial, cnpj);
        }
        
        clienteDao.salvarCliente(clienteModel);
    }

    public ResultSet getClientes() throws SQLException {
        return clienteDao.getClientes();
    }
    
    public ClienteModel getClienteById(Integer id) throws SQLException{
        ResultSet rs = clienteDao.getClienteById(id);
        if(rs.next()){
            return new ClienteModel(rs.getInt("id"),rs.getString("nome"),rs.getString("nomefantasia"),rs.getString("razaosocial"),rs.getString("cnpj"));
        }
        return null;
    }
    
    public ResultSet getClienteByCnpj(String cnpj) throws SQLException {
        ResultSet rs = clienteDao.getClienteByCnpj(cnpj);
        return rs;
    }

    public void removerCLiente(Integer id) throws SQLException {
        clienteDao.removeCliente(id);
    }
}
