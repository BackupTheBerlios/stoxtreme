package stoxtreme.interfaz_remota;

public interface StoxtremeMensajes {
	public Mensaje getSiguienteMensaje(String id);
	public void enviaMensaje(Mensaje m);
}
