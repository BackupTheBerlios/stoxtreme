package stoxtreme.herramienta_agentes.agentes.decisiones;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.IDAgente;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class RequestServicio extends Decision {
	private String servicio;
	private ArrayList<IDAgente> proveedores;


	/**
	 *  Constructor for the RequestServicio object
	 *
	 *@param  nombre_servicio  Description of Parameter
	 *@param  proveedores      Description of Parameter
	 */
	public RequestServicio(String nombre_servicio, ArrayList<IDAgente> proveedores) {
		this.servicio = nombre_servicio;
		this.proveedores = proveedores;
	}


	/**
	 *  Description of the Method
	 */
	public void ejecuta() {
		agente.requestServicio(servicio, proveedores);
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return "Peticion de servicio " + servicio;
	}
}
