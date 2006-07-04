package stoxtreme.servidor.eventos;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Clase que ejecuta las acciones asociadas a los eventos
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public class Ejecutor {
	private static final String OPA_Endesa = "OPA sobre Endesa";

//	private final static String DIHOLA = "DIHOLA";
//	private final static String DIHOLA_ALONSO = "DIHOLA_ALONSO";

	/**
	 *  Description of the Field
	 */
	public static final String[] acciones = {OPA_Endesa, "Precio_Crudo", "Problema_Altadis", "Amp_Cap_TPI",
			"split_Telef�nica", "Rumor_FCC"};
	private static final String Precio_Crudo = "Sube el precio del barril de gasolina";
	private static final String Preocupaci�n_Altadis = "Aprueban la ley anti-tabaco en lugares p�blicos";
	private static final String Amp_Cap_TPI = "Ampliaci�n de Capital de P�ginas Amarillas";
	private static final String split_Telefonica = "Se realiza un Split sobre las acciones de Telef�nica";
	private static final String Rumor_FCC = "Se Comenta la absorcion de ACS sobre FCC";


	/**
	 *  Constructor del objeto Ejecutor
	 */
	private Ejecutor() {
	}


	/**
	 *  Ejecuta las acciones asociadas a los eventos
	 *
	 *@param  s  Descripci�n del evento
	 */
	public static void ejecuta(String s) {
		if (s.equals(OPA_Endesa.toUpperCase())) {
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(OPA_Endesa, "RUMOR_AGENTES", Mensaje.GLOBAL));
		}
		if (s.equals("Amp_Cap_TPI".toUpperCase())) {
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Amp_Cap_TPI, "RUMOR_AGENTES", Mensaje.GLOBAL));
		}
		if (s.equals("Precio_Crudo".toUpperCase())) {
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Precio_Crudo, "RUMOR_AGENTES", Mensaje.GLOBAL));
		}
		if (s.equals("Problema_Altadis".toUpperCase())) {
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Preocupaci�n_Altadis, "RUMOR_AGENTES", Mensaje.GLOBAL));
		}
		if (s.equals("split_Telef�nica".toUpperCase())) {
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(split_Telefonica, "RUMOR_AGENTES", Mensaje.GLOBAL));
		}
		if (s.equals("Rumor_FCC".toUpperCase())) {
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Rumor_FCC, "RUMOR_AGENTES", Mensaje.GLOBAL));
		}
	}
}
