package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.config.BaseTest;
import br.com.pedrocamargo.vrvendas.dao.ProdutoDao;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import com.google.gson.Gson;
import java.math.BigDecimal;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class ProdutoServiceTest extends BaseTest {
    
    private ProdutoService produtoService;
    private String dataUltimaAtualizacao;
    
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
        produtoService = new ProdutoService(mockConnectionFactory,client);
        dataUltimaAtualizacao = "0001-01-01T00:00:00Z";
    }
    
    @Test
    public void deveAtualizarEstoqueDoProdutoInformadoComBaseNoEstoqueAtualNoBancoDeDados() throws Exception{
        ProdutoModel produto1 = new ProdutoModel(1,"Coca Cola 2 litros",40,BigDecimal.valueOf(10.99),"UN",dataUltimaAtualizacao);
        ProdutoModel produto2 = new ProdutoModel(2,"Pao de Batata",12,BigDecimal.valueOf(9.99),"UN",dataUltimaAtualizacao);
        ProdutoModel[] produtos = {produto1,produto2};
        produtoService.salvarProdutoLote(produtos);
        
        //Mocka retorno da API
        ProdutoModel[] produtos2 = {
            new ProdutoModel(1,"Coca Cola 2 litros",62,BigDecimal.valueOf(10.99),"UN",dataUltimaAtualizacao),
            new ProdutoModel(2,"Pao de Batata",6,BigDecimal.valueOf(9.99),"UN",dataUltimaAtualizacao)
        };
        Gson gson = new Gson();
        when(responseBody.string()).thenReturn(gson.toJson(produtos2));
        when(response.code()).thenReturn(200);
        when(response.isSuccessful()).thenReturn(true);
        
        produto1 = produtoService.getProdutoById(1);
        produto2 = produtoService.getProdutoById(2);
        
        assertEquals(40,produto1.getEstoque());
        assertEquals(12,produto2.getEstoque());
        
        produtoService.sincronizarProdutosApiBdLocal();
        produtoService.atualizaEstoqueProduto(produto1);
        produtoService.atualizaEstoqueProduto(produto1);
        
        produto1 = produtoService.getProdutoById(1);
        produto2 = produtoService.getProdutoById(2);
        
        assertEquals(62,produto1.getEstoque());
        assertEquals(6,produto2.getEstoque());
    }
}
