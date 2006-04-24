package stoxtreme.herramienta_agentes;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.table.AbstractTableModel;

public class HerramientaAgentesTableModel extends AbstractTableModel{
	private static String[] columnas = {"IDAgente", "Estado Actual", "Comportamiento", "Ganancias"};
	
	private ArrayList<ElementoTabla> elementos = new ArrayList<ElementoTabla>(); 
	
	public void insertar(String IDAgente, String estado, String comportamiento){
		elementos.add(new ElementoTabla(IDAgente, estado, comportamiento));
		fireTableRowsInserted(elementos.size()-1, elementos.size()-1);
	}
	
	public void cambiaEstado(String IDAgente, String nuevoEstado){
		int i=0;
		while(i<elementos.size() && !elementos.get(i).equals(IDAgente)){
			i++;
		}
		if(i<elementos.size()){
			elementos.get(i).setEstado(nuevoEstado);
			fireTableCellUpdated(i,1);
		}
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
			case 0: return elementos.get(rowIndex).getIDAgente();
			case 1: return elementos.get(rowIndex).getEstado();
			case 2: return elementos.get(rowIndex).getComportamiento();
			case 3: return new Double(elementos.get(rowIndex).getGanancias());
			default: return"";
		}
	}
	
	private class ElementoTabla{
		private String IDAgente, estado, comportamiento;
		private double ganancias;
		
		public ElementoTabla(String IDAgente, String estado, String comportamiento) {
			this.IDAgente = IDAgente;
			this.estado = estado;
			this.comportamiento = comportamiento;
			this.ganancias = 0;
		}
		
		public String getComportamiento() {
			return comportamiento;
		}
		public String getEstado() {
			return estado;
		}
		public String getIDAgente() {
			return IDAgente;
		}
		public void setComportamiento(String comportamiento) {
			this.comportamiento = comportamiento;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public void setIDAgente(String agente) {
			IDAgente = agente;
		}

		public double getGanancias() {
			return ganancias;
		}

		public void setGanancias(double ganancias) {
			this.ganancias = ganancias;
		}
	}
}
