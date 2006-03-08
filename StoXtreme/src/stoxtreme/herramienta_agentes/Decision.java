package stoxtreme.herramienta_agentes;

public interface Decision {
	public String getIDAccion();
	public int getTiempoEjecucion();
	public void ejecutar();
}
