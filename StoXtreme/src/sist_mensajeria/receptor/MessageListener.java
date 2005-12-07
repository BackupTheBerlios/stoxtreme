package sist_mensajeria.receptor;
import sist_mensajeria.mensajes.Mensaje;

public interface MessageListener {
	public void onMessage(Mensaje m);
}
