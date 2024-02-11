package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.ConnectionFactory;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.vo.VendaVO;
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
import java.util.List;
import java.util.Map;

public class VendaDao {
    
    private ConnectionFactory connF;
    private StringBuilder sql;
    private VendaProdutoDao vendaProdutoDao;
    
    public VendaDao(){
        this.connF = new ConnectionFactory();
        this.sql = new StringBuilder();
        this.vendaProdutoDao = new VendaProdutoDao();
    }
    
    public VendaDao(ConnectionFactory conn){
        this.connF = conn;
        this.sql = new StringBuilder();
        this.vendaProdutoDao = new VendaProdutoDao(conn);
    }
    
    public Integer salvarVenda(VendaModel venda) throws SQLException{
        if(venda.getId() <= 0){
            sql.setLength(0);
            sql.append("INSERT INTO public.venda ");
            sql.append("(id_cliente, id_status, valortotal, created_at, updated_at) ");
            sql.append("VALUES(?, ?, ?, ?, ?)");

            try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
                conn.setAutoCommit(false);

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

                            try {
                                vendaProdutoDao.inserirProdutoVendaProduto(conn,idGerado, produtos);
                            } catch (Exception ex) {
                                try {
                                    conn.rollback();
                                } catch (Exception exRollback) {
                                    throw new SQLException(exRollback.getMessage());
                                }
                                throw new SQLException(ex.getMessage());
                            }
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
            
            try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                conn.setAutoCommit(false);

                ps.setInt(1,venda.getId_cliente());
                ps.setInt(2,venda.getId_status());
                ps.setBigDecimal(3, venda.getValortotal());
                ps.setTimestamp(4, Timestamp.from(Instant.now().minus(Duration.ofHours(1))));
                ps.setInt(5, venda.getId());
                
                int linhasAfetadas = ps.executeUpdate();
                
                if(linhasAfetadas > 0){
                    Map<ProdutoModel, Integer> produtos = venda.getProdutosVenda();
                    ArrayList<Integer> idsProdutosVendaBanco = vendaProdutoDao.getIdsVendaProdutoByIdVenda(venda.getId());
                    ArrayList<Integer> idsProdutosVendaModel = venda.getIdsProdutosVenda();
                    
                    try{
                        vendaProdutoDao.inserirOuAtualizarVendaProduto(conn,venda.getId(), idsProdutosVendaBanco, produtos);                    
                        vendaProdutoDao.deletarVendaProduto(conn,venda.getId(), idsProdutosVendaModel, idsProdutosVendaBanco);
                    }catch(Exception ex){
                        try {
                            conn.rollback();
                        } catch (Exception exRollback) {
                            throw new SQLException(exRollback.getMessage());
                        }
                        throw new SQLException(ex.getMessage());
                    }
                    
                    conn.commit();
                    return venda.getId();
                }
                conn.rollback();
            }
        }
        return -1;
    }
    
    public VendaModel getVendaById(Integer id) throws SQLException{
        VendaModel venda = null;
        sql.setLength(0);
        sql.append("SELECT * FROM venda WHERE id = ?");
        
        try(Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())){
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                venda = new VendaModel(rs.getInt("id"),rs.getInt("id_cliente"),rs.getInt("id_status"),new BigDecimal(0),rs.getTimestamp("created_at"),rs.getTimestamp("updated_at"));
            }
        }
        return venda;
    }
    
    public List<VendaModel> getVendasByIdCliente(Integer idCliente) throws SQLException{
        List<VendaModel> vendas = new ArrayList<>();
        
        sql.setLength(0);
        sql.append("SELECT * FROM venda ");
        sql.append("WHERE id_cliente = ? ");
        sql.append("ORDER BY id_status");
        
        try(Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString());){
            
            ps.setInt(1,idCliente);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                vendas.add(new VendaModel(rs.getInt("id"),rs.getInt("id_cliente"),rs.getInt("id_status"),rs.getBigDecimal("valortotal")));
            }
        }
        return vendas;
    }

    public List<VendaVO> getAllVendasVo() throws SQLException {
        List<VendaVO> vendasVO = new ArrayList<>();
        
        sql.setLength(0);
        sql.append("SELECT v.id as \"id_venda\",v.id_cliente,v.id_status,v.valortotal,v.created_at,v.updated_at,c.id as \"id_cliente\",c.nome,c.nomefantasia,c.razaosocial,c.cnpj FROM venda v ");
        sql.append("INNER JOIN clientes c ON c.id = v.id_cliente ");
        sql.append("ORDER BY v.id");
        
        try(Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())){
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                vendasVO.add(new VendaVO(
                        rs.getInt("id_venda"),
                        rs.getInt("id_status"), 
                        new ClienteModel(rs.getInt("id_cliente"),
                                 rs.getString("nome"),
                                 rs.getString("nomefantasia"),
                                 rs.getString("razaosocial"),
                                 rs.getString("cnpj")
                             ), 
                        rs.getBigDecimal("valortotal"), 
                        rs.getTimestamp("created_at"), 
                        rs.getTimestamp("updated_at")
                    )
                );
            }
        }
        return vendasVO;
    }
        
    public List<VendaVO> getVendasVoByStatus(Integer idStatus) throws SQLException{
        List<VendaVO> vendasVO = new ArrayList<>();
        
        sql.setLength(0);
        sql.append("SELECT v.id as \"id_venda\",v.id_cliente,v.id_status,v.valortotal,v.created_at,v.updated_at,c.id as \"id_cliente\",c.nome,c.nomefantasia,c.razaosocial,c.cnpj FROM venda v ");
        sql.append("INNER JOIN clientes c ON c.id = v.id_cliente ");
        sql.append("WHERE id_status = ? ");
        sql.append("ORDER BY v.id");
        
        try(Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString());){
            ps.setInt(1,idStatus);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                vendasVO.add(new VendaVO(
                        rs.getInt("id_venda"),
                        rs.getInt("id_status"), 
                        new ClienteModel(rs.getInt("id_cliente"),
                                 rs.getString("nome"),
                                 rs.getString("nomefantasia"),
                                 rs.getString("razaosocial"),
                                 rs.getString("cnpj")
                             ), 
                        rs.getBigDecimal("valortotal"), 
                        rs.getTimestamp("created_at"), 
                        rs.getTimestamp("updated_at")
                    )
                );
            }
        }
        return vendasVO;
    }
    
    public VendaVO getVendaVoById(Integer idVenda) throws SQLException {
        VendaVO vendaVo = null;
        
        sql.setLength(0);
        sql.append("SELECT v.id as \"id_venda\",v.id_cliente,v.id_status,v.valortotal,v.created_at,v.updated_at,c.id as \"id_cliente\",c.nome,c.nomefantasia,c.razaosocial,c.cnpj FROM venda v ");
        sql.append("INNER JOIN clientes c ON c.id = v.id_cliente ");
        sql.append("WHERE v.id = ? ");
        sql.append("ORDER BY v.id_status");
        
        try(Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString());){
            ps.setInt(1,idVenda);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                vendaVo = new VendaVO(
                       rs.getInt("id_venda"),
                       rs.getInt("id_status"), 
                       new ClienteModel(rs.getInt("id_cliente"),
                                rs.getString("nome"),
                                rs.getString("nomefantasia"),
                                rs.getString("razaosocial"),
                                rs.getString("cnpj")
                            ), 
                       rs.getBigDecimal("valortotal"), 
                       rs.getTimestamp("created_at"), 
                       rs.getTimestamp("updated_at")
               );
            }
        }
        return vendaVo;
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
        
        try(Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())){
            ps.setInt(1,idStatus);
            ps.setInt(2,idVenda);
            
            ps.execute();
        }
    }
    
}
