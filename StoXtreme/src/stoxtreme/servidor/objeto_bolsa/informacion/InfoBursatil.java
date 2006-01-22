package stoxtreme.servidor.objeto_bolsa.informacion;

import java.util.Hashtable;
import java.util.Vector;

public class InfoBursatil {
	private Hashtable participaciones;
	private Vector ampliacionesCapital;
	private double capitalSocial;

	public void setParticipaciones(Hashtable participaciones) {
		this.participaciones = participaciones;
	}

	public Hashtable getParticipaciones() {
		return participaciones;
	}

	public void setAmpliacionesCapital(Vector ampliacionesCapital) {
		this.ampliacionesCapital = ampliacionesCapital;
	}

	public Vector getAmpliacionesCapital() {
		return ampliacionesCapital;
	}

	public void setCapitalSocial(double capitalSocial) {
		this.capitalSocial = capitalSocial;
	}

	public double getCapitalSocial() {
		return capitalSocial;
	}
	
}
