package servidor.eventos;

import java.util.*;
import javax.swing.table.AbstractTableModel;
import servidor.eventos.evaluador.ParseException;

public class SistemaEventos extends AbstractTableModel{
	/**
	 * @uml.property  name="variables"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Object" qualifier="var:java.lang.String java.lang.Object"
	 */
	private Hashtable variables;
	/**
	 * @uml.property  name="listaCondiciones"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="servidor.eventos.ObjetoCondicion"
	 */
	private ArrayList listaCondiciones;
	/**
	 * @uml.property  name="listaAcciones"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private ArrayList listaAcciones;
	/**
	 * @uml.property  name="ejecutor"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
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
			oc.cambiaVariable(var, valor);
			if (oc.evalua()){
				ejecutor.ejecuta((String)listaAcciones.get(i));
				
				if(oc.isUnaVez()){
					listaAcciones.remove(i);
					listaCondiciones.remove(i);
				}
			}
		}
	}
	
	public void insertarEvento(String descripcion, String accion, boolean unavez) throws ParseException{
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
