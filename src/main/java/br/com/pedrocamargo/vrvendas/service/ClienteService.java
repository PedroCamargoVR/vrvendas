package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.config.ConnectionFactory;
import br.com.pedrocamargo.vrvendas.dao.ClienteDao;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClienteService {
    
    private ClienteModel clienteModel;
    private ClienteDao clienteDao;
    
    public ClienteService(){
        clienteDao = new ClienteDao(new ConnectionFactory());
    }
    
    public void salvarCliente(String id, String nome, String nomefantasia, String razaosocial, String cnpj) throws SQLException{
        if(id.isEmpty()){
            clienteModel = new ClienteModel(0, nome, nomefantasia, razaosocial, cnpj);
        }else{
            clienteModel = new ClienteModel(Integer.parseInt(id), nome, nomefantasia, razaosocial, cnpj);
        }
        
        clienteDao.salvarCliente(clienteModel);
    }

    public List<ClienteModel> getClientes() throws SQLException {
        return clienteDao.getClientes();
    }
    
    public ClienteModel getClienteById(Integer id) throws SQLException{
        return clienteDao.getClienteById(id);
    }
    
    public List<ClienteModel> getClienteByCnpj(String cnpj) throws SQLException {
        return clienteDao.getClienteByCnpj(cnpj);
    }

    public void removerCLiente(Integer id) throws SQLException {
        clienteDao.removeCliente(id);
    }
}
