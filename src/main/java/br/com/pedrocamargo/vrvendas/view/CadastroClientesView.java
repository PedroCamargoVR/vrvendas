package br.com.pedrocamargo.vrvendas.view;

import br.com.pedrocamargo.vrvendas.controller.ClienteController;
import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import util.GerarTabelaUtil;

public class CadastroClientesView extends javax.swing.JInternalFrame {
    
    private ClienteController clienteController;
    private String[] nomeColunas = {"ID", "Nome", "Nome Fantasia", "Razão Social", "CNPJ"};
    private GerarTabelaUtil gerarTabela = new GerarTabelaUtil(nomeColunas,false);
    
    
    public CadastroClientesView() {
        this.clienteController = new ClienteController();
        initComponents();
        iniciarTabela();
        switchBotoes(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpClienteFormulario = new javax.swing.JPanel();
        jlNome = new javax.swing.JLabel();
        jtNome = new javax.swing.JTextField();
        jlNomeFantasia = new javax.swing.JLabel();
        jtNomeFantasia = new javax.swing.JTextField();
        jtRazaoSocial = new javax.swing.JTextField();
        jtCNPJ = new javax.swing.JTextField();
        jlCNPJ = new javax.swing.JLabel();
        jlRazaoSocial = new javax.swing.JLabel();
        lbId = new javax.swing.JLabel();
        jtId = new javax.swing.JTextField();
        jpClienteBotoes = new javax.swing.JPanel();
        jbClienteNovo = new javax.swing.JButton();
        jbClienteEditar = new javax.swing.JButton();
        jbClienteRemover = new javax.swing.JButton();
        jbClienteSalvar = new javax.swing.JButton();
        jbClienteCancelar = new javax.swing.JButton();
        jpClienteTabela = new javax.swing.JPanel();

        setClosable(true);
        setTitle("Cadastro de Cliente");
        setPreferredSize(new java.awt.Dimension(900, 700));

        jlNome.setText("Nome");

        jtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNomeActionPerformed(evt);
            }
        });

        jlNomeFantasia.setText("Nome Fantasia");

        jtRazaoSocial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtRazaoSocialActionPerformed(evt);
            }
        });

        jlCNPJ.setText("CNPJ");

        jlRazaoSocial.setText("Razão Social");

        lbId.setText("ID");

        jtId.setEnabled(false);

        javax.swing.GroupLayout jpClienteFormularioLayout = new javax.swing.GroupLayout(jpClienteFormulario);
        jpClienteFormulario.setLayout(jpClienteFormularioLayout);
        jpClienteFormularioLayout.setHorizontalGroup(
            jpClienteFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClienteFormularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpClienteFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpClienteFormularioLayout.createSequentialGroup()
                        .addComponent(lbId)
                        .addGap(96, 96, 96)
                        .addComponent(jlNome))
                    .addGroup(jpClienteFormularioLayout.createSequentialGroup()
                        .addComponent(jtId, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jpClienteFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlRazaoSocial)
                            .addComponent(jtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                            .addComponent(jtRazaoSocial))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jpClienteFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlNomeFantasia)
                    .addComponent(jlCNPJ)
                    .addGroup(jpClienteFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jtNomeFantasia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpClienteFormularioLayout.setVerticalGroup(
            jpClienteFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClienteFormularioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpClienteFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNome)
                    .addComponent(lbId)
                    .addComponent(jlNomeFantasia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpClienteFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtNomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpClienteFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlRazaoSocial)
                    .addComponent(jlCNPJ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpClienteFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jbClienteNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/novo.png"))); // NOI18N
        jbClienteNovo.setText("Novo");
        jbClienteNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClienteNovoActionPerformed(evt);
            }
        });

        jbClienteEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        jbClienteEditar.setText("Editar");
        jbClienteEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClienteEditarActionPerformed(evt);
            }
        });

        jbClienteRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remove.png"))); // NOI18N
        jbClienteRemover.setText("Remover");
        jbClienteRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClienteRemoverActionPerformed(evt);
            }
        });

        jbClienteSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/salvar.png"))); // NOI18N
        jbClienteSalvar.setText("Salvar");
        jbClienteSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClienteSalvarActionPerformed(evt);
            }
        });

        jbClienteCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancel.png"))); // NOI18N
        jbClienteCancelar.setText("Cancelar");
        jbClienteCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClienteCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpClienteBotoesLayout = new javax.swing.GroupLayout(jpClienteBotoes);
        jpClienteBotoes.setLayout(jpClienteBotoesLayout);
        jpClienteBotoesLayout.setHorizontalGroup(
            jpClienteBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClienteBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbClienteNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbClienteEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbClienteRemover)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbClienteSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbClienteCancelar)
                .addContainerGap())
        );
        jpClienteBotoesLayout.setVerticalGroup(
            jpClienteBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpClienteBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpClienteBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbClienteNovo)
                    .addComponent(jbClienteEditar)
                    .addComponent(jbClienteRemover)
                    .addComponent(jbClienteSalvar)
                    .addComponent(jbClienteCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpClienteTabelaLayout = new javax.swing.GroupLayout(jpClienteTabela);
        jpClienteTabela.setLayout(jpClienteTabelaLayout);
        jpClienteTabelaLayout.setHorizontalGroup(
            jpClienteTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpClienteTabelaLayout.setVerticalGroup(
            jpClienteTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpClienteBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jpClienteFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jpClienteTabela, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpClienteBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpClienteFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpClienteTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iniciarTabela(){
        gerarTabela.limparTabela();
        
        try {
            ResultSet rs = clienteController.getClientes();
            
            ArrayList<String[]> dadosTabela = new ArrayList<>();
            
            while(rs.next()){
                dadosTabela.add(new String[]{rs.getString("id"), rs.getString("nome"), rs.getString("nomefantasia"), rs.getString("razaosocial"),rs.getString("cnpj")});
            }
            
            dadosTabela.forEach((linha) -> {
                gerarTabela.addLinha(linha);
            });
            
            jpClienteTabela.setLayout(new BorderLayout());
            jpClienteTabela.add(gerarTabela.getScrollPane(), BorderLayout.CENTER);
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes\n"+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    private void jtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtNomeActionPerformed

    private void jtRazaoSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtRazaoSocialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtRazaoSocialActionPerformed

    private void jbClienteEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClienteEditarActionPerformed
        
        Map<String,String> dadosLinhaSelecionada = gerarTabela.dadosLinhaSelecionada();
        
        if(dadosLinhaSelecionada.isEmpty()){
             JOptionPane.showMessageDialog(null, "Nenhum registro selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }else{
           switchBotoes(false);
            jtId.setText(dadosLinhaSelecionada.get(nomeColunas[0]));
            jtNome.setText(dadosLinhaSelecionada.get(nomeColunas[1]));
            jtNomeFantasia.setText(dadosLinhaSelecionada.get(nomeColunas[2]));
            jtRazaoSocial.setText(dadosLinhaSelecionada.get(nomeColunas[3]));
            jtCNPJ.setText(dadosLinhaSelecionada.get(nomeColunas[4])); 
        }
    }//GEN-LAST:event_jbClienteEditarActionPerformed

    private void jbClienteNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClienteNovoActionPerformed
        switchBotoes(false);
    }//GEN-LAST:event_jbClienteNovoActionPerformed

    private void jbClienteSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClienteSalvarActionPerformed
        try {
            clienteController.salvarCliente(jtId.getText(), jtNome.getText(), jtNomeFantasia.getText(), jtRazaoSocial.getText(), jtCNPJ.getText());
            JOptionPane.showMessageDialog(null, "Operação realizada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao manipular cliente\n"+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        iniciarTabela();
        switchBotoes(true);
    }//GEN-LAST:event_jbClienteSalvarActionPerformed

    private void jbClienteCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClienteCancelarActionPerformed
        switchBotoes(true);
    }//GEN-LAST:event_jbClienteCancelarActionPerformed

    private void jbClienteRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClienteRemoverActionPerformed
        Map<String,String> dadosLinhaSelecionada = gerarTabela.dadosLinhaSelecionada();
        
        if(dadosLinhaSelecionada.isEmpty()){
             JOptionPane.showMessageDialog(null, "Nenhum registro selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }else{
            int resposta = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja remover o cliente?\n(Esta ação é permanente)",
          "Confirmação",JOptionPane.YES_NO_OPTION);
            if(resposta == JOptionPane.YES_OPTION){
                try {
                    clienteController.removerCliente(Integer.parseInt(dadosLinhaSelecionada.get("ID")));
                    JOptionPane.showMessageDialog(null, "Cliente removido com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    iniciarTabela();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao remover cliente\n"+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jbClienteRemoverActionPerformed

    private void switchBotoes(boolean b){
        jbClienteNovo.setEnabled(b);
        jbClienteEditar.setEnabled(b);
        jbClienteRemover.setEnabled(b);
        
        jbClienteSalvar.setEnabled(!b);
        jbClienteCancelar.setEnabled(!b);
        
        jtNome.setEnabled(!b);
        jtNomeFantasia.setEnabled(!b);
        jtRazaoSocial.setEnabled(!b);
        jtCNPJ.setEnabled(!b);
        
        jtId.setText("");
        jtNome.setText("");
        jtNomeFantasia.setText("");
        jtRazaoSocial.setText("");
        jtCNPJ.setText("");
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbClienteCancelar;
    private javax.swing.JButton jbClienteEditar;
    private javax.swing.JButton jbClienteNovo;
    private javax.swing.JButton jbClienteRemover;
    private javax.swing.JButton jbClienteSalvar;
    private javax.swing.JLabel jlCNPJ;
    private javax.swing.JLabel jlNome;
    private javax.swing.JLabel jlNomeFantasia;
    private javax.swing.JLabel jlRazaoSocial;
    private javax.swing.JPanel jpClienteBotoes;
    private javax.swing.JPanel jpClienteFormulario;
    private javax.swing.JPanel jpClienteTabela;
    private javax.swing.JTextField jtCNPJ;
    private javax.swing.JTextField jtId;
    private javax.swing.JTextField jtNome;
    private javax.swing.JTextField jtNomeFantasia;
    private javax.swing.JTextField jtRazaoSocial;
    private javax.swing.JLabel lbId;
    // End of variables declaration//GEN-END:variables
}
