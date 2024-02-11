package br.com.pedrocamargo.vrvendas.view;

import br.com.pedrocamargo.vrvendas.controller.ClienteController;
import br.com.pedrocamargo.vrvendas.controller.VendaController;
import br.com.pedrocamargo.vrvendas.enums.StatusVendaEnum;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import br.com.pedrocamargo.vrvendas.model.VendaModel;
import br.com.pedrocamargo.vrvendas.vo.VendaVO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import br.com.pedrocamargo.vrvendas.util.EditarCelulaTabelaUtil;
import br.com.pedrocamargo.vrvendas.util.ExibirJanelaUtil;
import br.com.pedrocamargo.vrvendas.util.GerarTabelaUtil;
import br.com.pedrocamargo.vrvendas.util.GerarTabelaEmCelulaUtil;
import java.util.List;

public class ConsultarVendasView extends javax.swing.JInternalFrame {

    private VendaController vendaController;
    private ClienteController clienteController;
    private String[] nomeColunasVisualizacaoNormal = {"ID","Nome Cliente", "CNPJ", "Valor Total", "Data Última Atualização","Status"};;
    private String[] nomeColunasConsolidadoPorCliente = {"ID", "Razao Social", "CNPJ", "Vendas"};
    private String[] nomeColunasConsolidadoPorStatus = {"STATUS","Vendas"};
    private GerarTabelaUtil gerarTabela;
   
