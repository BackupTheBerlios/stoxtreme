package stoxtreme.servidor;

/**
 *  Interfaz para tratar con variables del sistema
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface VariablesListener {
	/**
	 *  Metodo que ejecuta las acciones a especificar
	 *
	 *@param  var    Nombre de la variable
	 *@param  value  Nuevo valor
	 */
	public void cambioEstadoVariable(String var, Object value);
}
