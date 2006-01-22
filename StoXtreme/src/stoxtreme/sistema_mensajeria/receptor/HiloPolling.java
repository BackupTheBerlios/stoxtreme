package stoxtreme.sistema_mensajeria.receptor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;
import org.apache.xmlrpc.XmlRpcClientLite;
import org.apache.xmlrpc.XmlRpcException;

import stoxtreme.interfaz_remota.Mensaje;


public class HiloPolling extends Thread{
	/**
	 * @uml.property  name="smr"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="hPol:sist_mensajeria.receptor.SistemaMensajesReceptor"
	 */
	SistemaMensajesReceptor smr;
	/**
	 * @uml.property  name="xmlRpcCliente"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	XmlRpcClientLite xmlRpcCliente;
	/**
	 * @uml.property  name="iD"
	 */
	String ID;
	
	public HiloPolling(SistemaMensajesReceptor smr, String URL, String ID) throws MalformedURLException{
		this.smr = smr;
		this.ID = ID;
		xmlRpcCliente = new XmlRpcClientLite(URL);
	}
	
	public void run(){
		while(true){
			try {
				Thread.sleep(1000);
				Vector id = new Vector();
				id.add(ID);
				Object b = (Object)xmlRpcCliente.execute("sistMensajes.isNuevos", id);
				if (b instanceof Exception){
					throw (XmlRpcException)b;
				}
				if(((Boolean)b).booleanValue()){
					Vector nuevosMensajes = 
						(Vector)xmlRpcCliente.execute("sistMensajes.getNuevos", id); 
					smr.notificar(nuevosMensajes);
				}
			} catch (Exception e) {
				System.err.println("Error: "+e.getMessage());
				this.stop();
			}
		}
	}
}
