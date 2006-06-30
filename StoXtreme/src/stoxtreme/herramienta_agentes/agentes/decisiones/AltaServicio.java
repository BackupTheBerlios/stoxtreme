package stoxtreme.herramienta_agentes.agentes.decisiones;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class AltaServicio extends Decision {
	private String servicio;


	/**
	 *  Constructor for the AltaServicio object
	 *
	 *@param  servicio  Description of Parameter
	 */
	public AltaServicio(String servicio) {
		this.servicio = servicio;
	}


	/**
	 *  Description of the Method
	 */
	public void ejecuta() {
		agente.altaServicio(servicio);
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return "Alta Servicio";
	}
}
