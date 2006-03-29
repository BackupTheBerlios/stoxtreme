package stoxtreme.cliente.gui;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

public class ModeloCartera extends AbstractTableModel{
	private static String[] nCol = {"Empresa", "Numero Acciones"};
	private ArrayList<String> nombreEmpresas;
	private Hashtable<String, Integer> cartera;
	
	public ModeloCartera(){
		nombreEmpresas = new ArrayList<String>();
		cartera = new Hashtable<String, Integer>();
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
		default: return cartera.get(nombreEmpresas.get(rowIndex));
		}
	}
	
	public void insertarAcciones(String empresa, int numero){
		int nAnterior = 0;
		if(cartera.containsKey(empresa)){
			nAnterior = cartera.get(empresa).intValue();
			cartera.put(empresa, nAnterior+numero);
			fireTableCellUpdated(nombreEmpresas.indexOf(empresa), 1);
		}
		else{
			nombreEmpresas.add(empresa);
			cartera.put(empresa, numero);
			fireTableRowsInserted(nombreEmpresas.size()-1, nombreEmpresas.size()-1);
		}
	}
	
	public void restaAcciones(String empresa, int numero){
		int nAnterior = cartera.get(empresa);
		if(nAnterior - numero == 0){
			// Si no quedan acciones de esa empresa la borramos
			int indiceAntiguo = nombreEmpresas.indexOf(empresa);
			cartera.remove(empresa);
			nombreEmpresas.remove(indiceAntiguo);
			fireTableRowsDeleted(indiceAntiguo, indiceAntiguo);
		}
		else{
			cartera.put(empresa, nAnterior-numero);
			fireTableCellUpdated(nombreEmpresas.indexOf(empresa), 1);
		}
	}
}
