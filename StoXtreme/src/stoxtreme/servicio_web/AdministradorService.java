/**
 * AdministradorService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;

import stoxtreme.interfaz_remota.Administrador;

public interface AdministradorService extends javax.xml.rpc.Service {
    public java.lang.String getStoXtremeAdminAddress();

    public stoxtreme.interfaz_remota.Administrador getStoXtremeAdmin() throws javax.xml.rpc.ServiceException;

    public stoxtreme.interfaz_remota.Administrador getStoXtremeAdmin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
