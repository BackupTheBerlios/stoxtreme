package stoxtreme.cliente.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 *  Muestra el control de las empresas
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
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
	 * Obtenemos el n�mero de filas RowCount
	 *
	 *@return    Valor de RowCount
	 */
	public int getRowCount() {
		return listaEmpresas.size();
	}


	/**
	 *  Obtenemos el n�mero de Columnas ColumnCount
	 *
	 *@return    Valor de ColumnCount
	 */
	public int getColumnCount() {
		return 1;
	}


	/**
	 *  Obtenemos el valor ValueAt
	 *
	 *@param  rowIndex     N�mero de fila
	 *@param  columnIndex  N�mero de columna
	 *@return              Valor de ValueAt
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return listaEmpresas.get(rowIndex);
	}


	/**
	 *  Obtenemos el nombre de Columnas ColumnName
	 *
	 *@param  columnIndex   N�mero de columna
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