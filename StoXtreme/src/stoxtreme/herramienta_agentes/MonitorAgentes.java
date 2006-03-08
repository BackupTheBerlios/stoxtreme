package stoxtreme.herramienta_agentes;

import java.util.Comparator;
import java.util.PriorityQueue;

// Monitor para la interaccion entre la herramienta de los agentes y los
// propios agentes
public class MonitorAgentes extends Thread{
	private PriorityQueue<Decision> acciones;	
	
	public void run(){
		
	}
	
	public synchronized void insertarAccion(Decision accion){
		
	}
	
	public synchronized void eliminarAccino(String idAccion){
		
	}
	
	public static ComparatorDecisiones comparador = new ComparatorDecisiones();
	private static class ComparatorDecisiones implements Comparator<Decision>{
		public int compare(Decision decision1, Decision decision2) {
			return decision1.getTiempoEjecucion() - decision2.getTiempoEjecucion();
		}
	}
	
	public static void main(String[] args){
		PriorityQueue<Decision> pq1 = new PriorityQueue<Decision>(10, comparador);
		pq1.add(new DecisionDiHolaMundo(null, 20));
		pq1.add(new DecisionDiHolaMundo(null, 15));
		pq1.add(new DecisionDiHolaMundo(null, 10));
		pq1.add(new DecisionDiHolaMundo(null, 05));
		pq1.add(new DecisionDiHolaMundo(null, 15));
		pq1.add(new DecisionDiHolaMundo(null, 20));
		pq1.add(new DecisionDiHolaMundo(null, 25));
		pq1.add(new DecisionDiHolaMundo(null, 30));
		System.out.println(pq1);
	}
}
