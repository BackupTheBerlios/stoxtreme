package stoxtreme.servidor.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import stoxtreme.interfaz_remota.Operacion;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ModeloTablaOperaciones extends AbstractTableModel {
	private ArrayList listaOperaciones;


	/**
	 *  Constructor for the ModeloTablaOperaciones object
	 */
	public ModeloTablaOperaciones() {
		super();
		listaOperaciones = new ArrayList();
	}


	/**
	 *  Gets the ColumnCount attribute of the ModeloTablaOperaciones object
	 *
	 *@return    The ColumnCount value
	 */
	public int getColumnCount() {
		return 6;
	}


	/**
	 *  Gets the RowCount attribute of the ModeloTablaOperaciones object
	 *
	 *@return    The RowCount value
	 */
	public int getRowCount() {
		return listaOperaciones.size();
	}


	/**
	 *  Gets the ColumnName attribute of the ModeloTablaOperaciones object
	 *
	 *@param  columnIndex  Description of Parameter
	 *@return              The ColumnName value
	 */
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return "ID Operacion";
			case 1:
				return "ID Usuario";
			case 2:
				return "ID Empresa";
			case 3:
				return "Cantidad";
			case 4:
				return "Precio";
			default:
				return "Tipo Operacion";
		}
	}


	/**
	 *  Gets the ValueAt attribute of the ModeloTablaOperaciones object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The ValueAt value
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Operacion o = (Operacion) listaOperaciones.get(rowIndex);

		switch (columnIndex) {
			case 0:
				return new Integer(rowIndex);
			case 1:
				return o.getIdEmisor();
			case 2:
				return o.getEmpresa();
			case 3:
				return new Integer(o.getCantidad());
			case 4:
				return new Double(o.getPrecio());
			default:
				if (o.getTipoOp() == Operacion.COMPRA) {
					return "Compra";
				}
				else {
					return "Venta";
				}
		}
	}


	/**
	 *  Gets the ColumnClass attribute of the ModeloTablaOperaciones object
	 *
	 *@param  column  Description of Parameter
	 *@return         The ColumnClass value
	 */
	public Class getColumnClass(int column) {
		switch (column) {
			case 0:
			case 3:
				return Integer.class;
			case 1:
			case 2:
			case 5:
				return String.class;
			case 4:
				return Double.class;
			default:
				return Object.class;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  o  Description of Parameter
	 */
	public void insertarOperacion(Operacion o) {
		listaOperaciones.add(o);
		int fila = listaOperaciones.size() - 1;
		fireTableRowsInserted(fila, fila);
	}
}
