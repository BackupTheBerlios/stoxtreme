package stoxtreme.servidor.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ModeloTablaPrecioAcciones extends AbstractTableModel {
	private Hashtable precioAcciones;
	private ArrayList listaEmpresas;
	private Hashtable colorEmpresa;


	/**
	 *  Constructor for the ModeloTablaPrecioAcciones object
	 */
	public ModeloTablaPrecioAcciones() {
		precioAcciones = new Hashtable();
		listaEmpresas = new ArrayList();
		colorEmpresa = new Hashtable();
	}


	/**
	 *  Gets the ColumnName attribute of the ModeloTablaPrecioAcciones object
	 *
	 *@param  columnIndex  Description of Parameter
	 *@return              The ColumnName value
	 */
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return "Empresa";
			default:
				return "Precio Actual";
		}
	}


	/**
	 *  Gets the ColumnCount attribute of the ModeloTablaPrecioAcciones object
	 *
	 *@return    The ColumnCount value
	 */
	public int getColumnCount() {
		return 2;
	}


	/**
	 *  Gets the RowCount attribute of the ModeloTablaPrecioAcciones object
	 *
	 *@return    The RowCount value
	 */
	public int getRowCount() {
		return listaEmpresas.size();
	}


	/**
	 *  Gets the ValueAt attribute of the ModeloTablaPrecioAcciones object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The ValueAt value
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		String empresa = (String) listaEmpresas.get(rowIndex);
		if (columnIndex == 0) {
			return empresa;
		}
		else {
			return precioAcciones.get(empresa);
		}
	}


	/**
	 *  Gets the ColumnClass attribute of the ModeloTablaPrecioAcciones object
	 *
	 *@param  columnIndex  Description of Parameter
	 *@return              The ColumnClass value
	 */
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0) {
			return String.class;
		}
		else {
			return Double.class;
		}
	}


	/**
	 *  Gets the Renderer attribute of the ModeloTablaPrecioAcciones object
	 *
	 *@return    The Renderer value
	 */
	public TableCellRenderer getRenderer() {
		TableCellRenderer renderer =
			new DefaultTableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					if (column == 1) {
						component.setForeground((Color) colorEmpresa.get(listaEmpresas.get(row)));
					}
					return component;
				}
			};
		return renderer;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 */
	public void insertarEmpresa(String empresa) {
		listaEmpresas.add(empresa);
		precioAcciones.put(empresa, new Double(0.0));
		colorEmpresa.put(empresa, Color.black);
		this.fireTableRowsInserted(listaEmpresas.size() - 1, listaEmpresas.size() - 1);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa      Description of Parameter
	 *@param  nuevoPrecio  Description of Parameter
	 */
	public void cambiaPrecioAccion(String empresa, double nuevoPrecio) {
		precioAcciones.put(empresa, new Double(nuevoPrecio));
		int index = listaEmpresas.indexOf(empresa);
		this.fireTableCellUpdated(index, 1);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@param  color    Description of Parameter
	 */
	public void cambiaColor(String empresa, Color color) {
		colorEmpresa.put(empresa, color);
		int index = listaEmpresas.indexOf(empresa);
		this.fireTableCellUpdated(index, 1);
	}
}
