package stoxtreme.herramienta_agentes.agentes.decisiones;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class BajaServicio extends Decision {
	private String servicio;


	/**
	 *  Constructor for the BajaServicio object
	 *
	 *@param  servicio  Description of Parameter
	 */
	public BajaServicio(String servicio) {
		this.servicio = servicio;
	}


	/**
	 *  Description of the Method
	 */
	public void ejecuta() {
		agente.bajaServicio(servicio);
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return "Baja Servicio";
	}
}
