package stoxtreme.herramienta_agentes;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.table.AbstractTableModel;
import stoxtreme.herramienta_agentes.agentes.Agente;

public class HerramientaAgentesTableModel extends AbstractTableModel{
	private static String[] columnas = {"","IDAgente", "Estado Actual", "Comportamiento", "Ganancias"};
	private TreeSet<Integer> seleccionadas = new TreeSet<Integer>();
	private ArrayList<Agente> elementos = new ArrayList<Agente>(); 

	public HerramientaAgentesTableModel(ArrayList<Agente> agentes) {
		elementos = agentes;
	}
	
	public void setAgentes(ArrayList<Agente> agentes){
		elementos = agentes;
		fireTableDataChanged();
	}
	public String getColumnName(int column) {
		return columnas[column];
	}
	
	public int getRowCount() {
		return elementos.size();
	}

	public int getColumnCount() {
		return columnas.length;
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(columnIndex==0){
			if(seleccionadas.contains(columnIndex)){
				seleccionadas.remove(columnIndex);
			}
			else{
				seleccionadas.add(columnIndex);
			}
		}
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex==0;
	}
	
	public Class getColumnClass(int columnIndex) {
		return columnIndex==0?Boolean.class:Object.class;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
			case 0: return new Boolean(seleccionadas.contains(rowIndex));
			case 1: return elementos.get(rowIndex).getIDString();
			case 2: return elementos.get(rowIndex).getEstado();
			case 3: return elementos.get(rowIndex).getStringComportamiento();
			case 4: return new Double(elementos.get(rowIndex).getGanancias());
			default: return"";
		}
	}
}
