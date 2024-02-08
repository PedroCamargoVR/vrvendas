package br.com.pedrocamargo.vrvendas.util;

import br.com.pedrocamargo.vrvendas.controller.ClienteController;
import br.com.pedrocamargo.vrvendas.controller.ProdutoController;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import br.com.pedrocamargo.vrvendas.model.ProdutoModel;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class SwingUtils {
    
    /**
     * Classe utilizada para popular os campos de cliente, 
     * recebendo o ClienteModel diretamente e populando os campos
     * @param jtfVendaIdCliente
     * @param jtfVendaClienteNome
     * @param jtfVendaNomeFantasia
     * @param jtfVendaRazaoSocial
     * @param jtfVendaCnpj
     * @param cliente 
     */
    public static void populaCamposCliente(
            JTextField jtfVendaIdCliente,
            JTextField jtfVendaClienteNome,
            JTextField jtfVendaNomeFantasia,
            JTextField jtfVendaRazaoSocial,
            JTextField jtfVendaCnpj,
            ClienteModel cliente) {
        jtfVendaIdCliente.setText(cliente.getId().toString());
        jtfVendaClienteNome.setText(cliente.getNome());
        jtfVendaNomeFantasia.setText(cliente.getNomeFantasia());
        jtfVendaRazaoSocial.setText(cliente.getRazaoSocial());
        jtfVendaCnpj.setText(cliente.getCnpj());
    }
    
    /**
     * Classe utilizada para popular os campos de cliente, 
     * recebendo o ID do cliente e buscando no banco de dados o cliente referido
     * @param jtfVendaIdCliente
     * @param jtfVendaClienteNome
     * @param jtfVendaNomeFantasia
     * @param jtfVendaRazaoSocial
     * @param jtfVendaCnpj
     * @param idCliente
     * @param clienteController 
     */
    public static void populaCamposCliente(
            JTextField jtfVendaIdCliente,
            JTextField jtfVendaClienteNome,
            JTextField jtfVendaNomeFantasia,
            JTextField jtfVendaRazaoSocial,
            JTextField jtfVendaCnpj,
            Integer idCliente,
            ClienteController clienteController){
        ClienteModel cliente;
        try {
            cliente = clienteController.getClienteById(idCliente);
            jtfVendaIdCliente.setText(cliente.getId().toString());
            jtfVendaClienteNome.setText(cliente.getNome());
            jtfVendaNomeFantasia.setText(cliente.getNomeFantasia());
            jtfVendaRazaoSocial.setText(cliente.getRazaoSocial());
            jtfVendaCnpj.setText(cliente.getCnpj());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cliente não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            limpaCamposCliente(jtfVendaIdCliente,jtfVendaClienteNome,jtfVendaNomeFantasia,jtfVendaRazaoSocial,jtfVendaCnpj);
        }
    }
    
    /**
     * Classe utilizada para limpar os campos de formularios de cliente nas telas
     * @param jtfVendaIdCliente
     * @param jtfVendaClienteNome
     * @param jtfVendaNomeFantasia
     * @param jtfVendaRazaoSocial
     * @param jtfVendaCnpj 
     */
    public static void limpaCamposCliente(
            JTextField jtfVendaIdCliente,
            JTextField jtfVendaClienteNome,
            JTextField jtfVendaNomeFantasia,
            JTextField jtfVendaRazaoSocial,
            JTextField jtfVendaCnpj){
        jtfVendaIdCliente.setText("");
        jtfVendaClienteNome.setText("");
        jtfVendaNomeFantasia.setText("");
        jtfVendaRazaoSocial.setText("");
        jtfVendaCnpj.setText("");
        
    }
    
    /**
     * Classe utilizada para popular os campos de produto, 
     * recebendo um ProdutoModel e populando os campos
     * @param jtfVendaProdutoId
     * @param jtfVendaProdutoDescricao
     * @param jtfVendaTipoEmbalagem
     * @param jtfVendaEstoqueAtual
     * @param produto 
     */
    public static void popularCamposProduto(
            JTextField jtfVendaProdutoId,
            JTextField jtfVendaProdutoDescricao,
            JTextField jtfVendaTipoEmbalagem,
            JTextField jtfVendaEstoqueAtual,
            ProdutoModel produto
    ){
        jtfVendaProdutoId.setText(produto.getId().toString());
        jtfVendaProdutoDescricao.setText(produto.getDescricao());
        jtfVendaTipoEmbalagem.setText(produto.getUnidade());
        jtfVendaEstoqueAtual.setText(produto.getEstoque().toString());
    }
    
    /**
     * Classe de popular campos produtos sobrecarregada, 
     * recebendo o Id do produto e buscando o produto correspondente no banco de dados
     * @param jtfVendaProdutoId
     * @param jtfVendaProdutoDescricao
     * @param jtfVendaTipoEmbalagem
     * @param jtfVendaEstoqueAtual
     * @param idProduto
     * @param produtoController 
     */
    public static void popularCamposProduto(
            JTextField jtfVendaProdutoId,
            JTextField jtfVendaProdutoDescricao,
            JTextField jtfVendaTipoEmbalagem,
            JTextField jtfVendaEstoqueAtual,
            Integer idProduto,
            ProdutoController produtoController
    ){
        ProdutoModel produto;
        try{
            produto = produtoController.getProdutoById(idProduto);
            jtfVendaProdutoId.setText(produto.getId().toString());
            jtfVendaProdutoDescricao.setText(produto.getDescricao());
            jtfVendaTipoEmbalagem.setText(produto.getUnidade());
            jtfVendaEstoqueAtual.setText(produto.getEstoque().toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Produto não encontrado", "Erro", JOptionPane.WARNING_MESSAGE);
            limpaCamposProduto(jtfVendaProdutoId,jtfVendaProdutoDescricao,jtfVendaTipoEmbalagem,jtfVendaEstoqueAtual);
        }
    }
    
    /**
     * Classe para limpar os campos do formulario de produto
     * @param jtfVendaProdutoId
     * @param jtfVendaProdutoDescricao
     * @param jtfVendaTipoEmbalagem
     * @param jtfVendaEstoqueAtual 
     */
    public static void limpaCamposProduto(
            JTextField jtfVendaProdutoId,
            JTextField jtfVendaProdutoDescricao,
            JTextField jtfVendaTipoEmbalagem,
            JTextField jtfVendaEstoqueAtual
    ){
        jtfVendaProdutoId.setText("");
        jtfVendaProdutoDescricao.setText("");
        jtfVendaTipoEmbalagem.setText("");
        jtfVendaEstoqueAtual.setText("");
    }
    
    
}
