package stoxtreme.servidor;

/**
 *  Interfaz para los objetos que escuchan al reloj
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface RelojListener {
	/**
	 *  Metodo que ejecuta las acciones correspondientes en cada paso
	 */
	public void paso();
}
