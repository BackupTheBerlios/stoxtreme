package stoxtreme.herramienta_agentes.agentes.decisiones;

import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.MensajeACL;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class MandarMensaje extends Decision {
	private MensajeACL mensaje;
	private boolean privado;


	/**
	 *  Constructor for the MandarMensaje object
	 *
	 *@param  privado  Description of Parameter
	 *@param  mensaje  Description of Parameter
	 */
	public MandarMensaje(boolean privado, MensajeACL mensaje) {
		super();
		this.mensaje = mensaje;
		this.privado = privado;
	}


	/**
	 *  Description of the Method
	 */
	public void ejecuta() {
		if (!privado) {
			mensaje.setSender(agente.getIDAgente());
		}
		agente.enviarMensaje(mensaje);
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return "Mando mensaje acl " + mensaje;
	}
}
