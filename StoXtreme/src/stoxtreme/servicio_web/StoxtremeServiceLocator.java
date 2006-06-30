/**
 *  StoxtremeServiceLocator.java This file was auto-generated from WSDL by the
 *  Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.Stoxtreme;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class StoxtremeServiceLocator extends org.apache.axis.client.Service implements stoxtreme.servicio_web.StoxtremeService {

	// Use to get a proxy class for StoXtreme
	private java.lang.String StoXtreme_address = "http://localhost:8080/axis/services/StoXtreme";

	// The WSDD service name defaults to the port name.
	private java.lang.String StoXtremeWSDDServiceName = "StoXtreme";

	private java.util.HashSet ports = null;


	/**
	 *  Constructor for the StoxtremeServiceLocator object
	 */
	public StoxtremeServiceLocator() {
	}


	/**
	 *  Constructor for the StoxtremeServiceLocator object
	 *
	 *@param  config  Description of Parameter
	 */
	public StoxtremeServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}


	/**
	 *  Constructor for the StoxtremeServiceLocator object
	 *
	 *@param  wsdlLoc                             Description of Parameter
	 *@param  sName                               Description of Parameter
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public StoxtremeServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}


	/**
	 *  Sets the StoXtremeWSDDServiceName attribute of the
	 *  StoxtremeServiceLocator object
	 *
	 *@param  name  The new StoXtremeWSDDServiceName value
	 */
	public void setStoXtremeWSDDServiceName(java.lang.String name) {
		StoXtremeWSDDServiceName = name;
	}


	/**
	 *  Sets the StoXtremeEndpointAddress attribute of the
	 *  StoxtremeServiceLocator object
	 *
	 *@param  address  The new StoXtremeEndpointAddress value
	 */
	public void setStoXtremeEndpointAddress(java.lang.String address) {
		StoXtreme_address = address;
	}


	/**
	 *  Set the endpoint address for the specified port name.
	 *
	 *@param  portName                            The new EndpointAddress value
	 *@param  address                             The new EndpointAddress value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

		if ("StoXtreme".equals(portName)) {
			setStoXtremeEndpointAddress(address);
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
	 *  Gets the StoXtremeAddress attribute of the StoxtremeServiceLocator object
	 *
	 *@return    The StoXtremeAddress value
	 */
	public java.lang.String getStoXtremeAddress() {
		return StoXtreme_address;
	}


	/**
	 *  Gets the StoXtremeWSDDServiceName attribute of the
	 *  StoxtremeServiceLocator object
	 *
	 *@return    The StoXtremeWSDDServiceName value
	 */
	public java.lang.String getStoXtremeWSDDServiceName() {
		return StoXtremeWSDDServiceName;
	}


	/**
	 *  Gets the StoXtreme attribute of the StoxtremeServiceLocator object
	 *
	 *@return                                     The StoXtreme value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.Stoxtreme getStoXtreme() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(StoXtreme_address);
		}
		catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getStoXtreme(endpoint);
	}


	/**
	 *  Gets the StoXtreme attribute of the StoxtremeServiceLocator object
	 *
	 *@param  portAddress                         Description of Parameter
	 *@return                                     The StoXtreme value
	 *@exception  javax.xml.rpc.ServiceException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.Stoxtreme getStoXtreme(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			stoxtreme.servicio_web.StoXtremeSoapBindingStub _stub = new stoxtreme.servicio_web.StoXtremeSoapBindingStub(portAddress, this);
			_stub.setPortName(getStoXtremeWSDDServiceName());
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
			if (stoxtreme.interfaz_remota.Stoxtreme.class.isAssignableFrom(serviceEndpointInterface)) {
				stoxtreme.servicio_web.StoXtremeSoapBindingStub _stub = new stoxtreme.servicio_web.StoXtremeSoapBindingStub(new java.net.URL(StoXtreme_address), this);
				_stub.setPortName(getStoXtremeWSDDServiceName());
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
		if ("StoXtreme".equals(inputPortName)) {
			return getStoXtreme();
		}
		else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}


	/**
	 *  Gets the ServiceName attribute of the StoxtremeServiceLocator object
	 *
	 *@return    The ServiceName value
	 */
	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "StoxtremeService");
	}


	/**
	 *  Gets the Ports attribute of the StoxtremeServiceLocator object
	 *
	 *@return    The Ports value
	 */
	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "StoXtreme"));
		}
		return ports.iterator();
	}

}
