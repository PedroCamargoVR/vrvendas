package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.ConnectionFactory;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendaDao {
    
    private ConnectionFactory connF;
    private StringBuilder sql;
    
    public VendaDao(){
        this.connF = new ConnectionFactory();
        this.sql = new StringBuilder();
    }
    
    public Integer salvarVenda(VendaModel venda) throws SQLException{
        if(venda.getId() <= 0){
            sql.setLength(0);
            sql.append("INSERT INTO public.venda ");
            sql.append("(id_cliente, id_status, valortotal, created_at, updated_at) ");
            sql.append("VALUES(?, ?, ?, ?, ?)");

            try (Connection conn = connF.getConnection()) {
                conn.setAutoCommit(false);

                PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1,venda.getId_cliente());
                ps.setInt(2,venda.getId_status());
                ps.setBigDecimal(3, venda.getValortotal());
                ps.setTimestamp(4, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                ps.setTimestamp(5, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));

                int linhasAfetadas = ps.executeUpdate();

                if(linhasAfetadas > 0){
                    // Recupera a chave gerada
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            Integer idGerado = rs.getInt(1);

                            Map<ProdutoModel, Integer> produtos = venda.getProdutosSelecionados();

                            produtos.forEach((produto,quantidade) -> {
                                sql.setLength(0);                            
                                sql.append("INSERT INTO public.vendaproduto ");
                                sql.append("(id_venda, id_produto, valorprodutonavenda, created_at, updated_at, quantidade) ");
                                sql.append("VALUES(?, ?, ?, ?, ?, ?)");

                                PreparedStatement psProdutos;
                                try {
                                    psProdutos = conn.prepareStatement(sql.toString());
                                    psProdutos.setInt(1, idGerado);
                                    psProdutos.setInt(2, produto.getId());
                                    psProdutos.setBigDecimal(3, produto.getPreco());
                                    psProdutos.setTimestamp(4, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                                    psProdutos.setTimestamp(5, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                                    psProdutos.setInt(6, quantidade);

                                    psProdutos.execute();
                                } catch (SQLException ex) {
                                    try {
                                        conn.rollback();
                                    } catch (SQLException exRollback) {
                                        throw new RuntimeException(exRollback.getMessage());
                                    }
                                    throw new RuntimeException(ex.getMessage());
                                }
                            });
                            conn.commit();
                            return idGerado;
                        }
                    }
                }
                conn.rollback();
            }
        }else{
            sql.setLength(0);
            sql.append("UPDATE public.venda ");
            sql.append("SET id_cliente = ?, id_status = ?, valortotal = ?, updated_at = ? ");
            sql.append("WHERE id = ?");
            
            try (Connection conn = connF.getConnection()) {
                conn.setAutoCommit(false);
                
                PreparedStatement ps = conn.prepareStatement(sql.toString());

                ps.setInt(1,venda.getId_cliente());
                ps.setInt(2,venda.getId_status());
                ps.setBigDecimal(3, venda.getValortotal());
                ps.setTimestamp(4, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                ps.setInt(5, venda.getId());
                
                int linhasAfetadas = ps.executeUpdate();
                
                if(linhasAfetadas > 0){
                    Map<ProdutoModel, Integer> produtos = venda.getProdutosSelecionados();
                    ResultSet rsProdutosVenda = getProdutosVendaByIdVenda(venda.getId());
                    
                    ArrayList<Integer> idsProdutosVendaBanco = new ArrayList<Integer>();
                    
                    while(rsProdutosVenda.next()){
                        idsProdutosVendaBanco.add(rsProdutosVenda.getInt("id_produto"));
                    }
                    
                    produtos.forEach((produto,quantidade) -> {
                        if(idsProdutosVendaBanco.contains(produto.getId())){
                            sql.setLength(0);                            
                            sql.append("UPDATE public.vendaproduto ");
                            sql.append("SET valorprodutonavenda = ?, updated_at = ?, quantidade = ? ");
                            sql.append("WHERE id_venda = ?");
                            
                            PreparedStatement psProdutos;
                            try {
                                psProdutos = conn.prepareStatement(sql.toString());
                                psProdutos.setBigDecimal(1, produto.getPreco());
                                psProdutos.setTimestamp(2, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                                psProdutos.setInt(3, quantidade);
                                psProdutos.setInt(4, venda.getId());

                                psProdutos.executeUpdate();
                            } catch (SQLException ex) {
                                try {
                                    conn.rollback();
                                } catch (SQLException exRollback) {
                                    throw new RuntimeException(exRollback.getMessage());
                                }
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
                                psProdutos.setInt(1, venda.getId());
                                psProdutos.setInt(2, produto.getId());
                                psProdutos.setBigDecimal(3, produto.getPreco());
                                psProdutos.setTimestamp(4, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                                psProdutos.setTimestamp(5, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                                psProdutos.setInt(6, quantidade);

                                psProdutos.execute();
                            } catch (SQLException ex) {
                                try {
                                    conn.rollback();
                                } catch (SQLException exRollback) {
                                    throw new RuntimeException(exRollback.getMessage());
                                }
                                throw new RuntimeException(ex.getMessage());
                            }
                        }
                    });
                    conn.commit();
                    
                    return venda.getId();
                }
                conn.rollback();
            }
        }
        return -1;
    }
    
    public ResultSet getVendaById(Integer id) throws SQLException{
        
        sql.setLength(0);
        sql.append("SELECT * FROM venda WHERE id = ?");
        
        try(Connection conn = connF.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            return rs;
        }
    }
    
    public ResultSet getProdutosVendaByIdVenda(Integer idVenda) throws SQLException{
        sql.setLength(0);
        sql.append("SELECT * FROM vendaproduto WHERE id_venda = ?");
        
        try(Connection conn = connF.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, idVenda);
            
            ResultSet rs = ps.executeQuery();
            return rs;
        }
    }
    
}
