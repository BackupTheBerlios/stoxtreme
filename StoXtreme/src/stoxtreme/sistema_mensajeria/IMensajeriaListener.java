package stoxtreme.sistema_mensajeria;

import stoxtreme.interfaz_remota.Mensaje;

/**
 *  Description of the Interface
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface IMensajeriaListener {
	/**
	 *  Description of the Method
	 *
	 *@param  m  Description of Parameter
	 */
	public void onMensaje(Mensaje m);
}
