package stoxtreme.herramienta_agentes;

import java.util.Enumeration;
import java.util.Hashtable;



public class Notificador {
	private static Hashtable<String, ListenerNotificador> observadores =
		new Hashtable<String, ListenerNotificador>();
	
	public void addListener(String id, ListenerNotificador a){
		observadores.put(id, a);
	}
	
	public void notificar(String id, int idOp, int cantidad, double precio){
		observadores.get(id).onNotificacionOp(idOp, cantidad, precio);
	}
	
	public void notificarCancelacion(String id, int idOp){
		observadores.get(id).onCancelacionOp(idOp);
	}
}
