package stoxtreme.herramienta_agentes;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import stoxtreme.herramienta_agentes.agentes.Agente;

public class HerramientaAgentesTableModel extends AbstractTableModel{
	private static String[] columnas = {"IDAgente", "Estado Actual", "Comportamiento", "Ganancias"};
	
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

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
			case 0: return elementos.get(rowIndex).getIDString();
			case 1: return elementos.get(rowIndex).getEstado();
			case 2: return elementos.get(rowIndex).getStringComportamiento();
			case 3: return new Double(elementos.get(rowIndex).getGanancias());
			default: return"";
		}
	}
}
