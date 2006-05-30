package stoxtreme.cliente.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import stoxtreme.herramienta_agentes.agentes.Agente;

public class HerramientaAgentesTableModel extends AbstractTableModel{
	private static String[] columnas = {" ","IDAgente", "Estado Actual", "Comportamiento", "Ganancias"};
	private TreeSet<Integer> seleccionadas = new TreeSet<Integer>();
	private ArrayList<Agente> elementos = new ArrayList<Agente>(); 

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
			if(seleccionadas.contains(rowIndex)){
				seleccionadas.remove(rowIndex);
			}
			else{
				seleccionadas.add(rowIndex);
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
	public ArrayList<Agente> dameSeleccionados() {
		ArrayList<Agente> ret = new ArrayList(seleccionadas.size());
		Iterator<Integer> it = seleccionadas.iterator();
		while(it.hasNext()){
			ret.add(elementos.get(it.next()));
		}
		return ret;
	}
}
