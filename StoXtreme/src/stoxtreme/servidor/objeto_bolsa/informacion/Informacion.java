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
	 *  Constructor del objeto Informacion
	 *
	 *@param  ib  Informacion bursatil
	 */
	public Informacion(InfoBursatil ib) {
		iBursatil = ib;
	}


	/**
	 *  Accesor del atributo IBursatil
	 *
	 *@return    El valor de IBursatil
	 */
	public InfoBursatil getIBursatil() {
		return iBursatil;
	}
}
