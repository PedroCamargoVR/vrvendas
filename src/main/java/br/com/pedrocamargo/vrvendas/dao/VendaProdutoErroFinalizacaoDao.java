package br.com.pedrocamargo.vrvendas.dao;

import br.com.pedrocamargo.vrvendas.config.ConnectionFactory;
import br.com.pedrocamargo.vrvendas.model.ProdutoVendaErroFinalizacaoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaProdutoErroFinalizacaoDao {
    private ConnectionFactory connF;
    private StringBuilder sql;
    
    public VendaProdutoErroFinalizacaoDao(){
        this.connF = new ConnectionFactory();
        this.sql = new StringBuilder();
    }
    
    public List<ProdutoVendaErroFinalizacaoModel> getProdutosVendaErroFinalizacao(Integer idVenda) throws SQLException{
        List<ProdutoVendaErroFinalizacaoModel> produtoVendaErroFinalizacaoModelList = new ArrayList<>();
        
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
            
            while(rs.next()){
                produtoVendaErroFinalizacaoModelList.add(
                        new ProdutoVendaErroFinalizacaoModel(
                                rs.getInt("id"), 
                                rs.getInt("id_vendaproduto"), 
                                rs.getString("motivoerro"), 
                                rs.getString("descricao"), 
                                rs.getInt("quantidade"))
                );
            }
        }
        return produtoVendaErroFinalizacaoModelList;
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
}
