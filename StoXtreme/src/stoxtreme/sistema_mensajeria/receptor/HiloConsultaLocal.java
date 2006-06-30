package stoxtreme.sistema_mensajeria.receptor;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class HiloConsultaLocal extends Thread {
	ReceptorMensajes receptor;


	/**
	 *  Constructor for the HiloConsultaLocal object
	 *
	 *@param  mensajes  Description of Parameter
	 */
	public HiloConsultaLocal(ReceptorMensajes mensajes) {
		receptor = mensajes;
	}


	/**
	 *  Main processing method for the HiloConsultaLocal object
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
