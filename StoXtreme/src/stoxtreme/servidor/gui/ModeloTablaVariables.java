package stoxtreme.servidor.gui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ModeloTablaVariables extends AbstractTableModel {
	private Hashtable<String, Double> valorVariables;
	private ArrayList nombreVariables;


	/**
	 *  Constructor for the ModeloTablaVariables object
	 */
	public ModeloTablaVariables() {
		valorVariables = new Hashtable<String, Double>();
		nombreVariables = new ArrayList();
	}


	/**
	 *  Sets the NombreVariables attribute of the ModeloTablaVariables object
	 *
	 *@param  nombreVariables  The new NombreVariables value
	 */
	public void setNombreVariables(ArrayList nombreVariables) {
		this.nombreVariables = nombreVariables;
	}


	/**
	 *  Sets the ValorVariables attribute of the ModeloTablaVariables object
	 *
	 *@param  valorVariables  The new ValorVariables value
	 */
	public void setValorVariables(Hashtable<String, Double> valorVariables) {
		this.valorVariables = valorVariables;
	}


	/**
	 *  Gets the RowCount attribute of the ModeloTablaVariables object
	 *
	 *@return    The RowCount value
	 */
	public int getRowCount() {
		return nombreVariables.size();
	}


	/**
	 *  Gets the ColumnCount attribute of the ModeloTablaVariables object
	 *
	 *@return    The ColumnCount value
	 */
	public int getColumnCount() {
		return 2;
	}


	/**
	 *  Gets the ValueAt attribute of the ModeloTablaVariables object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The ValueAt value
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		String nombreVar = (String) nombreVariables.get(rowIndex);
		return (columnIndex == 0) ? nombreVar : valorVariables.get(nombreVar);
	}


	/**
	 *  Gets the ColumnClass attribute of the ModeloTablaVariables object
	 *
	 *@param  col  Description of Parameter
	 *@return      The ColumnClass value
	 */
	public Class getColumnClass(int col) {
		if (col == 0) {
			return String.class;
		}
		else {
			return Double.class;
		}
	}


	/**
	 *  Gets the ColumnName attribute of the ModeloTablaVariables object
	 *
	 *@param  columnIndex  Description of Parameter
	 *@return              The ColumnName value
	 */
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return "Nombre";
			default:
				return "Valor";
		}
	}


	/**
	 *  Gets the NombreVariables attribute of the ModeloTablaVariables object
	 *
	 *@return    The NombreVariables value
	 */
	public ArrayList getNombreVariables() {
		return nombreVariables;
	}


	/**
	 *  Gets the ValorVariables attribute of the ModeloTablaVariables object
	 *
	 *@return    The ValorVariables value
	 */
	public Hashtable<String, Double> getValorVariables() {
		return valorVariables;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  nombre  Description of Parameter
	 *@param  valor   Description of Parameter
	 */
	public void insertarVariable(String nombre, double valor) {
		nombreVariables.add(nombre);
		valorVariables.put(nombre, valor);
		fireTableRowsInserted(nombreVariables.size() - 1, nombreVariables.size() - 1);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  nombre      Description of Parameter
	 *@param  nuevoValor  Description of Parameter
	 */
	public void cambiaVariable(String nombre, double nuevoValor) {
		valorVariables.put(nombre, nuevoValor);
		int index = nombreVariables.indexOf(nombre);
		fireTableCellUpdated(index, 1);
	}
}