    public ConsultarVendasView() {
        this.vendaController = new VendaController();
        this.clienteController = new ClienteController();
        initComponents();
        iniciarTabelaNormal();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgTipoConsolidado = new javax.swing.ButtonGroup();
        jpViewBotoes = new javax.swing.JPanel();
        jbViewConsolidada = new javax.swing.JButton();
        jbViewNormal = new javax.swing.JButton();
        jrbPorCliente = new javax.swing.JRadioButton();
        jrbPorStatusVenda = new javax.swing.JRadioButton();
        jbEditarVenda = new javax.swing.JButton();
        jbRemoverVenda = new javax.swing.JButton();
        jbAtualizaTabelaNormal = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jpTabelaConsultaVendas = new javax.swing.JPanel();

        setClosable(true);
        setTitle("Consultar Vendas");
        setPreferredSize(new java.awt.Dimension(900, 730));

        jbViewConsolidada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/consolidada.png"))); // NOI18N
        jbViewConsolidada.setText("Visualização Consolidada");
        jbViewConsolidada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbViewConsolidadaActionPerformed(evt);
            }
        });

        jbViewNormal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/normal.png"))); // NOI18N
        jbViewNormal.setText("Visualização Normal");
        jbViewNormal.setEnabled(false);
        jbViewNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbViewNormalActionPerformed(evt);
            }
        });

        bgTipoConsolidado.add(jrbPorCliente);
        jrbPorCliente.setSelected(true);
        jrbPorCliente.setText("Por cliente");
        jrbPorCliente.setEnabled(false);
        jrbPorCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbPorClienteActionPerformed(evt);
            }
        });

        bgTipoConsolidado.add(jrbPorStatusVenda);
        jrbPorStatusVenda.setText("Por status venda");
        jrbPorStatusVenda.setEnabled(false);
        jrbPorStatusVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbPorStatusVendaActionPerformed(evt);
            }
        });

        jbEditarVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/editar.png"))); // NOI18N
        jbEditarVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditarVendaActionPerformed(evt);
            }
        });

        jbRemoverVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/remove.png"))); // NOI18N
        jbRemoverVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverVendaActionPerformed(evt);
            }
        });

        jbAtualizaTabelaNormal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/conferir.png"))); // NOI18N
        jbAtualizaTabelaNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAtualizaTabelaNormalActionPerformed(evt);
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
                .addGap(18, 18, 18)
                .addComponent(jrbPorCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jrbPorStatusVenda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(jbAtualizaTabelaNormal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbEditarVenda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbRemoverVenda)
                .addContainerGap())
        );
        jpViewBotoesLayout.setVerticalGroup(
            jpViewBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpViewBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpViewBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbRemoverVenda)
                    .addComponent(jbEditarVenda)
                    .addGroup(jpViewBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbViewConsolidada)
                        .addComponent(jbViewNormal)
                        .addComponent(jrbPorCliente)
                        .addComponent(jrbPorStatusVenda)
                        .addComponent(jbAtualizaTabelaNormal)))
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
    
    private void iniciarTabelaNormal(){
        for (Component comp : jpTabelaConsultaVendas.getComponents()) {
            if (comp.equals(gerarTabela.getScrollPane())) {
                jpTabelaConsultaVendas.remove(gerarTabela.getScrollPane());
            }
        }
        gerarTabela = new GerarTabelaUtil(nomeColunasVisualizacaoNormal,false);        
        List<VendaVO> vendas;
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        try {
            vendas = vendaController.getAllVendasVo();
            vendas.forEach((venda) -> {
                LocalDateTime ultimaAtualizacao = LocalDateTime.ofInstant(venda.getUpdated_at().toInstant(), ZoneId.of("America/Sao_Paulo"));
                
                gerarTabela.addLinha(new Object[]{
                        venda.getId(), 
                        venda.getCliente().getNome(), 
                        venda.getCliente().getCnpj(), 
                        "R$ " + venda.getValortotal().toString(),
                        ultimaAtualizacao.format(formatador), 
                        StatusVendaEnum.porCodigo(venda.getId_status()).getDescricao()
                    }
                );
            });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar tabela\n"+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        //Exibe tabela em tela
        jpTabelaConsultaVendas.setLayout(new BorderLayout());
        jpTabelaConsultaVendas.add(gerarTabela.getScrollPane(), BorderLayout.CENTER);
        jpTabelaConsultaVendas.setVisible(true);
        
        jpTabelaConsultaVendas.revalidate();
        jpTabelaConsultaVendas.repaint();
    }
    
    private void iniciarTabelaConsolidado(Integer tipo){
        /**
         * 1 - Por Clientes
         * 2 - Por Situação da Venda
         */
        switch(tipo){
            case 1:
                jpTabelaConsultaVendas.remove(gerarTabela.getScrollPane());
                gerarTabela = new GerarTabelaUtil(nomeColunasConsolidadoPorCliente,true);

                //Configurando a tabela
                gerarTabela.getTable().getColumnModel().getColumn(3).setCellRenderer(new GerarTabelaEmCelulaUtil());
                gerarTabela.getTable().getColumnModel().getColumn(3).setCellEditor(new EditarCelulaTabelaUtil());
                gerarTabela.getTable().setRowHeight(170);
                gerarTabela.getTable().getColumnModel().getColumn(0).setMaxWidth(30);
                gerarTabela.getTable().getColumnModel().getColumn(1).setMaxWidth(180);
                gerarTabela.getTable().getColumnModel().getColumn(2).setMaxWidth(130);
            
                try {
                    List<ClienteModel> clientes = clienteController.getClientes();                    
                    
                    clientes.forEach((cliente) -> {
                        try{
                            List<VendaModel> vendas = vendaController.getVendasByIdCliente(cliente.getId());
                            BigDecimal totalVendasDigitando = new BigDecimal(0);
                            BigDecimal totalVendasFinalParc = new BigDecimal(0);
                            BigDecimal totalVendasFinal = new BigDecimal(0);                        
                            
                            if(vendas.size() > 0){
                                DefaultTableModel subTabelaPorCliente = new DefaultTableModel(){
                                    @Override
                                    public boolean isCellEditable(int row, int column) {
                                        return false;
                                    }
                                };
                                subTabelaPorCliente.addColumn("ID");
                                subTabelaPorCliente.addColumn("Valor Total");
                                subTabelaPorCliente.addColumn("Status");

                                for(int i = 0; i < vendas.size(); i++){
                                    VendaModel venda = vendas.get(i);

                                    switch(venda.getId_status()){
                                        case 1:
                                            totalVendasDigitando = totalVendasDigitando.add(venda.getValortotal());
                                        break;
                                        case 2:
                                            totalVendasFinalParc = totalVendasFinalParc.add(venda.getValortotal());
                                        break;
                                        case 3:
                                            totalVendasFinal = totalVendasFinal.add(venda.getValortotal());
                                        break;
                                    }
                                    subTabelaPorCliente.addRow(new Object[]{venda.getId(),"R$ " + venda.getValortotal().toString(),StatusVendaEnum.porCodigo(venda.getId_status()).getDescricao()});
                                }

                                subTabelaPorCliente.addRow(new Object[]{""});
                                subTabelaPorCliente.addRow(new Object[]{"TOTALIZADORES:"});
                                subTabelaPorCliente.addRow(
                                        new Object[]{
                                            "Digitando: R$ " +  totalVendasDigitando.toString(),
                                            "Final. Parc.: R$ " +  totalVendasFinalParc.toString(),
                                            "Finalizados: R$ " +  totalVendasFinal.toString(),
                                        }
                                );
                                gerarTabela.addLinha(new Object[]{cliente.getId(), cliente.getNome(), cliente.getCnpj(),subTabelaPorCliente});
                            }
                        }catch(SQLException sqlE){
                            throw new RuntimeException(sqlE.getMessage());
                        }
                    });
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar clientes\n"+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
                
                //Exibe tabela em tela
                jpTabelaConsultaVendas.setLayout(new BorderLayout());
                jpTabelaConsultaVendas.add(gerarTabela.getScrollPane(), BorderLayout.CENTER);
                jpTabelaConsultaVendas.setVisible(true);

                jpTabelaConsultaVendas.revalidate();
                jpTabelaConsultaVendas.repaint();
            break;


            case 2:
                jpTabelaConsultaVendas.remove(gerarTabela.getScrollPane());
                gerarTabela = new GerarTabelaUtil(nomeColunasConsolidadoPorStatus,true);

                //Configurando a tabela
                gerarTabela.getTable().getColumnModel().getColumn(1).setCellRenderer(new GerarTabelaEmCelulaUtil());
                gerarTabela.getTable().getColumnModel().getColumn(1).setCellEditor(new EditarCelulaTabelaUtil());
                gerarTabela.getTable().setRowHeight(200);
                gerarTabela.getTable().getColumnModel().getColumn(0).setMaxWidth(150);                
                
                try {
                    for(int i = 1; i <= 3; i++){
                        List<VendaVO> vendasVo = vendaController.getVendasVoByIdStatus(i);
                        
                        //Definindo SubTabela
                        DefaultTableModel subTabelaSituacao = new DefaultTableModel(){
                                @Override
                                public boolean isCellEditable(int row, int column) {
                                    return false;
                                }
                            };
                        subTabelaSituacao.addColumn("ID");
                        subTabelaSituacao.addColumn("Valor Total");
                        subTabelaSituacao.addColumn("Cliente");
                        subTabelaSituacao.addColumn("CNPJ");

                        vendasVo.forEach((vendaVO) -> {
                            subTabelaSituacao.addRow(new Object[]{vendaVO.getId(),"R$ " + vendaVO.getValortotal().toString(),vendaVO.getCliente().getNome(),vendaVO.getCliente().getCnpj()});
                        });
                        
                        gerarTabela.addLinha(new Object[]{StatusVendaEnum.porCodigo(i).getDescricao().toUpperCase(),subTabelaSituacao});
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar vendas\n"+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }

                //Exibe tabela em tela
                jpTabelaConsultaVendas.setLayout(new BorderLayout());
                jpTabelaConsultaVendas.add(gerarTabela.getScrollPane(), BorderLayout.CENTER);
                jpTabelaConsultaVendas.setVisible(true);

                jpTabelaConsultaVendas.revalidate();
                jpTabelaConsultaVendas.repaint();
            break;

        }
        
    }
    
    private void jbViewConsolidadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbViewConsolidadaActionPerformed
        switchBotoes(true);
        iniciarTabelaConsolidado(1);
    }//GEN-LAST:event_jbViewConsolidadaActionPerformed

    private void jbViewNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbViewNormalActionPerformed
        switchBotoes(false);
        iniciarTabelaNormal();
    }//GEN-LAST:event_jbViewNormalActionPerformed

    private void jrbPorClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbPorClienteActionPerformed
        iniciarTabelaConsolidado(1);
    }//GEN-LAST:event_jrbPorClienteActionPerformed

    private void jrbPorStatusVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbPorStatusVendaActionPerformed
        iniciarTabelaConsolidado(2);
    }//GEN-LAST:event_jrbPorStatusVendaActionPerformed

    private void jbEditarVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditarVendaActionPerformed
        Map<String,String> dadosLinhaSelecionada = gerarTabela.dadosLinhaSelecionada();
        
        if(dadosLinhaSelecionada.isEmpty()){
             JOptionPane.showMessageDialog(null, "Nenhum registro selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }else{
            NovaVendaView telaEdicaoVenda;
            try {
                telaEdicaoVenda = new NovaVendaView(new VendaController(vendaController.getVendaById(Integer.parseInt(dadosLinhaSelecionada.get("ID")))));
                ExibirJanelaUtil.abrirFormulario(telaEdicaoVenda, super.getDesktopPane());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao buscar a venda para edição.", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jbEditarVendaActionPerformed

    private void jbAtualizaTabelaNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAtualizaTabelaNormalActionPerformed
        iniciarTabelaNormal();
    }//GEN-LAST:event_jbAtualizaTabelaNormalActionPerformed

    private void jbRemoverVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverVendaActionPerformed
       Map<String,String> dadosLinhaSelecionada = gerarTabela.dadosLinhaSelecionada();
        if(dadosLinhaSelecionada.isEmpty()){
             JOptionPane.showMessageDialog(null, "Nenhum registro selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }else if(!dadosLinhaSelecionada.get("Status").equals(StatusVendaEnum.porCodigo(1).getDescricao())){
            JOptionPane.showMessageDialog(null, "Você só pode excluir vendas em digitação.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }else{
             int resposta = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja excluir a venda selecionada?\n(Esta ação é permanente e não pode ser desfeita)",
             "Confirmação",JOptionPane.YES_NO_OPTION);
               if(resposta == JOptionPane.YES_OPTION){
                 try {
                     vendaController.excluirVenda(Integer.parseInt(dadosLinhaSelecionada.get("ID")));
                     iniciarTabelaNormal();
                 } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(null, "Erro ao exclur a venda", "Erro", JOptionPane.ERROR_MESSAGE);
                 }
               }
           }
    }//GEN-LAST:event_jbRemoverVendaActionPerformed
    
    private void switchBotoes(boolean b){
        jbViewNormal.setEnabled(b);
        jbViewConsolidada.setEnabled(!b);
        
        jrbPorCliente.setEnabled(b);
        jrbPorStatusVenda.setEnabled(b);
        
        jbEditarVenda.setEnabled(!b);
        jbRemoverVenda.setEnabled(!b);
        jbAtualizaTabelaNormal.setEnabled(!b);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgTipoConsolidado;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbAtualizaTabelaNormal;
    private javax.swing.JButton jbEditarVenda;
    private javax.swing.JButton jbRemoverVenda;
    private javax.swing.JButton jbViewConsolidada;
    private javax.swing.JButton jbViewNormal;
    private javax.swing.JPanel jpTabelaConsultaVendas;
    private javax.swing.JPanel jpViewBotoes;
    private javax.swing.JRadioButton jrbPorCliente;
    private javax.swing.JRadioButton jrbPorStatusVenda;
    // End of variables declaration//GEN-END:variables
}
