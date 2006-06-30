/**
 *  Stoxtreme.java This file was auto-generated from WSDL by the Apache Axis
 *  1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

import java.rmi.RemoteException;

/**
 *  Description of the Interface
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface Stoxtreme extends java.rmi.Remote {
	/**
	 *  Description of the Method
	 *
	 *@param  id                   Description of Parameter
	 *@param  pass                 Description of Parameter
	 *@return                      Description of the Returned Value
	 *@exception  RemoteException  Description of Exception
	 */
	public boolean registro(String id, String pass) throws RemoteException;


	/**
	 *  Description of the Method
	 *
	 *@param  id                   Description of Parameter
	 *@param  pass                 Description of Parameter
	 *@return                      Description of the Returned Value
	 *@exception  RemoteException  Description of Exception
	 */
	public int login(String id, String pass) throws RemoteException;


	/**
	 *  Description of the Method
	 *
	 *@param  id                   Description of Parameter
	 *@param  op                   Description of Parameter
	 *@return                      Description of the Returned Value
	 *@exception  RemoteException  Description of Exception
	 */
	public int insertarOperacion(String id, Operacion op) throws RemoteException;


	/**
	 *  Description of the Method
	 *
	 *@param  id                   Description of Parameter
	 *@param  idOp                 Description of Parameter
	 *@exception  RemoteException  Description of Exception
	 */
	public void cancelarOperacion(String id, int idOp) throws RemoteException;
}
