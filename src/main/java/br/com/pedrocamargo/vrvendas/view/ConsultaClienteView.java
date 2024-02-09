package br.com.pedrocamargo.vrvendas.view;

import br.com.pedrocamargo.vrvendas.controller.ClienteController;
import br.com.pedrocamargo.vrvendas.model.ClienteModel;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import br.com.pedrocamargo.vrvendas.util.GerarTabelaUtil;
import java.util.List;

public class ConsultaClienteView extends javax.swing.JInternalFrame {

    private ClienteController clienteController;
    private String[] nomeColunasConsulta = {"ID", "Nome", "Nome Fantasia", "Razão Social", "CNPJ"};
    private GerarTabelaUtil gerarTabelaConsulta = new GerarTabelaUtil(nomeColunasConsulta,false);
    private JInternalFrame framePai;
    
    public ConsultaClienteView(JInternalFrame framePai) {
        this.clienteController = new ClienteController();
        this.framePai = framePai;
        initComponents();
    }
    
    private void iniciarTabelaConsulta(){
        gerarTabelaConsulta.limparTabela();
        
        try {
            List<ClienteModel> clientes = clienteController.getClienteByCnpj(jtfCnpjBuscado.getText());
            ArrayList<String[]> dadosTabelaConsulta = new ArrayList<>();
            
            clientes.forEach((cliente) -> {
                dadosTabelaConsulta.add(new String[]{cliente.getId().toString(), cliente.getNome(), cliente.getNomeFantasia(), cliente.getRazaoSocial(),cliente.getCnpj()});
            });
            
            dadosTabelaConsulta.forEach((linha) -> {
                gerarTabelaConsulta.addLinha(linha);
            });
            
            jpTabelaClientes.setLayout(new BorderLayout());
            jpTabelaClientes.add(gerarTabelaConsulta.getScrollPane(), BorderLayout.CENTER);
            jpTabelaClientes.revalidate();
            jpTabelaClientes.repaint();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar clientes\n"+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpCampodeBusca = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfCnpjBuscado = new javax.swing.JTextField();
        jbConfirmar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jpTabelaClientes = new javax.swing.JPanel();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(700, 600));

        jLabel1.setText("Digite o CNPJ do cliente e pressione ENTER para buscar");

        jtfCnpjBuscado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfCnpjBuscadoKeyPressed(evt);
            }
        });

        jbConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/confirmar.png"))); // NOI18N
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
                    .addComponent(jtfCnpjBuscado)
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
                .addComponent(jtfCnpjBuscado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpTabelaClientes.setPreferredSize(new java.awt.Dimension(664, 458));
        jpTabelaClientes.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jpCampodeBusca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpTabelaClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jpTabelaClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            novaVendaView.populaCamposCliente(Integer.parseInt(dadosLinhaSelecionada.get("ID")));
            this.dispose();
        }
    }//GEN-LAST:event_jbConfirmarActionPerformed

    private void jtfCnpjBuscadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCnpjBuscadoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            iniciarTabelaConsulta();
        }
    }//GEN-LAST:event_jtfCnpjBuscadoKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbConfirmar;
    private javax.swing.JPanel jpCampodeBusca;
    private javax.swing.JPanel jpTabelaClientes;
    private javax.swing.JTextField jtfCnpjBuscado;
    // End of variables declaration//GEN-END:variables
}
