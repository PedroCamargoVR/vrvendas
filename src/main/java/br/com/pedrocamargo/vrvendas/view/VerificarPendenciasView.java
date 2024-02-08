package br.com.pedrocamargo.vrvendas.view;

import br.com.pedrocamargo.vrvendas.controller.VendaController;
import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import br.com.pedrocamargo.vrvendas.util.GerarTabelaUtil;

public class VerificarPendenciasView extends javax.swing.JInternalFrame {

    private String[] nomeColunas = {"ID", "Descricao", "Motivo Pendência"};
    private GerarTabelaUtil gerarTabela = new GerarTabelaUtil(nomeColunas,false);
    private VendaController vendaController;
    private Integer idVendaConsultada;
    
    public VerificarPendenciasView(Integer idVendaConsultada) {
        this.idVendaConsultada = idVendaConsultada;
        this.vendaController = new VendaController();
        
        initComponents();
        iniciarTabela();
    }
    
    private void iniciarTabela(){
        gerarTabela.limparTabela();
        gerarTabela.getTable().getColumnModel().getColumn(0).setMaxWidth(100);
        
        try {
            ResultSet rsProdutosVenda = vendaController.getProdutosVendaErroFinalizacao(this.idVendaConsultada);
            
            while(rsProdutosVenda.next()){
                gerarTabela.addLinha(new Object[]{rsProdutosVenda.getInt("id"),rsProdutosVenda.getString("descricao"),rsProdutosVenda.getString("motivoerro")});
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao recuperar produtos da venda.", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
        
        jpTabelaProdutosPendencia.setLayout(new BorderLayout());
        jpTabelaProdutosPendencia.add(gerarTabela.getScrollPane(), BorderLayout.CENTER);
        jpTabelaProdutosPendencia.setVisible(true);
        jpTabelaProdutosPendencia.revalidate();
        jpTabelaProdutosPendencia.repaint();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jpTabelaProdutosPendencia = new javax.swing.JPanel();

        setClosable(true);
        setResizable(true);
        setTitle("Verificação de Pendencias");
        setPreferredSize(new java.awt.Dimension(600, 500));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/atencao.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel2.setText("Os produtos abaixo estão com pendência e impedindo a finalização da venda");

        javax.swing.GroupLayout jpTituloLayout = new javax.swing.GroupLayout(jpTitulo);
        jpTitulo.setLayout(jpTituloLayout);
        jpTituloLayout.setHorizontalGroup(
            jpTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jpTituloLayout.setVerticalGroup(
            jpTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTituloLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout jpTabelaProdutosPendenciaLayout = new javax.swing.GroupLayout(jpTabelaProdutosPendencia);
        jpTabelaProdutosPendencia.setLayout(jpTabelaProdutosPendenciaLayout);
        jpTabelaProdutosPendenciaLayout.setHorizontalGroup(
            jpTabelaProdutosPendenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpTabelaProdutosPendenciaLayout.setVerticalGroup(
            jpTabelaProdutosPendenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 393, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpTabelaProdutosPendencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpTabelaProdutosPendencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel jpTabelaProdutosPendencia;
    private javax.swing.JPanel jpTitulo;
    // End of variables declaration//GEN-END:variables
}
