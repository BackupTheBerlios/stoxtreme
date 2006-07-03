package stoxtreme.sistema_mensajeria;

import stoxtreme.interfaz_remota.Mensaje;

/**
 *  Interfaz de mensajes entre cliente Servidor
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface IMensajeriaListener {
	/**
	 *  REcibe un mensaje
	 *
	 *@param  m  Mensaje Transmitido
	 */
	public void onMensaje(Mensaje m);
}
