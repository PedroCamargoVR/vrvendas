package br.com.pedrocamargo.vrvendas.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class VendaModelTest {
    
    @Test
    public void deveCriarUmaInstanciaComDatasVaziasERetornarCorretamenteSeusDados () throws Exception{
        VendaModel venda = new VendaModel(258,14,2,BigDecimal.valueOf(5));
        
        assertEquals(258,venda.getId());
        assertEquals(14,venda.getId_cliente());
        assertEquals(2,venda.getId_status());
        assertEquals(BigDecimal.valueOf(5).setScale(2),venda.getValortotal());
    }
    
    @Test
    public void deveCriarUmaInstanciaComDatasPreenchidasERetornarTodosOsDadosCorretamente () throws Exception{
        Timestamp created_at = Timestamp.valueOf(LocalDateTime.of(2024, 2, 9, 17, 30, 58, 1));
        Timestamp updated_at = Timestamp.valueOf(LocalDateTime.of(2024, 2, 9, 18, 58, 42, 1));
        VendaModel venda = new VendaModel(258,14,2,BigDecimal.valueOf(5),created_at,updated_at);
        
        assertEquals(258,venda.getId());
        assertEquals(14,venda.getId_cliente());
        assertEquals(2,venda.getId_status());
        assertEquals(BigDecimal.valueOf(5).setScale(2),venda.getValortotal());
        assertEquals(created_at,venda.getCreatedAt());
        assertEquals(updated_at,venda.getupdatedAt());
    }
    
    @Test
    public void deveCriarUmaInstanciaDaVendaEAposAtualizarDadosRetornarDadosAtualizadosCorretamente () throws Exception{
        VendaModel venda = new VendaModel(258,14,2,BigDecimal.valueOf(5));
        
        venda.setIdCliente(126);
        venda.setIdStatus(3);
        
        assertEquals(258,venda.getId());
        assertEquals(126,venda.getId_cliente());
        assertEquals(3,venda.getId_status());
        assertEquals(BigDecimal.valueOf(5).setScale(2),venda.getValortotal());
        
    }
   
    @Test
    public void deveAdicionarUmProdutoAVendaConformeProdutoModelEQuantidadePassadosERetornarloCorretamente () throws Exception{
        VendaModel venda = new VendaModel(258,14,2,BigDecimal.valueOf(5));
        ProdutoModel produto = new ProdutoModel(25,"Coca Cola 2 Litros",30,BigDecimal.valueOf(10.99),"UN","1707500217");
        
        venda.adicionarProduto(produto, 5);
        
        venda.getProdutosVenda().forEach((produtoVenda,quantidade) -> {
            assertEquals(25,produtoVenda.getId());
            assertEquals("Coca Cola 2 Litros",produtoVenda.getDescricao());
            assertEquals(30,produtoVenda.getEstoque());
            assertEquals(BigDecimal.valueOf(10.99).setScale(2),produtoVenda.getPreco());
            assertEquals("UN",produtoVenda.getUnidade());
            assertEquals("1707500217",produtoVenda.getUltimaAtualizacao());
            assertEquals(5,quantidade);
        });
        
        assertEquals(BigDecimal.valueOf(5 + (10.99 * 5)).setScale(2),venda.getValortotal());   
    }
     
    @Test
    public void deveAdicionarAlgunsProdutosERetornarASomaDeSeusPrecosNoValorTotalDaVendaAssimComoAListaCorretaDeProdutos () throws Exception{
        VendaModel venda = new VendaModel(258,14,2,BigDecimal.valueOf(5));
        Map<ProdutoModel,Integer> produtos = new LinkedHashMap<>();
        ProdutoModel produto1 = new ProdutoModel(25,"Coca Cola 2 Litros",30,BigDecimal.valueOf(10.99),"UN","1707501117");
        ProdutoModel produto2 = new ProdutoModel(32,"Pizza de Calabresa",12,BigDecimal.valueOf(25.99),"UN","1707501117");
        ProdutoModel produto3 = new ProdutoModel(45,"Assoalho de Celta",3,BigDecimal.valueOf(230.99),"UN","1707501117");
        ProdutoModel produto4 = new ProdutoModel(56,"Vinagre 1LT",54,BigDecimal.valueOf(12.99),"LT","1707501117");
        ProdutoModel produto5 = new ProdutoModel(284,"Pimenta Calabresa 5KG",284,BigDecimal.valueOf(5.99),"KG","1707501117");
        produtos.put(produto1, 10);
        produtos.put(produto2, 5);
        produtos.put(produto3, 1);
        produtos.put(produto4, 12);
        produtos.put(produto5, 52);
        
        produtos.forEach((produto,quantidade) -> {
            venda.adicionarProduto(produto, quantidade);
        });
        
        BigDecimal valorQueDeveSerNoTotalDaVenda = new BigDecimal(5);
        valorQueDeveSerNoTotalDaVenda = valorQueDeveSerNoTotalDaVenda.add(new BigDecimal(produto1.getPreco().multiply(BigDecimal.valueOf(10)).doubleValue()));
        valorQueDeveSerNoTotalDaVenda = valorQueDeveSerNoTotalDaVenda.add(new BigDecimal(produto2.getPreco().multiply(BigDecimal.valueOf(5)).doubleValue()));
        valorQueDeveSerNoTotalDaVenda = valorQueDeveSerNoTotalDaVenda.add(new BigDecimal(produto3.getPreco().multiply(BigDecimal.valueOf(1)).doubleValue()));
        valorQueDeveSerNoTotalDaVenda = valorQueDeveSerNoTotalDaVenda.add(new BigDecimal(produto4.getPreco().multiply(BigDecimal.valueOf(12)).doubleValue()));
        valorQueDeveSerNoTotalDaVenda = valorQueDeveSerNoTotalDaVenda.add(new BigDecimal(produto5.getPreco().multiply(BigDecimal.valueOf(52)).doubleValue()));
        
        //Verifica se valor total foi corretamente somado
        assertEquals(valorQueDeveSerNoTotalDaVenda.setScale(2, RoundingMode.HALF_UP),venda.getValortotal(),"Valor nao foi corretamente somado ao total da venda");
        
        //Verifica se produtos foram adicionados corretamente e quantidades corretas
         Map<ProdutoModel,Integer> produtosBanco = venda.getProdutosVenda();
         produtosBanco.forEach((ProdutoModel produto,Integer quantidade) -> {
             assertTrue(produtos.containsKey(produto),"Produto nao foi corretamente inserido na venda");
             assertTrue(produtos.get(produto).equals(quantidade),"Quantidade nao foi corretamente inserida na venda");
         });
    }
    
    @Test
    public void deveRemoverUmProdutoPassadoEDebitarSeuPrecoDoValorTotalDaVenda () throws Exception{
        VendaModel venda = new VendaModel(258,14,2,BigDecimal.valueOf(5));
        ProdutoModel produto = new ProdutoModel(25,"Coca Cola 2 Litros",30,BigDecimal.valueOf(10.99),"UN","1707500217");
        
        venda.adicionarProduto(produto, 5);
        assertFalse(venda.getProdutosVenda().isEmpty());
        
        venda.removerProduto(produto);
        assertTrue(venda.getProdutosVenda().isEmpty());
        
        assertEquals(BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP),venda.getValortotal());
    }
    
    @Test
    public void deveRemoverProdutoInformadoEAdicionarOutroProdutoNoLugarModificandoCorretamenteValorTotalDaVenda () throws Exception{
        VendaModel venda = new VendaModel(258,14,2,BigDecimal.valueOf(5));
        ProdutoModel produto1 = new ProdutoModel(25,"Coca Cola 2 Litros",30,BigDecimal.valueOf(10.99),"UN","1707501117");
        ProdutoModel produto2 = new ProdutoModel(32,"Pizza de Calabresa",12,BigDecimal.valueOf(25.99),"UN","1707501117");
        
        venda.adicionarProduto(produto1, 10);
        assertFalse(venda.getProdutosVenda().isEmpty(),"Lista de produtos nao pode mais ser vazia");
        assertEquals(BigDecimal.valueOf(5 + (produto1.getPreco().doubleValue() * 10)).setScale(2, RoundingMode.HALF_UP),venda.getValortotal(),"Valor total da venda deve ser 114.90");
        
        venda.editarProduto(produto1, produto2, 6);
        
        assertTrue(venda.getProdutosVenda().size() == 1,"Quantidade de produtos ainda deve ser 1");
        
        venda.getProdutosVenda().forEach((produto,quantidade) -> {
            assertTrue(produto.equals(produto2), "Produto na veda devee ser o produto 2");
            assertTrue(quantidade.equals(6),"Quantidade do produto deve ser 6");
        });
        
        assertEquals(BigDecimal.valueOf(5 + (produto2.getPreco().doubleValue() * 6)).setScale(2, RoundingMode.HALF_UP),venda.getValortotal(),"Valor total da venda deve ser 160,94");
    }
    
    @Test
    public void deveEditarAQuantidadeDeUmProdutoExistenteNaVendaEAtualizarValorTotalDaVendaCorretamente () throws Exception{
        VendaModel venda = new VendaModel(258,14,2,BigDecimal.valueOf(5));
        ProdutoModel produto1 = new ProdutoModel(25,"Coca Cola 2 Litros",30,BigDecimal.valueOf(10.99),"UN","1707501117");
        ProdutoModel produto2 = new ProdutoModel(32,"Pizza de Calabresa",12,BigDecimal.valueOf(25.99),"UN","1707501117");
        
        venda.adicionarProduto(produto1, 10);
        venda.adicionarProduto(produto2, 6);
        assertTrue(venda.getProdutosVenda().size() == 2,"Quantidade de produtos deve ser 2");
        assertEquals(
                BigDecimal.valueOf(
                        5 + ((produto1.getPreco().doubleValue() * 10) + (produto2.getPreco().doubleValue() * 6))
                ).setScale(2, RoundingMode.HALF_UP),
                venda.getValortotal(),
                "Valor total da venda deve ser 160,94"
        );
        
        venda.editarQuantidadeProduto(produto1, 17);
        venda.editarQuantidadeProduto(produto2, 9);
        assertTrue(venda.getProdutosVenda().size() == 2,"Quantidade de produtos ainda deve ser 2");
        assertEquals(
                BigDecimal.valueOf(
                        5 + ((produto1.getPreco().doubleValue() * 17) + (produto2.getPreco().doubleValue() * 9))
                ).setScale(2, RoundingMode.HALF_UP),
                venda.getValortotal(),
                "Valor total da venda deve ser 414,75"
        );
    }
    
    @Test
    public void deveRetornarListagemDeTodosOsProdutosPresentesNaVendaCorretamente () throws Exception{
        VendaModel venda = new VendaModel(258,14,2,BigDecimal.valueOf(5));
        Map<ProdutoModel,Integer> produtos = new LinkedHashMap<>();
        ProdutoModel produto1 = new ProdutoModel(25,"Coca Cola 2 Litros",30,BigDecimal.valueOf(10.99),"UN","1707501117");
        ProdutoModel produto2 = new ProdutoModel(32,"Pizza de Calabresa",12,BigDecimal.valueOf(25.99),"UN","1707501117");
        ProdutoModel produto3 = new ProdutoModel(45,"Assoalho de Celta",3,BigDecimal.valueOf(230.99),"UN","1707501117");
        ProdutoModel produto4 = new ProdutoModel(56,"Vinagre 1LT",54,BigDecimal.valueOf(12.99),"LT","1707501117");
        ProdutoModel produto5 = new ProdutoModel(284,"Pimenta Calabresa 5KG",284,BigDecimal.valueOf(5.99),"KG","1707501117");
        produtos.put(produto1, 56);
        produtos.put(produto2, 3);
        produtos.put(produto3, 7);
        produtos.put(produto4, 48);
        produtos.put(produto5, 29);
        
        produtos.forEach((produto,quantidade) -> {
            venda.adicionarProduto(produto, quantidade);
        });
        assertTrue(venda.getProdutosVenda().size() == 5);
        
        Map<ProdutoModel,Integer> produtosBanco = venda.getProdutosVenda();
        //Verifica se produtos foram adicionados corretamente e quantidades corretas
        produtosBanco.forEach((ProdutoModel produto,Integer quantidade) -> {
            assertTrue(produtos.containsKey(produto),"Produto nao foi corretamente inserido na venda");
            assertTrue(produtos.get(produto).equals(quantidade),"Quantidade nao foi corretamente inserida na venda");
        });
        
    }
    
    @Test
    public void deveRetornarListagemApenasDosIdsDosProdutosPresentesNaVenda () throws Exception{
        VendaModel venda = new VendaModel(258,14,2,BigDecimal.valueOf(5));
        Map<ProdutoModel,Integer> produtos = new LinkedHashMap<>();
        ProdutoModel produto1 = new ProdutoModel(25,"Coca Cola 2 Litros",30,BigDecimal.valueOf(10.99),"UN","1707501117");
        ProdutoModel produto2 = new ProdutoModel(32,"Pizza de Calabresa",12,BigDecimal.valueOf(25.99),"UN","1707501117");
        ProdutoModel produto3 = new ProdutoModel(45,"Assoalho de Celta",3,BigDecimal.valueOf(230.99),"UN","1707501117");
        ProdutoModel produto4 = new ProdutoModel(56,"Vinagre 1LT",54,BigDecimal.valueOf(12.99),"LT","1707501117");
        ProdutoModel produto5 = new ProdutoModel(284,"Pimenta Calabresa 5KG",284,BigDecimal.valueOf(5.99),"KG","1707501117");
        produtos.put(produto1, 56);
        produtos.put(produto2, 3);
        produtos.put(produto3, 7);
        produtos.put(produto4, 48);
        produtos.put(produto5, 29);
        
        produtos.forEach((produto,quantidade) -> {
            venda.adicionarProduto(produto, quantidade);
        });
        assertTrue(venda.getProdutosVenda().size() == 5);
        
        List<Integer> idsProdutos = venda.getIdsProdutosVenda();
        assertEquals(idsProdutos.get(0),produto1.getId());
        assertEquals(idsProdutos.get(1),produto2.getId());
        assertEquals(idsProdutos.get(2),produto3.getId());
        assertEquals(idsProdutos.get(3),produto4.getId());
        assertEquals(idsProdutos.get(4),produto5.getId());
    }
}
