/**
 *  Administrador.java This file was auto-generated from WSDL by the Apache
 *  Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

/**
 *  Description of the Interface
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface Administrador extends java.rmi.Remote {
	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void iniciarServidor() throws java.rmi.RemoteException;


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void pararServidor() throws java.rmi.RemoteException;


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void iniciaSesion() throws java.rmi.RemoteException;


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void finalizaSesion() throws java.rmi.RemoteException;


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void showGUI() throws java.rmi.RemoteException;


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void hideGUI() throws java.rmi.RemoteException;
}
