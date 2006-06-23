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
		switch(columnIndex){
			case 0: return Boolean.class;
			case 1: case 2: case 3: return String.class;
			case 4: return Double.class;
			default: return Object.class;
		}
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

	public void cambioEnAgente(String id){
		int i=0;
		while(i<elementos.size() && !elementos.get(i).getIDString().equals(id)){
			i++;
		}
		if(i<elementos.size()){
			fireTableRowsUpdated(i,i);
		}
	}
	
	public ArrayList<Agente> getAgentesSeleccionados(){
		ArrayList<Agente> lista = new ArrayList<Agente>();
		Iterator<Integer> iterator = seleccionadas.iterator();
		while(iterator.hasNext()){
			int i = iterator.next();
			lista.add(elementos.get(i));
		}
		return lista;
	}
	
	public void clearSeleccionados(){
		seleccionadas.clear();
	}
	
	public void eliminaAgente(Agente agente) {
		int index = elementos.indexOf(agente);
		elementos.remove(index);
		fireTableRowsDeleted(index,index);
	}
	public Agente getAgente(int row) {
		return elementos.get(row);
	}
}
