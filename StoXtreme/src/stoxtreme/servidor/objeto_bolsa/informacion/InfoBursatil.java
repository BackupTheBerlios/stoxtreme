package stoxtreme.servidor.objeto_bolsa.informacion;

import java.util.Hashtable;
import java.util.Vector;

public class InfoBursatil {
	private Hashtable participaciones;
	private Vector ampliacionesCapital;
	private Vector capitalSocial;//0-Capital,1-Valor nominal,2-Numero acciones
	
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

	public void setCapitalSocial(Vector capitalSocial) {
		this.capitalSocial = capitalSocial;
	}

	public Vector getCapitalSocial() {
		return capitalSocial;
	}
	
}
