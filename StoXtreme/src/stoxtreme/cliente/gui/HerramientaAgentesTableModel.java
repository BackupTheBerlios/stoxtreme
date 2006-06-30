package stoxtreme.cliente.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import stoxtreme.herramienta_agentes.agentes.Agente;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class HerramientaAgentesTableModel extends AbstractTableModel {
	private TreeSet<Integer> seleccionadas = new TreeSet<Integer>();
	private ArrayList<Agente> elementos = new ArrayList<Agente>();
	private static String[] columnas = {" ", "IDAgente", "Estado Actual", "Comportamiento", "Ganancias"};


	/**
	 *  Sets the Agentes attribute of the HerramientaAgentesTableModel object
	 *
	 *@param  agentes  The new Agentes value
	 */
	public void setAgentes(ArrayList<Agente> agentes) {
		elementos = agentes;
		fireTableDataChanged();
	}


	/**
	 *  Sets the ValueAt attribute of the HerramientaAgentesTableModel object
	 *
	 *@param  aValue       The new ValueAt value
	 *@param  rowIndex     The new ValueAt value
	 *@param  columnIndex  The new ValueAt value
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			if (seleccionadas.contains(rowIndex)) {
				seleccionadas.remove(rowIndex);
			}
			else {
				seleccionadas.add(rowIndex);
			}
		}
	}


	/**
	 *  Gets the ColumnName attribute of the HerramientaAgentesTableModel object
	 *
	 *@param  column  Description of Parameter
	 *@return         The ColumnName value
	 */
	public String getColumnName(int column) {
		return columnas[column];
	}


	/**
	 *  Gets the RowCount attribute of the HerramientaAgentesTableModel object
	 *
	 *@return    The RowCount value
	 */
	public int getRowCount() {
		return elementos.size();
	}


	/**
	 *  Gets the ColumnCount attribute of the HerramientaAgentesTableModel object
	 *
	 *@return    The ColumnCount value
	 */
	public int getColumnCount() {
		return columnas.length;
	}


	/**
	 *  Gets the CellEditable attribute of the HerramientaAgentesTableModel
	 *  object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The CellEditable value
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 0;
	}


	/**
	 *  Gets the ColumnClass attribute of the HerramientaAgentesTableModel object
	 *
	 *@param  columnIndex  Description of Parameter
	 *@return              The ColumnClass value
	 */
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return Boolean.class;
			case 1:
			case 2:
			case 3:
				return String.class;
			case 4:
				return Double.class;
			default:
				return Object.class;
		}
	}


	/**
	 *  Gets the ValueAt attribute of the HerramientaAgentesTableModel object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The ValueAt value
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return new Boolean(seleccionadas.contains(rowIndex));
			case 1:
				return elementos.get(rowIndex).getIDString();
			case 2:
				return elementos.get(rowIndex).getEstado();
			case 3:
				return elementos.get(rowIndex).getStringComportamiento();
			case 4:
				return new Double(elementos.get(rowIndex).getGanancias());
			default:
				return "";
		}
	}


	/**
	 *  Gets the AgentesSeleccionados attribute of the
	 *  HerramientaAgentesTableModel object
	 *
	 *@return    The AgentesSeleccionados value
	 */
	public ArrayList<Agente> getAgentesSeleccionados() {
		ArrayList<Agente> lista = new ArrayList<Agente>();
		Iterator<Integer> iterator = seleccionadas.iterator();
		while (iterator.hasNext()) {
			int i = iterator.next();
			lista.add(elementos.get(i));
		}
		return lista;
	}


	/**
	 *  Gets the Agente attribute of the HerramientaAgentesTableModel object
	 *
	 *@param  row  Description of Parameter
	 *@return      The Agente value
	 */
	public Agente getAgente(int row) {
		return elementos.get(row);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  id  Description of Parameter
	 */
	public void cambioEnAgente(String id) {
		int i = 0;
		while (i < elementos.size() && !elementos.get(i).getIDString().equals(id)) {
			i++;
		}
		if (i < elementos.size()) {
			fireTableRowsUpdated(i, i);
		}
	}


	/**
	 *  Description of the Method
	 */
	public void clearSeleccionados() {
		seleccionadas.clear();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  agente  Description of Parameter
	 */
	public void eliminaAgente(Agente agente) {
		int index = elementos.indexOf(agente);
		elementos.remove(index);
		fireTableRowsDeleted(index, index);
	}
}
