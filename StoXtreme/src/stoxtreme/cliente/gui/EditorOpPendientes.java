package stoxtreme.cliente.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public abstract class EditorOpPendientes extends AbstractCellEditor implements TableCellEditor{
	private abstract class ListenerFila implements ActionListener{
		private int fila;
		public ListenerFila(int fila) {
			this.fila = fila;
		}
		public void actionPerformed(ActionEvent e) {
			if(JOptionPane.showConfirmDialog((Component)e.getSource(), "Esta seguro?","Borrar Operacion", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
				_borraOperacion(fila);
			}
		}
		public abstract void _borraOperacion(int i);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		JButton boton = new JButton(new ImageIcon("cancel.png"));
		boton.addActionListener(new ListenerFila(row){
			public void _borraOperacion(int fila) {
				borraOperacion(fila);
			}
		});
		boton.setBorderPainted(false);
		return boton;
	}
	
	public Object getCellEditorValue() {
		return " ";
	}
	public abstract void borraOperacion(int fila);
}
