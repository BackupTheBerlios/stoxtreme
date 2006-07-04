package stoxtreme.servidor.eventos;

import java.util.*;

import stoxtreme.servidor.VariablesListener;
import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.eventos.evaluador.ParseException;
import stoxtreme.servidor.gui.ModeloTablaEventos;

/**
 *  Sistema de eventos del servidor
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
@SuppressWarnings({"serial", "unchecked"})
public class SistemaEventos extends ModeloTablaEventos implements VariablesListener {
	private VariablesSistema variables;
	private ArrayList<ObjetoCondicion> listaCondiciones;
	private ArrayList<String> listaAcciones;


	/**
	 *  Constructor del objeto SistemaEventos
	 *
	 *@param  variables  Variables del sistema
	 */
	public SistemaEventos(VariablesSistema variables) {
		super();
		this.variables = variables;
		listaCondiciones = new ArrayList<ObjetoCondicion>();
		listaAcciones = new ArrayList<String>();
	}


	/**
	 *  Clase que implementa el método cambioEstadoVariable(var, valor) de
	 *  la interfaz VariablesListener
	 *
	 *@param  var    Variable
	 *@param  valor  Nuevo valor de la variable
	 */
	public void cambioEstadoVariable(String var, Object valor) {
		for (int i = listaCondiciones.size() - 1; i >= 0; i--) {
			ObjetoCondicion oc = listaCondiciones.get(i);
			oc.cambiaVariable(var, valor);
			if (oc.isActivado() && oc.evalua()) {
				Ejecutor.ejecuta((String) listaAcciones.get(i));

				if (oc.isUnaVez()) {
					quitarEvento(oc, (String) listaAcciones.get(i));
					listaAcciones.remove(i);
					listaCondiciones.remove(i);
				}
			}
		}
	}


	/**
	 *  Inserta un nuevo evento en el sistema de eventos
	 *
	 *@param  descripcionIn  Descripción del evento
	 *@param  accionIn       Acción a tomar
	 *@param  unavez         Indica si el evento se ejecuta una o varias veces
	 *@param  activo         Indica si el evento está activado
	 */
	public void insertarEvento(String descripcionIn, String accionIn, boolean unavez, boolean activo) {
		try {
			String descripcion = descripcionIn.toUpperCase();
			String accion = accionIn.toUpperCase();

			ObjetoCondicion oc = new ObjetoCondicion(descripcion, variables, unavez, activo);

			boolean ejecutado = false;
			if (oc.isActivado() && oc.evalua()) {
				Ejecutor.ejecuta(accion);
				ejecutado = true;
			}
			if (!ejecutado || !oc.isUnaVez()) {
				listaCondiciones.add(oc);
				listaAcciones.add(accion);
				addEvento(descripcion, accion, unavez, activo);
			}
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Cambia los eventos que están activados
	 *
	 *@param  condicion  Condición de ejecución
	 *@param  accion     Acción a realizar
	 *@param  valor      Booleano qu indica si hay que activar o desactivar el evento/s
	 */
	public void cambiaSeleccion(String condicion, String accion, boolean valor) {
		for (int i = 0; i < listaCondiciones.size(); i++) {
			if (listaCondiciones.get(i).getDescripcion().equals(condicion) &&
					listaAcciones.get(i).equals(accion)) {
				listaCondiciones.get(i).setActivado(valor);
			}
		}
	}


	/**
	 *  Quita un evento del sistema de eventos
	 *
	 *@param  oc      Objeto que representa la condición
	 *@param  accion  Acción que ejecuta el evento
	 */
	private void quitarEvento(ObjetoCondicion oc, String accion) {
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		for (int i = 0; i < listaEventos.size(); i++) {
			Evento evento = listaEventos.get(i);
			String acEv = evento.getAccion();
			String conEv = evento.getCondicion();

			if (acEv.equals(accion) && conEv.equals(oc.getDescripcion())) {
				eventos.add(evento);
			}
		}
	}

}
