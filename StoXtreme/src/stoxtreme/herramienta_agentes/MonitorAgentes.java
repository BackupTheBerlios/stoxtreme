package stoxtreme.herramienta_agentes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.PriorityBlockingQueue;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.Perceptor;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.interfaz_remota.Stoxtreme;

/*
 *  Objeto mediador entre los agentes y la herramienta agentes
 */
/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class MonitorAgentes extends Thread {
	private PriorityBlockingQueue<Decision> colaPeticiones;
	private int ciclo;
	private ArrayList<Agente> listaAgentes;
	private ArrayList<TimerListener> listaTimerListeners;
	private boolean exit;
	private boolean pausa;
	private Object esperaTimer;
	private Stoxtreme conexion;
	private ConsolaAgentes consola;


	/**
	 *  Constructor for the MonitorAgentes object
	 *
	 *@param  conexion     Description of Parameter
	 *@param  consola      Description of Parameter
	 *@param  tiempoCiclo  Description of Parameter
	 */
	public MonitorAgentes(Stoxtreme conexion, ConsolaAgentes consola, int tiempoCiclo) {
		this.conexion = conexion;
		this.consola = consola;
		this.pausa = false;
		colaPeticiones = new PriorityBlockingQueue<Decision>();
		ciclo = 0;
		exit = false;
		esperaTimer = new Object();
		listaTimerListeners = new ArrayList<TimerListener>();
		Timer t = new Timer(true);
		TimerTask task =
			new TimerTask() {
				public void run() {
					synchronized (esperaTimer) {
						esperaTimer.notifyAll();
					}
				}
			};
		t.schedule(task, new Date(), tiempoCiclo);
	}


	/**
	 *  Gets the ConexionBolsa attribute of the MonitorAgentes object
	 *
	 *@return    The ConexionBolsa value
	 */
	public Stoxtreme getConexionBolsa() {
		return conexion;
	}


	/**
	 *  Gets the ConsolaAgentes attribute of the MonitorAgentes object
	 *
	 *@return    The ConsolaAgentes value
	 */
	public ConsolaAgentes getConsolaAgentes() {
		return consola;
	}


	/**
	 *  Adds a feature to the Agente attribute of the MonitorAgentes object
	 *
	 *@param  a  The feature to be added to the Agente attribute
	 */
	public void addAgente(Agente a) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  a  Description of Parameter
	 */
	public void removeAgente(Agente a) {
	}


	/**
	 *  Adds the specified Timer listener to receive Timer events from this
	 *  component. If listener l is null, no exception is thrown and no action is
	 *  performed.
	 *
	 *@param  listener  Contains the TimerListener for TimerEvent data.
	 */
	public void addTimerListener(TimerListener listener) {
		listaTimerListeners.add(listener);
	}


	/**
	 *  Removes the specified Timer listener so that it no longer receives Timer
	 *  events from this component. This method performs no function, nor does it
	 *  throw an exception, if the listener specified by the argument was not
	 *  previously added to this component. If listener l is null, no exception
	 *  is thrown and no action is performed.
	 *
	 *@param  listener  Contains the TimerListener for TimerEvent data.
	 */
	public void removeTimerListener(TimerListener listener) {
		listaTimerListeners.remove(listener);
	}


	/**
	 *  Adds a feature to the Decision attribute of the MonitorAgentes object
	 *
	 *@param  decision  The feature to be added to the Decision attribute
	 */
	public void addDecision(Decision decision) {
		// Añade una peticion a la cola de peticiones, es synchronized
		// para que no aumenten el ciclo mientras se procesa
		decision.addTActual(ciclo);
		colaPeticiones.add(decision);
	}


	/**
	 *  Description of the Method
	 */
	public synchronized void ejecutaPeticiones() {
		//	Ejecuta todas las peticiones de la cola de prioridad hasta
		// 	La que sea igual al ciclo actual
		while (colaPeticiones.size() > 0 && colaPeticiones.peek().getTiempoEjecucion() <= ciclo) {
			Decision decision = colaPeticiones.poll();

			boolean ejecutada;
			do {
				ejecutada = true;
				try {
					decision.ejecuta();
				}
				catch (Exception e) {
					System.err.println(e.getMessage());
					ejecutada = false;
				}
			} while (!ejecutada);
		}
	}


	/**
	 *  Main processing method for the MonitorAgentes object
	 */
	public void run() {
		while (!exit) {
			try {
				if (!pausa) {
					ejecutaPeticiones();
					synchronized (esperaTimer) {
						esperaTimer.wait();
					}
					ciclo++;
					Iterator<TimerListener> it = listaTimerListeners.iterator();
					while (it.hasNext()) {
						it.next().onTick(ciclo);
					}
				}

			}
			catch (InterruptedException e) {
				synchronized (this) {
					exit = true;
					Iterator<Agente> it = listaAgentes.iterator();
					while (it.hasNext()) {
						it.next().interrupt();
					}
					System.err.println("Error fatal: interrumpido el monitor");
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 *  Description of the Method
	 */
	public void pausar() {
		this.pausa = true;
	}


	/**
	 *  Description of the Method
	 */
	public void reanudar() {
		this.pausa = false;
	}
}
