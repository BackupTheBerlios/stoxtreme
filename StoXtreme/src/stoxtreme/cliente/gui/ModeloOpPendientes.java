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

/**
 *  Muestra la tabla de operaciones pendientes
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ModeloOpPendientes extends AbstractTableModel {
	private Hashtable<Integer, Operacion> opPendientes;
	private ArrayList listaIDS;

	private DefaultTableCellRenderer renderer =
		new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JButton b = new JButton(new ImageIcon("cancel.png"));
				b.setEnabled(true);
				b.setBorderPainted(false);
				return b;
			}
		};
	private static String[] nCol = {" ", "IDOperacion", "Tipo", "Empresa", "Cantidad", "Precio"};


	/**
	 *  Constructor de ModeloOpPendientes
	 */
	public ModeloOpPendientes() {
		opPendientes = new Hashtable<Integer, Operacion>();
		listaIDS = new ArrayList();
	}


	/**
	  *  Obtenemos el número de filas RowCount
	 *
	 *@return    Valor de RowCount
	 */
	public int getRowCount() {
		return listaIDS.size();
	}


	/**
	  *  Obtenemos el número de Columnas ColumnCount
	 *
	 *@return    Valor de ColumnCount
	 */
	public int getColumnCount() {
		return nCol.length;
	}


	/**
	 *  Obtenemos el nombre de Columnas ColumnName
	 *
	 *@param  index  Número de columna
	 *@return        Valor de ColumnName
	 */
	public String getColumnName(int index) {
		return nCol[index];
	}


	/**
	*  Obtenemos el valor ValueAt
	 *
	 *@param  rowIndex     Número de fila
	 *@param  columnIndex  Número de columna
	 *@return              Valor de ValueAt
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return null;
			case 1:
				return listaIDS.get(rowIndex);
			case 2:
				return ((Operacion) opPendientes.get(listaIDS.get(rowIndex))).getTipoOp() == Operacion.COMPRA ? "COMPRA" : "VENTA";
			case 3:
				return ((Operacion) opPendientes.get(listaIDS.get(rowIndex))).getEmpresa();
			case 4:
				return new Integer(((Operacion) opPendientes.get(listaIDS.get(rowIndex))).getCantidad());
			default:
				return new Double(((Operacion) opPendientes.get(listaIDS.get(rowIndex))).getPrecio());
		}
	}


	/**
	 *  Nos informa si una celda es editable o no
	 *
	 *@param  row  Número de fila
	 *@param  col  Número de columna
	 *@return      The CellEditable value
	 */
	public boolean isCellEditable(int row, int col) {
		return col == 0;
	}


	/**
	 *  Gets the Renderer attribute of the ModeloOpPendientes object
	 *
	 *@return    The Renderer value
	 */
	public DefaultTableCellRenderer getRenderer() {
		return renderer;
	}


	/**
	 *  Obtiene la Operacion del ModeloOpPendientes
	 *
	 *@param  idOp  Id de la operación
	 *@return       Devuelve la operación indicada
	 */
	public Operacion getOperacion(int idOp) {
		return opPendientes.get(idOp);
	}


	/**
	 *  Inserta una nueva operación
	 *
	 *@param  o     Operación a insertar
	 *@param  idOp  Id de la operación
	 */
	public void insertarOperacion(Operacion o, int idOp) {
		listaIDS.add(new Integer(idOp));
		opPendientes.put(new Integer(idOp), o);
		fireTableRowsInserted(listaIDS.size() - 1, listaIDS.size() - 1);
	}


	/**
	 *  Borra una operación existente
	 *
	 *@param  idOp  Id de la operación
	 */
	public void borrarOperacion(int idOp) {
		int i = listaIDS.indexOf(new Integer(idOp));
		listaIDS.remove(i);
		opPendientes.remove(new Integer(idOp));
		fireTableDataChanged();
		//fireTableRowsDeleted(i, i);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOp  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public boolean contains(int idOp) {
		return opPendientes.containsKey(idOp);
	}
}
