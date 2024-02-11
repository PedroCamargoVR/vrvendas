package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.ConnectionFactory;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import br.com.pedrocamargo.vrvendas.model.VendaProdutoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class VendaProdutoDao {
    
    private ConnectionFactory connF;
    private StringBuilder sql;
    
    public VendaProdutoDao(){
        this.connF = new ConnectionFactory();
        this.sql = new StringBuilder();
    }
    
    public VendaProdutoDao(ConnectionFactory conn){
        this.connF = conn;
        this.sql = new StringBuilder();
    }
    
    public void inserirProdutoVendaProduto(Connection conn, Integer idVenda, Map<ProdutoModel, Integer> produtos) throws SQLException{
        sql.setLength(0);                            
        sql.append("INSERT INTO public.vendaproduto ");
        sql.append("(id_venda, id_produto, valorprodutonavenda, created_at, updated_at, quantidade) ");
        sql.append("VALUES(?, ?, ?, ?, ?, ?)");
            
        try{
            produtos.forEach((produto,quantidade) -> {
                try{
                    PreparedStatement psProdutos = conn.prepareStatement(sql.toString());
                    psProdutos.setInt(1, idVenda);
                    psProdutos.setInt(2, produto.getId());
                    psProdutos.setBigDecimal(3, produto.getPreco());
                    psProdutos.setTimestamp(4, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                    psProdutos.setTimestamp(5, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                    psProdutos.setInt(6, quantidade);

                    psProdutos.execute();
                }catch(Exception e){
                    throw new RuntimeException(e.getMessage());
                }
            });
        }catch(Exception e){
            throw new SQLException(e.getMessage());
        }
    }
    
    public void inserirOuAtualizarVendaProduto(Connection conn, Integer idVenda, ArrayList<Integer> idsProdutosVendaBanco, Map<ProdutoModel, Integer> produtos) throws SQLException{
        try{
            produtos.forEach((produto,quantidade) -> {
                if(idsProdutosVendaBanco.contains(produto.getId())){
                    sql.setLength(0);                            
                    sql.append("UPDATE public.vendaproduto ");
                    sql.append("SET valorprodutonavenda = ?, updated_at = ?, quantidade = ? ");
                    sql.append("WHERE id_venda = ? ");
                    sql.append("AND id_produto = ?");

                    PreparedStatement psProdutos;
                    try {
                        psProdutos = conn.prepareStatement(sql.toString());
                        psProdutos.setBigDecimal(1, produto.getPreco());
                        psProdutos.setTimestamp(2, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                        psProdutos.setInt(3, quantidade);
                        psProdutos.setInt(4, idVenda);
                        psProdutos.setInt(5, produto.getId());

                        psProdutos.executeUpdate();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex.getMessage());
                    }
                }else{
                    sql.setLength(0);                            
                    sql.append("INSERT INTO public.vendaproduto ");
                    sql.append("(id_venda, id_produto, valorprodutonavenda, created_at, updated_at, quantidade) ");
                    sql.append("VALUES(?, ?, ?, ?, ?, ?)");

                    PreparedStatement psProdutos;
                    try {
                        psProdutos = conn.prepareStatement(sql.toString());
                        psProdutos.setInt(1, idVenda);
                        psProdutos.setInt(2, produto.getId());
                        psProdutos.setBigDecimal(3, produto.getPreco());
                        psProdutos.setTimestamp(4, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                        psProdutos.setTimestamp(5, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                        psProdutos.setInt(6, quantidade);

                        psProdutos.execute();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex.getMessage());
                    }
                }
            });
        }catch(Exception e){
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deletarVendaProduto(Connection conn, Integer idVenda, ArrayList<Integer> idsProdutosVendaModel, ArrayList<Integer> idsProdutosVendaBanco) throws SQLException{
        sql.setLength(0);                            
        sql.append("DELETE FROM vendaproduto ");
        sql.append("WHERE id_venda = ? ");
        sql.append("AND id_produto = ?");
            
        try{
            idsProdutosVendaBanco.forEach((idProduto) -> {
                /**
                 * Verifica se o ID do produto que está no banco existe nos produtos presentes na VendaModel
                 * Se NÃO EXISTIR, executa a deleção
                 */
                if(!idsProdutosVendaModel.contains(idProduto)){
                    try {
                        PreparedStatement psDelete = conn.prepareStatement(sql.toString());
                        psDelete.setInt(1, idVenda);
                        psDelete.setInt(2, idProduto);

                        psDelete.execute();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex.getMessage());
                    }

                }
            });
        }catch(Exception e){
            throw new SQLException(e.getMessage());
        }
    }
    
    public List<VendaProdutoModel> getVendaProdutoByIdVenda(Integer idVenda) throws SQLException{
        List<VendaProdutoModel> vendaProduto = new ArrayList<>();
        
        sql.setLength(0);
        sql.append("SELECT * FROM vendaproduto WHERE id_venda = ?");
        
        try(Connection conn = connF.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, idVenda);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                vendaProduto.add(
                        new VendaProdutoModel(
                                rs.getInt("id"),
                                rs.getInt("id_venda"),
                                rs.getInt("id_produto"),
                                rs.getBigDecimal("valorprodutonavenda"),
                                rs.getTimestamp("created_at"),
                                rs.getTimestamp("updated_at"),
                                rs.getInt("quantidade")
                        )
                );
            }
        }
        return vendaProduto;
    }
    
    public ArrayList<Integer> getIdsVendaProdutoByIdVenda(Integer idVenda) throws SQLException{
        List<VendaProdutoModel> produtosVenda = getVendaProdutoByIdVenda(idVenda);
        ArrayList<Integer> idsProdutosVendaBanco = new ArrayList<>();
        
        produtosVenda.forEach((vendaProduto) -> {
            idsProdutosVendaBanco.add(vendaProduto.getId_produto());
        });
        
        return idsProdutosVendaBanco;
    }
    
}
