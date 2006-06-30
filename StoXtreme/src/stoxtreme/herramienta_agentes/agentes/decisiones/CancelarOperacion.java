package stoxtreme.herramienta_agentes.agentes.decisiones;
import stoxtreme.herramienta_agentes.agentes.Agente;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class CancelarOperacion extends Decision {
	private int idOp;


	/**
	 *  Constructor for the CancelarOperacion object
	 *
	 *@param  idOperacion  Description of Parameter
	 */
	public CancelarOperacion(int idOperacion) {
		super();
		idOp = idOperacion;
	}


	/**
	 *  Description of the Method
	 */
	public void ejecuta() {
		agente.cancelarOperacion(idOp);
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return "Cancela operacion " + idOp;
	}
}
