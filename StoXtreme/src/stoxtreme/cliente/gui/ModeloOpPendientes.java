package stoxtreme.cliente.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import stoxtreme.interfaz_remota.Operacion;

public class ModeloOpPendientes extends AbstractTableModel{
	private static String[] nCol = {" ","IDOperacion", "Tipo", "Empresa", "Cantidad", "Precio"};
	private Hashtable opPendientes;
	private ArrayList listaIDS;
	
	public ModeloOpPendientes(){
		opPendientes = new Hashtable();
		listaIDS = new ArrayList();
	}
	public int getRowCount() {
		return listaIDS.size();
	}

	public int getColumnCount() {
		return nCol.length;
	}
	public String getColumnName(int index){
		return nCol[index];
	}
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0: return null;
		case 1: return listaIDS.get(rowIndex);
		case 2: return ((Operacion)opPendientes.get(listaIDS.get(rowIndex))).getTipoOp()==Operacion.COMPRA?"COMPRA":"VENTA";
		case 3: return ((Operacion)opPendientes.get(listaIDS.get(rowIndex))).getEmpresa();
		case 4: return new Integer(((Operacion)opPendientes.get(listaIDS.get(rowIndex))).getCantidad());
		default: return new Double(((Operacion)opPendientes.get(listaIDS.get(rowIndex))).getPrecio());
		}
	}
	
	public void insertarOperacion(Operacion o, int idOp){
		listaIDS.add(new Integer(idOp));
		opPendientes.put(new Integer(idOp), o);
		fireTableRowsInserted(listaIDS.size()-1, listaIDS.size()-1);
	}
	
	public void borrarOperacion(int idOp){
		int i = listaIDS.indexOf(new Integer(idOp)); 
		listaIDS.remove(i);
		opPendientes.remove(new Integer(idOp));
		fireTableRowsDeleted(i, i);
	}
	
	public boolean isCellEditable(int row, int col){
		return col==0;
	}
	
	private class EditorOpPendientes extends AbstractCellEditor implements TableCellEditor, ActionListener{
		private JButton b;
		
		public EditorOpPendientes(){
			b =  new JButton(new ImageIcon("icons/cancel.png"));
			b.setActionCommand("borra_op");
			b.addActionListener(this);
			b.setBorderPainted(false);
		}
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			return b;
		}

		public Object getCellEditorValue() {
			return " ";
		}
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showConfirmDialog(b, "Esta seguro?");
		}
		
	}
	
	private EditorOpPendientes editor = new EditorOpPendientes();
	public TableCellEditor getEditor() {
		return editor;
	}
	
	private DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JButton b = new JButton(new ImageIcon("icons/cancel.png"));
			b.setEnabled(true);
			b.setBorderPainted(false);
			return b;
		}
	};
	public DefaultTableCellRenderer getRenderer(){
		return renderer;
	}
}
