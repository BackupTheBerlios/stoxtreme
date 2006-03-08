package stoxtreme.herramienta_agentes;

public abstract class Decision {
	abstract public String getIDAccion();
	abstract public double getTiempoEjecucion();
	abstract public void ejecutar();
}
