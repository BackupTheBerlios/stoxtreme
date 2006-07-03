package stoxtreme.servidor.objeto_bolsa.informacion;

/**
 *  Clase que contiene un objeto InfoBursatil.
 *  Podría añadirse nueva información sobre la empresa y se guardaría aquí.
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Informacion {
	private InfoBursatil iBursatil;


	/**
	 *  Constructor for the Informacion object
	 *
	 *@param  ib  Description of Parameter
	 */
	public Informacion(InfoBursatil ib) {
		iBursatil = ib;
	}


	/**
	 *  Gets the IBursatil attribute of the Informacion object
	 *
	 *@return    The IBursatil value
	 */
	public InfoBursatil getIBursatil() {
		return iBursatil;
	}
}
