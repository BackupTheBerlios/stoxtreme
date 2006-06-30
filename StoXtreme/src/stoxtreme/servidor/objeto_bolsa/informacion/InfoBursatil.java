package stoxtreme.servidor.objeto_bolsa.informacion;

import java.util.Hashtable;
import java.util.Vector;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class InfoBursatil {
	private Hashtable participaciones;
	private Vector ampliacionesCapital;
	private Vector capitalSocial;
	//0-Capital,1-Valor nominal,2-Numero acciones


			/**
	 *  Sets the Participaciones attribute of the InfoBursatil object
	 *
	 *@param  participaciones  The new Participaciones value
	 */
	public void setParticipaciones(Hashtable participaciones) {
		this.participaciones = participaciones;
	}


	/**
	 *  Sets the AmpliacionesCapital attribute of the InfoBursatil object
	 *
	 *@param  ampliacionesCapital  The new AmpliacionesCapital value
	 */
	public void setAmpliacionesCapital(Vector ampliacionesCapital) {
		this.ampliacionesCapital = ampliacionesCapital;
	}


	/**
	 *  Sets the CapitalSocial attribute of the InfoBursatil object
	 *
	 *@param  capitalSocial  The new CapitalSocial value
	 */
	public void setCapitalSocial(Vector capitalSocial) {
		this.capitalSocial = capitalSocial;
	}


	/**
	 *  Gets the Participaciones attribute of the InfoBursatil object
	 *
	 *@return    The Participaciones value
	 */
	public Hashtable getParticipaciones() {
		return participaciones;
	}


	/**
	 *  Gets the AmpliacionesCapital attribute of the InfoBursatil object
	 *
	 *@return    The AmpliacionesCapital value
	 */
	public Vector getAmpliacionesCapital() {
		return ampliacionesCapital;
	}


	/**
	 *  Gets the CapitalSocial attribute of the InfoBursatil object
	 *
	 *@return    The CapitalSocial value
	 */
	public Vector getCapitalSocial() {
		return capitalSocial;
	}

}
