package stoxtreme.servidor.gui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

public class ModeloTablaVariables extends AbstractTableModel{
	private Hashtable valorVariables;
	private ArrayList nombreVariables;
	
	public ModeloTablaVariables(){
		valorVariables = new Hashtable();
		nombreVariables = new ArrayList();
	}
	
	public int getRowCount() {
		return nombreVariables.size();
	}

	public int getColumnCount() {
		return 2;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		String nombreVar = (String)nombreVariables.get(rowIndex);
		return (columnIndex==0)?nombreVar:valorVariables.get(nombreVar);
	}
	
	public String getColumnName(int columnIndex){
		switch(columnIndex){
		case 0: return "Nombre";
		default: return "Valor";
		}
	}
	public void insertarVariable(String nombre, Object valor) {
		nombreVariables.add(nombre);
		valorVariables.put(nombre, valor);
		fireTableRowsInserted(nombreVariables.size()-1, nombreVariables.size()-1);
	}

	public void cambiaVariable(String nombre, Object nuevoValor) {
		valorVariables.put(nombre, nuevoValor);
		int index = nombreVariables.indexOf(nombre);
		fireTableCellUpdated(index, 1);
	}
}
