package stoxtreme.herramienta_agentes;

import java.util.Hashtable;

/**
 *  Notificador del sistema de agentes.
 *  El notificador se encarga de actualizar a todos los observadores
 *  de realizaciones en las operaciones, y las cancelaciones.
 *  También notifica los cambios de precios en las acciones
 *
 *  @author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Notificador {
	/**
	 *  Mapa que asigna a los identificadores de los observadores
	 *  el objeto de listener asociado para la posible notificacion
	 */
	private static Hashtable<String, ListenerNotificador> observadores =
			new Hashtable<String, ListenerNotificador>();


	/**
	 *  Inserta un oyente en el notificador. Ante un evento el notificador
	 *  le notificará de la realizacion de la operacion.W
	 *
	 *  @param  id  Identificador del observador
	 *  @param  listener  Observador que deseamos añadir al notificador
	 */
	public void addListener(String id, ListenerNotificador listener) {
		if(id != null && listener != null){
			observadores.put(id, listener);
		}
	}

	/**
	 *  Notifica una operación al observador concreto
	 *
	 *  @param  id        Identificador del observador a informar
	 *  @param  idOp      Identificador de la operacion que notifica
	 *  @param  cantidad  Cantidad de acciones cruzadas
	 *  @param  precio    Precio de cruce
	 */
	public void notificar(String id, int idOp, int cantidad, double precio) {
		observadores.get(id).onNotificacionOp(idOp, cantidad, precio);
	}


	/**
	 *  Notifica una cancelacion de una operación al observador
	 *
	 *  @param  id    Identificador del observador a informar
	 *  @param  idOp  Identificador de la operacion cancelada
	 */
	public void notificarCancelacion(String id, int idOp) {
		observadores.get(id).onCancelacionOp(idOp);
	}
}
