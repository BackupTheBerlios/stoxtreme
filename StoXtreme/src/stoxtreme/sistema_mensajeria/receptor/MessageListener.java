package stoxtreme.sistema_mensajeria.receptor;
import stoxtreme.interfaz_remota.Mensaje;

public interface MessageListener {
	public void onMessage(Mensaje m);
}
