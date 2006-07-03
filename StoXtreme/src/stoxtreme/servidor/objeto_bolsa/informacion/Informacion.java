package stoxtreme.servidor.objeto_bolsa.informacion;

/**
 *  Clase que contiene un objeto InfoBursatil.
 *  Podr�a a�adirse nueva informaci�n sobre la empresa y se guardar�a aqu�.
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
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
