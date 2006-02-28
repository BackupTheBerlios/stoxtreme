/**
 * StoxtremeMensajesServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.StoxtremeMensajes;

public class StoxtremeMensajesServiceLocator extends org.apache.axis.client.Service implements stoxtreme.servicio_web.StoxtremeMensajesService {

    public StoxtremeMensajesServiceLocator() {
    }


    public StoxtremeMensajesServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StoxtremeMensajesServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for StoXtremeMsg
    private java.lang.String StoXtremeMsg_address = "http://localhost:8080/axis/services/StoXtremeMsg";

    public java.lang.String getStoXtremeMsgAddress() {
        return StoXtremeMsg_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StoXtremeMsgWSDDServiceName = "StoXtremeMsg";

    public java.lang.String getStoXtremeMsgWSDDServiceName() {
        return StoXtremeMsgWSDDServiceName;
    }

    public void setStoXtremeMsgWSDDServiceName(java.lang.String name) {
        StoXtremeMsgWSDDServiceName = name;
    }

    public stoxtreme.interfaz_remota.StoxtremeMensajes getStoXtremeMsg() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(StoXtremeMsg_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStoXtremeMsg(endpoint);
    }

    public stoxtreme.interfaz_remota.StoxtremeMensajes getStoXtremeMsg(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            stoxtreme.servicio_web.StoXtremeMsgSoapBindingStub _stub = new stoxtreme.servicio_web.StoXtremeMsgSoapBindingStub(portAddress, this);
            _stub.setPortName(getStoXtremeMsgWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStoXtremeMsgEndpointAddress(java.lang.String address) {
        StoXtremeMsg_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (stoxtreme.interfaz_remota.StoxtremeMensajes.class.isAssignableFrom(serviceEndpointInterface)) {
                stoxtreme.servicio_web.StoXtremeMsgSoapBindingStub _stub = new stoxtreme.servicio_web.StoXtremeMsgSoapBindingStub(new java.net.URL(StoXtremeMsg_address), this);
                _stub.setPortName(getStoXtremeMsgWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("StoXtremeMsg".equals(inputPortName)) {
            return getStoXtremeMsg();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "StoxtremeMensajesService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "StoXtremeMsg"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("StoXtremeMsg".equals(portName)) {
            setStoXtremeMsgEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
