/**
 * Stoxtreme.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.interfaz_remota;

import java.rmi.RemoteException;

public interface Stoxtreme extends java.rmi.Remote {
    public boolean registro(String id, String pass) throws RemoteException;
    public int login(String id, String pass) throws RemoteException;
    public int insertarOperacion(String id, Operacion op) throws RemoteException;
    public void cancelarOperacion(String id, int idOp) throws RemoteException;
}
