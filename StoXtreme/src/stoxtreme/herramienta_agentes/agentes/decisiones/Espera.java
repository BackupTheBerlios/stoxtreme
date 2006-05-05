package stoxtreme.herramienta_agentes.agentes.decisiones;

import stoxtreme.herramienta_agentes.agentes.Agente;

public class Espera extends Decision{

	public Espera(int tiempoEspera){
		super(tiempoEspera);
	}
	
	public void ejecuta() {
		synchronized(agente){
			agente.notify();
		}
	}
	
	public String toString(){
		return "Espero hasta el ciclo " + getTiempoEjecucion();
	}
}
