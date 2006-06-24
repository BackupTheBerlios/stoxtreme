package stoxtreme.interfaz_remota;

import stoxtreme.servidor.objeto_bolsa.informacion.InfoBursatil;

public interface IInformacion {
	// Participaciones, ampliaciones de capital, etc...
	public InfoBursatil getDatosBursatiles();
	public void setInfoBursatil(InfoBursatil i);
	// Valoraci�n de como se va a comportar la empresa en el futuro
	// Util para los eventos, etc.
	public String getPerspectivaFuturo();
	public void setPerspectivaFuturo(String s);
}
