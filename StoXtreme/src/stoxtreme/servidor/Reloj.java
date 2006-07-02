package stoxtreme.servidor;
import java.util.*;

/**
 *  Clase que lleva el control del tiempo
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Reloj {
	private ArrayList<RelojListener> oyentes;
	private Timer timer;
	private long ms;
	private boolean parado = true;


	/**
	 *  Default Constructor for the Reloj object
	 */
	public Reloj() {
		this(500);
	}

	// Recibe el numero de milisegundos cada cuanto se

	// ejecuta un paso
	/**
	 *  Constructor for the Reloj object
	 *
	 *@param  ms  Número de milisegundos entre paso y paso
	 */
	public Reloj(long ms) {
		this.oyentes = new ArrayList<RelojListener>();
		this.timer = new Timer();
		this.ms = ms;
	}


	/**
	 *  Adds the specified listener to receive events from this component. If
	 *  listener l is null, no exception is thrown and no action is performed.
	 *
	 *@param  r  The feature to be added to the attribute
	 */
	public void addListener(RelojListener r) {
		oyentes.add(r);
	}


	/**
	 *  Todos los objetos que escuchan al reloj ejecutan un paso de tiempo
	 */
	public void ejecuta() {
		Iterator<RelojListener> i = oyentes.iterator();
		while (i.hasNext()) {
			i.next().paso();
		}
	}


	/**
	 *  Inicia el reloj
	 */
	public void iniciarReloj() {
		parado = false;
		reiniciarReloj();
	}


	/**
	 *  Reinicia el reloj
	 */
	public void reiniciarReloj() {
		timer = new Timer(true);
		timer.schedule(new Ejecucion(this), new Date(), ms);
	}


	/**
	 *  Para el reloj
	 */
	public void pararReloj() {
		if (!parado) {
			parado = true;
			timer.cancel();
			timer = null;
		}
	}


	/**
	 *  Reanuda el reloj
	 */
	public void reanudarReloj() {
		iniciarReloj();
	}


	/**
	 *  //TODO
	 *
	 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
	 */
	private class Ejecucion extends TimerTask {
		private Reloj reloj;


		/**
		 *  Constructor for the Ejecucion object
		 *
		 *@param  r  Reloj que mide el tiempo
		 */
		public Ejecucion(Reloj r) {
			this.reloj = r;
		}


		/**
		 *  Main processing method for the Ejecucion object
		 */
		public void run() {
			reloj.ejecuta();
		}
	}
}
