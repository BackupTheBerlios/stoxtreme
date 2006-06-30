/**
 *  StoXtremeAdminSoapBindingStub.java This file was auto-generated from WSDL
 *  by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.Administrador;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class StoXtremeAdminSoapBindingStub extends org.apache.axis.client.Stub implements stoxtreme.interfaz_remota.Administrador {

	static org.apache.axis.description.OperationDesc[] _operations;


	/**
	 *  Constructor for the StoXtremeAdminSoapBindingStub object
	 *
	 *@exception  org.apache.axis.AxisFault  Description of Exception
	 */
	public StoXtremeAdminSoapBindingStub() throws org.apache.axis.AxisFault {
		this(null);
	}


	/**
	 *  Constructor for the StoXtremeAdminSoapBindingStub object
	 *
	 *@param  endpointURL                    Description of Parameter
	 *@param  service                        Description of Parameter
	 *@exception  org.apache.axis.AxisFault  Description of Exception
	 */
	public StoXtremeAdminSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}


	/**
	 *  Constructor for the StoXtremeAdminSoapBindingStub object
	 *
	 *@param  service                        Description of Parameter
	 *@exception  org.apache.axis.AxisFault  Description of Exception
	 */
	public StoXtremeAdminSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		}
		else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void iniciarServidor() throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "iniciarServidor"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[]{});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			}
			extractAttachments(_call);
		}
		catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void pararServidor() throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "pararServidor"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[]{});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			}
			extractAttachments(_call);
		}
		catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void iniciaSesion() throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "iniciaSesion"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[]{});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			}
			extractAttachments(_call);
		}
		catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void finalizaSesion() throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[3]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "finalizaSesion"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[]{});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			}
			extractAttachments(_call);
		}
		catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void showGUI() throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[4]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "showGUI"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[]{});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			}
			extractAttachments(_call);
		}
		catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void hideGUI() throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[5]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "hideGUI"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[]{});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			}
			extractAttachments(_call);
		}
		catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@return                               Description of the Returned Value
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
		try {
			org.apache.axis.client.Call _call = super._createCall();
			if (super.maintainSessionSet) {
				_call.setMaintainSession(super.maintainSession);
			}
			if (super.cachedUsername != null) {
				_call.setUsername(super.cachedUsername);
			}
			if (super.cachedPassword != null) {
				_call.setPassword(super.cachedPassword);
			}
			if (super.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
			if (super.cachedTimeout != null) {
				_call.setTimeout(super.cachedTimeout);
			}
			if (super.cachedPortName != null) {
				_call.setPortName(super.cachedPortName);
			}
			java.util.Enumeration keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				java.lang.String key = (java.lang.String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			return _call;
		}
		catch (java.lang.Throwable _t) {
			throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
		}
	}


	/**
	 *  Description of the Method
	 */
	private static void _initOperationDesc1() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("iniciarServidor");
		oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[0] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("pararServidor");
		oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[1] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("iniciaSesion");
		oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[2] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("finalizaSesion");
		oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[3] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("showGUI");
		oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[4] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("hideGUI");
		oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[5] = oper;

	}

	static {
		_operations = new org.apache.axis.description.OperationDesc[6];
		_initOperationDesc1();
	}

}
