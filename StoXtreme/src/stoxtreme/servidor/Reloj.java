package stoxtreme.servidor;
import java.util.*;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Reloj {
	private ArrayList<RelojListener> oyentes;
	private Timer timer;
	private long ms;
	private boolean parado = true;


	/**
	 *  Constructor for the Reloj object
	 */
	public Reloj() {
		this(500);
	}

	// Recibe el numero de milisegundos cada cuanto se

	// ejecuta un paso
	/**
	 *  Constructor for the Reloj object
	 *
	 *@param  ms  Description of Parameter
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
	 *  Description of the Method
	 */
	public void ejecuta() {
		Iterator<RelojListener> i = oyentes.iterator();
		while (i.hasNext()) {
			i.next().paso();
		}
	}


	/**
	 *  Description of the Method
	 */
	public void iniciarReloj() {
		parado = false;
		reiniciarReloj();
	}


	/**
	 *  Description of the Method
	 */
	public void reiniciarReloj() {
		timer = new Timer(true);
		timer.schedule(new Ejecucion(this), new Date(), ms);
	}


	/**
	 *  Description of the Method
	 */
	public void pararReloj() {
		if (!parado) {
			parado = true;
			timer.cancel();
			timer = null;
		}
	}


	/**
	 *  Description of the Method
	 */
	public void reanudarReloj() {
		iniciarReloj();
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	private class Ejecucion extends TimerTask {
		private Reloj reloj;


		/**
		 *  Constructor for the Ejecucion object
		 *
		 *@param  r  Description of Parameter
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
