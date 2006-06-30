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
 *  Description of the Class
 *
 *@author    Chris Seguin
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
	 *  Constructor for the ModeloOpPendientes object
	 */
	public ModeloOpPendientes() {
		opPendientes = new Hashtable<Integer, Operacion>();
		listaIDS = new ArrayList();
	}


	/**
	 *  Gets the RowCount attribute of the ModeloOpPendientes object
	 *
	 *@return    The RowCount value
	 */
	public int getRowCount() {
		return listaIDS.size();
	}


	/**
	 *  Gets the ColumnCount attribute of the ModeloOpPendientes object
	 *
	 *@return    The ColumnCount value
	 */
	public int getColumnCount() {
		return nCol.length;
	}


	/**
	 *  Gets the ColumnName attribute of the ModeloOpPendientes object
	 *
	 *@param  index  Description of Parameter
	 *@return        The ColumnName value
	 */
	public String getColumnName(int index) {
		return nCol[index];
	}


	/**
	 *  Gets the ValueAt attribute of the ModeloOpPendientes object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The ValueAt value
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
	 *  Gets the CellEditable attribute of the ModeloOpPendientes object
	 *
	 *@param  row  Description of Parameter
	 *@param  col  Description of Parameter
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
	 *  Gets the Operacion attribute of the ModeloOpPendientes object
	 *
	 *@param  idOp  Description of Parameter
	 *@return       The Operacion value
	 */
	public Operacion getOperacion(int idOp) {
		return opPendientes.get(idOp);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  o     Description of Parameter
	 *@param  idOp  Description of Parameter
	 */
	public void insertarOperacion(Operacion o, int idOp) {
		listaIDS.add(new Integer(idOp));
		opPendientes.put(new Integer(idOp), o);
		fireTableRowsInserted(listaIDS.size() - 1, listaIDS.size() - 1);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOp  Description of Parameter
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
