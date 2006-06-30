package stoxtreme.cliente.gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jfree.data.time.TimeSeriesCollection;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
@SuppressWarnings("serial")
public class ModeloPrecioAccionesGrafico extends AbstractTableModel {
	private ArrayList listaEmpresas;
	private ArrayList seleccionado;
	private Hashtable valoresAcciones;
	private Hashtable volumenAcciones;

	private String empresaEscucha = null;
	private JTextField textEscucha = null;



	/**
	 *  Constructor for the ModeloPrecioAccionesGrafico object
	 *
	 *@param  lEmpresas  Description of Parameter
	 *@param  fecha      Description of Parameter
	 */
	public
	@SuppressWarnings("unchecked") ModeloPrecioAccionesGrafico(ArrayList lEmpresas, Date fecha) {
		listaEmpresas = new ArrayList();
		seleccionado = new ArrayList();
		valoresAcciones = new Hashtable();
		volumenAcciones = new Hashtable();
		if (lEmpresas.size() >= 1) {
			for (int i = 0; i < lEmpresas.size(); i++) {
				String empresa = (String) lEmpresas.get(i);
				valoresAcciones.put(empresa, new ValoresEmpresa(empresa, 1));
				volumenAcciones.put(empresa, new ValoresHistoricos(empresa, fecha));
				listaEmpresas.add(empresa);
			}
			seleccionado.add(lEmpresas.get(0));
		}
	}


	/**
	 *  Sets the ValueAt attribute of the ModeloPrecioAccionesGrafico object
	 *
	 *@param  value  The new ValueAt value
	 *@param  row    The new ValueAt value
	 *@param  col    The new ValueAt value
	 */
	public void setValueAt(Object value, int row, int col) {
		if (col == 2) {
			String empresa = (String) listaEmpresas.get(row);
			if (((Boolean) value).booleanValue()) {
				seleccionado.add(empresa);
			}
			else {
				seleccionado.remove(empresa);
			}
		}
	}


	/**
	 *  Gets the ColumnName attribute of the ModeloPrecioAccionesGrafico object
	 *
	 *@param  columnIndex  Description of Parameter
	 *@return              The ColumnName value
	 */
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return "Empresa";
			case 1:
				return "Precio Actual";
			default:
				return "Ver en grafico";
		}
	}


	/**
	 *  Gets the ColumnCount attribute of the ModeloPrecioAccionesGrafico object
	 *
	 *@return    The ColumnCount value
	 */
	public int getColumnCount() {
		return 3;
	}


	/**
	 *  Gets the RowCount attribute of the ModeloPrecioAccionesGrafico object
	 *
	 *@return    The RowCount value
	 */
	public int getRowCount() {
		return listaEmpresas.size();
	}


	/**
	 *  Gets the ValueAt attribute of the ModeloPrecioAccionesGrafico object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The ValueAt value
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		String empresa = (String) listaEmpresas.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return empresa;
			case 1:
				return new Double(((ValoresEmpresa) valoresAcciones.get(empresa)).ultimoPrecio());
			default:
				return new Boolean(seleccionado.contains(empresa));
		}

	}


	/**
	 *  Gets the ColumnClass attribute of the ModeloPrecioAccionesGrafico object
	 *
	 *@param  columnIndex  Description of Parameter
	 *@return              The ColumnClass value
	 */
	@SuppressWarnings("unchecked")
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
				return Double.class;
			case 2:
				return Boolean.class;
			default:
				return Object.class;
		}
	}


	/**
	 *  Gets the Renderer attribute of the ModeloPrecioAccionesGrafico object
	 *
	 *@return    The Renderer value
	 */
	public TableCellRenderer getRenderer() {
		TableCellRenderer renderer =
			new DefaultTableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					if (column == 1 && ((ValoresEmpresa) valoresAcciones.get((String) listaEmpresas.get(row))).isTendenciaAlta()) {
						component.setForeground(Color.GREEN);
					}
					else {
						component.setForeground(Color.RED);
					}
					return component;
				}
			};
		return renderer;
	}


	/**
	 *  Gets the CellEditable attribute of the ModeloPrecioAccionesGrafico object
	 *
	 *@param  row    Description of Parameter
	 *@param  colum  Description of Parameter
	 *@return        The CellEditable value
	 */
	public boolean isCellEditable(int row, int colum) {
		return colum == 2;
	}


	/**
	 *  Gets the PreciosSeleccionados attribute of the
	 *  ModeloPrecioAccionesGrafico object
	 *
	 *@return    The PreciosSeleccionados value
	 */
	public TimeSeriesCollection getPreciosSeleccionados() {
		TimeSeriesCollection series = new TimeSeriesCollection();
		for (int i = 0; i < seleccionado.size(); i++) {
			String empresa = (String) seleccionado.get(i);
			series.addSeries((ValoresEmpresa) valoresAcciones.get(empresa));
		}
		return series;
	}


	/**
	 *  Gets the VolumenSeleccionado attribute of the ModeloPrecioAccionesGrafico
	 *  object
	 *
	 *@return    The VolumenSeleccionado value
	 */
	public TimeSeriesCollection getVolumenSeleccionado() {
		TimeSeriesCollection series = new TimeSeriesCollection();
		for (int i = 0; i < seleccionado.size(); i++) {
			String empresa = (String) seleccionado.get(i);
			series.addSeries((ValoresHistoricos) volumenAcciones.get(empresa));
		}
		return series;
	}


	/**
	 *  Gets the ModeloComboBox attribute of the ModeloPrecioAccionesGrafico
	 *  object
	 *
	 *@return    The ModeloComboBox value
	 */
	public ComboBoxModel getModeloComboBox() {
		ComboBoxModel comboModel = new DefaultComboBoxModel(listaEmpresas.toArray());
		return comboModel;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  row  Description of Parameter
	 */
	public void cambiaSeleccion(int row) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@param  valor    Description of Parameter
	 */
	public void insertaValor(String empresa, double valor) {
		((ValoresEmpresa) valoresAcciones.get(empresa)).insertarSiguienteValor(valor);
		fireTableCellUpdated(listaEmpresas.indexOf(empresa), 1);
		if (textEscucha != null && empresaEscucha.equals(empresa)) {
			textEscucha.setText(Double.toString(valor));
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@param  volumen  Description of Parameter
	 */
	public void insertaVolumen(String empresa, double volumen) {
		((ValoresHistoricos) volumenAcciones.get(empresa)).insertarSiguienteValor(volumen);
//		fireTableCellUpdated(listaEmpresas.indexOf(empresa),1);
//		if(textEscucha != null && empresaEscucha.equals(empresa)){
//			textEscucha.setText(Double.toString(valor));
//		}
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String empresaSeleccionada() {
		if (seleccionado.size() > 0) {
			return (String) seleccionado.get(0);
		}
		return null;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa             Description of Parameter
	 *@param  precioSeleccionado  Description of Parameter
	 */
	public void escuchaPrecio(String empresa, JTextField precioSeleccionado) {
		empresaEscucha = empresa;
		textEscucha = precioSeleccionado;
	}


	/**
	 *  Description of the Method
	 */
	public void quitaEscuchaPrecio() {
		empresaEscucha = null;
		textEscucha = null;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa             Description of Parameter
	 *@param  precioSeleccionado  Description of Parameter
	 */
	public void ponPrecio(String empresa, JTextField precioSeleccionado) {
		precioSeleccionado.setText(new Double(((ValoresEmpresa) valoresAcciones.get(empresa)).ultimoPrecio()).toString());
	}
}
