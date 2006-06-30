package stoxtreme.sistema_mensajeria.receptor;
import java.util.ArrayList;
import java.util.Iterator;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ReceptorMensajes {
	private ArrayList<IMensajeriaListener> listaOyentes;
	private Thread hiloConsulta;
	private String usuario;
	/**
	 *  Description of the Field
	 */
	public static final int LOCAL = 1;
	/**
	 *  Description of the Field
	 */
	public static final int WEB_SERVICE = 2;


	/**
	 *  Constructor for the ReceptorMensajes object
	 *
	 *@param  usuario  Description of Parameter
	 *@param  tipo     Description of Parameter
	 */
	public ReceptorMensajes(String usuario, int tipo) {
		this(usuario, tipo, null);
	}


	/**
	 *  Constructor for the ReceptorMensajes object
	 *
	 *@param  usuario  Description of Parameter
	 *@param  tipo     Description of Parameter
	 *@param  url      Description of Parameter
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
	 *  Gets the Usuario attribute of the ReceptorMensajes object
	 *
	 *@return    The Usuario value
	 */
	public String getUsuario() {
		return usuario;
	}


	/**
	 *  Adds the specified listener to receive events from this component. If
	 *  listener l is null, no exception is thrown and no action is performed.
	 *
	 *@param  l  The feature to be added to the attribute
	 */
	public void addListener(IMensajeriaListener l) {
		listaOyentes.add(l);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  m  Description of Parameter
	 */
	public synchronized void notifica(Mensaje m) {
		Iterator it = listaOyentes.iterator();

		while (it.hasNext()) {
			((IMensajeriaListener) it.next()).onMensaje(m);
		}
	}
}
