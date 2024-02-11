package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.config.BaseTest;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteServiceTest extends BaseTest {
    
    private ClienteService clienteService;
    
    @BeforeEach
    public void setUp(){
        this.clienteService = new ClienteService(mockConnectionFactory);
    }
    
    @Test
    public void deveAdicionarClienteComOsDadosInformadosNoParametroDoMetodo() throws Exception{
        assertTrue(clienteService.getClientes().isEmpty());
        clienteService.salvarCliente("0", "Cliente de Teste", "Nome Fantasia de Teste", "Razao Social de Teste LTDA", "46773419000187");
        assertTrue(clienteService.getClientes().size() == 1);
        
        ClienteModel cliente = clienteService.getClienteById(1);
        
        assertEquals(1,cliente.getId());
        assertEquals("Cliente de Teste",cliente.getNome());
        assertEquals("Nome Fantasia de Teste",cliente.getNomeFantasia());
        assertEquals("Razao Social de Teste LTDA",cliente.getRazaoSocial());
        assertEquals("46773419000187",cliente.getCnpj());
    }
    
    @Test
    public void aoInformarDadosDeUmClienteQueJaExisteNosParametrosDeveAtualizarOsDadosDesteCliente() throws Exception{
        assertTrue(clienteService.getClientes().isEmpty());
        clienteService.salvarCliente("0", "Cliente de Teste", "Nome Fantasia de Teste", "Razao Social de Teste LTDA", "46773419000187");
        assertTrue(clienteService.getClientes().size() == 1);
        clienteService.salvarCliente("1", "Nome Alterado", "Nome Fantasia Alterado", "Razao Social Alterada LTDA", "02293488000118");
        
        ClienteModel cliente = clienteService.getClienteById(1);
        
        assertEquals(1,cliente.getId());
        assertEquals("Nome Alterado",cliente.getNome());
        assertEquals("Nome Fantasia Alterado",cliente.getNomeFantasia());
        assertEquals("Razao Social Alterada LTDA",cliente.getRazaoSocial());
        assertEquals("02293488000118",cliente.getCnpj());
    }
    
}
