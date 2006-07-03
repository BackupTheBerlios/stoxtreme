package stoxtreme.sistema_mensajeria;

import stoxtreme.interfaz_remota.Mensaje;

/**
 *  Interfaz de mensajes entre cliente Servidor
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface IMensajeriaListener {
	/**
	 *  REcibe un mensaje
	 *
	 *@param  m  Mensaje Transmitido
	 */
	public void onMensaje(Mensaje m);
}
