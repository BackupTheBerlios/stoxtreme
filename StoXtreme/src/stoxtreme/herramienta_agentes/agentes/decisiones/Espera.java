package stoxtreme.herramienta_agentes.agentes.decisiones;

import stoxtreme.herramienta_agentes.agentes.Agente;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Espera extends Decision {

	/**
	 *  Constructor for the Espera object
	 *
	 *@param  tiempoEspera  Description of Parameter
	 */
	public Espera(int tiempoEspera) {
		super(tiempoEspera);
	}


	/**
	 *  Description of the Method
	 */
	public void ejecuta() {
		synchronized (agente) {
			agente.notify();
		}
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return "Espero hasta el ciclo " + getTiempoEjecucion();
	}
}
