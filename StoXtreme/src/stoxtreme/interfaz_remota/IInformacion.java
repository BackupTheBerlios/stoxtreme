package stoxtreme.interfaz_remota;

import stoxtreme.servidor.objeto_bolsa.informacion.InfoBursatil;

/**
 *  Posee informaci�n de los datos burs�tiles que hay en el sistema
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface IInformacion {
	// Participaciones, ampliaciones de capital, etc...
	/**
	 *  Obtiene los DatosBursatiles
	 *
	 *@return    Valor de DatosBursatiles
	 */
	public InfoBursatil getDatosBursatiles();


	/**
	 *  Asignamos una nueva InfoBursatil
	 *
	 *@param  i  Nuevo valor de InfoBursatil
	 */
	public void setInfoBursatil(InfoBursatil i);

	// Valoraci�n de como se va a comportar la empresa en el futuro

	// Util para los eventos, etc.
	/**
	 *  Obtiene la  PerspectivaFuturo
	 *
	 *@return    Valor de PerspectivaFuturo
	 */
	public String getPerspectivaFuturo();


	/**
	 *  Asignamos una nueva PerspectivaFuturo
	 *
	 *@param  s  Nuevo valor de PerspectivaFuturo
	 */
	public void setPerspectivaFuturo(String s);
}
