package stoxtreme.cliente.gui;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

public class ModeloCartera extends AbstractTableModel{
	private static String[] nCol = {"Empresa", "Numero Acciones","Precios Medio por Accion"};
	private ArrayList<String> nombreEmpresas;
	private Hashtable<String, Integer> cartera;
	private Hashtable<String, Double> precios;
	
	public ModeloCartera(){
		nombreEmpresas = new ArrayList<String>();
		cartera = new Hashtable<String, Integer>();
		precios = new Hashtable<String, Double>();
	}
	public int getRowCount() {
		return nombreEmpresas.size();
	}

	public int getColumnCount() {
		return nCol.length;
	}
	
	public String getColumnName(int index){
		return nCol[index];
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0: return nombreEmpresas.get(rowIndex);
		case 1: return cartera.get(nombreEmpresas.get(rowIndex));
		default: return precios.get(nombreEmpresas.get(rowIndex));
		}
	}
	
	public void insertarAcciones(String empresa, int numero,double precio){
		int nAnterior = 0;
		int pAnterior = 0;
		if(cartera.containsKey(empresa)){
			nAnterior = cartera.get(empresa).intValue();
			cartera.put(empresa, nAnterior+numero);
			pAnterior = precios.get(empresa).intValue();
			precios.put(empresa, (pAnterior+precio)/2);
			fireTableCellUpdated(nombreEmpresas.indexOf(empresa), 1);
		}
		else{
			nombreEmpresas.add(empresa);
			cartera.put(empresa, numero);
			precios.put(empresa, precio);
			fireTableRowsInserted(nombreEmpresas.size()-1, nombreEmpresas.size()-1);
		}
	}
	
	public void restaAcciones(String empresa, int numero){
		int nAnterior = 0;
		if(cartera.containsKey(empresa)){
			nAnterior = cartera.get(empresa);
		}
		if (nAnterior>0){ 
			if(nAnterior - numero == 0){
				// Si no quedan acciones de esa empresa la borramos
				int indiceAntiguo = nombreEmpresas.indexOf(empresa);
				cartera.remove(empresa);
				precios.remove(empresa);
				nombreEmpresas.remove(indiceAntiguo);
				fireTableRowsDeleted(indiceAntiguo, indiceAntiguo);
			}
			else{
				cartera.remove(empresa);
				cartera.put(empresa, nAnterior-numero);
				fireTableCellUpdated(nombreEmpresas.indexOf(empresa), 1);
			}
		}
		else{
			//AlmacenMensajes.getInstance().enviaMensaje(new Mensaje("No puedes vender acciones que no posees","INFORMACION",))
		}
	}
}
