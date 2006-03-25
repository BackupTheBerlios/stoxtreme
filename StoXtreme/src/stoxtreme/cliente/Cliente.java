package stoxtreme.cliente;

import java.awt.Dimension;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	private Stoxtreme servidor;
	
	public Cliente(String url){
		try{
			StoxtremeServiceLocator locator = new StoxtremeServiceLocator();
			servidor = locator.getStoXtreme(new URL(url));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Cliente c = new Cliente("http://localhost:8080/axis/services/StoXtreme");
		try {
			c.init("alonso", "alonso");
			ReceptorMensajes receptor = new ReceptorMensajes("alonso", ReceptorMensajes.WEB_SERVICE);
			receptor.addListener(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init(String usuario, String pass) throws Exception{
		boolean login = servidor.login(usuario, pass);
		if(!login){
			boolean reg = servidor.registro(usuario, pass);
			if(!reg) throw new Exception("Fallo en el registro");
			login = servidor.login(usuario, pass);
			if(!login) throw new Exception("Fallo en el login");
		}
		
		//Eliminamos el DialogoInicial porque ya he identificado al usuario
//		identificacion.dispose();
		
		this.nUsuario = usuario; this.password = pass;
		eBolsa = new EstadoBolsa();
		opPendientes = new OperacionesPendientes();
		cartera = new CarteraAcciones();
		gui = new MainFrameCliente(this, cartera.getMCartera(), opPendientes.getMOpPendientes(), eBolsa.getMAcciones());
		gui.init();
		gui.pack();
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Lo ultimo que hacemos es dar de alta en el receptor
		
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
		else if(m.getTipoMensaje().equals("INFORMACION")){
			if(gui!= null){
				JOptionPane.showMessageDialog(gui,m.getContenido());
			}
			else{
				System.out.println("Mensaje informacion: "+m.getContenido());
			}
		}
		else{
			System.out.println("Mensaje("+m.getTipoMensaje()+"): "+ m.getContenido());
		}
	}
	
	public void insertarOperacion(Operacion op)throws Exception{
		int idOp = servidor.insertarOperacion(nUsuario, op);
		opPendientes.inserta(idOp, op);
	}
	
	public void cancelarOperacion(int op)throws Exception{
		servidor.cancelarOperacion(nUsuario, op);
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
