package stoxtreme.interfaz_remota;

import stoxtreme.servidor.objeto_bolsa.informacion.InfoBursatil;

/**
 *  Description of the Interface
 *
 *@author    Chris Seguin
 */
public interface IInformacion {
	// Participaciones, ampliaciones de capital, etc...
	/**
	 *  Gets the DatosBursatiles attribute of the IInformacion object
	 *
	 *@return    The DatosBursatiles value
	 */
	public InfoBursatil getDatosBursatiles();


	/**
	 *  Sets the InfoBursatil attribute of the IInformacion object
	 *
	 *@param  i  The new InfoBursatil value
	 */
	public void setInfoBursatil(InfoBursatil i);

	// Valoraci�n de como se va a comportar la empresa en el futuro

	// Util para los eventos, etc.
	/**
	 *  Gets the PerspectivaFuturo attribute of the IInformacion object
	 *
	 *@return    The PerspectivaFuturo value
	 */
	public String getPerspectivaFuturo();


	/**
	 *  Sets the PerspectivaFuturo attribute of the IInformacion object
	 *
	 *@param  s  The new PerspectivaFuturo value
	 */
	public void setPerspectivaFuturo(String s);
}
