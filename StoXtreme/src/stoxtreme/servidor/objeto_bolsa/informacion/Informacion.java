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
