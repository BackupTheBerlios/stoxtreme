package stoxtreme.cliente.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 *  Muestra el control de las empresas
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
@SuppressWarnings("serial")
public class ModeloListaEmpresas extends AbstractTableModel {

	private ArrayList listaEmpresas = new ArrayList();



	/**
	 *  Constructor de ModeloListaEmpresas
	 *
	 *@param  lEmpresas  Listado de empresas
	 */
	public
	@SuppressWarnings("unchecked") ModeloListaEmpresas(ArrayList lEmpresas) {
		listaEmpresas = lEmpresas;
	}


	/**
	 * Obtenemos el número de filas RowCount
	 *
	 *@return    Valor de RowCount
	 */
	public int getRowCount() {
		return listaEmpresas.size();
	}


	/**
	 *  Obtenemos el número de Columnas ColumnCount
	 *
	 *@return    Valor de ColumnCount
	 */
	public int getColumnCount() {
		return 1;
	}


	/**
	 *  Obtenemos el valor ValueAt
	 *
	 *@param  rowIndex     Número de fila
	 *@param  columnIndex  Número de columna
	 *@return              Valor de ValueAt
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return listaEmpresas.get(rowIndex);
	}


	/**
	 *  Obtenemos el nombre de Columnas ColumnName
	 *
	 *@param  columnIndex   Número de columna
	 *@return        		Valor de ColumnName
	 */
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return "Empresa";
			default:
				return "Ver en grafico";
		}
	}

}