package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import stoxtreme.herramienta_agentes.agentes.IDAgente;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class IDConversacion {
	private String ID = new String("Conversation" + (++ids));
	private static int ids = 0;


	/**
	 *  Constructor for the IDConversacion object
	 */
	public IDConversacion() {

	}


	/**
	 *  Constructor for the IDConversacion object
	 *
	 *@param  id  Description of Parameter
	 */
	private IDConversacion(String id) {
		this.ID = id;
	}


	/**
	 *  Compares this to the parameter.
	 *
	 *@param  o  the reference object with which to compare.
	 *@return    <tt>true</tt> if this object is the same as the obj argument;
	 *      <tt>false</tt> otherwise.
	 */
	public boolean equals(Object o) {
		return (o instanceof IDAgente)
				&& ((IDConversacion) o).getID().equals(ID);
	}


	/**
	 *  Gets the ID attribute of the IDConversacion object
	 *
	 *@return    The ID value
	 */
	private String getID() {
		return ID;
	}
}
