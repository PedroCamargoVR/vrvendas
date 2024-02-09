package br.com.pedrocamargo.vrvendas.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProdutoModelTest {
    
    @Test
    public void deveCriarCorretamenteUmaInstanciaDeProdutoERetornarSeusDados() throws Exception{
        ProdutoModel produto = new ProdutoModel(1,"Produto de teste",35,BigDecimal.valueOf(5),"UN",Timestamp.from(Instant.now()).toString());
        
        assertEquals(1,produto.getId());
        assertEquals("Produto de teste",produto.getDescricao());
        assertEquals(35,produto.getEstoque());
        assertEquals(BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP),produto.getPreco());
        assertEquals("UN",produto.getUnidade());
    }
    
    @Test
    public void deveCriarUmaInstanciadeProdutoModelEAoAlterarEstoqueDeveRetornarOEstoqueAlteradoCorretamente() throws Exception{
        ProdutoModel produto = new ProdutoModel(1,"Produto de teste",35,new BigDecimal(5),"UN",Timestamp.from(Instant.now()).toString());
        
        produto.setEstoque(70);

        assertEquals(70,produto.getEstoque());
    }
    
    @Test
    public void aoCompararDoisProdutosComMesmoIdDeveRetornarQueSaoIguaisMesmoSendoInstanciasDiferentes() throws Exception{
        ProdutoModel produto1 = new ProdutoModel(1,"Produto de teste",35,new BigDecimal(5),"UN",Timestamp.from(Instant.now()).toString());
        ProdutoModel produto2 = new ProdutoModel(1,"Produto de teste com outra descricao",56,new BigDecimal(10),"KG",Timestamp.from(Instant.now()).toString());
        
        Boolean equals = produto1.equals(produto2);
        
        assertTrue(equals);
    }
    
}
