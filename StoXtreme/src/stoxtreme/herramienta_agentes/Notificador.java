package stoxtreme.herramienta_agentes;

import java.util.Enumeration;
import java.util.Hashtable;


/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Notificador {
	private static Hashtable<String, ListenerNotificador> observadores =
			new Hashtable<String, ListenerNotificador>();


	/**
	 *  Adds the specified listener to receive events from this component. If
	 *  listener l is null, no exception is thrown and no action is performed.
	 *
	 *@param  id  The feature to be added to the attribute
	 *@param  a   The feature to be added to the attribute
	 */
	public void addListener(String id, ListenerNotificador a) {
		observadores.put(id, a);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  id        Description of Parameter
	 *@param  idOp      Description of Parameter
	 *@param  cantidad  Description of Parameter
	 *@param  precio    Description of Parameter
	 */
	public void notificar(String id, int idOp, int cantidad, double precio) {
		observadores.get(id).onNotificacionOp(idOp, cantidad, precio);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  id    Description of Parameter
	 *@param  idOp  Description of Parameter
	 */
	public void notificarCancelacion(String id, int idOp) {
		observadores.get(id).onCancelacionOp(idOp);
	}
}
