package stoxtreme.servidor.objeto_bolsa.informacion;

import java.util.Hashtable;
import java.util.Vector;

public class InfoBursatil {
	/**
	 * @uml.property  name="participaciones"
	 */
	private Hashtable participaciones;
	/**
	 * @uml.property  name="ampliacionesCapital"
	 */
	private Vector ampliacionesCapital;
	/**
	 * @uml.property  name="capitalSocial"
	 */
	private double capitalSocial;
	/**
	 * @param participaciones  The participaciones to set.
	 * @uml.property  name="participaciones"
	 */
	public void setParticipaciones(Hashtable participaciones) {
		this.participaciones = participaciones;
	}
	/**
	 * @return  Returns the participaciones.
	 * @uml.property  name="participaciones"
	 */
	public Hashtable getParticipaciones() {
		return participaciones;
	}
	/**
	 * @param ampliacionesCapital  The ampliacionesCapital to set.
	 * @uml.property  name="ampliacionesCapital"
	 */
	public void setAmpliacionesCapital(Vector ampliacionesCapital) {
		this.ampliacionesCapital = ampliacionesCapital;
	}
	/**
	 * @return  Returns the ampliacionesCapital.
	 * @uml.property  name="ampliacionesCapital"
	 */
	public Vector getAmpliacionesCapital() {
		return ampliacionesCapital;
	}
	/**
	 * @param capitalSocial  The capitalSocial to set.
	 * @uml.property  name="capitalSocial"
	 */
	public void setCapitalSocial(double capitalSocial) {
		this.capitalSocial = capitalSocial;
	}
	/**
	 * @return  Returns the capitalSocial.
	 * @uml.property  name="capitalSocial"
	 */
	public double getCapitalSocial() {
		return capitalSocial;
	}
	
}
