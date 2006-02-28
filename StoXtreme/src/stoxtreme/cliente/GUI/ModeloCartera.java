package stoxtreme.cliente.GUI;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

public class ModeloCartera extends AbstractTableModel{
	private static String[] nCol = {"Empresa", "Numero Acciones"};
	private ArrayList nombreEmpresas;
	private Hashtable cartera;
	
	public ModeloCartera(){
		nombreEmpresas = new ArrayList();
		cartera = new Hashtable();
	}
	public int getRowCount() {
		return nombreEmpresas.size();
	}

	public int getColumnCount() {
		return nombreEmpresas.size();
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
			nAnterior = ((Integer)cartera.get(empresa)).intValue();
			cartera.put(empresa, new Integer(nAnterior+numero));
			fireTableCellUpdated(nombreEmpresas.indexOf(empresa), 1);
		}
		else{
			nombreEmpresas.add(empresa);
			cartera.put(empresa, new Integer(numero));
			fireTableRowsInserted(nombreEmpresas.size()-1, nombreEmpresas.size()-1);
		}
	}
	
	public void sumaAcciones(String empresa, int numero){
		
	}
	
	public void restaAcciones(String empresa, int numero){
		
	}
}
