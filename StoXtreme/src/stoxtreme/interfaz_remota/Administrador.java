/**
 *  Administrador.java This file was auto-generated from WSDL by the Apache
 *  Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

/**
 *  Interfaz del Administrador
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface Administrador extends java.rmi.Remote {
	/**
	 *  M�todo que permite iniciar el sistema al Administrador
	 *
	 *@exception  java.rmi.RemoteException  Excepci�n remota del servidor
	 */
	public void iniciarServidor() throws java.rmi.RemoteException;


	/**
	 *  Permite parar el servidor al administrador en un momento determinado
	 *
	 *@exception  java.rmi.RemoteException  Excepci�n remota del servidor
	 */
	public void pararServidor() throws java.rmi.RemoteException;


	/**
	 *  Permite iniciar una sesi�n burs�til
	 *
	 *@exception  java.rmi.RemoteException  Excepci�n remota del servidor
	 */
	public void iniciaSesion() throws java.rmi.RemoteException;


	/**
	 *  Permite finalizar una sesi�n burs�til
	 *
	 *@exception  java.rmi.RemoteException  Excepci�n remota del servidor
	 */
	public void finalizaSesion() throws java.rmi.RemoteException;


	/**
	 *  Muestra la interfaz gr�fica
	 *
	 *@exception  java.rmi.RemoteException  Excepci�n remota del servidor
	 */
	public void showGUI() throws java.rmi.RemoteException;


	/**
	 *  Oculta la Interfaz gr�fica
	 *
	 *@exception  java.rmi.RemoteException  Excepci�n remota del servidor
	 */
	public void hideGUI() throws java.rmi.RemoteException;
}
