package stoxtreme.servidor.eventos;

import java.util.*;

import stoxtreme.servidor.VariablesListener;
import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.eventos.evaluador.ParseException;
import stoxtreme.servidor.gui.ModeloTablaEventos;

@SuppressWarnings({"serial","unchecked"})
public class SistemaEventos extends ModeloTablaEventos implements VariablesListener{
	private VariablesSistema variables;
	private ArrayList<ObjetoCondicion> listaCondiciones;
	private ArrayList<String> listaAcciones;
	
	public SistemaEventos(VariablesSistema variables){
		super();
		this.variables = variables;
		listaCondiciones = new ArrayList<ObjetoCondicion>();
		listaAcciones = new ArrayList<String>();
	}

	public void cambioEstadoVariable(String var, Object valor){
		for (int i=listaCondiciones.size()-1; i>=0; i--){
			ObjetoCondicion oc = listaCondiciones.get(i);
			oc.cambiaVariable(var, valor);
			if (oc.isActivado() && oc.evalua()){
				Ejecutor.ejecuta((String)listaAcciones.get(i));
				
				if(oc.isUnaVez()){
					quitarEvento(oc, (String)listaAcciones.get(i));
					listaAcciones.remove(i);
					listaCondiciones.remove(i);
				}
			}
		}
	}
	
	private void quitarEvento(ObjetoCondicion oc, String accion) {
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		for(int i=0; i<listaEventos.size(); i++){
			Evento evento = listaEventos.get(i);
			String acEv = evento.getAccion();
			String conEv = evento.getCondicion();
			
			if(acEv.equals(accion)&&conEv.equals(oc.getDescripcion())){
				eventos.add(evento);
			}
		}
	}

	public void insertarEvento(String descripcionIn, String accionIn, boolean unavez, boolean activo){
		try {
			String descripcion = descripcionIn.toUpperCase();
			String accion = accionIn.toUpperCase();
			
			ObjetoCondicion oc = new ObjetoCondicion(descripcion, variables, unavez, activo);
			
			boolean ejecutado = false;
			if (oc.isActivado() && oc.evalua()){
				Ejecutor.ejecuta(accion);
				ejecutado = true;
			}
			if (!ejecutado || !oc.isUnaVez()) {
				listaCondiciones.add(oc);
				listaAcciones.add(accion);
				addEvento(descripcion, accion, unavez, activo);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void cambiaSeleccion(String condicion, String accion, boolean valor) {
		for(int i=0; i<listaCondiciones.size(); i++){
			if(listaCondiciones.get(i).getDescripcion().equals(condicion) &&
					listaAcciones.get(i).equals(accion)){
				listaCondiciones.get(i).setActivado(valor);
			}
		}
	}
	
	
}
