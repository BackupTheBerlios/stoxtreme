/**
 *  StoXtremeMsgSoapBindingStub.java This file was auto-generated from WSDL by
 *  the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.StoxtremeMensajes;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class StoXtremeMsgSoapBindingStub extends org.apache.axis.client.Stub implements stoxtreme.interfaz_remota.StoxtremeMensajes {
	private java.util.Vector cachedSerClasses = new java.util.Vector();
	private java.util.Vector cachedSerQNames = new java.util.Vector();
	private java.util.Vector cachedSerFactories = new java.util.Vector();
	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	static org.apache.axis.description.OperationDesc[] _operations;


	/**
	 *  Constructor for the StoXtremeMsgSoapBindingStub object
	 *
	 *@exception  org.apache.axis.AxisFault  Description of Exception
	 */
	public StoXtremeMsgSoapBindingStub() throws org.apache.axis.AxisFault {
		this(null);
	}


	/**
	 *  Constructor for the StoXtremeMsgSoapBindingStub object
	 *
	 *@param  endpointURL                    Description of Parameter
	 *@param  service                        Description of Parameter
	 *@exception  org.apache.axis.AxisFault  Description of Exception
	 */
	public StoXtremeMsgSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}


	/**
	 *  Constructor for the StoXtremeMsgSoapBindingStub object
	 *
	 *@param  service                        Description of Parameter
	 *@exception  org.apache.axis.AxisFault  Description of Exception
	 */
	public StoXtremeMsgSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		}
		else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
		java.lang.Class cls;
		javax.xml.namespace.QName qName;
		javax.xml.namespace.QName qName2;
		java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
		java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
		java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
		java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
		java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
		java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
		java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
		java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
		java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
		java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
		qName = new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "Mensaje");
		cachedSerQNames.add(qName);
		cls = stoxtreme.interfaz_remota.Mensaje.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

	}


	/**
	 *  Gets the SiguienteMensaje attribute of the StoXtremeMsgSoapBindingStub
	 *  object
	 *
	 *@param  in0                           Description of Parameter
	 *@return                               The SiguienteMensaje value
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.Mensaje getSiguienteMensaje(java.lang.String in0) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "getSiguienteMensaje"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[]{in0});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			}
			else {
				extractAttachments(_call);
				try {
					return (stoxtreme.interfaz_remota.Mensaje) _resp;
				}
				catch (java.lang.Exception _exception) {
					return (stoxtreme.interfaz_remota.Mensaje) org.apache.axis.utils.JavaUtils.convert(_resp, stoxtreme.interfaz_remota.Mensaje.class);
				}
			}
		}
		catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  in0                           Description of Parameter
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void enviaMensaje(stoxtreme.interfaz_remota.Mensaje in0) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "enviaMensaje"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[]{in0});

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
			// All the type mapping information is registered
			// when the first call is made.
			// The type mapping information is actually registered in
			// the TypeMappingRegistry of the service, which
			// is the reason why registration is only needed for the first call.
			synchronized (this) {
				if (firstCall()) {
					// must set encoding style before registering serializers
					_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
					_call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
					for (int i = 0; i < cachedSerFactories.size(); ++i) {
						java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
						javax.xml.namespace.QName qName =
								(javax.xml.namespace.QName) cachedSerQNames.get(i);
						java.lang.Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							java.lang.Class sf = (java.lang.Class)
									cachedSerFactories.get(i);
							java.lang.Class df = (java.lang.Class)
									cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						}
						else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
							org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
									cachedSerFactories.get(i);
							org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
									cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						}
					}
				}
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
		oper.setName("getSiguienteMensaje");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "Mensaje"));
		oper.setReturnClass(stoxtreme.interfaz_remota.Mensaje.class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "getSiguienteMensajeReturn"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[0] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("enviaMensaje");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "Mensaje"), stoxtreme.interfaz_remota.Mensaje.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[1] = oper;

	}

	static {
		_operations = new org.apache.axis.description.OperationDesc[2];
		_initOperationDesc1();
	}

}
