/**
 *  Stoxtreme.java This file was auto-generated from WSDL by the Apache Axis
 *  1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

import java.rmi.RemoteException;

/**
 *  Interfaz del servidor
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface Stoxtreme extends java.rmi.Remote {
	/**
	 *  Registro de un usuario
	 *
	 *@param  id                   Id del usuario
	 *@param  pass                 Contraseña de usuario
	 *@return                      Devuelve exito o fracaso en la conexión
	 *@exception  RemoteException  Excepción Remota
	 */
	public boolean registro(String id, String pass) throws RemoteException;


	/**
	 *  Comprobación de un usuario ya registrado
	 *
	 *@param  id                   Id del usuario
	 *@param  pass                 Contraseña de usuario
	 *@return                      Devuelve exito o fracaso en la conexión
	 *@exception  RemoteException  Excepción Remota
	 */
	public int login(String id, String pass) throws RemoteException;

	/**
	 
	 */
	/**
	 *  Inserta una operación
	 *
	 *@param  id  					Id de la operación
	 *@param  op  					Operación introducida
	 *@return     					devuelve el entero que ha generado la inserción
	 *@exception  RemoteException   Excepción Remota  
	 */
	public int insertarOperacion(String id, Operacion op) throws RemoteException;


	/**
	 *  Cancela una operación
	 *
	 *@param  id                   Id del que la cancela
	 *@param  idOp                 Id de la operación
	 *@exception  RemoteException  Excepción Remota
	 */
	public void cancelarOperacion(String id, int idOp) throws RemoteException;
}
