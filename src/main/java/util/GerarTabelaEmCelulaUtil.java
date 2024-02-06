package util;

import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class GerarTabelaEmCelulaUtil implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected, boolean hasFocus, int row, int column) {
        DefaultTableModel model = (DefaultTableModel) o;
        JScrollPane jsp = new JScrollPane();
        JTable table = new JTable(model);
        jsp.setViewportView(table);
        return jsp;
    }
    
}
