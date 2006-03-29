package stoxtreme.herramienta_agentes.agentes.decisiones;
import stoxtreme.herramienta_agentes.agentes.Agente;

public abstract class Decision implements Comparable{
	private int tEspera;
	private int tEjecucion;
	protected Agente agente;
	
	public Decision(Agente agente, int tEspera){
		this.tEspera = tEspera;
		this.agente = agente;
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
	public abstract void ejecuta(); 
}

