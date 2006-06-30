package stoxtreme.sistema_mensajeria;

import stoxtreme.interfaz_remota.Mensaje;

/**
 *  Description of the Interface
 *
 *@author    Chris Seguin
 */
public interface IMensajeriaListener {
	/**
	 *  Description of the Method
	 *
	 *@param  m  Description of Parameter
	 */
	public void onMensaje(Mensaje m);
}
