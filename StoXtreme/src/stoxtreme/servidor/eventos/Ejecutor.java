package stoxtreme.servidor.eventos;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

public class Ejecutor {
	private final static String DIHOLA = "DIHOLA";
	private final static String DIHOLA_ALONSO = "DIHOLA_ALONSO";
	
	public final static String[] acciones = {DIHOLA, DIHOLA_ALONSO};
	
	private Ejecutor(){
	}

	// TODO IMPLEMENTAR LA CLASE, QUIZAS EJECUTAR DEBERIA SER ESTATICO
	public static void ejecuta(String s){
		if(s.equals(DIHOLA)){
			System.out.println("Hola!!");
		}
		if(s.equals(DIHOLA_ALONSO)){
			Mensaje m = new Mensaje("HOLAAAA", "INFORMACION", "alonso");
			AlmacenMensajes.getInstance().enviaMensaje(m);
		}
	}
}
