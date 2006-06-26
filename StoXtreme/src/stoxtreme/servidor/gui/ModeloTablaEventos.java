package stoxtreme.servidor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import stoxtreme.servidor.Servidor;
import stoxtreme.servidor.eventos.evaluador.ParseException;

public abstract class ModeloTablaEventos extends AbstractTableModel implements ActionListener{
	public static final String INSERTAR_EVENTO = "Insertar Evento";
	public static final String CANCELAR_EVENTO = "Cancelar Evento";
	protected ArrayList<Evento> listaEventos;
	private ArrayList<Boolean> eventosActivos;
	
	public ModeloTablaEventos(){
		listaEventos = new ArrayList<Evento>();
		eventosActivos = new ArrayList<Boolean>();
	}
	
	abstract public void cambiaSeleccion(String condicion, String accion, boolean valor);
	abstract public void insertarEvento(String condicion, String  accion, boolean unaVez, boolean activo);

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
	
	public void setValueAt(Object aValue, int row, int column){
		if(column == 2){
			cambiaSeleccion(listaEventos.get(row).getCondicion(), 
					listaEventos.get(row).getAccion(),
					((Boolean)aValue).booleanValue());
			eventosActivos.set(row, (Boolean)aValue);
		}
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0: return listaEventos.get(rowIndex).getCondicion();
		case 1: return listaEventos.get(rowIndex).getAccion();
		default: return eventosActivos.get(rowIndex);
		}
	}
	
	public Class getColumnClass(int indice){
		return (indice==2)?Boolean.class:String.class;
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex){
		return columnIndex==2;
	}
	
	public void addEvento(String condicion, String accion, boolean unavez, boolean activo) {
		listaEventos.add(new Evento(condicion, accion));
		eventosActivos.add(new Boolean(activo));
		this.fireTableRowsInserted(listaEventos.size()-1, listaEventos.size()-1);
	}
	
	protected class Evento{
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

	public void quitarEvento(Evento evento) {
		boolean cambiado = false;
		for(int i=listaEventos.size()-1; i>=0; i--){
			if(listaEventos.get(i).equals(evento)){
				listaEventos.remove(i);
				cambiado = true;
			}
		}
		if(cambiado) fireTableDataChanged();
	}
	
	private JTable lista;
	public void setLista(JTable lista){
		this.lista = lista;
	}
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText().equals(INSERTAR_EVENTO)){
			DialogoInsercionEvento diag = new DialogoInsercionEvento(Servidor.getInstance().getGUI());
			diag.setVisible(true);
			if(diag.isAceptado()){
				insertarEvento(diag.getCondicion(), diag.getEvento(),diag.isUnaVez(), diag.isIniciarActivo());
			}
		}
		else if(((JButton)e.getSource()).getText().equals(CANCELAR_EVENTO)){
			int[] indices = lista.getSelectedRows();
			ArrayList<Evento> descripciones = new ArrayList<Evento>();
			
			for(int i=0; i<indices.length; i++){
				descripciones.add(listaEventos.get(indices[i]));
			}
			
			for(int i=0; i<descripciones.size(); i++){
				quitarEvento(descripciones.get(i));
			}
		}
	}
}
