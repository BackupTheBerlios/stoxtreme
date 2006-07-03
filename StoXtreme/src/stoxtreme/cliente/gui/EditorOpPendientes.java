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
 *  Muestra en una tabla todas las operaciones pendientes de un cliente
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public abstract class EditorOpPendientes extends AbstractCellEditor implements TableCellEditor {

	/**
	 *  Gets the TableCellEditorComponent attribute of the EditorOpPendientes
	 *  object
	 *
	 *@param  table       tabla de operaciones
	 *@param  value       elemento seleccionado
	 *@param  isSelected  booleano para seleccionar
	 *@param  row         número de filas 
	 *@param  column      número de columnas
	 *@return             devuelve el componenete gráfico
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
	 *  elimina una operacion
	 *
	 *@param  fila  que se desea eliminar
	 */
	public abstract void borraOperacion(int fila);


	/**
	 *  Clase para el control de eventos de las filas
	 *
	 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
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
