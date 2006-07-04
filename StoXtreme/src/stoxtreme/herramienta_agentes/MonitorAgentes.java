package stoxtreme.herramienta_agentes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.PriorityBlockingQueue;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.interfaz_remota.Stoxtreme;

/**
 *  Objeto mediador entre los agentes y la herramienta agentes
 *
 *  @author  Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class MonitorAgentes extends Thread {
	/**
	 * 
	 */
	private PriorityBlockingQueue<Decision> colaPeticiones;
	
	/**
	 * 
	 */
	private int ciclo;
	
	/**
	 * Lista de agentes en la simulacion
	 */
	private ArrayList<Agente> listaAgentes;
	
	/**
	 * Lista de oyentes del reloj de la simulacion
	 */
	private ArrayList<TimerListener> listaTimerListeners;
	
	/**
	 * Flag para la salida del monitor
	 */
	private boolean exit;
	
	/**
	 * Flag para la pausa del monitor
	 */
	private boolean pausa;
	
	/**
	 * Cerrojo para la espera del timer
	 */
	private Object esperaTimer;
	
	/**
	 * Conexión con el objeto remoto
	 */
	private Stoxtreme conexion;
	
	/**
	 * Salida de los agentes
	 */
	private ConsolaAgentes consola;

	/**
	 *  Constructor del monitor.
	 *  Construye el monitor pero no lo inicia
	 *
	 *  @param  conexion     Conexión con el objeto remoto del servidor
	 *  @param  consola      Salida para los agentes
	 *  @param  tiempoCiclo  Tiempo en milisegundos que durara cada ciclo de la simulacion
	 */
	public MonitorAgentes(Stoxtreme conexion, ConsolaAgentes consola, int tiempoCiclo) {
		this.conexion = conexion;
		this.consola = consola;
		this.listaAgentes=new ArrayList<Agente>();
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
	 *  Obtiene la conexión remota con el servidor
	 *
	 *  @return    El objeto remoto
	 */
	public Stoxtreme getConexionBolsa() {
		return conexion;
	}

	/**
	 *  Obtiene la salida de la simulacion de los agentes
	 *
	 *  @return    Devuelve la salida de los agentes
	 */
	public ConsolaAgentes getConsolaAgentes() {
		return consola;
	}

	/**
	 *  Inserta agente en el monitor
	 *
	 *  @param  agente  El agente a añadir en el sistema
	 */
	public void addAgente(Agente agente) {
		listaAgentes.add(agente);
	}

	/**
	 *  Borra el agente del monitor
	 *
	 *@param  agente  Agente que deseamos borrar
	 */
	public void removeAgente(Agente agente) {
		listaAgentes.remove(agente);
	}

	/**
	 *  Inserta nuevo oyente en el timer
	 *
	 *  @param  listener  Oyente que deseamos insertar
	 */
	public void addTimerListener(TimerListener listener) {
		listaTimerListeners.add(listener);
	}

	/**
	 *  Borra al oyente de la lista de oyentes del timer
	 *  
	 *  @param  listener  Oyente que deseamos quitar
	 */
	public void removeTimerListener(TimerListener listener) {
		listaTimerListeners.remove(listener);
	}

	/**
	 *  Inserta una decision en el monitor para que este la ejecute cuando
	 *  sea oportuno
	 *
	 *  @param  decision  Decision que deseamos insertar en el sistema
	 */
	public void addDecision(Decision decision) {
		// Añade una peticion a la cola de peticiones, es synchronized
		// para que no aumenten el ciclo mientras se procesa
		decision.addTActual(ciclo);
		colaPeticiones.add(decision);
	}

	/**
	 *  Ejecuta todas las peticiones planificadas para este turno
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
			} 
			while (!ejecutada);
		}
	}

	/**
	 *  Metodo principal del monitor de agentes.
	 *  Bucle que dura mientras no deseamos salir para la ejecucion de 
	 *  todas las peticiones y notificacion de todos los oyentes del timer
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
	 *  Pausa el monitor de agentes
	 */
	public void pausar() {
		this.pausa = true;
	}

	/**
	 *  Reanuda el monitor despues de una pausa
	 */
	public void reanudar() {
		this.pausa = false;
	}
}
