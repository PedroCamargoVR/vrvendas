package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.ConnectionFactory;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProdutoDao {
    private ConnectionFactory connF;
    OkHttpClient client;
    private StringBuilder sql;
    
    public ProdutoDao(){
        this.connF = new ConnectionFactory();
        this.client = new OkHttpClient();
        this.sql = new StringBuilder();
    }
    
    public ProdutoModel[] getProdutosApi(){
        ProdutoModel[] produtos = null;
        
        Request request = new Request.Builder()
                .url("http://192.168.1.104:3000/produtos")
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
    
    
    public void salvarProdutoLote(ProdutoModel[] produtos) throws SQLException{
        if(produtos.length > 0){
            try (Connection conn = connF.getConnection()) {
                sql.setLength(0);
                sql.append("SELECT * FROM produtos ORDER BY id");
                
                PreparedStatement ps = conn.prepareStatement(sql.toString());
                ResultSet res = ps.executeQuery();
                
                ArrayList<Integer> idsProdutosBanco = new ArrayList<>();
                
                while(res.next()){
                    idsProdutosBanco.add(res.getInt("id"));
                }
                
                for (ProdutoModel produto : produtos) {
                    
                    if(idsProdutosBanco.contains(produto.getId())){
                        sql.setLength(0);
                        
                        sql.append("UPDATE public.produtos ");
                        sql.append("SET descricao = ?, estoque = ?, preco = ?, unidade = ?, ultimaatualizacao = ? ");
                        sql.append("WHERE id = ?");
                        
                        PreparedStatement psUpdate = conn.prepareStatement(sql.toString());
                        psUpdate.setString(1, produto.getDescricao());
                        psUpdate.setDouble(2, produto.getEstoque());
                        psUpdate.setBigDecimal(3, produto.getPreco());
                        psUpdate.setString(4, produto.getUnidade());
                        psUpdate.setTimestamp(5, produto.getUltimaAtualizacaoTimeStamp());
                        psUpdate.setInt(6,produto.getId());
                        
                        psUpdate.execute();
                        
                    }else{
                        sql.setLength(0);
                        
                        sql.append("INSERT INTO public.produtos ");
                        sql.append("(descricao, estoque, preco, unidade, ultimaatualizacao) ");
                        sql.append("VALUES(?, ?, ?, ?, ?) ");
                        
                        PreparedStatement psInsert = conn.prepareStatement(sql.toString());
                        
                        psInsert.setString(1, produto.getDescricao());
                        psInsert.setDouble(2, produto.getEstoque());
                        psInsert.setBigDecimal(3, produto.getPreco());
                        psInsert.setString(4, produto.getUnidade());
                        psInsert.setTimestamp(5, produto.getUltimaAtualizacaoTimeStamp());
                        
                        psInsert.execute();
                    }
                }
            }
        }
    }
    
    public void sincronizarProdutosApiBdLocal() throws SQLException{
        ProdutoModel[] produtos = getProdutosApi();
        if(produtos != null){
            salvarProdutoLote(produtos);
        }
    }
    
    
    public ResultSet getProdutoById(Integer id) throws SQLException{
        sql.setLength(0);
        sql.append("SELECT * FROM produtos WHERE id = ?");
        
        try (Connection conn = connF.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            return rs;
        }
    }
    
    public ResultSet getProdutoByDescricao(String descricao) throws SQLException{
        sql.setLength(0);
        sql.append("SELECT * FROM produtos WHERE descricao ILIKE ? ");
        sql.append("ORDER BY id");
        
        try (Connection conn = connF.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setString(1, "%" + descricao + "%");
            ResultSet rs = ps.executeQuery();
            
            return rs;
        }
    }
    
}
