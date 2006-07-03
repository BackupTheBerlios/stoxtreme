package stoxtreme.sistema_mensajeria.receptor;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Hilo para los mensajes locales
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
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
	 *  M�todo que realiza la consulta local
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
