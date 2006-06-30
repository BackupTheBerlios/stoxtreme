package stoxtreme.herramienta_agentes.agentes;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class IDAgente {

	private String idAgente;
	/**
	 *  Description of the Field
	 */
	public static IDAgente BROADCAST = new IDAgente("BROADCAST");
	private static int IDS = 0;
	private static String idUsuario;


	/**
	 *  Constructor for the IDAgente object
	 */
	public IDAgente() {
		idAgente = "Agente" + (++IDS);
	}


	/**
	 *  Constructor for the IDAgente object
	 *
	 *@param  idAgente  Description of Parameter
	 */
	private IDAgente(String idAgente) {
		this.idAgente = idAgente;
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return idUsuario + "#" + idAgente;
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
				&& ((IDAgente) o).toString().equals(this.toString());
	}


	/**
	 *  Sets the Usuario attribute of the IDAgente class
	 *
	 *@param  id  The new Usuario value
	 */
	public static void setUsuario(String id) {
		idUsuario = id;
	}
}
