package br.com.pedrocamargo.vrvendas.service;

import br.com.pedrocamargo.vrvendas.config.BaseTest;
import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models.ErroFinalizacaoResponseModel;
import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models.ErrorDetailModel;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import br.com.pedrocamargo.vrvendas.model.ProdutoVendaErroFinalizacaoModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.model.VendaProdutoModel;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class VendaServiceTest extends BaseTest {
    
    private VendaService vendaService;
    private ClienteService clienteService;
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
        vendaService = new VendaService(mockConnectionFactory,client);
        clienteService = new ClienteService(mockConnectionFactory);
        produtoService = new ProdutoService(mockConnectionFactory);
        dataUltimaAtualizacao = "0001-01-01T00:00:00Z";
    }

    @Test
    public void deveRetornarVendaCorrespondenteAoIdInformadoETodosOsSeusProdutos() throws Exception{
        //Cadastra Cliente para usar no teste
        clienteService.salvarCliente("0", "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        
        //Cadastrando Produtos para usar no teste
        ProdutoModel produto1 = new ProdutoModel(1,"Coca Cola 2 litros",40,BigDecimal.valueOf(10.99),"UN",dataUltimaAtualizacao);
        ProdutoModel produto2 = new ProdutoModel(2,"Pao de Batata",12,BigDecimal.valueOf(9.99),"UN",dataUltimaAtualizacao);
        ProdutoModel[] produtos = {produto1,produto2};
        produtoService.salvarProdutoLote(produtos);
        
        VendaModel venda = new VendaModel(0,1,2,BigDecimal.valueOf(0));
        venda.adicionarProduto(produto1, 21);
        venda.adicionarProduto(produto2, 9);
        vendaService.salvarVenda(venda);
        
        VendaModel vendaBanco = vendaService.getVendaById(1);
        
        assertEquals(1,vendaBanco.getId());
        assertEquals(1,vendaBanco.getId_cliente());
        assertEquals(2,vendaBanco.getId_status());
        assertEquals(2,vendaBanco.getIdsProdutosVenda().size());
        assertEquals(BigDecimal.valueOf((10.99 * 21) + (9.99 * 9)).setScale(2, RoundingMode.HALF_UP),vendaBanco.getValortotal());
    }
    
    @Test
    public void deveFinalizarAVendaCorretamente() throws Exception{
        //Cadastra Cliente para usar no teste
        clienteService.salvarCliente("0", "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        
        //Cadastrando Produtos para usar no teste
        ProdutoModel produto1 = new ProdutoModel(1,"Coca Cola 2 litros",40,BigDecimal.valueOf(10.99),"UN",dataUltimaAtualizacao);
        ProdutoModel produto2 = new ProdutoModel(2,"Pao de Batata",12,BigDecimal.valueOf(9.99),"UN",dataUltimaAtualizacao);
        ProdutoModel[] produtos = {produto1,produto2};
        produtoService.salvarProdutoLote(produtos);
        
        VendaModel venda = new VendaModel(0,1,1,BigDecimal.valueOf(0));
        venda.adicionarProduto(produto1, 21);
        venda.adicionarProduto(produto2, 9);
        vendaService.salvarVenda(venda);
        
        VendaModel vendaBanco = vendaService.getVendaById(1);
        
        //Mockar retorno da API
        when(responseBody.string()).thenReturn("{\n  \"message\": \"Estoque baixado com sucesso\"\n }");
        when(response.code()).thenReturn(200);
        when(response.isSuccessful()).thenReturn(true);
        
        vendaService.finalizarVenda(vendaBanco);
        vendaBanco = vendaService.getVendaById(1);
        
        assertEquals(3,vendaBanco.getId_status());
        assertTrue(vendaService.getProdutosVendaErroFinalizacao(1).size() == 0);
    }
    
    @Test
    public void deveFinalizarAVendaParcialmenteEInserirSeusProdutosComErroNaTabelaDoRelatorio() throws Exception{
        //Cadastra Cliente para usar no teste
        clienteService.salvarCliente("0", "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        
        //Cadastrando Produtos para usar no teste
        ProdutoModel produto1 = new ProdutoModel(1,"Coca Cola 2 litros",40,BigDecimal.valueOf(10.99),"UN",dataUltimaAtualizacao);
        ProdutoModel produto2 = new ProdutoModel(2,"Pao de Batata",12,BigDecimal.valueOf(9.99),"UN",dataUltimaAtualizacao);
        ProdutoModel produto3 = new ProdutoModel(3,"Pao de Forma",23,BigDecimal.valueOf(5.99),"UN",dataUltimaAtualizacao);
        ProdutoModel[] produtos = {produto1,produto2,produto3};
        produtoService.salvarProdutoLote(produtos);
        
        VendaModel venda = new VendaModel(0,1,1,BigDecimal.valueOf(0));
        venda.adicionarProduto(produto1, 35);
        venda.adicionarProduto(produto2, 9);
        venda.adicionarProduto(produto3, 9);
        vendaService.salvarVenda(venda);
        
        VendaModel vendaBanco = vendaService.getVendaById(1);
        
        //Mockar retorno da API
        Gson gson = new Gson();
        Map<Integer, ErrorDetailModel> errors = new LinkedHashMap<>();
        errors.put(1,new ErrorDetailModel(32,35,"Estoque insuficiente. Estoque atual: 32, Quantidade solicitada: 35"));
        errors.put(2,new ErrorDetailModel(8,9,"Estoque insuficiente. Estoque atual: 8, Quantidade solicitada: 9"));
        
        ErroFinalizacaoResponseModel erroFinalizacao = new ErroFinalizacaoResponseModel("Estoque baixado com erros parciais",errors);
        
        when(responseBody.string()).thenReturn(gson.toJson(erroFinalizacao));
        when(response.code()).thenReturn(207);
        when(response.isSuccessful()).thenReturn(true);
        
        vendaService.finalizarVenda(vendaBanco);
        vendaBanco = vendaService.getVendaById(1);
        List<VendaProdutoModel> vendaProduto = vendaService.getProdutosVendaByIdVenda(vendaBanco.getId());
        
        assertEquals(2,vendaBanco.getId_status());
        
        List<ProdutoVendaErroFinalizacaoModel> produtosErro = vendaService.getProdutosVendaErroFinalizacao(1);
        
        assertEquals(2,produtosErro.size());
        assertEquals(35,produtosErro.get(0).getQuantidade());
        assertEquals(9,produtosErro.get(1).getQuantidade());
        assertEquals("Estoque insuficiente. Estoque atual: 32, Quantidade solicitada: 35",produtosErro.get(0).getMotivoErro());
        assertEquals("Estoque insuficiente. Estoque atual: 8, Quantidade solicitada: 9",produtosErro.get(1).getMotivoErro());
        assertEquals(vendaProduto.get(0).getId(),produtosErro.get(0).getId_vendaproduto());
        assertEquals(vendaProduto.get(1).getId(),produtosErro.get(1).getId_vendaproduto());
    }
    
    @Test
    public void deveRetornarErroNaVendaENaoFinalizarAVendaNemAdicionarNenhumProdutoATabelaDoRelatorio() throws Exception{
        //Cadastra Cliente para usar no teste
        clienteService.salvarCliente("0", "Nome de Teste", "Nome fantasia de teste", "Razao Social de Teste", "12123123000164");
        
        //Cadastrando Produtos para usar no teste
        ProdutoModel produto1 = new ProdutoModel(1,"Coca Cola 2 litros",40,BigDecimal.valueOf(10.99),"UN",dataUltimaAtualizacao);
        ProdutoModel produto2 = new ProdutoModel(2,"Pao de Batata",12,BigDecimal.valueOf(9.99),"UN",dataUltimaAtualizacao);
        ProdutoModel[] produtos = {produto1,produto2};
        produtoService.salvarProdutoLote(produtos);
        
        VendaModel venda = new VendaModel(0,1,1,BigDecimal.valueOf(0));
        venda.adicionarProduto(produto1, 35);
        venda.adicionarProduto(produto2, 9);
        vendaService.salvarVenda(venda);
        
        VendaModel vendaBanco = vendaService.getVendaById(1);
        
        //Mockar retorno da API
        Gson gson = new Gson();
        Map<Integer, ErrorDetailModel> errors = new LinkedHashMap<>();
        errors.put(1,new ErrorDetailModel(32,35,"Estoque insuficiente. Estoque atual: 32, Quantidade solicitada: 35"));
        errors.put(2,new ErrorDetailModel("Produto com ID 222 não encontrado"));
        
        ErroFinalizacaoResponseModel erroFinalizacao = new ErroFinalizacaoResponseModel("Não passou validações",errors);
        
        when(responseBody.string()).thenReturn(gson.toJson(erroFinalizacao));
        when(response.code()).thenReturn(400);
        when(response.isSuccessful()).thenReturn(true);
        
        vendaService.finalizarVenda(vendaBanco);
        vendaBanco = vendaService.getVendaById(1);
        List<ProdutoVendaErroFinalizacaoModel> produtosErro = vendaService.getProdutosVendaErroFinalizacao(1);
        
        assertEquals(1,vendaBanco.getId_status());
        assertEquals(0,produtosErro.size());
    }
}
