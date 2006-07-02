package stoxtreme.herramienta_agentes;
/**
 *  Interfaz para los observadores del timer sean informados de un nuevo
 *  tick
 *
 *  @author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
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
