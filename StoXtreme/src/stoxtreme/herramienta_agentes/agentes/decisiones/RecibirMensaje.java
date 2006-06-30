package stoxtreme.herramienta_agentes.agentes.decisiones;

import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.PlantillaMensajes;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.RecepcionMensaje;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class RecibirMensaje extends Decision {
	private RecepcionMensaje r;
	private PlantillaMensajes p;
	private int n;


	/**
	 *  Constructor for the RecibirMensaje object
	 *
	 *@param  n  Description of Parameter
	 *@param  p  Description of Parameter
	 *@param  r  Description of Parameter
	 */
	public RecibirMensaje(int n, PlantillaMensajes p, RecepcionMensaje r) {
		super();
		this.n = n;
		this.p = p;
		this.r = r;
	}


	/**
	 *  Description of the Method
	 */
	public void ejecuta() {
		agente.recibeMensaje(n, p, r);
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return "Espero recepcion mensaje " + p;
	}
}
