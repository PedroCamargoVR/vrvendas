package util;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class EditarCelulaTabelaUtil extends DefaultCellEditor {
    
    private DefaultTableModel model;
    
    public EditarCelulaTabelaUtil() {
        super(new JCheckBox());
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int i, int i1){
        model = (DefaultTableModel) o;
        JScrollPane jsp = new JScrollPane();
        JTable table = new JTable(model);
        jsp.setViewportView(table);
        return jsp;
    }
    
    @Override
    public Object getCellEditorValue(){
        return model;
    }
}
