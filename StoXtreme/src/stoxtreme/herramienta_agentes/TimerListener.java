package stoxtreme.herramienta_agentes;
/**
 *  Interfaz para los observadores del timer sean informados de un nuevo
 *  tick
 *
 *  @author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface TimerListener {
	/**
	 * Recepcion de un tick de reloj
	 *
	 * @param  tick  tick de tiempo recibido
	 * 
	 */
	public void onTick(int tick);
}
