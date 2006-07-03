package stoxtreme.sistema_mensajeria.receptor;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Hilo para los mensajes locales
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class HiloConsultaLocal extends Thread {
	ReceptorMensajes receptor;


	/**
	 *  Constructor de HiloConsultaLocal 
	 *  
	 *@param  mensajes  Tipo de Mensajes
	 */
	public HiloConsultaLocal(ReceptorMensajes mensajes) {
		receptor = mensajes;
	}


	/**
	 *  Método que realiza la consulta local
	 */
	public void run() {
		try {
			StoxtremeMensajes stoxtreme = AlmacenMensajes.getInstance();
			while (true) {
				Mensaje m = stoxtreme.getSiguienteMensaje(receptor.getUsuario());
				receptor.notifica(m);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
