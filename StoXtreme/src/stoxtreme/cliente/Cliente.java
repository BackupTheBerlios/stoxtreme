package stoxtreme.cliente;

import java.awt.Dimension;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.xml.rpc.ServiceException;

import com.sun.corba.se.spi.ior.iiop.JavaCodebaseComponent;

import stoxtreme.cliente.gui.MainFrameCliente;
import stoxtreme.cliente.gui.ModeloCartera;
import stoxtreme.cliente.gui.ModeloOpPendientes;
import stoxtreme.cliente.gui.ModeloPrecioAccionesGrafico;
import stoxtreme.cliente.gui.DialogoInicial;
import stoxtreme.interfaz_remota.*;
import stoxtreme.servicio_web.StoxtremeServiceLocator;
import stoxtreme.servidor.Servidor;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;
import stoxtreme.sistema_mensajeria.receptor.*;

public class Cliente implements IMensajeriaListener{
	private String nUsuario;
	private String password;
	private Stoxtreme servicio;
	EstadoBolsa eBolsa;
	OperacionesPendientes opPendientes;
	CarteraAcciones cartera;
	MainFrameCliente gui;
	static DialogoInicial identificacion;
	
	public Cliente(){
//		try {
//			StoxtremeServiceLocator locator = new StoxtremeServiceLocator();
//			servicio = locator.getStoXtreme();
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
	}
	public static void main(String[] args) {
		/*
		ClienteGUI gui = new ClienteGUI();
		gui.init();
		gui.setPreferredSize(new Dimension(300, 300));
		gui.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		gui.pack();
		gui.setVisible(true);
		*/
		
		try{
			//TODO Mirar porque el login funciona si se inicia el servidor desde aqui,
			//pero si se inicia como desde el main del servidor, peta (no detecta al servidor)
			Servidor serv = Servidor.getInstance();
			serv.iniciarServidor();
			serv.showGUI();
			
			Cliente c = new Cliente();
			identificacion=new DialogoInicial(c);
			identificacion.init();
			identificacion.pack();
			identificacion.setVisible(true);
			identificacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void init(String usuario,String pass){
		/*
		boolean login = Servidor.getInstance().login(usuario, pass);
		if(!login){
			boolean reg = Servidor.getInstance().registro(usuario, pass);
			if(!reg) throw new Exception("Fallo en el registro");
			login = Servidor.getInstance().login(usuario, pass);
			if(!login) throw new Exception("Fallo en el login");
		}*/
		identificacion.dispose();
		this.nUsuario = usuario; this.password = pass;
		
		eBolsa = new EstadoBolsa();
		opPendientes = new OperacionesPendientes();
		cartera = new CarteraAcciones();
		gui = new MainFrameCliente(this, cartera.getMCartera(), opPendientes.getMOpPendientes(), eBolsa.getMAcciones());
		gui.init();
		gui.pack();
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public synchronized void onMensaje(Mensaje m){
		System.out.println("RECIBIDO MENSAJE " + m.getContenido());
		if(m.getTipoMensaje().equals("NOTIFICACION_OPERACION")){
			String[] valores = m.getContenido().split(",");
			int idOp = Integer.parseInt(valores[0]);
			int cantidad = Integer.parseInt(valores[1]);
			notificaOperacion(idOp, cantidad);
		}
		else if(m.getTipoMensaje().equals("NOTIFICACION_CANCELACION")){
			int idOp = Integer.parseInt(m.getContenido());
			notificaCancelacion(idOp);
		}
		else if(m.getTipoMensaje().equals("CAMBIO_PRECIO")){
			String[] valores = m.getContenido().split(",");
			String empresa = valores[0];
			double nuevoPrecio = Double.parseDouble(valores[1]);
			eBolsa.cambiaValor(empresa, nuevoPrecio);
		}
	}
	
	public void insertarOperacion(Operacion op){
		int idOp = Servidor.getInstance().insertarOperacion(nUsuario, op);
		opPendientes.inserta(idOp, op);
	}
	
	public void cancelarOperacion(int op){
		Servidor.getInstance().cancelarOperacion(nUsuario, op);
	}
	
	public void notificaOperacion(int idOp, int cantidad){
		if(idOp>0){
			Operacion o = opPendientes.dameOperacion(idOp);
			cartera.actualiza(o, cantidad);
		}
		int vOp = Math.abs(idOp);
		opPendientes.quitaOperacion(vOp);
	}
	
	public void notificaCancelacion(int idOp){
		opPendientes.quitaOperacion(idOp);
	}
	
	public String getNUsuario() {
		return nUsuario;
	}
}
