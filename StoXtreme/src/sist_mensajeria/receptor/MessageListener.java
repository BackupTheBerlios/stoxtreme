package sist_mensajeria.receptor;
import interfaz_remota.Mensaje;

public interface MessageListener {
	public void onMessage(Mensaje m);
}
