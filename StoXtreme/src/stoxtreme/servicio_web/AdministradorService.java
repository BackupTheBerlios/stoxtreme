/**
 *  AdministradorService.java This file was auto-generated from WSDL by the
 *  Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.Administrador;

/**
 *  Description of the Interface
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface AdministradorService extends javax.xml.rpc.Service {
	/**
	 *  Gets the StoXtremeAdminAddress attribute of the AdministradorService
	 *  object
	 *
	 *@return    The StoXtremeAdminAddress value
	 */
	public java.lang.String getStoXtremeAdminAddress();


	/**
	 *  Gets the StoXtremeAdmin attribute of the AdministradorService object
	 *
	 *@return                                     The StoXtremeAdmin value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.Administrador getStoXtremeAdmin() throws javax.xml.rpc.ServiceException;


	/**
	 *  Gets the StoXtremeAdmin attribute of the AdministradorService object
	 *
	 *@param  portAddress                         Description of Parameter
	 *@return                                     The StoXtremeAdmin value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.Administrador getStoXtremeAdmin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
