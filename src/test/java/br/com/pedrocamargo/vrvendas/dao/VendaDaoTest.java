package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.BaseTest;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.vo.VendaVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class VendaDaoTest extends BaseTest{
    private VendaDao vendaDao;
    private ClienteDao clienteDao;
    private ProdutoDao produtoDao;
    private String dataUltimaAtualizacao;
    
    @BeforeEach
    public void setupAll() throws Exception{
        vendaDao = new VendaDao(mockConnectionFactory);
        clienteDao = new ClienteDao(mockConnectionFactory);
        produtoDao = new ProdutoDao(mockConnectionFactory);
        dataUltimaAtualizacao = "0001-01-01T00:00:00Z";
    }
    
    @Test
    public void deveSalvarVendaModelPassadaPorParametroCorretamenteERetornarSeuId () throws Exception{
        //Cadastra Cliente para usar no teste
        ClienteModel clienteModel = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel);
        
        VendaModel venda = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        Integer id = vendaDao.salvarVenda(venda);
        
        assertEquals(1,id);
        VendaModel vendaBanco = vendaDao.getVendaById(1);
        
        assertEquals(1,vendaBanco.getId());
        assertEquals(1,vendaBanco.getId_cliente());
        assertEquals(2,vendaBanco.getId_status());
        assertEquals(BigDecimal.valueOf(0).setScale(2,RoundingMode.HALF_UP),vendaBanco.getValortotal());
    }
    
    @Test
    public void aoEnviarVendaJaExistenteDeveAtualizarVendaCorretamenteERetornarSeuId () throws Exception{
        //Cadastra Cliente para usar no teste
        ClienteModel clienteModel1 = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        ClienteModel clienteModel2 = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel1);
        clienteDao.salvarCliente(clienteModel2);
        
        VendaModel venda = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        Integer id = vendaDao.salvarVenda(venda);        
        assertEquals(1,id);
        
        VendaModel vendaBanco = vendaDao.getVendaById(1);
        vendaBanco.setIdCliente(2);
        vendaBanco.setIdStatus(3);
        id = vendaDao.salvarVenda(vendaBanco);        
        assertEquals(1,id);
        
        vendaBanco = vendaDao.getVendaById(1);
        assertEquals(1,vendaBanco.getId());
        assertEquals(2,vendaBanco.getId_cliente());
        assertEquals(3,vendaBanco.getId_status());
        
    }
     
    @Test
    public void deveRetornarVendasPertencentesAoIdClienteInformado () throws Exception{
        //Cadastra Cliente para usar no teste
        ClienteModel clienteModel1 = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel1);
        VendaModel venda = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        vendaDao.salvarVenda(venda);
        vendaDao.salvarVenda(venda);
        vendaDao.salvarVenda(venda);
        
        assertTrue(vendaDao.getVendasByIdCliente(1).size() == 3);
    }
    
    @Test
    public void deveRetornarValueObjectDeTodasAsVendas () throws Exception{
        //Cadastra Cliente para usar no teste
        ClienteModel clienteModel1 = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel1);
        VendaModel venda = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        vendaDao.salvarVenda(venda);
        vendaDao.salvarVenda(venda);
        vendaDao.salvarVenda(venda);
                
        assertTrue(vendaDao.getAllVendasVo().size() == 3);
    }
    
    @Test
    public void deveRetornarOValueObjectDasVendasComOIdStatusInformado () throws Exception{
        //Cadastra Cliente para usar no teste
        ClienteModel clienteModel1 = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel1);
        VendaModel venda1 = new VendaModel(0,1,1,BigDecimal.valueOf(0));
        VendaModel venda2 = new VendaModel(0,1,1,BigDecimal.valueOf(0));
        VendaModel venda3 = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        VendaModel venda4 = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        VendaModel venda5 = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        VendaModel venda6 = new VendaModel(0,1,3,BigDecimal.valueOf(0));
        VendaModel venda7 = new VendaModel(0,1,3,BigDecimal.valueOf(0));
        VendaModel venda8 = new VendaModel(0,1,3,BigDecimal.valueOf(0));
        VendaModel venda9 = new VendaModel(0,1,3,BigDecimal.valueOf(0));
        vendaDao.salvarVenda(venda1);
        vendaDao.salvarVenda(venda2);
        vendaDao.salvarVenda(venda3);
        vendaDao.salvarVenda(venda4);
        vendaDao.salvarVenda(venda5);
        vendaDao.salvarVenda(venda6);
        vendaDao.salvarVenda(venda7);
        vendaDao.salvarVenda(venda8);
        vendaDao.salvarVenda(venda9);
                
        assertTrue(vendaDao.getVendasVoByStatus(1).size() == 2);
        assertTrue(vendaDao.getVendasVoByStatus(2).size() == 3);
        assertTrue(vendaDao.getVendasVoByStatus(3).size() == 4);
    }
    
    @Test
    public void deveRetornarOValueObjectDaVendaCorrespondenteAoIdInformado () throws Exception{
        //Cadastra Cliente para usar no teste
        ClienteModel clienteModel1 = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel1);
        VendaModel venda1 = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        vendaDao.salvarVenda(venda1);
        
        VendaVO vendaVo = vendaDao.getVendaVoById(1);
        
        assertEquals(1,vendaVo.getId());
        assertEquals(2,vendaVo.getId_status());
        assertEquals(1,vendaVo.getCliente().getId());
        assertEquals("Nome de Teste",vendaVo.getCliente().getNome());
        assertEquals("Nome fantasia de teste",vendaVo.getCliente().getNomeFantasia());
        assertEquals("Razao Social de Teste",vendaVo.getCliente().getRazaoSocial());
        assertEquals("12123123000164",vendaVo.getCliente().getCnpj());
    }
    
    @Test
    public void deveExcluirAVendaCorrespondenteAoIdInformado () throws Exception{
        //Cadastra Cliente para usar no teste
        ClienteModel clienteModel1 = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel1);
        VendaModel venda1 = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        vendaDao.salvarVenda(venda1);
        
        assertTrue(vendaDao.getAllVendasVo().size() == 1);
        
        vendaDao.excluirVenda(1);
        
        assertFalse(vendaDao.getAllVendasVo().size() > 0);
    }
    
    @Test
    public void deveAtualizarStatusDaVendaCorrespondenteAoIdInformado () throws Exception{
        //Cadastra Cliente para usar no teste
        ClienteModel clienteModel1 = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel1);
        VendaModel venda1 = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        vendaDao.salvarVenda(venda1);
        
        VendaModel venda = vendaDao.getVendaById(1);
        assertEquals(2,venda.getId_status());
        
        vendaDao.atualizarStatusVenda(1, 3);
        
        venda = vendaDao.getVendaById(1);
        assertEquals(3,venda.getId_status());
    }
}
