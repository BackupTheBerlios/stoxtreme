/**
 * StoxtremeServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.Stoxtreme;

public class StoxtremeServiceLocator extends org.apache.axis.client.Service implements stoxtreme.servicio_web.StoxtremeService {

    public StoxtremeServiceLocator() {
    }


    public StoxtremeServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StoxtremeServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for StoXtreme
    private java.lang.String StoXtreme_address = "http://localhost:8080/axis/services/StoXtreme";

    public java.lang.String getStoXtremeAddress() {
        return StoXtreme_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StoXtremeWSDDServiceName = "StoXtreme";

    public java.lang.String getStoXtremeWSDDServiceName() {
        return StoXtremeWSDDServiceName;
    }

    public void setStoXtremeWSDDServiceName(java.lang.String name) {
        StoXtremeWSDDServiceName = name;
    }

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

    public void setStoXtremeEndpointAddress(java.lang.String address) {
        StoXtreme_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
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
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("StoXtreme".equals(inputPortName)) {
            return getStoXtreme();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "StoxtremeService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://interfaz_remota.stoxtreme", "StoXtreme"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("StoXtreme".equals(portName)) {
            setStoXtremeEndpointAddress(address);
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
