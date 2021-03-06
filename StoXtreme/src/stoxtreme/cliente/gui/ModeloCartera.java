package stoxtreme.cliente.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Permite representar gr�ficamente la cartera del Cliente
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public class ModeloCartera extends AbstractTableModel {
	private ArrayList<String> nombreEmpresas;
	private Hashtable<String, Integer> cartera;
	private Hashtable<String, Double> precios;
	private static String[] nCol = {"Empresa", "Numero Acciones", "Precios Medio por Accion"};


	/**
	 *  Constructor de ModeloCartera
	 */
	public ModeloCartera() {
		nombreEmpresas = new ArrayList<String>();
		cartera = new Hashtable<String, Integer>();
		precios = new Hashtable<String, Double>();
	}


	/**
	 *  Obtenemos el n�mero de filas RowCount
	 *
	 *@return    Valor de RowCount
	 */
	public int getRowCount() {
		return nombreEmpresas.size();
	}


	/**
	 *  Obtenemos el n�mero de Columnas ColumnCount
	 *
	 *@return    Valor de ColumnCount
	 */
	public int getColumnCount() {
		return nCol.length;
	}


	/**
	 *  Obtenemos el nombre de Columnas ColumnName
	 *
	 *@param  index  N�mero de columna
	 *@return        Valor de ColumnName
	 */
	public String getColumnName(int index) {
		return nCol[index];
	}


	/**
	 *  Obtenemos el valor ValueAt
	 *
	 *@param  rowIndex     N�mero de fila
	 *@param  columnIndex  N�mero de columna
	 *@return              Valor de ValueAt
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
	 *  Insertamos acciones a la cartera del cliente
	 *
	 *@param  empresa  Empresa de la que provienen las acciones
	 *@param  numero   Cantidad
	 *@param  precio   Precio de compra
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
	 *  Una vez producida la venta de acciones
	 *
	 *@param  empresa  Empresa de la que provienen las acciones
	 *@param  numero   Cantidad
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
