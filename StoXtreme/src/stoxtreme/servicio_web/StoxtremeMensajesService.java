/**
 *  StoxtremeMensajesService.java This file was auto-generated from WSDL by
 *  the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.StoxtremeMensajes;

/**
 *  Description of the Interface
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface StoxtremeMensajesService extends javax.xml.rpc.Service {
	/**
	 *  Gets the StoXtremeMsgAddress attribute of the StoxtremeMensajesService
	 *  object
	 *
	 *@return    The StoXtremeMsgAddress value
	 */
	public java.lang.String getStoXtremeMsgAddress();


	/**
	 *  Gets the StoXtremeMsg attribute of the StoxtremeMensajesService object
	 *
	 *@return                                     The StoXtremeMsg value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.StoxtremeMensajes getStoXtremeMsg() throws javax.xml.rpc.ServiceException;


	/**
	 *  Gets the StoXtremeMsg attribute of the StoxtremeMensajesService object
	 *
	 *@param  portAddress                         Description of Parameter
	 *@return                                     The StoXtremeMsg value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.StoxtremeMensajes getStoXtremeMsg(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
