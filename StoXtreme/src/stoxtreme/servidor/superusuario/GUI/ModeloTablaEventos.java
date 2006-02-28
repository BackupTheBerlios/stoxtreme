package stoxtreme.servidor.superusuario.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class ModeloTablaEventos extends AbstractTableModel implements MouseListener, TableModelListener{
	private ArrayList listaEventos;
	private ArrayList eventosActivos;
	
	public ModeloTablaEventos(){
		listaEventos = new ArrayList();
		eventosActivos = new ArrayList();
		this.addTableModelListener(this);
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
	
	public void insertarEvento(Evento evento, boolean activo) {
		listaEventos.add(evento);
		eventosActivos.add(new Boolean(activo));
		this.fireTableRowsInserted(listaEventos.size()-1, listaEventos.size()-1);
	}
	
	public void setEventoActivo(int indice, boolean valor){
		eventosActivos.set(indice, new Boolean(valor));
		this.fireTableCellUpdated(indice, 2);
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void tableChanged(TableModelEvent e) {
		System.out.println(e);
	}
}
