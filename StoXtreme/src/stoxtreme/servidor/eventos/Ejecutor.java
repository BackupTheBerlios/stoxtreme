package stoxtreme.servidor.eventos;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

public class Ejecutor {
	private final static String OPA_Endesa = "OPA sobre Endesa";
	private final static String Precio_Crudo = "Sube el precio del barril de gasolina";
	private final static String Preocupaci�n_Altadis = "Aprueban la ley anti-tabaco en lugares p�blicos";
	private final static String Amp_Cap_TPI = "Ampliaci�n de Capital de P�ginas Amarillas";
	private final static String split_Telefonica = "Se realiza un Split sobre las acciones de Telef�nica";
	private final static String Rumor_FCC = "Se Comenta la absorcion de ACS sobre FCC";
	
//	private final static String DIHOLA = "DIHOLA";
//	private final static String DIHOLA_ALONSO = "DIHOLA_ALONSO";
	
	public final static String[] acciones = {OPA_Endesa,"Precio_Crudo","Problema_Altadis","Amp_Cap_TPI",
		"split_Telef�nica","Rumor_FCC"};
	
	private Ejecutor(){
	}

	// TODO: IMPLEMENTAR LA CLASE, QUIZAS EJECUTAR DEBERIA SER ESTATICO
	public static void ejecuta(String s){
		if (s.equals(OPA_Endesa.toUpperCase())){
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(OPA_Endesa,"RUMOR_AGENTES",Mensaje.GLOBAL));
		}
		if (s.equals("Amp_Cap_TPI".toUpperCase())){
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Amp_Cap_TPI,"RUMOR_AGENTES",Mensaje.GLOBAL));
		}
		if (s.equals("Precio_Crudo".toUpperCase())){
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Precio_Crudo,"RUMOR_AGENTES",Mensaje.GLOBAL));
		}
		if (s.equals("Problema_Altadis".toUpperCase())){
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Preocupaci�n_Altadis,"RUMOR_AGENTES",Mensaje.GLOBAL));
		}
		if (s.equals("split_Telef�nica".toUpperCase())){
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(split_Telefonica,"RUMOR_AGENTES",Mensaje.GLOBAL));
		}
		if (s.equals("Rumor_FCC".toUpperCase())){
			AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Rumor_FCC,"RUMOR_AGENTES",Mensaje.GLOBAL));
		}
//		if(s.equals(DIHOLA)){
//			System.out.println("Hola!!");
//		}
//		if(s.equals(DIHOLA_ALONSO)){
//			Mensaje m = new Mensaje("HOLAAAA", "INFORMACION", "alonso");
//			AlmacenMensajes.getInstance().enviaMensaje(m);
//		}
	}
}
