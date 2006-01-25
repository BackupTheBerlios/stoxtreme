package stoxtreme.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.xml.rpc.ServiceException;

import stoxtreme.interfaz_remota.*;
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
	
		Cliente c = new Cliente();
		c.init();
		ReceptorMensajes receptor = new ReceptorMensajesWS("alonso");
		receptor.addListener(c);
		
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
