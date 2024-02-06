package br.com.pedrocamargo.vrvendas.view;

import br.com.pedrocamargo.vrvendas.controller.ProdutoController;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import util.GerarTabelaUtil;

public class ConsultaProdutosView extends javax.swing.JInternalFrame {

    ProdutoController produtoController;
    String[] nomeColunasConsulta = {"ID", "Descricao", "Estoque Atual", "Embalagem", "Preco"};
    GerarTabelaUtil gerarTabelaConsulta = new GerarTabelaUtil(nomeColunasConsulta,false);
    JInternalFrame framePai;
    
    public ConsultaProdutosView(JInternalFrame framePai) {
        this.produtoController = new ProdutoController();
        this.framePai = framePai;
        initComponents();
    }
    
    private void iniciarTabelaConsulta(){
        gerarTabelaConsulta.limparTabela();
        
        try {
            ResultSet rs = produtoController.getProdutoByDescricao(jtfDescricaoBuscada.getText());
            
            ArrayList<String[]> dadosTabelaConsulta = new ArrayList<>();
            
            while(rs.next()){
                dadosTabelaConsulta.add(new String[]{rs.getString("id"), rs.getString("descricao"), rs.getString("estoque"), rs.getString("unidade"),rs.getBigDecimal("preco").setScale(2, RoundingMode.HALF_UP).toString()});
            }
            
            dadosTabelaConsulta.forEach((linha) -> {
                gerarTabelaConsulta.addLinha(linha);
            });
            
            jpTabelaProdutos.setLayout(new BorderLayout());
            jpTabelaProdutos.add(gerarTabelaConsulta.getScrollPane(), BorderLayout.CENTER);
            jpTabelaProdutos.revalidate();
            jpTabelaProdutos.repaint();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos\n"+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpCampodeBusca = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfDescricaoBuscada = new javax.swing.JTextField();
        jbConfirmar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jpTabelaProdutos = new javax.swing.JPanel();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(700, 600));

        jLabel1.setText("Digite a descricao do produto e pressione ENTER para buscar");

        jtfDescricaoBuscada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfDescricaoBuscadaKeyPressed(evt);
            }
        });

        jbConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/confirmar.png"))); // NOI18N
        jbConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCampodeBuscaLayout = new javax.swing.GroupLayout(jpCampodeBusca);
        jpCampodeBusca.setLayout(jpCampodeBuscaLayout);
        jpCampodeBuscaLayout.setHorizontalGroup(
            jpCampodeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCampodeBuscaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCampodeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfDescricaoBuscada)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCampodeBuscaLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbConfirmar)))
                .addContainerGap())
        );
        jpCampodeBuscaLayout.setVerticalGroup(
            jpCampodeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCampodeBuscaLayout.createSequentialGroup()
                .addGroup(jpCampodeBuscaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbConfirmar)
                    .addGroup(jpCampodeBuscaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtfDescricaoBuscada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpTabelaProdutos.setPreferredSize(new java.awt.Dimension(664, 458));
        jpTabelaProdutos.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jpCampodeBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpTabelaProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpCampodeBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpTabelaProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConfirmarActionPerformed
        Map<String,String> dadosLinhaSelecionada = gerarTabelaConsulta.dadosLinhaSelecionada();
        
        if(dadosLinhaSelecionada.isEmpty()){
             JOptionPane.showMessageDialog(null, "Nenhum registro selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }else{
            NovaVendaView novaVendaView = (NovaVendaView) framePai;
            novaVendaView.popularCamposProduto(Integer.parseInt(dadosLinhaSelecionada.get("ID")));
            this.dispose();
        }
    }//GEN-LAST:event_jbConfirmarActionPerformed

    private void jtfDescricaoBuscadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfDescricaoBuscadaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            iniciarTabelaConsulta();
        }
    }//GEN-LAST:event_jtfDescricaoBuscadaKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbConfirmar;
    private javax.swing.JPanel jpCampodeBusca;
    private javax.swing.JPanel jpTabelaProdutos;
    private javax.swing.JTextField jtfDescricaoBuscada;
    // End of variables declaration//GEN-END:variables
}
