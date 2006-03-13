package stoxtreme.servidor.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaEventos extends AbstractTableModel{
	private ArrayList<Evento> listaEventos;
	private ArrayList<Boolean> eventosActivos;
	
	public ModeloTablaEventos(){
		listaEventos = new ArrayList<Evento>();
		eventosActivos = new ArrayList<Boolean>();
	}
	
	public String getColumnName(int columnIndex){
		switch(columnIndex){
			case 0: return "Condicion";
			case 1: return "Accion";
			default: return "Activo?";
		}
	}
	public int getRowCount() {
		return listaEventos.size();
	}

	public int getColumnCount() {
		return 3; 
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0: return ((Evento)listaEventos.get(rowIndex)).getCondicion();
		case 1: return ((Evento)listaEventos.get(rowIndex)).getAccion();
		default: return eventosActivos.get(rowIndex);
		}
	}
	
	public Class getColumnClass(int indice){
		return (indice==2)?Boolean.class:String.class;
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex){
		return columnIndex==2;
	}
	
	public void addEvento(String condicion, String accion, boolean activo) {
		listaEventos.add(new Evento(condicion, accion));
		eventosActivos.add(new Boolean(activo));
		this.fireTableRowsInserted(listaEventos.size()-1, listaEventos.size()-1);
	}
	
	public void setEventoActivo(int indice, boolean valor){
		eventosActivos.set(indice, new Boolean(valor));
		this.fireTableCellUpdated(indice, 2);
	}

	private class Evento{
		private String condicion;
		private String accion;
		
		public Evento(String condicion, String accion) {
			this.accion = accion;
			this.condicion = condicion;
		}

		public String getAccion() {
			return accion;
		}

		public void setAccion(String accion) {
			this.accion = accion;
		}

		public String getCondicion() {
			return condicion;
		}

		public void setCondicion(String condicion) {
			this.condicion = condicion;
		}
	}

	public void quitarEvento(String descripcion) {
		for(int i=listaEventos.size()-1; i>=0; i--){
			if(listaEventos.get(i).getCondicion().equals(descripcion)){
				listaEventos.remove(i);
			}
		}
	}
}
