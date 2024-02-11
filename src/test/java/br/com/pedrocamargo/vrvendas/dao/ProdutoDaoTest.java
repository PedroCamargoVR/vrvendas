package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.BaseTest;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.vo.ProdutoQuantidadeVO;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class ProdutoDaoTest extends BaseTest {
    private ProdutoDao produtoDao;
    
    @Mock
    public OkHttpClient client;
    @Mock
    public Call call;
    @Mock
    public Response response;
    @Mock
    public ResponseBody responseBody;
    
    @BeforeEach
    public void setupAll() throws Exception{
        MockitoAnnotations.openMocks(this);
        when(client.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(response.body()).thenReturn(responseBody);
        produtoDao = new ProdutoDao(mockConnectionFactory,client);
    }
    
    @Test
    public void aoEnviarVetorDeProdutosComProdutosQueNaoExistemNoBancoDeveCadastralos ()throws Exception {        
        ProdutoModel[] produtos = {
            new ProdutoModel(1,"Coca Cola 2 litros",40,BigDecimal.valueOf(10.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString()),
            new ProdutoModel(2,"Pao de Batata",12,BigDecimal.valueOf(9.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString())
        };
        assertFalse(produtoDao.getProdutos().size() > 0,"Quantidade de produtos no banco deve ser 0");
        produtoDao.salvarProdutoLote(produtos);
        assertTrue(produtoDao.getProdutos().size() == 2,"Quantidade de produtos no banco deve ser 2");
        
        ProdutoModel produto1 = produtoDao.getProdutoById(1);
        
        assertEquals(1,produto1.getId());
        assertEquals("Coca Cola 2 litros",produto1.getDescricao());
        assertEquals(40,produto1.getEstoque());
        assertEquals(BigDecimal.valueOf(10.99).setScale(2,RoundingMode.HALF_UP),produto1.getPreco());
        assertEquals("UN",produto1.getUnidade());
    }
    
    @Test
    public void aoEnviarVetorDeProdutosContendoProdutosQueJaExistemNoBancoDeveAtualizalos () throws Exception{        
        ProdutoModel[] produtos = {
            new ProdutoModel(1,"Coca Cola 2 litros",40,BigDecimal.valueOf(10.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString()),
        };
        produtoDao.salvarProdutoLote(produtos);
        
        ProdutoModel produto1 = produtoDao.getProdutoById(1);        
        assertEquals(1,produto1.getId());
        assertEquals("Coca Cola 2 litros",produto1.getDescricao());
        assertEquals(40,produto1.getEstoque());
        assertEquals(BigDecimal.valueOf(10.99).setScale(2,RoundingMode.HALF_UP),produto1.getPreco());
        assertEquals("UN",produto1.getUnidade());
        
        ProdutoModel[] produtos2 = {
            new ProdutoModel(1,"Coca cola 1 litro",35,BigDecimal.valueOf(7.99),"KG",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString()),
        };
        produtoDao.salvarProdutoLote(produtos2);
        
        produto1 = produtoDao.getProdutoById(1);        
        assertEquals(1,produto1.getId());
        assertEquals("Coca cola 1 litro",produto1.getDescricao());
        assertEquals(35,produto1.getEstoque());
        assertEquals(BigDecimal.valueOf(7.99).setScale(2,RoundingMode.HALF_UP),produto1.getPreco());
        assertEquals("KG",produto1.getUnidade());
    }
    
    @Test
    public void aoConsultarApiProdutosDeveAtualizarBaseDadosConformeDadosObtidosDosprodutos () throws Exception{
        ProdutoModel[] produtos = {
            new ProdutoModel(1,"Coca Cola 2 litros",40,BigDecimal.valueOf(10.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString()),
            new ProdutoModel(2,"Pao de Batata",12,BigDecimal.valueOf(9.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString())
        };
        Gson gson = new Gson();
        when(responseBody.string()).thenReturn(gson.toJson(produtos));
        when(response.code()).thenReturn(200);
        when(response.isSuccessful()).thenReturn(true);
        
        produtoDao.sincronizarProdutosApiBdLocal();
        
        List<ProdutoModel> produtosBanco = produtoDao.getProdutos();
        
        assertEquals(1,produtosBanco.get(0).getId());
        assertEquals("Coca Cola 2 litros",produtosBanco.get(0).getDescricao());
        assertEquals(40,produtosBanco.get(0).getEstoque());
        assertEquals(BigDecimal.valueOf(10.99).setScale(2,RoundingMode.HALF_UP),produtosBanco.get(0).getPreco());
        assertEquals("UN",produtosBanco.get(0).getUnidade());
        
        assertEquals(2,produtosBanco.get(1).getId());
        assertEquals("Pao de Batata",produtosBanco.get(1).getDescricao());
        assertEquals(12,produtosBanco.get(1).getEstoque());
        assertEquals(BigDecimal.valueOf(9.99).setScale(2,RoundingMode.HALF_UP),produtosBanco.get(1).getPreco());
        assertEquals("UN",produtosBanco.get(1).getUnidade());
    }
    
    @Test
    public void deveRetornarListaDeProdutosConformeIdDaVendaInformado () throws Exception{
        ClienteDao clienteDao = new ClienteDao(mockConnectionFactory);
        VendaDao vendaDao = new VendaDao(mockConnectionFactory);
        //Cadastro 2 Produtos
        ProdutoModel[] produtos = {
            new ProdutoModel(1,"Coca Cola 2 litros",40,BigDecimal.valueOf(10.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString()),
            new ProdutoModel(2,"Pao de Batata",12,BigDecimal.valueOf(9.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString())
        };
        produtoDao.salvarProdutoLote(produtos);
        
        //Cadastra Cliente para usar no teste
        ClienteModel clienteModel = new ClienteModel(0, "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        clienteDao.salvarCliente(clienteModel);
        
        //Adiciona venda para ser usada no teste
        VendaModel venda = new VendaModel(0, 1, 1, BigDecimal.valueOf(0));
        venda.adicionarProduto(produtoDao.getProdutoById(1), 5);
        venda.adicionarProduto(produtoDao.getProdutoById(2), 3);
        vendaDao.salvarVenda(venda);
        
        List<ProdutoQuantidadeVO> produtosBanco = produtoDao.getProdutosByVendaId(1);
        
        assertEquals(1,produtosBanco.get(0).getProduto().getId());
        assertEquals("Coca Cola 2 litros",produtosBanco.get(0).getProduto().getDescricao());
        assertEquals(40,produtosBanco.get(0).getProduto().getEstoque());
        assertEquals(BigDecimal.valueOf(10.99).setScale(2,RoundingMode.HALF_UP),produtosBanco.get(0).getProduto().getPreco());
        assertEquals("UN",produtosBanco.get(0).getProduto().getUnidade());
        assertEquals(5,produtosBanco.get(0).getQuantidade());
        
        assertEquals(2,produtosBanco.get(1).getProduto().getId());
        assertEquals("Pao de Batata",produtosBanco.get(1).getProduto().getDescricao());
        assertEquals(12,produtosBanco.get(1).getProduto().getEstoque());
        assertEquals(BigDecimal.valueOf(9.99).setScale(2,RoundingMode.HALF_UP),produtosBanco.get(1).getProduto().getPreco());
        assertEquals("UN",produtosBanco.get(1).getProduto().getUnidade());
        assertEquals(3,produtosBanco.get(1).getQuantidade());
    }
    
    @Test
    public void deveRetornarListaDeProdutosConformeDescricaoDoProdutoInformado () throws Exception{
        //Cadastro 2 Produtos
        ProdutoModel[] produtos = {
            new ProdutoModel(1,"Coca Cola 2 Litros",40,BigDecimal.valueOf(10.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString()),
            new ProdutoModel(2,"Coca Cola 350ML",12,BigDecimal.valueOf(9.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString())
        };
        produtoDao.salvarProdutoLote(produtos);
        
        List<ProdutoModel> produtosBanco = produtoDao.getProdutoByDescricao("Coca cola");
        assertTrue(produtosBanco.size() == 2);
        
        produtosBanco = produtoDao.getProdutoByDescricao("Coca cola 2");
        assertTrue(produtosBanco.size() == 1);
    }
    
    @Test
    public void deveRetornarOEstoqueDoProdutoCorrespondenteAoIdInformado () throws Exception{
        //Cadastro 2 Produtos
        ProdutoModel[] produtos = {
            new ProdutoModel(1,"Coca Cola 2 Litros",40,BigDecimal.valueOf(10.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString()),
            new ProdutoModel(2,"Coca Cola 350ML",12,BigDecimal.valueOf(9.99),"UN",Timestamp.valueOf(LocalDateTime.of(2020, Month.MARCH, 14, 17, 30)).toString())
        };
        produtoDao.salvarProdutoLote(produtos);
        
        Integer estoque = produtoDao.getEstoqueProduto(1);
        assertEquals(40,estoque);
        estoque = produtoDao.getEstoqueProduto(2);
        assertEquals(12,estoque);
    }
}
