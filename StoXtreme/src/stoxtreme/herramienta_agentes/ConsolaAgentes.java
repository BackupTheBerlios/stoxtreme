package stoxtreme.herramienta_agentes;

/**
 *  Description of the Interface
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface ConsolaAgentes {
	/**
	 *  Description of the Method
	 *
	 *@param  idAgente  Description of Parameter
	 *@param  accion    Description of Parameter
	 */
	public void insertarAccion(String idAgente, String accion);


	/**
	 *  Description of the Method
	 *
	 *@param  idAgente  Description of Parameter
	 *@param  notif     Description of Parameter
	 */
	public void insertarNotificacion(String idAgente, String notif);
}
