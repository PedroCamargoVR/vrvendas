package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.ConnectionFactory;
import br.com.pedrocamargo.vrvendas.integration.fakeprodutoapi.FakeProdutoAPIService;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import br.com.pedrocamargo.vrvendas.vo.ProdutoQuantidadeVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;

public class ProdutoDao {
    private ConnectionFactory connF;
    private StringBuilder sql;
    private FakeProdutoAPIService fakeProdutoApiService;
    
    public ProdutoDao(){
        this.fakeProdutoApiService = new FakeProdutoAPIService();
        this.connF = new ConnectionFactory();
        this.sql = new StringBuilder();
    }
    
    public ProdutoDao(ConnectionFactory conn){
        this.fakeProdutoApiService = new FakeProdutoAPIService();
        this.connF = conn;
        this.sql = new StringBuilder();
    }
    
    public ProdutoDao(ConnectionFactory conn, OkHttpClient fakeApi){
        this.fakeProdutoApiService = new FakeProdutoAPIService(fakeApi);
        this.connF = conn;
        this.sql = new StringBuilder();
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
        ProdutoModel[] produtos = fakeProdutoApiService.getProdutosApi();
        if(produtos != null){
            salvarProdutoLote(produtos);
        }
    }
    
    public List<ProdutoModel> getProdutos() throws SQLException{
        List<ProdutoModel> produtos = new ArrayList<>();
        
        sql.setLength(0);
        sql.append("SELECT * FROM produtos ");
        sql.append("ORDER BY id");
        
        try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                produtos.add(new ProdutoModel(rs.getInt("id"),rs.getString("descricao"),rs.getInt("estoque"),rs.getBigDecimal("preco"),rs.getString("unidade"),rs.getString("ultimaatualizacao")));
            }
        }
        return produtos;
    }
    
    public ProdutoModel getProdutoById(Integer id) throws SQLException{
        ProdutoModel produto = null;
        
        sql.setLength(0);
        sql.append("SELECT * FROM produtos WHERE id = ?");
        
        try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                produto = new ProdutoModel(rs.getInt("id"),rs.getString("descricao"),rs.getInt("estoque"),rs.getBigDecimal("preco"),rs.getString("unidade"),rs.getString("ultimaatualizacao"));
            }
        }
        
        return produto;
    }
    
    public List<ProdutoQuantidadeVO> getProdutosByVendaId(Integer idVenda) throws SQLException{
        List<ProdutoQuantidadeVO> produtos = new ArrayList<>();
        
        sql.setLength(0);
        sql.append("SELECT p.id,p.descricao,p.estoque,p.unidade,p.ultimaatualizacao,vp.quantidade,vp.valorprodutonavenda ");
        sql.append("FROM produtos p ");
        sql.append("INNER JOIN vendaproduto vp ON vp.id_produto = p.id ");
        sql.append("WHERE p.id in (select id_produto from vendaproduto where id_venda = ?) ");
        sql.append("AND vp.id_venda = ? ");
        sql.append("ORDER BY vp.id");
        
        try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setInt(1, idVenda);
            ps.setInt(2, idVenda);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                produtos.add(new ProdutoQuantidadeVO(
                    new ProdutoModel(rs.getInt("id"),rs.getString("descricao"),rs.getInt("estoque"),rs.getBigDecimal("valorprodutonavenda"),rs.getString("unidade"),rs.getString("ultimaatualizacao")),
                    rs.getInt("quantidade")
                ));
            }
        }
        
        return produtos;
    }
    
    public List<ProdutoModel> getProdutoByDescricao(String descricao) throws SQLException{
        List<ProdutoModel> produtos = new ArrayList<>();
        
        sql.setLength(0);
        sql.append("SELECT * FROM produtos WHERE descricao ILIKE ? ");
        sql.append("ORDER BY id");
        
        try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setString(1, "%" + descricao + "%");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                produtos.add(new ProdutoModel(rs.getInt("id"),rs.getString("descricao"),rs.getInt("estoque"),rs.getBigDecimal("preco"),rs.getString("unidade"),rs.getString("ultimaatualizacao")));
            }
        }
        return produtos;
    }

    public Integer getEstoqueProduto(Integer id) throws SQLException {
        sql.setLength(0);
        sql.append("SELECT estoque FROM produtos WHERE id = ? ");
        
        ResultSet rs;
        try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("estoque");
            }
        }
        return -1;
    }
    
}
