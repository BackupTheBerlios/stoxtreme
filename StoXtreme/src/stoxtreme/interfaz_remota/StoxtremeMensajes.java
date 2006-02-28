/**
 * StoxtremeMensajes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StoxtremeMensajes extends Remote {
    public Mensaje getSiguienteMensaje(String id) throws RemoteException;
    public void enviaMensaje(Mensaje mensaje) throws RemoteException;
}
