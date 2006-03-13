package stoxtreme.servidor.eventos;

import java.util.*;
import javax.swing.table.AbstractTableModel;

import stoxtreme.servidor.VariablesListener;
import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.eventos.evaluador.ParseException;
import stoxtreme.servidor.gui.MainFrameAdmin;
import stoxtreme.servidor.gui.ModeloTablaEventos;

public class SistemaEventos extends ModeloTablaEventos implements VariablesListener{
	private VariablesSistema variables;
	private ArrayList listaCondiciones;
	private ArrayList listaAcciones;
	private Ejecutor ejecutor;
	
	public SistemaEventos(VariablesSistema variables){
		super();
		this.variables = variables;
		listaCondiciones = new ArrayList();
		listaAcciones = new ArrayList();
	}

	public void cambioEstadoVariable(String var, Object valor){
		for (int i=listaCondiciones.size()-1; i>=0; i--){
			ObjetoCondicion oc = ((ObjetoCondicion)listaCondiciones.get(i));
			oc.cambiaVariable(var, valor);
			if (oc.evalua()){
				Ejecutor.ejecuta((String)listaAcciones.get(i));
				
				if(oc.isUnaVez()){
					listaAcciones.remove(i);
					listaCondiciones.remove(i);
					quitarEvento(oc.getDescripcion());
				}
			}
		}
	}
	
	public void insertarEvento(String descripcionIn, String accionIn, boolean unavez) throws ParseException{
		String descripcion = descripcionIn.toUpperCase();
		String accion = accionIn.toUpperCase();
		addEvento(descripcion, accion, unavez);
		
		ObjetoCondicion oc = new ObjetoCondicion(descripcion, variables, unavez);
		
		boolean ejecutado = false;
		if (oc.evalua()){
			Ejecutor.ejecuta(accion);
			ejecutado = true;
		}
		if (!ejecutado || !oc.isUnaVez()){
			listaCondiciones.add(oc);
			listaAcciones.add(accion);
			addEvento(descripcion, accion, unavez);
		}
	}
}
