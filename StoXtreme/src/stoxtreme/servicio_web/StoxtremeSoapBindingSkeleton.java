/**
 * StoxtremeSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

public class StoxtremeSoapBindingSkeleton implements stoxtreme.interfaz_remota.Stoxtreme, org.apache.axis.wsdl.Skeleton {
    private stoxtreme.interfaz_remota.Stoxtreme impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("inicializacion", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://servicio_web.stoxtreme", "inicializacion"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("inicializacion") == null) {
            _myOperations.put("inicializacion", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("inicializacion")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "usuario"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pass"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("registro", _params, new javax.xml.namespace.QName("", "registroReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://servicio_web.stoxtreme", "registro"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("registro") == null) {
            _myOperations.put("registro", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("registro")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "usuario"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pass"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("login", _params, new javax.xml.namespace.QName("", "loginReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://servicio_web.stoxtreme", "login"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("login") == null) {
            _myOperations.put("login", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("login")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "usuario"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "operacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "Operacion"), stoxtreme.interfaz_remota.Operacion.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("insertarOperacion", _params, new javax.xml.namespace.QName("", "insertarOperacionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://servicio_web.stoxtreme", "insertarOperacion"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("insertarOperacion") == null) {
            _myOperations.put("insertarOperacion", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("insertarOperacion")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "usuario"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idOperacion"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("cancelarOperacion", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("http://servicio_web.stoxtreme", "cancelarOperacion"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("cancelarOperacion") == null) {
            _myOperations.put("cancelarOperacion", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("cancelarOperacion")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "usuario"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("siguienteMensaje", _params, new javax.xml.namespace.QName("", "siguienteMensajeReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "Mensaje"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://servicio_web.stoxtreme", "siguienteMensaje"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("siguienteMensaje") == null) {
            _myOperations.put("siguienteMensaje", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("siguienteMensaje")).add(_oper);
    }

    public StoxtremeSoapBindingSkeleton() {
        this.impl = new stoxtreme.servicio_web.StoxtremeSoapBindingImpl();
    }

    public StoxtremeSoapBindingSkeleton(stoxtreme.interfaz_remota.Stoxtreme impl) {
        this.impl = impl;
    }
    public void inicializacion() throws java.rmi.RemoteException
    {
        impl.inicializacion();
    }

    public boolean registro(java.lang.String usuario, java.lang.String pass) throws java.rmi.RemoteException
    {
        boolean ret = impl.registro(usuario, pass);
        return ret;
    }

    public boolean login(java.lang.String usuario, java.lang.String pass) throws java.rmi.RemoteException
    {
        boolean ret = impl.login(usuario, pass);
        return ret;
    }

    public int insertarOperacion(java.lang.String usuario, stoxtreme.interfaz_remota.Operacion operacion) throws java.rmi.RemoteException
    {
        int ret = impl.insertarOperacion(usuario, operacion);
        return ret;
    }

    public void cancelarOperacion(java.lang.String usuario, int idOperacion) throws java.rmi.RemoteException
    {
        impl.cancelarOperacion(usuario, idOperacion);
    }

    public stoxtreme.interfaz_remota.Mensaje siguienteMensaje(java.lang.String usuario) throws java.rmi.RemoteException
    {
        stoxtreme.interfaz_remota.Mensaje ret = impl.siguienteMensaje(usuario);
        return ret;
    }

}
