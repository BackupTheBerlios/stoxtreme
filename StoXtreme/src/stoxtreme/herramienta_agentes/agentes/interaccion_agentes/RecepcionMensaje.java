package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public abstract class RecepcionMensaje extends Decision implements Cloneable {
	/**
	 *  Description of the Field
	 */
	protected MensajeACL mensajeRecibido;


	/**
	 *  Constructor for the RecepcionMensaje object
	 */
	public RecepcionMensaje() {

	}


	/**
	 *  Sets the Mensaje attribute of the RecepcionMensaje object
	 *
	 *@param  m  The new Mensaje value
	 */
	public void setMensaje(MensajeACL m) {
		this.mensajeRecibido = m;
	}


	/**
	 *  Creates an exact copy of this object.
	 *
	 *@return                                 Description of the Returned Value
	 *@exception  CloneNotSupportedException  Description of Exception
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
