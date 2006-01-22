package stoxtreme.servidor;
import java.util.*;
import sun.security.krb5.internal.crypto.t;


public class Reloj extends Thread{
	/**
	 * @uml.property  name="oyentes"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="servidor.RelojListener"
	 */
	private ArrayList oyentes;
	/**
	 * @uml.property  name="timer"
	 */
	private Timer timer;
	/**
	 * @uml.property  name="ms"
	 */
	private long ms;
	
	public Reloj(){
		this(500);
	}
	// Recibe el numero de milisegundos cada cuanto se 
	// ejecuta un paso
	public Reloj(long ms){
		this.oyentes = new ArrayList();
		this.timer = new Timer();
		this.ms = ms;
	}
	
	public void addListener(RelojListener r){
		oyentes.add(r);
	}
	
	public void run(){
		TimerTask t = new TimerTask(){
			public void run(){
				notificaOyentes();
			}
		};
		
		timer.schedule(t, ms, ms); 
	}
	
	private void notificaOyentes(){
		Iterator i = oyentes.iterator();
		while(i.hasNext()){
			// TODO CUIDADO CON LA CONCURRENCIA EN ESTE METODO
			((RelojListener)i.next()).paso();
		}
	}
}
