package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.ConnectionFactory;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDao {
    
    private ConnectionFactory connF;
    private StringBuilder sql;
    
    public ClienteDao(){
        this.connF = new ConnectionFactory();
        this.sql = new StringBuilder();
    }
    
    public void salvarCliente(ClienteModel model) throws SQLException{
        
        if(model.getId() == 0){
            sql.setLength(0);
            sql.append("INSERT INTO public.clientes");
            sql.append("(nome, nomefantasia, razaosocial, cnpj)");
            sql.append(" VALUES(?, ?, ?, ?);");
            
            try (Connection conn = connF.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql.toString());
                
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
            
            try (Connection conn = connF.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql.toString());
                
                ps.setString(1, model.getNome());
                ps.setString(2, model.getNomeFantasia());
                ps.setString(3, model.getRazaoSocial());
                ps.setString(4, model.getCnpj());
                ps.setInt(5, model.getId());
                
                ps.execute();
            }
        }
        
    }
    
    public ResultSet getClientes() throws SQLException{
                
        sql.setLength(0);
        sql.append("SELECT * FROM clientes ORDER BY id");
        
        ResultSet rs;
        try (Connection conn = connF.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
        }
        
        return rs;
    }
    
    public ResultSet getClienteById(Integer id) throws SQLException{
        sql.setLength(0);
        sql.append("SELECT * FROM clientes WHERE id = ?");
        
        ResultSet rs;
        try (Connection conn = connF.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, id);
            rs = ps.executeQuery();
        }
        return rs;
        
    }
    
    public ResultSet getClienteByCnpj(String cnpj) throws SQLException{
        sql.setLength(0);
        sql.append("SELECT * FROM clientes WHERE cnpj LIKE ?");
        
        ResultSet rs;
        try (Connection conn = connF.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setString(1, "%" + cnpj + "%");
            rs = ps.executeQuery();
        }
        return rs;
    }
    
    public void removeCliente(Integer id) throws SQLException{
        
        sql.setLength(0);
        sql.append("DELETE FROM clientes WHERE id = ?");
        
        try (Connection conn = connF.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            
            ps.setInt(1, id);
            ps.execute();
        }
    }
    
}
