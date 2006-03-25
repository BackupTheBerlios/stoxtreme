package stoxtreme.herramienta_agentes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;

/* Objeto mediador entre los agentes y la herramienta agentes */
public class MonitorAgentes extends Thread{
	private PriorityQueue<Decision> colaPeticiones;
	private int ciclo;
	private ArrayList<Agente> listaAgentes;
	private boolean exit;
	private Object esperaTimer;
	private ConexionBolsa conexion;
	
	public MonitorAgentes(ConexionBolsa conexion){
		this.conexion = conexion;
		colaPeticiones = new PriorityQueue<Decision>();
		ciclo = 0;
		exit = false;
		esperaTimer = new Object();
		listaAgentes = new ArrayList<Agente>();
		Timer t = new Timer(true);
		TimerTask task = new TimerTask(){
			public void run() { 
				synchronized(esperaTimer){
					esperaTimer.notifyAll();
				}
			}
		};
		t.schedule(task, new Date(), 1000);
	}
	
	public void addAgente(Agente a){
		listaAgentes.add(a);
	}
	
	public void removeAgente(Agente a){
		listaAgentes.remove(a);
	}
	
	public synchronized void addDecision(Decision decision) {
		// A�ade una peticion a la cola de peticiones, es synchronized
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
				ejecutaPeticiones();
				synchronized(esperaTimer){
					esperaTimer.wait();
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
			ciclo++;
			System.out.println(ciclo);
		}
	}

	public ConexionBolsa getConexionBolsa() {
		return conexion;
	}
}