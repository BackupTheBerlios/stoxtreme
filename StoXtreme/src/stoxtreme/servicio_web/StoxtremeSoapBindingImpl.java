/**
 * StoxtremeSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.Servidor;
import stoxtreme.sistema_mensajeria.emisor.EmisorMensajes;
import javax.swing.*;

public class StoxtremeSoapBindingImpl implements stoxtreme.interfaz_remota.Stoxtreme{
    private Servidor servidor = null;
	private EmisorMensajes emisor = null;
	
	public void inicializacion() throws java.rmi.RemoteException {
		if(servidor == null || emisor == null){
			servidor = new Servidor();
			emisor = new EmisorMensajes();
			
			JFrame ventana = new JFrame("Hola mundo");
			JButton boton = new JButton("Añadir mensaje");
			boton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					emisor.nuevoMensajeGlobal(new Mensaje("holamundo"));
				}
			});
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ventana.add(boton);
			ventana.pack();
			ventana.setVisible(true);
		}
	}

    public boolean registro(java.lang.String usuario, java.lang.String pass) throws java.rmi.RemoteException {
        emisor.altaUsuario(usuario);
    	return servidor.registro(usuario, pass);
    }

    public boolean login(java.lang.String usuario, java.lang.String pass) throws java.rmi.RemoteException {
        return servidor.login(usuario, pass);
    }

    public int insertarOperacion(java.lang.String usuario, stoxtreme.interfaz_remota.Operacion operacion) throws java.rmi.RemoteException {
        return servidor.insertarOperacion(usuario, operacion);
    }

    public void cancelarOperacion(java.lang.String usuario, int idOperacion) throws java.rmi.RemoteException {
    	servidor.cancelarOperacion(usuario, idOperacion);
    }

    public stoxtreme.interfaz_remota.Mensaje siguienteMensaje(java.lang.String usuario) throws java.rmi.RemoteException {
        return emisor.siguienteMensaje(usuario);
    }

}
