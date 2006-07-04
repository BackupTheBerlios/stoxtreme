package stoxtreme.servidor.eventos;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Clase que ejecuta las acciones asociadas a los eventos
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Ejecutor {
	private static final String OPA_Endesa = "OPA sobre Endesa";

//	private final static String DIHOLA = "DIHOLA";
//	private final static String DIHOLA_ALONSO = "DIHOLA_ALONSO";

	/**
	 *  Description of the Field
	 */
	public static final String[] acciones = {OPA_Endesa, "Precio_Crudo", "Problema_Altadis", "Amp_Cap_TPI",
			"split_Telefónica", "Rumor_FCC"};
	private static final String Precio_Crudo = "Sube el precio del barril de gasolina";
	private static final String Preocupación_Altadis = "Aprueban la ley anti-tabaco en lugares públicos";
	private static final String Amp_Cap_TPI = "Ampliación de Capital de Páginas Amarillas";
	private static final String split_Telefonica = "Se realiza un Split sobre las acciones de Telefónica";
	private static final String Rumor_FCC = "Se Comenta la absorcion de ACS sobre FCC";


	/**
	 *  Constructor del objeto Ejecutor
	 */
	private Ejecutor() {
	}


	/**
	 *  Ejecuta las acciones asociadas a los eventos
	 *
	 *@param  s  Descripción del evento
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
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Preocupación_Altadis, "RUMOR_AGENTES", Mensaje.GLOBAL));
		}
		if (s.equals("split_Telefónica".toUpperCase())) {
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(split_Telefonica, "RUMOR_AGENTES", Mensaje.GLOBAL));
		}
		if (s.equals("Rumor_FCC".toUpperCase())) {
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Rumor_FCC, "RUMOR_AGENTES", Mensaje.GLOBAL));
		}
	}
}
