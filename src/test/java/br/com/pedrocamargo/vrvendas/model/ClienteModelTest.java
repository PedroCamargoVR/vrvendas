package br.com.pedrocamargo.vrvendas.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class ClienteModelTest {
    
    @Test
    public void deveCriarUmaInstanciaDeClienteModelERetornarOsDadosInformadosNosParametrosDoConstrutor() throws Exception{
        ClienteModel clienteModel = new ClienteModel(1,"Nome de teste","Nome fantasia de teste","Razao Social de Teste", "12123123000649");
        
        assertEquals(1,clienteModel.getId());
        assertEquals("Nome de teste",clienteModel.getNome());
        assertEquals("Nome fantasia de teste",clienteModel.getNomeFantasia());
        assertEquals("Razao Social de Teste",clienteModel.getRazaoSocial());
        assertEquals("12123123000649",clienteModel.getCnpj());        
    }
    
    @Test
    public void deveCriarUmaInstanciaDeClienteModelEAlterarSeusDadosCorretamenteComMetodosSetters() throws Exception{
        ClienteModel clienteModel = new ClienteModel(1,"Nome de teste","Nome fantasia de teste","Razao Social de Teste", "12123123000649");
        
        clienteModel.setNome("Nome Alterado");
        clienteModel.setNomeFantasia("Nome fantasia alterado");
        clienteModel.setRazaoSocial("Razao Social Alterada");
        
        assertEquals(1,clienteModel.getId());
        assertEquals("Nome Alterado",clienteModel.getNome());
        assertEquals("Nome fantasia alterado",clienteModel.getNomeFantasia());
        assertEquals("Razao Social Alterada",clienteModel.getRazaoSocial());
        assertEquals("12123123000649",clienteModel.getCnpj());
    }
    
}
