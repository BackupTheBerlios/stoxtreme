package stoxtreme.herramienta_agentes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.Perceptor;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.interfaz_remota.Stoxtreme;

/* Objeto mediador entre los agentes y la herramienta agentes */
public class MonitorAgentes extends Thread{
	private PriorityQueue<Decision> colaPeticiones;
	private int ciclo;
	private ArrayList<Agente> listaAgentes;
	private ArrayList<TimerListener> listaTimerListeners;
	private boolean exit;
	private boolean pausa;
	private Object esperaTimer;
	private Stoxtreme conexion;
	private ConsolaAgentes consola;
	
	public MonitorAgentes(Stoxtreme conexion, ConsolaAgentes consola){
		this.conexion = conexion;
		this.consola = consola;
		this.pausa = false;
		colaPeticiones = new PriorityQueue<Decision>();
		ciclo = 0;
		exit = false;
		esperaTimer = new Object();
		listaTimerListeners = new ArrayList<TimerListener>();
		Timer t = new Timer(true);
		TimerTask task = new TimerTask(){
			public void run() { 
				synchronized(esperaTimer){
					esperaTimer.notifyAll();
				}
			}
		};
		t.schedule(task, new Date(), 200);
	}
	
	public void addAgente(Agente a){
		listaAgentes.add(a);
	}
	
	public void removeAgente(Agente a){
		listaAgentes.remove(a);
	}
	
	public void addTimerListener(TimerListener listener){
		listaTimerListeners.add(listener);
	}
	
	public void removeTimerListener(TimerListener listener){
		listaTimerListeners.remove(listener);
	}
	
	public synchronized void addDecision(Decision decision) {
		// Añade una peticion a la cola de peticiones, es synchronized
		// para que no aumenten el ciclo mientras se procesa
		decision.addTActual(ciclo);
		colaPeticiones.add(decision);
	}
	public void ejecutaPeticiones(){
		//	Ejecuta todas las peticiones de la cola de prioridad hasta
		// 	La que sea igual al ciclo actual
		while(colaPeticiones.size()>0 && colaPeticiones.peek().getTiempoEjecucion() <= ciclo){
			colaPeticiones.poll().ejecuta();
		}
	}
	
	public void run(){
		while(!exit){
			try {
				if(!pausa){
					ejecutaPeticiones();
					synchronized(esperaTimer){
						esperaTimer.wait();
					}
					ciclo++;
					Iterator<TimerListener>it = listaTimerListeners.iterator();
					while(it.hasNext()) it.next().onTick(ciclo);
				}

			} catch (InterruptedException e) {
				synchronized(this){
					exit = true;
					Iterator<Agente> it = listaAgentes.iterator();
					while(it.hasNext()) it.next().interrupt();
					System.err.println("Error fatal: interrumpido el monitor");
					e.printStackTrace();
				}
			}
		}
	}

	public Stoxtreme getConexionBolsa() {
		return conexion;
	}
	
	public ConsolaAgentes getConsolaAgentes(){
		return consola;
	}
	
	public void pausar(){
		this.pausa = true;
	}
	
	public void reanudar(){
		this.pausa = false;
	}
}
