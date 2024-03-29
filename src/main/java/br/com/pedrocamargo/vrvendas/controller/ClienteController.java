package br.com.pedrocamargo.vrvendas.controller;

import br.com.pedrocamargo.vrvendas.interfaces.ClienteControllerInterface;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import br.com.pedrocamargo.vrvendas.service.ClienteService;
import java.sql.SQLException;
import java.util.List;

public class ClienteController implements ClienteControllerInterface{

    private ClienteService clienteService;
    
    public ClienteController(){
        this.clienteService = new ClienteService();
    }
    
    @Override
    public void salvarCliente(String id, String nome, String nomefantasia, String razaosocial, String cnpj) throws SQLException{
        clienteService.salvarCliente(id, nome, nomefantasia, razaosocial, cnpj);
    }

    @Override
    public List<ClienteModel> getClientes() throws SQLException {
        return clienteService.getClientes();
    }
    
    @Override
    public ClienteModel getClienteById(Integer id) throws SQLException {
        return clienteService.getClienteById(id);
    }
    
    @Override
    public List<ClienteModel> getClienteByCnpj(String cnpj) throws SQLException {
        return clienteService.getClienteByCnpj(cnpj);
    }

    @Override
    public void removerCliente(Integer id) throws SQLException {
        clienteService.removerCLiente(id);
    }    
}
