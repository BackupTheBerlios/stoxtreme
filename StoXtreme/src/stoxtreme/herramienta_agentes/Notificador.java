package stoxtreme.herramienta_agentes;

import java.util.Hashtable;

/**
 *  Notificador del sistema de agentes.
 *  El notificador se encarga de actualizar a todos los observadores
 *  de realizaciones en las operaciones, y las cancelaciones.
 *  Tambi�n notifica los cambios de precios en las acciones
 *
 *  @author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
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
	 *  le notificar� de la realizacion de la operacion.W
	 *
	 *  @param  id  Identificador del observador
	 *  @param  listener  Observador que deseamos a�adir al notificador
	 */
	public void addListener(String id, ListenerNotificador listener) {
		if(id != null && listener != null){
			observadores.put(id, listener);
		}
	}

	/**
	 *  Notifica una operaci�n al observador concreto
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
	 *  Notifica una cancelacion de una operaci�n al observador
	 *
	 *  @param  id    Identificador del observador a informar
	 *  @param  idOp  Identificador de la operacion cancelada
	 */
	public void notificarCancelacion(String id, int idOp) {
		observadores.get(id).onCancelacionOp(idOp);
	}
}
