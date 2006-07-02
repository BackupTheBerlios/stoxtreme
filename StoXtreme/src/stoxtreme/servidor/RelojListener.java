package stoxtreme.servidor;

/**
 *  Interfaz para los objetos que escuchan al reloj
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface RelojListener {
	/**
	 *  Metodo que ejecuta las acciones correspondientes en cada paso
	 */
	public void paso();
}
