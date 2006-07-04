/**
 *  Stoxtreme.java This file was auto-generated from WSDL by the Apache Axis
 *  1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

import java.rmi.RemoteException;

/**
 *  Interfaz del servidor
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface Stoxtreme extends java.rmi.Remote {
	/**
	 *  Registro de un usuario
	 *
	 *@param  id                   Id del usuario
	 *@param  pass                 Contrase�a de usuario
	 *@return                      Devuelve exito o fracaso en la conexi�n
	 *@exception  RemoteException  Excepci�n Remota
	 */
	public boolean registro(String id, String pass) throws RemoteException;


	/**
	 *  Comprobaci�n de un usuario ya registrado
	 *
	 *@param  id                   Id del usuario
	 *@param  pass                 Contrase�a de usuario
	 *@return                      Devuelve exito o fracaso en la conexi�n
	 *@exception  RemoteException  Excepci�n Remota
	 */
	public int login(String id, String pass) throws RemoteException;

	/**
	 
	 */
	/**
	 *  Inserta una operaci�n
	 *
	 *@param  id  					Id de la operaci�n
	 *@param  op  					Operaci�n introducida
	 *@return     					devuelve el entero que ha generado la inserci�n
	 *@exception  RemoteException   Excepci�n Remota  
	 */
	public int insertarOperacion(String id, Operacion op) throws RemoteException;


	/**
	 *  Cancela una operaci�n
	 *
	 *@param  id                   Id del que la cancela
	 *@param  idOp                 Id de la operaci�n
	 *@exception  RemoteException  Excepci�n Remota
	 */
	public void cancelarOperacion(String id, int idOp) throws RemoteException;
}
