package util;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ListSelectionModel;
import org.intellij.lang.annotations.JdkConstants;

public class GerarTabelaUtil {

    private JTable table;
    private DefaultTableModel modeloTabela;
    private JScrollPane scrollPane;
    private String[] nomeColunas;

    public GerarTabelaUtil(String[] columnNames, boolean isConsolidado) {
        this.nomeColunas = columnNames;
        
        if(isConsolidado){
            modeloTabela = new DefaultTableModel();
        }else{
            modeloTabela = new DefaultTableModel(){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }
        modeloTabela.setColumnIdentifiers(columnNames);
        table = new JTable(modeloTabela);
        scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        table.setVisible(true);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void addLinha(Object[] data) {
        modeloTabela.addRow(data);
    }
    
    public void addLinha(Object[] data, boolean isCabecalho) {
        if(isCabecalho){
            modeloTabela.addRow(data);
            
        }else{
            modeloTabela.addRow(data);   
        }
    }
    
    public void limparTabela() {
        modeloTabela.setRowCount(0);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    
    public JTable getTable() {
        return table;
    }
    
    public String[] getNomeColunas(){
        return nomeColunas;
    }
    
    public Map<String,String> dadosLinhaSelecionada(){
        Map<String,String> dados = new HashMap<String,String>();
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada != -1) {
            int modelRow = table.convertRowIndexToModel(linhaSelecionada);    
            for(int i = 0; i < nomeColunas.length; i++){
                dados.put((String) nomeColunas[i], (String) table.getModel().getValueAt(modelRow, i));
            }
        }
        return dados;
    }
}
