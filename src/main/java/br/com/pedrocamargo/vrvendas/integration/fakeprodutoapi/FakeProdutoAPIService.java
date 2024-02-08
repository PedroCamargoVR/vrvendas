package br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi;

import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models.EnviarProdutoModel;
import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.models.ErroFinalizacaoResponseModel;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.swing.JOptionPane;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;
import java.util.List;


public class FakeProdutoAPIService {
    private OkHttpClient client;
    private String urlApi;
    private final MediaType JSON;
    
    public FakeProdutoAPIService(){
        this.client = new OkHttpClient();
        this.JSON = MediaType.get("application/json; charset=utf-8");
        this.urlApi = "http://192.168.1.104:3000";
    }
    
    public ErroFinalizacaoResponseModel obterObjetoErro(String json){
        
        Gson gson = new Gson();
        ErroFinalizacaoResponseModel response = gson.fromJson(json, ErroFinalizacaoResponseModel.class);
        
        return response;        
    }
    
    public ProdutoModel[] getProdutosApi(){
        ProdutoModel[] produtos = null;
        
        Request request = new Request.Builder()
                .url(urlApi+"/produtos")
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();

                Gson gson = new Gson();
                produtos = gson.fromJson(jsonResponse, ProdutoModel[].class);
                
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao capturar produtos da API\n"+response, "Erro", JOptionPane.ERROR_MESSAGE);
                System.out.println("Falha na requisição: " + response);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao capturar produtos da API\n"+e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return produtos;
    }
    
    public Map<Integer,String> postProdutosApi(List<EnviarProdutoModel> produtos) throws Exception {
        
        Gson gson = new Gson();
        String jsonProdutos = gson.toJson(produtos);
        
        RequestBody body = RequestBody.create(jsonProdutos, JSON);
        Request request = new Request.Builder()
                .url(urlApi+"/produtos/baixa")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return Collections.singletonMap(response.code(), response.body().string());
        }
    }
    
}
