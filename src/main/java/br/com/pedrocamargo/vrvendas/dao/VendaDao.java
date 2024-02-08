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

                            Map<ProdutoModel, Integer> produtos = venda.getProdutosVenda();

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
                    Map<ProdutoModel, Integer> produtos = venda.getProdutosVenda();
                    ArrayList<Integer> idsProdutosVendaBanco = getProdutosVendaBanco(venda.getId());
                    
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
                                psProdutos.setInt(4, venda.getId());
                                psProdutos.setInt(5, produto.getId());

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
                        
                        ArrayList<Integer> idsProdutosVendaModel = venda.getIdsProdutosVenda();
                        idsProdutosVendaBanco.forEach((idProduto) -> {
                            /**
                             * Verifica se o ID do produto que está no banco existe nos produtos presentes na VendaModel
                             * Se NÃO EXISTIR, executa a deleção
                             */
                            if(!idsProdutosVendaModel.contains(idProduto)){
                                sql.setLength(0);                            
                                sql.append("DELETE FROM vendaproduto ");
                                sql.append("WHERE id_venda = ? ");
                                sql.append("AND id_produto = ?");
                                
                                try {
                                    PreparedStatement psDelete = conn.prepareStatement(sql.toString());
                                    psDelete.setInt(1, venda.getId());
                                    psDelete.setInt(2, idProduto);
                                    
                                    psDelete.execute();
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
    
    public ArrayList<Integer> getProdutosVendaBanco(Integer idVenda) throws SQLException{
        ResultSet rsProdutosVenda = getProdutosVendaByIdVenda(idVenda);
        ArrayList<Integer> idsProdutosVendaBanco = new ArrayList<Integer>();
        
        while(rsProdutosVenda.next()){
            idsProdutosVendaBanco.add(rsProdutosVenda.getInt("id_produto"));
        }
        
        return idsProdutosVendaBanco;
    }

    public ResultSet getAllVendasVo() throws SQLException {
        sql.setLength(0);
        sql.append("SELECT v.id as \"id_venda\",v.id_cliente,v.id_status,v.valortotal,v.created_at,v.updated_at,c.id as \"id_cliente\",c.nome,c.nomefantasia,c.razaosocial,c.cnpj FROM venda v ");
        sql.append("INNER JOIN clientes c ON c.id = v.id_cliente ");
        sql.append("ORDER BY v.id");
        
        try(Connection conn = connF.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            
            ResultSet rs = ps.executeQuery();
            return rs;
        }
    }
    
    public ResultSet getVendasByIdCliente(Integer idCliente) throws SQLException{
        
        sql.setLength(0);
        sql.append("SELECT * FROM venda ");
        sql.append("WHERE id_cliente = ? ");
        sql.append("ORDER BY id_status");
        
        try(Connection conn = connF.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            
            ps.setInt(1,idCliente);
            
            ResultSet rs = ps.executeQuery();
            return rs;
        }
        
    }
    
    public ResultSet getVendasVoByStatus(Integer idStatus) throws SQLException{
        sql.setLength(0);
        sql.append("SELECT v.id as \"id_venda\",v.id_cliente,v.id_status,v.valortotal,v.created_at,v.updated_at,c.id as \"id_cliente\",c.nome,c.nomefantasia,c.razaosocial,c.cnpj FROM venda v ");
        sql.append("INNER JOIN clientes c ON c.id = v.id_cliente ");
        sql.append("WHERE id_status = ? ");
        sql.append("ORDER BY v.id");
        
        try(Connection conn = connF.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            
            ps.setInt(1,idStatus);
            
            ResultSet rs = ps.executeQuery();
            return rs;
        }
    }
    
    public ResultSet getVendaVoById(Integer idVenda) throws SQLException {
        sql.setLength(0);
        sql.append("SELECT v.id as \"id_venda\",v.id_cliente,v.id_status,v.valortotal,v.created_at,v.updated_at,c.id as \"id_cliente\",c.nome,c.nomefantasia,c.razaosocial,c.cnpj FROM venda v ");
        sql.append("INNER JOIN clientes c ON c.id = v.id_cliente ");
        sql.append("WHERE v.id = ? ");
        sql.append("ORDER BY v.id_status");
        
        try(Connection conn = connF.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            
            ps.setInt(1,idVenda);
            
            ResultSet rs = ps.executeQuery();
            return rs;
        }
    }

    public void excluirVenda(Integer idVenda) throws SQLException {
        try(Connection conn = connF.getConnection()){
            conn.setAutoCommit(false);
            
            try{
                sql.setLength(0);
                sql.append("DELETE FROM vendaproduto ");
                sql.append("WHERE id_venda = ? ");

                PreparedStatement ps = conn.prepareStatement(sql.toString());
                ps.setInt(1,idVenda);
                ps.execute();
                
                sql.setLength(0);
                sql.append("DELETE FROM venda ");
                sql.append("WHERE id = ? ");
                
                ps = conn.prepareStatement(sql.toString());
                ps.setInt(1,idVenda);
                ps.execute();
                
                conn.commit();
            }catch(SQLException e){
                try {
                    conn.rollback();
                } catch (SQLException exRollback) {
                    throw new RuntimeException(exRollback.getMessage());
                }
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void atualizarStatusVenda(Integer idVenda,Integer idStatus) throws SQLException {
        sql.setLength(0);
        sql.append("UPDATE venda ");
        sql.append("SET id_status = ? ");
        sql.append("WHERE id = ? ");
        
        try(Connection conn = connF.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            
            ps.setInt(1,idStatus);
            ps.setInt(2,idVenda);
            
            ps.execute();
        }
    }
    
    public void inserirProdutoVendaProdutoErro(Integer idVendaProduto, String motivo) throws SQLException{
        sql.setLength(0);
        sql.append("SELECT * FROM public.vendaprodutoerrofinalizacao ");
        sql.append("WHERE id_vendaproduto = ? ");
        PreparedStatement ps;
        
        try(Connection conn = connF.getConnection()){
            ps = conn.prepareStatement(sql.toString());
            
            ps.setInt(1,idVendaProduto);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                sql.setLength(0);
                sql.append("UPDATE public.vendaprodutoerrofinalizacao ");
                sql.append("SET motivoerro = ? ");
                sql.append("WHERE id_vendaproduto = ? ");

                ps = conn.prepareStatement(sql.toString());

                ps.setString(1,motivo);
                ps.setInt(2,idVendaProduto);

                ps.execute();
            }else{
                sql.setLength(0);
                sql.append("INSERT INTO public.vendaprodutoerrofinalizacao ");
                sql.append("(id_vendaproduto, motivoerro) ");
                sql.append("VALUES(?, ?) ");

                ps = conn.prepareStatement(sql.toString());

                ps.setInt(1,idVendaProduto);
                ps.setString(2,motivo);

                ps.execute();
            }
        }
        
    }
    
    public void removeProdutoVendaProdutoErro(Integer idVendaProduto) throws SQLException{
        sql.setLength(0);
        sql.append("DELETE FROM public.vendaprodutoerrofinalizacao ");
        sql.append("WHERE id_vendaproduto = ? ");
        
        try(Connection conn = connF.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            
            ps.setInt(1,idVendaProduto);
            
            ps.execute();
        }
        
    }
    
    public ResultSet getProdutosVendaErroFinalizacao(Integer idVenda) throws SQLException{
        
        sql.setLength(0);
        sql.append("SELECT p.id,vpe.id_vendaproduto,vpe.motivoerro,p.descricao,vp.quantidade ");
        sql.append("FROM vendaprodutoerrofinalizacao vpe ");
        sql.append("INNER JOIN vendaproduto vp on vp.id = vpe.id_vendaproduto ");
        sql.append("INNER JOIN produtos p on p.id = vp.id_produto ");
        sql.append("WHERE vpe.id_vendaproduto in (");
        sql.append("SELECT id FROM vendaproduto WHERE id_venda = ?");
        sql.append(")");
        
        try(Connection conn = connF.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            
            ps.setInt(1,idVenda);
            
            ResultSet rs = ps.executeQuery();
            
            return rs;
        }
    }
    
}
