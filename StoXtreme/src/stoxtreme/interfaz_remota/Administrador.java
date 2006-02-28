/**
 * Administrador.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

public interface Administrador extends java.rmi.Remote {
    public void iniciarServidor() throws java.rmi.RemoteException;
    public void pararServidor() throws java.rmi.RemoteException;
    public void iniciaSesion() throws java.rmi.RemoteException;
    public void finalizaSesion() throws java.rmi.RemoteException;
    public void showGUI() throws java.rmi.RemoteException;
    public void hideGUI() throws java.rmi.RemoteException;
}
