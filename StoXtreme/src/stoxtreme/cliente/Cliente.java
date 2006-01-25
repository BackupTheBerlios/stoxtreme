package stoxtreme.cliente;

import java.awt.Dimension;
import java.rmi.RemoteException;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.xml.rpc.ServiceException;

import com.sun.corba.se.spi.ior.iiop.JavaCodebaseComponent;

import stoxtreme.interfaz_remota.*;
import stoxtreme.servicio_web.StoxtremeServiceLocator;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;
import stoxtreme.sistema_mensajeria.receptor.*;

public class Cliente implements IMensajeriaListener{
	Stoxtreme servicio;
	
	public Cliente(){
		try {
			StoxtremeServiceLocator locator = new StoxtremeServiceLocator();
			servicio = locator.getStoxtreme();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ClienteGUI gui = new ClienteGUI();
		gui.init();
		gui.setPreferredSize(new Dimension(300, 300));
		gui.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		gui.pack();
		gui.setVisible(true);
		/*
		Cliente c = new Cliente();
		c.init();
		ReceptorMensajes receptor = new ReceptorMensajesWS("alonso");
		receptor.addListener(c);
		*/
	}
	
	public void init() {
		try {
			servicio.inicializacion();
			servicio.registro("alonso", "alonso");
			servicio.login("alonso", "alonso");
			int i = servicio.insertarOperacion("alonso", new Operacion(Operacion.COMPRA, 100, "Empresa1", 20.0f));
			servicio.cancelarOperacion("alonso", i);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void onMensaje(Mensaje m){
		System.out.println("RECIBIDO MENSAJE " + m.getMensaje());
	}
}
