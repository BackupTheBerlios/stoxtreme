/**
 *  StoXtremeMsgSoapBindingSkeleton.java This file was auto-generated from
 *  WSDL by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
import stoxtreme.servidor.Servidor;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class StoXtremeMsgSoapBindingSkeleton implements org.apache.axis.wsdl.Skeleton, stoxtreme.interfaz_remota.StoxtremeMensajes {
	private stoxtreme.interfaz_remota.StoxtremeMensajes impl;
	private static java.util.Map _myOperations = new java.util.Hashtable();
	private static java.util.Collection _myOperationsList = new java.util.ArrayList();


	/**
	 *  Constructor for the StoXtremeMsgSoapBindingSkeleton object
	 */
	public StoXtremeMsgSoapBindingSkeleton() {
		// Generado automaticamente
		//this.impl = new stoxtreme.interfaz_remota.StoXtremeMsgSoapBindingImpl();
		// Lo sobreescribimos
		this.impl = AlmacenMensajes.getInstance();
	}


	/**
	 *  Constructor for the StoXtremeMsgSoapBindingSkeleton object
	 *
	 *@param  impl  Description of Parameter
	 */
	public StoXtremeMsgSoapBindingSkeleton(stoxtreme.interfaz_remota.StoxtremeMensajes impl) {
		this.impl = impl;
	}


	/**
	 *  Gets the SiguienteMensaje attribute of the
	 *  StoXtremeMsgSoapBindingSkeleton object
	 *
	 *@param  in0                           Description of Parameter
	 *@return                               The SiguienteMensaje value
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public stoxtreme.interfaz_remota.Mensaje getSiguienteMensaje(java.lang.String in0) throws java.rmi.RemoteException {
		stoxtreme.interfaz_remota.Mensaje ret = impl.getSiguienteMensaje(in0);
		return ret;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  in0                           Description of Parameter
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void enviaMensaje(stoxtreme.interfaz_remota.Mensaje in0) throws java.rmi.RemoteException {
		impl.enviaMensaje(in0);
	}


	/**
	 *  Returns List of OperationDesc objects with this name
	 *
	 *@param  methodName  Description of Parameter
	 *@return             The OperationDescByName value
	 */
	public static java.util.List getOperationDescByName(java.lang.String methodName) {
		return (java.util.List) _myOperations.get(methodName);
	}


	/**
	 *  Returns Collection of OperationDescs
	 *
	 *@return    The OperationDescs value
	 */
	public static java.util.Collection getOperationDescs() {
		return _myOperationsList;
	}

	static {
		org.apache.axis.description.OperationDesc _oper;
		org.apache.axis.description.FaultDesc _fault;
		org.apache.axis.description.ParameterDesc[] _params;
		_params = new org.apache.axis.description.ParameterDesc[]{
				new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false),
				};
		_oper = new org.apache.axis.description.OperationDesc("getSiguienteMensaje", _params, new javax.xml.namespace.QName("", "getSiguienteMensajeReturn"));
		_oper.setReturnType(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "Mensaje"));
		_oper.setElementQName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "getSiguienteMensaje"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("getSiguienteMensaje") == null) {
			_myOperations.put("getSiguienteMensaje", new java.util.ArrayList());
		}
		((java.util.List) _myOperations.get("getSiguienteMensaje")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{
				new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "in0"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "Mensaje"), stoxtreme.interfaz_remota.Mensaje.class, false, false),
				};
		_oper = new org.apache.axis.description.OperationDesc("enviaMensaje", _params, null);
		_oper.setElementQName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "enviaMensaje"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("enviaMensaje") == null) {
			_myOperations.put("enviaMensaje", new java.util.ArrayList());
		}
		((java.util.List) _myOperations.get("enviaMensaje")).add(_oper);
	}

}
