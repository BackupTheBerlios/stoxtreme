/**
 *  StoxtremeService.java This file was auto-generated from WSDL by the Apache
 *  Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.Stoxtreme;

/**
 *  Description of the Interface
 *
 *@author    Chris Seguin
 */
public interface StoxtremeService extends javax.xml.rpc.Service {
	/**
	 *  Gets the StoXtremeAddress attribute of the StoxtremeService object
	 *
	 *@return    The StoXtremeAddress value
	 */
	public java.lang.String getStoXtremeAddress();


	/**
	 *  Gets the StoXtreme attribute of the StoxtremeService object
	 *
	 *@return                                     The StoXtreme value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.Stoxtreme getStoXtreme() throws javax.xml.rpc.ServiceException;


	/**
	 *  Gets the StoXtreme attribute of the StoxtremeService object
	 *
	 *@param  portAddress                         Description of Parameter
	 *@return                                     The StoXtreme value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.Stoxtreme getStoXtreme(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
