package stoxtreme.herramienta_agentes;

/**
 *  Interfaz para que los agentes accedan a métodos para la visualizacion
 *  de sus acciones.
 *
 *  @author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface ConsolaAgentes {
	/**
	 *  Inserta una salida de tipo "accion"
	 *  Las acciones de los agentes suelen ser las decisiones que
	 *  toman o las acciones que realizan
	 *
	 *  @param  idAgente  Identificador del agente que realiza la accion
	 *  @param  accion    Accion que realiza
	 */
	public void insertarAccion(String idAgente, String accion);


	/**
	 *  Inserta una salida de tipo "notificacion"
	 *  Suelen ser notificaciones de las acciones realizadas que estaban
	 *  a la espera de notificación
	 *
	 *  @param  idAgente  Description of Parameter
	 *  @param  notif     Description of Parameter
	 */
	public void insertarNotificacion(String idAgente, String notif);
}
