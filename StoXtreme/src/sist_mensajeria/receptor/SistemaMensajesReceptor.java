package sist_mensajeria.receptor;
import interfaz_remota.Mensaje;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;


public class SistemaMensajesReceptor {
	/**
	 * @uml.property  name="listaSub"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="sist_mensajeria.receptor.MessageListener"
	 */
	Vector listaSub;
	/**
	 * @uml.property  name="hPol"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="smr:sist_mensajeria.receptor.HiloPolling"
	 */
	HiloPolling hPol;
	
	public SistemaMensajesReceptor(String URL, String ID) throws MalformedURLException{
		listaSub = new Vector();
		hPol = new HiloPolling(this, URL, ID);
		hPol.start();
	}
	
	public void subscribir(MessageListener ml){
		listaSub.add(ml);
	}
	
	public void quitar(MessageListener ml){
		listaSub.remove(ml);
	}
	
	public void notificar(Vector mensajes){
		Iterator iteratorSubscritor = listaSub.iterator();
		Iterator iteratorMensajes = mensajes.iterator();
		// Recorre los subscriptores
		while(iteratorSubscritor.hasNext()){
			MessageListener ml = (MessageListener)iteratorSubscritor.next();
			
			// Recorre los mensajes y se lo manda a todos los subscriptores
			while(iteratorMensajes.hasNext()){
				Hashtable hMsg = (Hashtable)iteratorMensajes.next();
				ml.onMessage(new Mensaje(hMsg));
			}
				
		}
	}
	
	public void pararPolling(){
		hPol.stop();
	}
}
