package stoxtreme.herramienta_agentes;

public interface ConexionBolsa {
	public int insertarOperacion(String id, Operacion o);
	public void cancelaOperacion(int operacion);
	public void addNotificadorListener(String id, ListenerNotificador n);
}
