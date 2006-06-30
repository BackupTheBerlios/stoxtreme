package stoxtreme.cliente.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ModeloCartera extends AbstractTableModel {
	private ArrayList<String> nombreEmpresas;
	private Hashtable<String, Integer> cartera;
	private Hashtable<String, Double> precios;
	private static String[] nCol = {"Empresa", "Numero Acciones", "Precios Medio por Accion"};


	/**
	 *  Constructor for the ModeloCartera object
	 */
	public ModeloCartera() {
		nombreEmpresas = new ArrayList<String>();
		cartera = new Hashtable<String, Integer>();
		precios = new Hashtable<String, Double>();
	}


	/**
	 *  Gets the RowCount attribute of the ModeloCartera object
	 *
	 *@return    The RowCount value
	 */
	public int getRowCount() {
		return nombreEmpresas.size();
	}


	/**
	 *  Gets the ColumnCount attribute of the ModeloCartera object
	 *
	 *@return    The ColumnCount value
	 */
	public int getColumnCount() {
		return nCol.length;
	}


	/**
	 *  Gets the ColumnName attribute of the ModeloCartera object
	 *
	 *@param  index  Description of Parameter
	 *@return        The ColumnName value
	 */
	public String getColumnName(int index) {
		return nCol[index];
	}


	/**
	 *  Gets the ValueAt attribute of the ModeloCartera object
	 *
	 *@param  rowIndex     Description of Parameter
	 *@param  columnIndex  Description of Parameter
	 *@return              The ValueAt value
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return nombreEmpresas.get(rowIndex);
			case 1:
				return cartera.get(nombreEmpresas.get(rowIndex));
			default:
				return precios.get(nombreEmpresas.get(rowIndex));
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@param  numero   Description of Parameter
	 *@param  precio   Description of Parameter
	 */
	public void insertarAcciones(String empresa, int numero, double precio) {
		int nAnterior = 0;
		int pAnterior = 0;
		if (cartera.containsKey(empresa)) {
			nAnterior = cartera.get(empresa).intValue();
			cartera.put(empresa, nAnterior + numero);
			pAnterior = precios.get(empresa).intValue();
			precios.put(empresa, (pAnterior + precio) / 2);
			fireTableCellUpdated(nombreEmpresas.indexOf(empresa), 1);
		}
		else {
			nombreEmpresas.add(empresa);
			cartera.put(empresa, numero);
			precios.put(empresa, precio);
			fireTableRowsInserted(nombreEmpresas.size() - 1, nombreEmpresas.size() - 1);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@param  numero   Description of Parameter
	 */
	public void restaAcciones(String empresa, int numero) {
		int nAnterior = 0;
		if (cartera.containsKey(empresa)) {
			nAnterior = cartera.get(empresa);
		}
		if (nAnterior > 0) {
			if (nAnterior - numero == 0) {
				// Si no quedan acciones de esa empresa la borramos
				int indiceAntiguo = nombreEmpresas.indexOf(empresa);
				cartera.remove(empresa);
				precios.remove(empresa);
				nombreEmpresas.remove(indiceAntiguo);
				fireTableRowsDeleted(indiceAntiguo, indiceAntiguo);
			}
			else {
				cartera.remove(empresa);
				cartera.put(empresa, nAnterior - numero);
				fireTableCellUpdated(nombreEmpresas.indexOf(empresa), 1);
			}
		}
		else {
			System.out.println();
		}
	}
}
