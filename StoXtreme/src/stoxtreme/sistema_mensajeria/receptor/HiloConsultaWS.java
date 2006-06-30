package stoxtreme.sistema_mensajeria.receptor;

import java.net.URL;

import javax.xml.rpc.ServiceException;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
import stoxtreme.servicio_web.StoxtremeMensajesServiceLocator;
import stoxtreme.servicio_web.StoxtremeServiceLocator;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class HiloConsultaWS extends Thread {
	private ReceptorMensajes receptor;
	private String url;
	private boolean parar = false;


	/**
	 *  Constructor for the HiloConsultaWS object
	 *
	 *@param  mensajesWS  Description of Parameter
	 *@param  url         Description of Parameter
	 */
	public HiloConsultaWS(ReceptorMensajes mensajesWS, String url) {
		receptor = mensajesWS;
		this.url = url;
	}


	/**
	 *  Main processing method for the HiloConsultaWS object
	 */
	public void run() {
		try {
			StoxtremeMensajesServiceLocator locator = new StoxtremeMensajesServiceLocator();
			StoxtremeMensajes stoxtreme;
			if (url != null) {
				stoxtreme = locator.getStoXtremeMsg(new URL(url));
			}
			else {
				stoxtreme = locator.getStoXtremeMsg();
			}

			while (!parar) {
				Mensaje m = stoxtreme.getSiguienteMensaje(receptor.getUsuario());
				if ("FIN".equals(m.getTipoMensaje())) {
					parar = true;
				}
				if (m != null) {
					receptor.notifica(m);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
