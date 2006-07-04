package stoxtreme.servidor.objeto_bolsa.informacion;

import java.util.Hashtable;
import java.util.Vector;

/**
 *  Guarda la información bursatil de la empresa, extraida del archivo de información correspondiente
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class InfoBursatil {
	private Hashtable participaciones;
	private Vector ampliacionesCapital;
	private Vector capitalSocial;
	//0-Capital,1-Valor nominal,2-Numero acciones


	/**
	 *  Mutador del atributo Participaciones
	 *
	 *@param  participaciones  El nuevo valor de Participaciones
	 */
	public void setParticipaciones(Hashtable participaciones) {
		this.participaciones = participaciones;
	}


	/**
	 *  Mutador del atributo AmpliacionesCapital
	 *
	 *@param  ampliacionesCapital  El nuevo valor de AmpliacionesCapital
	 */
	public void setAmpliacionesCapital(Vector ampliacionesCapital) {
		this.ampliacionesCapital = ampliacionesCapital;
	}


	/**
	 *  Mutador del atributo CapitalSocial
	 *
	 *@param  capitalSocial  El nuevo valor de CapitalSocial
	 */
	public void setCapitalSocial(Vector capitalSocial) {
		this.capitalSocial = capitalSocial;
	}


	/**
	 *  Accesor del atributo Participaciones
	 *
	 *@return    El valor de Participaciones
	 */
	public Hashtable getParticipaciones() {
		return participaciones;
	}


	/**
	 *  Accesor del atributo AmpliacionesCapital
	 *
	 *@return    El valor de AmpliacionesCapital
	 */
	public Vector getAmpliacionesCapital() {
		return ampliacionesCapital;
	}


	/**
	 *  Accesor del atributo CapitalSocial
	 *
	 *@return    El valor de CapitalSocial
	 */
	public Vector getCapitalSocial() {
		return capitalSocial;
	}

}
