package stoxtreme.herramienta_agentes;

public interface ListenerNotificador {
	public static final int OPERACION_EXITO = 1;
	public static final int OPERACION_ERROR = 2;
	
	public void onCambioPrecioAccion(String empresa, double nuevoPrecio);
	public void onNotificacionOp(int idOp, int cantidad, double precio);
	public void onCancelacionOp(int idOp);
}
