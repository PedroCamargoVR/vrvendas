package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class VendaDaoTest extends BaseTest{
   private VendaDao vendaDao;
    
    @BeforeEach
    public void setupAll() throws Exception{
        vendaDao = new VendaDao(mockConnectionFactory);
    }
    
    @Test
    public void deveSalvarVendaModelPassadaPorParametroCorretamenteERetornarSeuId () throws Exception{
        
    }
    
    @Test
    public void aoEnviarVendaJaExistenteDeveAtualizarVendaCorretamenteERetornarSeuId () throws Exception{
        
    }
    
   
    @Test
    public void deveRetornarVendaCorrespondenteAoIdInformado () throws Exception{
        
    }
     
    @Test
    public void deveRetornarVendasPertencentesAoIdClienteInformado () throws Exception{
        
    }
    
    @Test
    public void deveRetornarValueObjectDeTodasAsVendas () throws Exception{
        
    }
    
    @Test
    public void deveRetornarOValueObjectDasVendasComOIdStatusInformado () throws Exception{
        
    }
    
    @Test
    public void deveRetornarOValueObjectDaVendaCorrespondenteAoIdInformado () throws Exception{
        
    }
    
    @Test
    public void deveExcluirAVendaCorrespondenteAoIdInformadoETodosOsSeusProdutos () throws Exception{
        
    }
    
    @Test
    public void deveAtualizarStatusDaVendaCorrespondenteAoIdInformado () throws Exception{
        
    }
}
