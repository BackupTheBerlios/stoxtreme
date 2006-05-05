package stoxtreme.herramienta_agentes.agentes.decisiones;
import stoxtreme.herramienta_agentes.MonitorAgentes;
import stoxtreme.herramienta_agentes.agentes.Agente;

public abstract class Decision implements Comparable{
	private static MonitorAgentes _monitor;
	public static void setMonitor(MonitorAgentes monitor){
		_monitor = monitor;
	}
	
	private int tEspera;
	private int tEjecucion;
	protected Agente agente;
	
	public Decision(){
		this.tEspera = 1;
	}
	
	public Decision(int tEspera){
		this.tEspera = tEspera;
	}
	
	public int compareTo(Object o){
		Decision d2 = (Decision)o;
		return getTiempoEjecucion() - d2.getTiempoEjecucion();
	}
	
	public int getTiempoEjecucion(){
		return tEjecucion;
	}
	
	public void addTActual(int ciclo){
		this.tEjecucion = tEspera + ciclo;
	}
	
	public String toString(){
		return "Decision: " + Integer.toString(tEjecucion);
	}
	
	public void insertarEnMonitor(){
		_monitor.addDecision(this);
	}
	
	public void setAgente(Agente agente){
		this.agente = agente;
	}
	public abstract void ejecuta(); 
}

