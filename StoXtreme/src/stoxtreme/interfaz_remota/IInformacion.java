package stoxtreme.interfaz_remota;

import stoxtreme.servidor.objeto_bolsa.informacion.InfoBursatil;

/**
 *  Posee información de los datos bursátiles que hay en el sistema
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
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
}
