package stoxtreme.servidor.gui;
import java.awt.Component;
import java.util.Hashtable;
import java.util.TreeSet;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ModeloPsicologico;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public abstract class ModeloTablaEdicion extends AbstractTableModel implements TableCellRenderer {

	private String id;
	private Hashtable<String, String> valores;
	private TreeSet<String> distribuidas;
	private ComboTextoCellEditor editor;

	private int tipo;

	private String[] distribuciones = {
			"uniforme1", "normal2", "binomial3"
			};
	/**
	 *  Description of the Field
	 */
	public static int MODELO_PSICOLOGICO = 0;
	/**
	 *  Description of the Field
	 */
	public static int MODELO_SOCIAL = 1;

	/**
	 *  Description of the Field
	 */
	public static String[] params_psicologicos = {
			"tiempo_espera",
			"numero_maximo_acciones_compra",
			"numero_minimo_acciones_compra",
			"numero_maximo_acciones_venta",
			"numero_minimo_acciones_venta",
			"numero_maximo_cancelaciones",
			"porcentaje_maximo_compra",
			"porcentaje_maximo_venta",
			"porcentaje_minimo_compra",
			"porcentaje_minimo_venta",
			"porcentaje_subida_precio",
			"porcentaje_bajada_precio",
			"precio_recomendacion",
			"precio_compra_recomendacion"
			};

	/**
	 *  Description of the Field
	 */
	public static String[] params_social = {
			"fiabilidad_rumor",
			"numero_conocidos"
			};

	private static String[] columnas = {
			"Parametro", "Valor", "Distribucion"
			};


	/**
	 *  Constructor for the ModeloTablaEdicion object
	 *
	 *@param  tipo     Description of Parameter
	 *@param  editor2  Description of Parameter
	 *@param  id       Description of Parameter
	 */
	public ModeloTablaEdicion(int tipo, ComboTextoCellEditor editor2, String id) {
		this.tipo = tipo;
		valores = new Hashtable<String, String>();
		distribuidas = new TreeSet<String>();
		this.editor = editor2;
		this.id = id;
	}


	/**
	 *  Sets the ValorNormal attribute of the ModeloTablaEdicion object
	 *
	 *@param  opcion  The new ValorNormal value
	 *@param  valor   The new ValorNormal value
	 */
	public void setValorNormal(String opcion, double valor) {
		valores.put(opcion, Double.toString(valor));
	}


	/**
	 *  Sets the ValorDistribuido attribute of the ModeloTablaEdicion object
	 *
	 *@param  opcion  The new ValorDistribuido value
	 *@param  valor   The new ValorDistribuido value
	 */
	public void setValorDistribuido(String opcion, String valor) {
		distribuidas.add(opcion);
		valores.put(opcion, valor);
	}


	/**
	 *  Sets the ValueAt attribute of the ModeloTablaEdicion object
	 *
	 *@param  aValue       The new ValueAt value
	 *@param  rowIndex     The new ValueAt value
	 *@param  columnIndex  The new ValueAt value
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if ("".equals(aValue)) {
			return;
		}
		if (columnIndex == 1) {
			if (tipo == MODELO_PSICOLOGICO) {
				valores.put(params_psicologicos[rowIndex], aValue.toString());
			}
			else {
				valores.put(params_social[rowIndex], aValue.toString());
			}
		}
		else if (columnIndex == 2) {
			if (tipo == MODELO_PSICOLOGICO) {
				if (distribuidas.contains(params_psicologicos[rowIndex])) {
					distribuidas.remove(params_psicologicos[rowIndex]);
					editor.setTexto(rowIndex);
				}
				else {
					distribuidas.add(params_psicologicos[rowIndex]);
					editor.setCombo(rowIndex);
				}
			}
			else {
				if (distribuidas.contains(params_social[rowIndex])) {
					distribuidas.remove(params_social[rowIndex]);
					editor.setTexto(rowIndex);
				}
				else {
					distribuidas.add(params_social[rowIndex]);
					editor.setCombo(rowIndex);
				}
			}
		}
		actualiza();
	}


	/**
	 *  Sets the Id attribute of the ModeloTablaEdicion object
	 *
	 *@param  id  The new Id value
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 *  Gets the CellEditable attribute of the ModeloTablaEdicion object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The CellEditable value
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex != 0;
	}


	/**
	 *  Gets the RowCount attribute of the ModeloTablaEdicion object
	 *
	 *@return    The RowCount value
	 */
	public int getRowCount() {
		if (tipo == MODELO_PSICOLOGICO) {
			return params_psicologicos.length;
		}
		else {
			return params_social.length;
		}
	}


	/**
	 *  Gets the ColumnCount attribute of the ModeloTablaEdicion object
	 *
	 *@return    The ColumnCount value
	 */
	public int getColumnCount() {
		return 3;
	}


	/**
	 *  Gets the ValueAt attribute of the ModeloTablaEdicion object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The ValueAt value
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			if (tipo == MODELO_PSICOLOGICO) {
				return params_psicologicos[rowIndex];
			}
			else {
				return params_social[rowIndex];
			}
		}
		else if (columnIndex == 1) {
			String clave;
			if (tipo == MODELO_PSICOLOGICO) {
				clave = params_psicologicos[rowIndex];
			}
			else {
				clave = params_social[rowIndex];
			}
			return valores.get(clave);
		}
		else {
			if (tipo == MODELO_PSICOLOGICO) {
				return distribuidas.contains(params_psicologicos[rowIndex]);
			}
			else {
				return distribuidas.contains(params_social[rowIndex]);
			}
		}
	}


	/**
	 *  Gets the ColumnName attribute of the ModeloTablaEdicion object
	 *
	 *@param  column  Description of Parameter
	 *@return         The ColumnName value
	 */
	public String getColumnName(int column) {
		return columnas[column];
	}


	/**
	 *  Gets the ColumnClass attribute of the ModeloTablaEdicion object
	 *
	 *@param  columnIndex  Description of Parameter
	 *@return              The ColumnClass value
	 */
	public Class<?> getColumnClass(int columnIndex) {
		return (columnIndex != 2) ? Object.class : Boolean.class;
	}


	/**
	 *  Gets the Distribucion attribute of the ModeloTablaEdicion object
	 *
	 *@param  row  Description of Parameter
	 *@return      The Distribucion value
	 */
	public boolean isDistribucion(int row) {
		if (tipo == MODELO_PSICOLOGICO) {
			return distribuidas.contains(params_psicologicos[row]);
		}
		else {
			return distribuidas.contains(params_social[row]);
		}
	}


	/**
	 *  Gets the TableCellRendererComponent attribute of the ModeloTablaEdicion
	 *  object
	 *
	 *@param  table       Description of Parameter
	 *@param  value       Description of Parameter
	 *@param  isSelected  Description of Parameter
	 *@param  hasFocus    Description of Parameter
	 *@param  row         Description of Parameter
	 *@param  column      Description of Parameter
	 *@return             The TableCellRendererComponent value
	 */
	public Component getTableCellRendererComponent(
			JTable table, Object value,
			boolean isSelected, boolean hasFocus,
			int row, int column) {
		String v;
		if (tipo == MODELO_PSICOLOGICO) {
			v = valores.get(params_psicologicos[row]);
		}
		else {
			v = valores.get(params_social[row]);
		}
		return new JLabel(v);
	}


	/**
	 *  Gets the Id attribute of the ModeloTablaEdicion object
	 *
	 *@return    The Id value
	 */
	public String getId() {
		return id;
	}


	/**
	 *  Gets the FilaPsicologico attribute of the ModeloTablaEdicion object
	 *
	 *@param  opcion  Description of Parameter
	 *@return         The FilaPsicologico value
	 */
	public int getFilaPsicologico(String opcion) {
		int i = 0;
		while (i < params_psicologicos.length && !params_psicologicos[i].equals(opcion)) {
			i++;
		}
		return i;
	}


	/**
	 *  Gets the FilaSocial attribute of the ModeloTablaEdicion object
	 *
	 *@param  opcion  Description of Parameter
	 *@return         The FilaSocial value
	 */
	public int getFilaSocial(String opcion) {
		int i = 0;
		while (i < params_social.length && !params_social[i].equals(opcion)) {
			i++;
		}
		return i;
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return id;
	}


	/**
	 *  Description of the Method
	 */
	public abstract void actualiza();
}
