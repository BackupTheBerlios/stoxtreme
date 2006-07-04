package stoxtreme.servidor;
import java.util.*;

/**
 *  Clase que lleva el control del tiempo
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public class Reloj {
	private ArrayList<RelojListener> oyentes;
	private Timer timer;
	private long ms;
	private boolean parado = true;


	/**
	 *  Constructor del objeto Reloj
	 */
	public Reloj() {
		this(500);
	}

	
	/**
	 *  Constructor del objeto Reloj
	 *
	 *@param  ms  N�mero de milisegundos entre paso y paso
	 */
	public Reloj(long ms) {
		this.oyentes = new ArrayList<RelojListener>();
		this.timer = new Timer();
		this.ms = ms;
	}


	/**
	 *  A�ade el listener especificado  para que reciba eventos de este componente.
	 *  Si el listener l es nulo, no se lanza excepci�n y no se ejecuta ninguna acci�n.
	 *
	 *@param  r  El listener a a�adir
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
	 * 	Una tarea programada para ejecutarse cada cierto tiempo,
	 * gobernada por un Timer
	 *
	 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
	 */
	private class Ejecucion extends TimerTask {
		private Reloj reloj;


		/**
		 *  Constructor del objeto Ejecucion
		 *
		 *@param  r  Reloj que mide el tiempo
		 */
		public Ejecucion(Reloj r) {
			this.reloj = r;
		}


		/**
		 *  Main de la calse ejecucion
		 */
		public void run() {
			reloj.ejecuta();
		}
	}
}
