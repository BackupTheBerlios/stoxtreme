/**
 *  StoXtremeAdminSoapBindingSkeleton.java This file was auto-generated from
 *  WSDL by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.Administrador;
import stoxtreme.servidor.Servidor;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class StoXtremeAdminSoapBindingSkeleton implements stoxtreme.interfaz_remota.Administrador, org.apache.axis.wsdl.Skeleton {
	private stoxtreme.interfaz_remota.Administrador impl;
	private static java.util.Map _myOperations = new java.util.Hashtable();
	private static java.util.Collection _myOperationsList = new java.util.ArrayList();


	/**
	 *  Constructor for the StoXtremeAdminSoapBindingSkeleton object
	 */
	public StoXtremeAdminSoapBindingSkeleton() {
		// Generado automaticamente
		//this.impl = new stoxtreme.interfaz_remota.StoXtremeAdminSoapBindingImpl();
		// Lo sobreescribo
		this.impl = Servidor.getInstance();
	}


	/**
	 *  Constructor for the StoXtremeAdminSoapBindingSkeleton object
	 *
	 *@param  impl  Description of Parameter
	 */
	public StoXtremeAdminSoapBindingSkeleton(stoxtreme.interfaz_remota.Administrador impl) {
		this.impl = impl;
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void iniciarServidor() throws java.rmi.RemoteException {
		impl.iniciarServidor();
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void pararServidor() throws java.rmi.RemoteException {
		impl.pararServidor();
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void iniciaSesion() throws java.rmi.RemoteException {
		impl.iniciaSesion();
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void finalizaSesion() throws java.rmi.RemoteException {
		impl.finalizaSesion();
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void showGUI() throws java.rmi.RemoteException {
		impl.showGUI();
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  java.rmi.RemoteException  Description of Exception
	 */
	public void hideGUI() throws java.rmi.RemoteException {
		impl.hideGUI();
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
				};
		_oper = new org.apache.axis.description.OperationDesc("iniciarServidor", _params, null);
		_oper.setElementQName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "iniciarServidor"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("iniciarServidor") == null) {
			_myOperations.put("iniciarServidor", new java.util.ArrayList());
		}
		((java.util.List) _myOperations.get("iniciarServidor")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{
				};
		_oper = new org.apache.axis.description.OperationDesc("pararServidor", _params, null);
		_oper.setElementQName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "pararServidor"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("pararServidor") == null) {
			_myOperations.put("pararServidor", new java.util.ArrayList());
		}
		((java.util.List) _myOperations.get("pararServidor")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{
				};
		_oper = new org.apache.axis.description.OperationDesc("iniciaSesion", _params, null);
		_oper.setElementQName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "iniciaSesion"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("iniciaSesion") == null) {
			_myOperations.put("iniciaSesion", new java.util.ArrayList());
		}
		((java.util.List) _myOperations.get("iniciaSesion")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{
				};
		_oper = new org.apache.axis.description.OperationDesc("finalizaSesion", _params, null);
		_oper.setElementQName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "finalizaSesion"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("finalizaSesion") == null) {
			_myOperations.put("finalizaSesion", new java.util.ArrayList());
		}
		((java.util.List) _myOperations.get("finalizaSesion")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{
				};
		_oper = new org.apache.axis.description.OperationDesc("showGUI", _params, null);
		_oper.setElementQName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "showGUI"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("showGUI") == null) {
			_myOperations.put("showGUI", new java.util.ArrayList());
		}
		((java.util.List) _myOperations.get("showGUI")).add(_oper);
		_params = new org.apache.axis.description.ParameterDesc[]{
				};
		_oper = new org.apache.axis.description.OperationDesc("hideGUI", _params, null);
		_oper.setElementQName(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "hideGUI"));
		_oper.setSoapAction("");
		_myOperationsList.add(_oper);
		if (_myOperations.get("hideGUI") == null) {
			_myOperations.put("hideGUI", new java.util.ArrayList());
		}
		((java.util.List) _myOperations.get("hideGUI")).add(_oper);
	}

}
