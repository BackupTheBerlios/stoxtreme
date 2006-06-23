package stoxtreme.servidor;
import java.util.*;
import sun.security.krb5.internal.crypto.t;


public class Reloj{
	private ArrayList<RelojListener> oyentes;
	private Timer timer;
	private long ms;
	private boolean parado = true;
	
	public Reloj(){
		this(500);
	}
	// Recibe el numero de milisegundos cada cuanto se 
	// ejecuta un paso
	public Reloj(long ms){
		this.oyentes = new ArrayList<RelojListener>();
		this.timer = new Timer();
		this.ms = ms;
	}
	
	public void addListener(RelojListener r){
		oyentes.add(r);
	}
	
	public void ejecuta(){
		System.err.println("Paso");
		Iterator<RelojListener> i = oyentes.iterator();
		while(i.hasNext()){
			i.next().paso();
		}
	}
	
	public void iniciarReloj(){
		parado = false;
		reiniciarReloj();
	}
	
	public void reiniciarReloj(){
		timer = new Timer(true);
		timer.schedule(new Ejecucion(this), new Date(), ms);
	}
	
	public void pararReloj(){
		if(!parado){
			parado = true;
			timer.cancel();
			timer = null;
		}
	}
	
	public void reanudarReloj(){
		iniciarReloj();
	}
	
	private class Ejecucion extends TimerTask{
		private Reloj reloj;
		public Ejecucion(Reloj r) {
			this.reloj = r;
		}
		public void run() {
			reloj.ejecuta();
		}
	}
}
