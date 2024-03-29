package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.ConnectionFactory;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    
    private ConnectionFactory connF;
    private StringBuilder sql;
    
    public ClienteDao(ConnectionFactory connFactory){
        this.connF = connFactory;
        this.sql = new StringBuilder();
    }
    
    public void salvarCliente(ClienteModel model) throws SQLException{
        
        if(model.getId() == 0){
            sql.setLength(0);
            sql.append("INSERT INTO public.clientes");
            sql.append("(nome, nomefantasia, razaosocial, cnpj)");
            sql.append(" VALUES(?, ?, ?, ?);");
            
            try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                
                ps.setString(1, model.getNome());
                ps.setString(2, model.getNomeFantasia());
                ps.setString(3, model.getRazaoSocial());
                ps.setString(4, model.getCnpj());
                
                ps.execute();
            }
        }else{
            sql.setLength(0);
            sql.append("UPDATE public.clientes ");
            sql.append("SET nome = ?, nomefantasia = ?, razaosocial = ?, cnpj = ? ");
            sql.append("WHERE id = ?;");
            
            try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                
                ps.setString(1, model.getNome());
                ps.setString(2, model.getNomeFantasia());
                ps.setString(3, model.getRazaoSocial());
                ps.setString(4, model.getCnpj());
                ps.setInt(5, model.getId());
                
                ps.execute();
            }
        }
        
    }
    
    public List<ClienteModel> getClientes() throws SQLException{
        List<ClienteModel> clientes = new ArrayList<>();   
        
        sql.setLength(0);
        sql.append("SELECT * FROM clientes ORDER BY id");
        
        ResultSet rs;
        try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            rs = ps.executeQuery();
            
            while(rs.next()){
                clientes.add(new ClienteModel(rs.getInt("id"),rs.getString("nome"),rs.getString("nomefantasia"),rs.getString("razaosocial"),rs.getString("cnpj")));
            }
            rs.close();
        }
        
        return clientes;
    }
    
    public ClienteModel getClienteById(Integer id) throws SQLException{
        ClienteModel cliente = null;
        sql.setLength(0);
        sql.append("SELECT * FROM clientes WHERE id = ?");
        
        ResultSet rs;
        try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString());) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                cliente = new ClienteModel(rs.getInt("id"),rs.getString("nome"),rs.getString("nomefantasia"),rs.getString("razaosocial"),rs.getString("cnpj"));
            }
            rs.close();
        }
        
        return cliente;
    }
    
    public List<ClienteModel> getClienteByCnpj(String cnpj) throws SQLException{
        List<ClienteModel> clientes = new ArrayList<>();
        
        sql.setLength(0);
        sql.append("SELECT * FROM clientes WHERE cnpj LIKE ?");
        
        ResultSet rs;
        try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {            
            ps.setString(1, "%" + cnpj + "%");
            rs = ps.executeQuery();
            
            while(rs.next()){
                clientes.add(new ClienteModel(rs.getInt("id"),rs.getString("nome"),rs.getString("nomefantasia"),rs.getString("razaosocial"),rs.getString("cnpj")));
            }
            rs.close();
        }
        return clientes;
    }
    
    public void removeCliente(Integer id) throws SQLException{
        
        sql.setLength(0);
        sql.append("DELETE FROM clientes WHERE id = ?");
        
        try (Connection conn = connF.getConnection();PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            ps.setInt(1, id);
            ps.execute();
        }
    }
    
}
