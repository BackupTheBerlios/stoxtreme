/**
 *  AdministradorServiceLocator.java This file was auto-generated from WSDL by
 *  the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.Administrador;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class AdministradorServiceLocator extends org.apache.axis.client.Service implements stoxtreme.servicio_web.AdministradorService {

	// Use to get a proxy class for StoXtremeAdmin
	private java.lang.String StoXtremeAdmin_address = "http://localhost:8080/axis/services/StoXtremeAdmin";

	// The WSDD service name defaults to the port name.
	private java.lang.String StoXtremeAdminWSDDServiceName = "StoXtremeAdmin";

	private java.util.HashSet ports = null;


	/**
	 *  Constructor for the AdministradorServiceLocator object
	 */
	public AdministradorServiceLocator() {
	}


	/**
	 *  Constructor for the AdministradorServiceLocator object
	 *
	 *@param  config  Description of Parameter
	 */
	public AdministradorServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}


	/**
	 *  Constructor for the AdministradorServiceLocator object
	 *
	 *@param  wsdlLoc                             Description of Parameter
	 *@param  sName                               Description of Parameter
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public AdministradorServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}


	/**
	 *  Sets the StoXtremeAdminWSDDServiceName attribute of the
	 *  AdministradorServiceLocator object
	 *
	 *@param  name  The new StoXtremeAdminWSDDServiceName value
	 */
	public void setStoXtremeAdminWSDDServiceName(java.lang.String name) {
		StoXtremeAdminWSDDServiceName = name;
	}


	/**
	 *  Sets the StoXtremeAdminEndpointAddress attribute of the
	 *  AdministradorServiceLocator object
	 *
	 *@param  address  The new StoXtremeAdminEndpointAddress value
	 */
	public void setStoXtremeAdminEndpointAddress(java.lang.String address) {
		StoXtremeAdmin_address = address;
	}


	/**
	 *  Set the endpoint address for the specified port name.
	 *
	 *@param  portName                            The new EndpointAddress value
	 *@param  address                             The new EndpointAddress value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("StoXtremeAdmin".equals(portName)) {
			setStoXtremeAdminEndpointAddress(address);
		}
		else {
			// Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}


	/**
	 *  Set the endpoint address for the specified port name.
	 *
	 *@param  portName                            The new EndpointAddress value
	 *@param  address                             The new EndpointAddress value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}


	/**
	 *  Gets the StoXtremeAdminAddress attribute of the
	 *  AdministradorServiceLocator object
	 *
	 *@return    The StoXtremeAdminAddress value
	 */
	public java.lang.String getStoXtremeAdminAddress() {
		return StoXtremeAdmin_address;
	}


	/**
	 *  Gets the StoXtremeAdminWSDDServiceName attribute of the
	 *  AdministradorServiceLocator object
	 *
	 *@return    The StoXtremeAdminWSDDServiceName value
	 */
	public java.lang.String getStoXtremeAdminWSDDServiceName() {
		return StoXtremeAdminWSDDServiceName;
	}


	/**
	 *  Gets the StoXtremeAdmin attribute of the AdministradorServiceLocator
	 *  object
	 *
	 *@return                                     The StoXtremeAdmin value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.Administrador getStoXtremeAdmin() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(StoXtremeAdmin_address);
		}
		catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getStoXtremeAdmin(endpoint);
	}


	/**
	 *  Gets the StoXtremeAdmin attribute of the AdministradorServiceLocator
	 *  object
	 *
	 *@param  portAddress                         Description of Parameter
	 *@return                                     The StoXtremeAdmin value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.Administrador getStoXtremeAdmin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			stoxtreme.servicio_web.StoXtremeAdminSoapBindingStub _stub = new stoxtreme.servicio_web.StoXtremeAdminSoapBindingStub(portAddress, this);
			_stub.setPortName(getStoXtremeAdminWSDDServiceName());
			return _stub;
		}
		catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}


	/**
	 *  For the given interface, get the stub implementation. If this service has
	 *  no port for the given interface, then ServiceException is thrown.
	 *
	 *@param  serviceEndpointInterface            Description of Parameter
	 *@return                                     The Port value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (stoxtreme.interfaz_remota.Administrador.class.isAssignableFrom(serviceEndpointInterface)) {
				stoxtreme.servicio_web.StoXtremeAdminSoapBindingStub _stub = new stoxtreme.servicio_web.StoXtremeAdminSoapBindingStub(new java.net.URL(StoXtremeAdmin_address), this);
				_stub.setPortName(getStoXtremeAdminWSDDServiceName());
				return _stub;
			}
		}
		catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}


	/**
	 *  For the given interface, get the stub implementation. If this service has
	 *  no port for the given interface, then ServiceException is thrown.
	 *
	 *@param  portName                            Description of Parameter
	 *@param  serviceEndpointInterface            Description of Parameter
	 *@return                                     The Port value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("StoXtremeAdmin".equals(inputPortName)) {
			return getStoXtremeAdmin();
		}
		else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}


	/**
	 *  Gets the ServiceName attribute of the AdministradorServiceLocator object
	 *
	 *@return    The ServiceName value
	 */
	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "AdministradorService");
	}


	/**
	 *  Gets the Ports attribute of the AdministradorServiceLocator object
	 *
	 *@return    The Ports value
	 */
	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "StoXtremeAdmin"));
		}
		return ports.iterator();
	}

}
