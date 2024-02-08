package br.com.pedrocamargo.vrvendas.view;

import br.com.pedrocamargo.vrvendas.controller.ProdutoController;
import br.com.pedrocamargo.vrvendas.dao.ProdutoDao;
import br.com.pedrocamargo.vrvendas.service.VendaService;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import br.com.pedrocamargo.vrvendas.util.ExibirJanelaUtil;

public class Main extends javax.swing.JFrame {
    private ProdutoController produtoController;
   
    public Main() {
        //Injeta dependencia do ProdutoController
        this.produtoController = new ProdutoController();
        initComponents();
        //Atualiza dados dos produtos ao abrir aplicacao
        try {
            produtoController.sincronizarProdutosApiBdLocal();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Erro ao sincronizar produtos\n"+ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmClientes = new javax.swing.JMenu();
        jmNovaVenda = new javax.swing.JMenu();
        jmConsultaVenda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VRVendas - Sistema de Lançamento de Vendas");
        setPreferredSize(new java.awt.Dimension(1000, 800));
        setResizable(false);

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
        );

        jmClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cliente.png"))); // NOI18N
        jmClientes.setMnemonic('C');
        jmClientes.setText("Clientes");
        jmClientes.setMaximumSize(new java.awt.Dimension(160, 32767));
        jmClientes.setPreferredSize(new java.awt.Dimension(160, 30));
        jmClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jmClientesMouseClicked(evt);
            }
        });
        jmClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmClientesActionPerformed(evt);
            }
        });
        jMenuBar1.add(jmClientes);

        jmNovaVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/novavenda.png"))); // NOI18N
        jmNovaVenda.setMnemonic('N');
        jmNovaVenda.setText("Nova Venda");
        jmNovaVenda.setPreferredSize(new java.awt.Dimension(160, 30));
        jmNovaVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jmNovaVendaMouseClicked(evt);
            }
        });
        jMenuBar1.add(jmNovaVenda);

        jmConsultaVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/consultarvenda.png"))); // NOI18N
        jmConsultaVenda.setMnemonic('O');
        jmConsultaVenda.setText("Consultar Vendas");
        jmConsultaVenda.setPreferredSize(new java.awt.Dimension(160, 19));
        jmConsultaVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jmConsultaVendaMouseClicked(evt);
            }
        });
        jMenuBar1.add(jmConsultaVenda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jmClientesActionPerformed(java.awt.event.ActionEvent evt){
        
    }
    private void jmClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmClientesMouseClicked
        CadastroClientesView telaClientes = new CadastroClientesView();
        ExibirJanelaUtil.abrirFormulario(telaClientes, desktop);
    }//GEN-LAST:event_jmClientesMouseClicked

    private void jmNovaVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmNovaVendaMouseClicked
        NovaVendaView telaNovaVenda = new NovaVendaView();
        ExibirJanelaUtil.abrirFormulario(telaNovaVenda,desktop);
    }//GEN-LAST:event_jmNovaVendaMouseClicked

    private void jmConsultaVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmConsultaVendaMouseClicked
        ConsultarVendasView telaConsultaVenda = new ConsultarVendasView();
        ExibirJanelaUtil.abrirFormulario(telaConsultaVenda,desktop);
    }//GEN-LAST:event_jmConsultaVendaMouseClicked

   
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jmClientes;
    private javax.swing.JMenu jmConsultaVenda;
    private javax.swing.JMenu jmNovaVenda;
    // End of variables declaration//GEN-END:variables

    private void JOptionPane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
