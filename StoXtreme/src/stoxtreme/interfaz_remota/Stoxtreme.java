/**
 * Stoxtreme.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

public interface Stoxtreme extends java.rmi.Remote {
    public void inicializacion() throws java.rmi.RemoteException;
    public boolean registro(java.lang.String usuario, java.lang.String pass) throws java.rmi.RemoteException;
    public boolean login(java.lang.String usuario, java.lang.String pass) throws java.rmi.RemoteException;
    public int insertarOperacion(java.lang.String usuario, stoxtreme.interfaz_remota.Operacion operacion) throws java.rmi.RemoteException;
    public void cancelarOperacion(java.lang.String usuario, int idOperacion) throws java.rmi.RemoteException;
    public stoxtreme.interfaz_remota.Mensaje siguienteMensaje(java.lang.String usuario) throws java.rmi.RemoteException;
}
