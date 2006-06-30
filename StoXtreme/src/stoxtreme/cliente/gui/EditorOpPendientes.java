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

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public abstract class EditorOpPendientes extends AbstractCellEditor implements TableCellEditor {

	/**
	 *  Gets the TableCellEditorComponent attribute of the EditorOpPendientes
	 *  object
	 *
	 *@param  table       Description of Parameter
	 *@param  value       Description of Parameter
	 *@param  isSelected  Description of Parameter
	 *@param  row         Description of Parameter
	 *@param  column      Description of Parameter
	 *@return             The TableCellEditorComponent value
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		JButton boton = new JButton(new ImageIcon("cancel.png"));
		boton.addActionListener(
					new ListenerFila(row) {
						public void _borraOperacion(int fila) {
							borraOperacion(fila);
						}
					});
		boton.setBorderPainted(false);
		return boton;
	}


	/**
	 *  Gets the CellEditorValue attribute of the EditorOpPendientes object
	 *
	 *@return    The CellEditorValue value
	 */
	public Object getCellEditorValue() {
		return " ";
	}


	/**
	 *  Description of the Method
	 *
	 *@param  fila  Description of Parameter
	 */
	public abstract void borraOperacion(int fila);


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	private abstract class ListenerFila implements ActionListener {
		private int fila;


		/**
		 *  Constructor for the ListenerFila object
		 *
		 *@param  fila  Description of Parameter
		 */
		public ListenerFila(int fila) {
			this.fila = fila;
		}


		/**
		 *  Description of the Method
		 *
		 *@param  e  Description of Parameter
		 */
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog((Component) e.getSource(), "Esta seguro?", "Borrar Operacion", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				_borraOperacion(fila);
			}
		}


		/**
		 *  Description of the Method
		 *
		 *@param  i  Description of Parameter
		 */
		public abstract void _borraOperacion(int i);
	}
}
