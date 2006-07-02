package stoxtreme.herramienta_agentes;

/**
 *  Interfaz de los observadores del notificador
 *
 *  @author  Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface ListenerNotificador {
	/**
	 *  Notificacion del cruce de una operacion
	 *
	 *  @param  idOp      Identificador de una operacion
	 *  @param  cantidad  Cantidad de acciones cruzadas
	 *  @param  precio    Precio de cruce
	 */
	public void onNotificacionOp(int idOp, int cantidad, double precio);

	/**
	 *  Notificacion de una cancelacion
	 *
	 *  @param  idOp  Identificacion de la operacion cancelada
	 */
	public void onCancelacionOp(int idOp);
}
