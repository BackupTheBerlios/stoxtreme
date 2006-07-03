package stoxtreme.sistema_mensajeria.receptor;
import java.util.ArrayList;
import java.util.Iterator;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;

/**
 *  Clase que permite enviar un mensaje a una serie de oyentes
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public class ReceptorMensajes {
	private ArrayList<IMensajeriaListener> listaOyentes;
	private Thread hiloConsulta;
	private String usuario;
	/**
	 *  Indica si es de local
	 */
	public static final int LOCAL = 1;
	/**
	 *  Indica si es de Web Service
	 */
	public static final int WEB_SERVICE = 2;


	/**
	 *  Constructor de ReceptorMensajes 
	 *
	 *@param  usuario  Usuario Asociado
	 *@param  tipo     Tipo de consulta(Local o WS)
	 */
	public ReceptorMensajes(String usuario, int tipo) {
		this(usuario, tipo, null);
	}


	/**
	 *  Constructor de ReceptorMensajes 
	 *
	 *@param  usuario  Usuario Asociado
	 *@param  tipo     Tipo de consulta(Local o WS)
	 *@param  url      Direcci�n URL
	 */
	public ReceptorMensajes(String usuario, int tipo, String url) {
		listaOyentes = new ArrayList();
		this.usuario = usuario;
		if (tipo == LOCAL) {
			hiloConsulta = new HiloConsultaLocal(this);
		}
		else {
			hiloConsulta = new HiloConsultaWS(this, url);
		}
		hiloConsulta.start();
	}


	/**
	 *  Nos devuelve el usuario asociado
	 *
	 *@return    valor del usuario
	 */
	public String getUsuario() {
		return usuario;
	}


	/**
	 * A�adimos un listener espec�fico para recivir eventos del componente si el listener 
	 * es null , no lanzaremos excepci�n y ninguna acci�n se llevar� a cabo
	 *
	 *@param  l  Atributo a�adido a la lista de tipo IMensajeriaListener
	 */
	public void addListener(IMensajeriaListener l) {
		listaOyentes.add(l);
	}


	/**
	 *  Realiza una notificaci�n
	 *
	 *@param  m  Mensaje a notificar
	 */
	public synchronized void notifica(Mensaje m) {
		Iterator it = listaOyentes.iterator();

		while (it.hasNext()) {
			((IMensajeriaListener) it.next()).onMensaje(m);
		}
	}
}
