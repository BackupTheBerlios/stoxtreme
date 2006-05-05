package stoxtreme.herramienta_agentes;

public interface ListenerNotificador {
	//public void onCambioPrecioAccion(String empresa, double nuevoPrecio);
	public void onNotificacionOp(int idOp, int cantidad, double precio);
	public void onCancelacionOp(int idOp);
}
