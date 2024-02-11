package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.BaseTest;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteDaoTest extends BaseTest {
   
    @Test
    public void aoEnviarUmClienteModelComIdZeroDeveSalvarClienteNoBancoDeDados() throws Exception {
        ClienteDao clienteDao = new ClienteDao(mockConnectionFactory);
        
        ClienteModel clienteModel = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel);
        
        ClienteModel clienteBanco = clienteDao.getClienteById(1);
        
        assertEquals(1,clienteBanco.getId(),"O ID deve ser igual a 1");
        assertEquals("Nome de Teste",clienteBanco.getNome(),"O nome deve ser Nome de Teste");
        assertEquals("Nome fantasia de teste",clienteBanco.getNomeFantasia(),"O nome fatansia deve ser Nome fantasia de teste");
        assertEquals("Razao Social de Teste",clienteBanco.getRazaoSocial(),"A razao social deve ser Razao Social de Teste");
        assertEquals("12123123000164",clienteBanco.getCnpj(),"O cnpj deve ser 12123123000164");
    }
    
    @Test
    public void aoEnviarUmClienteModelComIdMaiorQueZeroDeveAtualizarClienteNoBancoDeDados() throws Exception {
        ClienteDao clienteDao = new ClienteDao(mockConnectionFactory);
        
        ClienteModel clienteModel = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel);
        
        ClienteModel clienteBanco = clienteDao.getClienteById(1);
        
        assertEquals(1,clienteBanco.getId(),"O ID deve ser igual a 1");
        assertEquals("Nome de Teste",clienteBanco.getNome(),"O nome deve ser Nome de Teste");
        assertEquals("Nome fantasia de teste",clienteBanco.getNomeFantasia(),"O nome fatansia deve ser Nome fantasia de teste");
        assertEquals("Razao Social de Teste",clienteBanco.getRazaoSocial(),"A razao social deve ser Razao Social de Teste");
        assertEquals("12123123000164",clienteBanco.getCnpj(),"O cnpj deve ser 12123123000164");
        
        clienteBanco.setNome("Nome de Teste Modificado");
        clienteBanco.setNomeFantasia("Nome fantasia de teste modificado");
        clienteBanco.setRazaoSocial("Razao Social de teste modificada");
        
        clienteDao.salvarCliente(clienteBanco);
        clienteBanco = null;
        clienteBanco = clienteDao.getClienteById(1);
        
        assertEquals(1,clienteBanco.getId(),"O ID deve ser igual a 2");
        assertEquals("Nome de Teste Modificado",clienteBanco.getNome(),"O nome deve ser Nome de Teste Modificado");
        assertEquals("Nome fantasia de teste modificado",clienteBanco.getNomeFantasia(),"O nome fatansia deve ser Nome fantasia de teste modificado");
        assertEquals("Razao Social de teste modificada",clienteBanco.getRazaoSocial(),"A razao social deve ser Razao Social de teste modificada");
        assertEquals("12123123000164",clienteBanco.getCnpj(),"O cnpj deve ser 12123123000164 permanece o mesmo");
    }
    
    @Test
    public void dadoUmBancoComTresClientesAoPuxarClientesDeveRetornarUmaListaComTres() throws Exception{
        ClienteDao clienteDao = new ClienteDao(mockConnectionFactory);
        
        ClienteModel clienteModel1 = new ClienteModel(0, "Nome de Teste 1", "Nome fantasia de teste 1", "Razao Social de Teste 1", "01889095000109");
        ClienteModel clienteModel2 = new ClienteModel(0, "Nome de Teste 2", "Nome fantasia de teste 2", "Razao Social de Teste 2", "89397658000192");
        ClienteModel clienteModel3 = new ClienteModel(0, "Nome de Teste 3", "Nome fantasia de teste 3", "Razao Social de Teste 3", "55605654000130");
        clienteDao.salvarCliente(clienteModel1);
        clienteDao.salvarCliente(clienteModel2);
        clienteDao.salvarCliente(clienteModel3);
        
        List<ClienteModel> clientes = clienteDao.getClientes();
        
        assertEquals(3,clientes.size(),"Tamanho da lista deve ser 3");
    }
    
    @Test
    public void aoBuscarClienteComCnpjEspecificoDeveRetornarClienteCorrespondente() throws Exception{
        ClienteDao clienteDao = new ClienteDao(mockConnectionFactory);
        
        ClienteModel clienteModel = new ClienteModel(0, "Nome de Teste 1", "Nome fantasia de teste 1", "Razao Social de Teste 1", "01889095000109"); 
        clienteDao.salvarCliente(clienteModel);
        
        ClienteModel clienteBanco = clienteDao.getClienteByCnpj("01889095000109").get(0);
        
        assertEquals("Nome de Teste 1",clienteBanco.getNome(),"O nome deve ser Nome de Teste 1");
        assertEquals("Nome fantasia de teste 1",clienteBanco.getNomeFantasia(),"O nome fatansia deve ser Nome fantasia de teste 1");
        assertEquals("Razao Social de Teste 1",clienteBanco.getRazaoSocial(),"A razao social deve ser Razao Social de Teste 1");
        assertEquals("01889095000109",clienteBanco.getCnpj(),"O cnpj deve ser 01889095000109");
    }
    
    @Test
    public void aoBuscarClientePorCnpjDeveTrazerMultiplasCorrespondencias() throws Exception{
        ClienteDao clienteDao = new ClienteDao(mockConnectionFactory);
        
        ClienteModel clienteModel1 = new ClienteModel(0, "Nome de Teste 1", "Nome fantasia de teste 1", "Razao Social de Teste 1", "01889095000109");
        ClienteModel clienteModel2 = new ClienteModel(0, "Nome de Teste 2", "Nome fantasia de teste 2", "Razao Social de Teste 2", "01889098000192");
        ClienteModel clienteModel3 = new ClienteModel(0, "Nome de Teste 3", "Nome fantasia de teste 3", "Razao Social de Teste 3", "01889094000130");
        clienteDao.salvarCliente(clienteModel1);
        clienteDao.salvarCliente(clienteModel2);
        clienteDao.salvarCliente(clienteModel3);
        
        List<ClienteModel> clienteBanco = clienteDao.getClienteByCnpj("0188909");
        
        assertEquals(3,clienteBanco.size(),"Tamanho da lista deve ser 3");
    }
    
    @Test
    public void aoChamarMetodoDeRemocaoEnviandoIdVendaDeveRemoverVendaCorrespondente() throws Exception{
        ClienteDao clienteDao = new ClienteDao(mockConnectionFactory);
        
        ClienteModel clienteModel1 = new ClienteModel(0, "Nome de Teste 1", "Nome fantasia de teste 1", "Razao Social de Teste 1", "01889095000109");
        ClienteModel clienteModel2 = new ClienteModel(0, "Nome de Teste 2", "Nome fantasia de teste 2", "Razao Social de Teste 2", "01889098000192");
        ClienteModel clienteModel3 = new ClienteModel(0, "Nome de Teste 3", "Nome fantasia de teste 3", "Razao Social de Teste 3", "01889094000130");
        clienteDao.salvarCliente(clienteModel1);
        clienteDao.salvarCliente(clienteModel2);
        clienteDao.salvarCliente(clienteModel3);
        
        List<ClienteModel> clienteBanco = clienteDao.getClientes();
        assertEquals(3,clienteBanco.size(),"Tamanho da lista deve ser 3");
        
        clienteDao.removeCliente(1);
        
        clienteBanco = clienteDao.getClientes();
        assertEquals(2,clienteBanco.size(),"Tamanho da lista deve ser 2");
        
        ClienteModel cliente = clienteDao.getClienteById(1);
        assertEquals(null,cliente,"Tamanho da lista deve ser 2");
    }
    
}
