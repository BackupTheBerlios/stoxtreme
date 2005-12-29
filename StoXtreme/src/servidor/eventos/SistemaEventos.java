package servidor.eventos;

import java.util.*;
import javax.swing.table.AbstractTableModel;

public class SistemaEventos extends AbstractTableModel{
	private Hashtable variables;
	private ArrayList listaCondiciones;
	private ArrayList listaAcciones;
	private Ejecutor ejecutor;
	
	public SistemaEventos(){
		variables = new Hashtable();
		listaCondiciones = new ArrayList();
		listaAcciones = new ArrayList();
		ejecutor = new Ejecutor();
	}
	public void registrarVariable(String var, Object v){
		
	}
	
	public void cambiarEstadoVariable(String var, Object valor){
		variables.put(var, valor);
		for (int i=listaCondiciones.size(); i>=0; i--){
			ObjetoCondicion oc = ((ObjetoCondicion)listaCondiciones.get(i));
			if (oc.evalua()){
				ejecutor.ejecuta((String)listaAcciones.get(i));
				
				if(oc.isUnaVez()){
					listaAcciones.remove(i);
					listaCondiciones.remove(i);
				}
			}
		}
	}
	
	public void insertarEvento(String descripcion, String accion, boolean unavez) throws ExceptionMalformedEvent{
		ObjetoCondicion oc = new ObjetoCondicion(descripcion, variables, unavez);
		if (oc.evalua()){
			ejecutor.ejecuta(accion);
			if (!oc.isUnaVez()){
				listaCondiciones.add(oc);
				listaAcciones.add(accion);
			}
		}
		else{
			listaCondiciones.add(oc);
			listaAcciones.add(accion);
		}
	}

	public int getRowCount() {
		return listaCondiciones.size();
	}

	public int getColumnCount() {
		return 2;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0){
			return ((ObjetoCondicion)listaCondiciones.get(rowIndex)).toString();
		}
		else{
			return (String)listaAcciones.get(rowIndex);
		}
	}
}
