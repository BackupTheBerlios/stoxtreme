/**
 * StoxtremeService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

public interface StoxtremeService extends javax.xml.rpc.Service {
    public java.lang.String getStoxtremeAddress();

    public stoxtreme.interfaz_remota.Stoxtreme getStoxtreme() throws javax.xml.rpc.ServiceException;

    public stoxtreme.interfaz_remota.Stoxtreme getStoxtreme(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
