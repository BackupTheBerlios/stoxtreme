package stoxtreme.servidor;
import java.util.*;
import sun.security.krb5.internal.crypto.t;


public class Reloj extends TimerTask{
	private ArrayList oyentes;
	private Timer timer;
	private long ms;
	private boolean parado = true;
	
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
		System.out.println("Reloj");
		Iterator i = oyentes.iterator();
		while(i.hasNext()){
			// TODO CUIDADO CON LA CONCURRENCIA EN ESTE METODO
			((RelojListener)i.next()).paso();
		}
		
	}
	
	public void iniciarReloj(){
		parado = false;
		reiniciarReloj();
	}
	
	public void reiniciarReloj(){
		timer = new Timer(true);
		timer.schedule(this, new Date(), ms);
	}
	
	public void pararReloj(){
		if(!parado){
			timer.cancel();
			timer = null;
		}
	}
	
	public void reanudarReloj(){
		timer = new Timer();
	}
	
	
}
