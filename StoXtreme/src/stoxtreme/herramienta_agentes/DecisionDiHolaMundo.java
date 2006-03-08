package stoxtreme.herramienta_agentes;

import java.util.Comparator;

public class DecisionDiHolaMundo implements Decision{
	private Agente a;
	private int tEjecucion;
	
	public DecisionDiHolaMundo(Agente a, int tEjec){
		this.tEjecucion = tEjec;
	}
	
	public String getIDAccion() {
		return null;
	}

	public int getTiempoEjecucion() {
		return tEjecucion;
	}

	public void ejecutar() {
		// TODO Auto-generated method stub
	}
	
	public String toString(){
		return Integer.toString(tEjecucion);
	}
}
