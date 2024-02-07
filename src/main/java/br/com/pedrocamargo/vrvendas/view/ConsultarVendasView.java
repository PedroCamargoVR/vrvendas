package br.com.pedrocamargo.vrvendas.view;

import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import util.EditarCelulaTabelaUtil;
import util.GerarTabelaUtil;
import util.GerarTabelaEmCelulaUtil;

public class ConsultarVendasView extends javax.swing.JInternalFrame {

    private String[] nomeColunas = {"ID", "Razao Social", "CNPJ", "Vendas"};
    private GerarTabelaUtil gerarTabela = new GerarTabelaUtil(nomeColunas,true);
   
    public ConsultarVendasView() {
        initComponents();
        iniciarTabela();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpViewBotoes = new javax.swing.JPanel();
        jbViewConsolidada = new javax.swing.JButton();
        jbViewNormal = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jpTabelaConsultaVendas = new javax.swing.JPanel();

        setClosable(true);
        setTitle("Consultar Vendas");
        setPreferredSize(new java.awt.Dimension(900, 730));

        jbViewConsolidada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/consolidada.png"))); // NOI18N
        jbViewConsolidada.setText("Visualização Consolidada");
        jbViewConsolidada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbViewConsolidadaActionPerformed(evt);
            }
        });

        jbViewNormal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/normal.png"))); // NOI18N
        jbViewNormal.setText("Visualização Normal");
        jbViewNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbViewNormalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpViewBotoesLayout = new javax.swing.GroupLayout(jpViewBotoes);
        jpViewBotoes.setLayout(jpViewBotoesLayout);
        jpViewBotoesLayout.setHorizontalGroup(
            jpViewBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpViewBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbViewNormal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbViewConsolidada)
                .addContainerGap(512, Short.MAX_VALUE))
        );
        jpViewBotoesLayout.setVerticalGroup(
            jpViewBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpViewBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpViewBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbViewConsolidada)
                    .addComponent(jbViewNormal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpTabelaConsultaVendasLayout = new javax.swing.GroupLayout(jpTabelaConsultaVendas);
        jpTabelaConsultaVendas.setLayout(jpTabelaConsultaVendasLayout);
        jpTabelaConsultaVendasLayout.setHorizontalGroup(
            jpTabelaConsultaVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpTabelaConsultaVendasLayout.setVerticalGroup(
            jpTabelaConsultaVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpViewBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(jpTabelaConsultaVendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpViewBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpTabelaConsultaVendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iniciarTabela(){
        gerarTabela.limparTabela();
        
        //Configurando a tabela
        gerarTabela.getTable().getColumnModel().getColumn(3).setCellRenderer(new GerarTabelaEmCelulaUtil());
        gerarTabela.getTable().getColumnModel().getColumn(3).setCellEditor(new EditarCelulaTabelaUtil());
        gerarTabela.getTable().setRowHeight(150);
        gerarTabela.getTable().getColumnModel().getColumn(0).setMaxWidth(30);
        gerarTabela.getTable().getColumnModel().getColumn(1).setMaxWidth(180);
        gerarTabela.getTable().getColumnModel().getColumn(2).setMaxWidth(130);
        
        //Definindo SubTabela
        DefaultTableModel subTabela = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        subTabela.addColumn("ID");
        subTabela.addColumn("Valor Total");
        subTabela.addColumn("Status");
        subTabela.addRow(new Object[]{"1","R$ 45,60","Finalizado"});
        subTabela.addRow(new Object[]{"2","R$ 57,30","Finalizado"});
        subTabela.addRow(new Object[]{"3","R$ 58,70","Finalizado"});
        subTabela.addRow(new Object[]{"4","R$ 62,30","Finalizado"});
        subTabela.addRow(new Object[]{"5","R$ 40,50","Finalizado"});
        subTabela.addRow(new Object[]{"1","R$ 45,60","Finalizado"});
        subTabela.addRow(new Object[]{"2","R$ 57,30","Finalizado"});
        subTabela.addRow(new Object[]{"3","R$ 58,70","Finalizado"});
        subTabela.addRow(new Object[]{"4","R$ 62,30","Finalizado"});
        subTabela.addRow(new Object[]{"5","R$ 40,50","Final. Parcial"});
        subTabela.addRow(new Object[]{"1","R$ 45,60","Final. Parcial"});
        subTabela.addRow(new Object[]{"2","R$ 57,30","Final. Parcial"});
        subTabela.addRow(new Object[]{"3","R$ 58,70","Final. Parcial"});
        subTabela.addRow(new Object[]{"4","R$ 62,30","Final. Parcial"});
        subTabela.addRow(new Object[]{"5","R$ 40,50","Final. Parcial"});
        subTabela.addRow(new Object[]{"Total","Final. Parcial: R$4523.23","Finalizado: R$ 5678.65"});
        
        // Adicionando linhas
        gerarTabela.addLinha(new Object[]{"1", "Cliente Fictício LTDA", "02.952.561/0001-90",subTabela});
        gerarTabela.addLinha(new Object[]{"1", "Cliente Fictício LTDA", "02.952.561/0001-90",subTabela});
        gerarTabela.addLinha(new Object[]{"1", "Cliente Fictício LTDA", "02.952.561/0001-90",subTabela});
        gerarTabela.addLinha(new Object[]{"1", "Cliente Fictício LTDA", "02.952.561/0001-90",subTabela});
        gerarTabela.addLinha(new Object[]{"1", "Cliente Fictício LTDA", "02.952.561/0001-90",subTabela});
        
        //Exibe tabela em tela
        jpTabelaConsultaVendas.setLayout(new BorderLayout());
        jpTabelaConsultaVendas.add(gerarTabela.getScrollPane(), BorderLayout.CENTER);
        jpTabelaConsultaVendas.setVisible(true);
    }
    
    private void jbViewConsolidadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbViewConsolidadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbViewConsolidadaActionPerformed

    private void jbViewNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbViewNormalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbViewNormalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbViewConsolidada;
    private javax.swing.JButton jbViewNormal;
    private javax.swing.JPanel jpTabelaConsultaVendas;
    private javax.swing.JPanel jpViewBotoes;
    // End of variables declaration//GEN-END:variables
}
