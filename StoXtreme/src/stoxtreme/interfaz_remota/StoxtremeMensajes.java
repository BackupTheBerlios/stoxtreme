/**
 *  StoxtremeMensajes.java This file was auto-generated from WSDL by the
 *  Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *  Mensajes remotos del servidor
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface StoxtremeMensajes extends Remote {
	/**
	 *  Obtiene el siguiente mensaje de un usuario SiguienteMensaje
	 *
	 *@param  id                   Id del usuario a revisar
	 *@return                      Valor del SiguienteMensaje
	 *@exception  RemoteException  Excepci�n de conexion con el servidor
	 */
	public Mensaje getSiguienteMensaje(String id) throws RemoteException;


	/**
	 *  Env�a un mensaje
	 *
	 *@param  mensaje              contenido del mensaje
	 *@exception  RemoteException  Excepci�n remota
	 */
	public void enviaMensaje(Mensaje mensaje) throws RemoteException;
}
