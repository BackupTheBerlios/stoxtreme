/**
 * StoxtremeSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package stoxtreme.servicio_web;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.Servidor;
import stoxtreme.sistema_mensajeria.emisor.EmisorMensajes;
import javax.swing.*;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

public class StoxtremeSoapBindingImpl implements stoxtreme.interfaz_remota.Stoxtreme{
    private Servidor servidor = null;
	private AlmacenMensajes almacen = null;
	
	public void inicializacion() throws java.rmi.RemoteException {
		if(servidor == null || almacen == null){
			servidor = new Servidor();
			almacen = new AlmacenMensajes();
			
			JFrame ventana = new JFrame("Hola mundo");
			JButton boton = new JButton("Insertar mensaje");
			boton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					almacen.insertarMensajeGlobal(new Mensaje("holamundo"));
				}
			});
			JButton boton2 = new JButton("Mandar mensaje a alonso");
			boton2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					almacen.insertarMensajePrivado("alonso", new Mensaje("HolaAlonso"));
				}
			});
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel panel = new JPanel(new FlowLayout());
			ventana.getContentPane().add(panel);
			panel.add(boton);
			panel.add(boton2);
			ventana.pack();
			ventana.setVisible(true);
		}
	}

    public boolean registro(java.lang.String usuario, java.lang.String pass) throws java.rmi.RemoteException {
        almacen.darAlta(usuario);
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
        return almacen.getSiguienteMensaje(usuario);
    }

}
