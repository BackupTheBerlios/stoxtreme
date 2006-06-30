/**
 *  StoxtremeMensajes.java This file was auto-generated from WSDL by the
 *  Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *  Description of the Interface
 *
 *@author    Chris Seguin
 */
public interface StoxtremeMensajes extends Remote {
	/**
	 *  Gets the SiguienteMensaje attribute of the StoxtremeMensajes object
	 *
	 *@param  id                   Description of Parameter
	 *@return                      The SiguienteMensaje value
	 *@exception  RemoteException  Description of Exception
	 */
	public Mensaje getSiguienteMensaje(String id) throws RemoteException;


	/**
	 *  Description of the Method
	 *
	 *@param  mensaje              Description of Parameter
	 *@exception  RemoteException  Description of Exception
	 */
	public void enviaMensaje(Mensaje mensaje) throws RemoteException;
}
