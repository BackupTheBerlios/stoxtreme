package stoxtreme.interfaz_remota;

/**
 *  Interfaz de usuario
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface IUsuario {
	/**
	 *  Premite registrar un nuevo usuario
	 *
	 *@param  nUsuario  Nombre del nuevo usuario
	 *@param  pswd      Contraseña del nuevo usuario
	 *@return           devuelve si se ha podido realizar el registro
	 */
	public boolean registro(String nUsuario, String pswd);


	/**
	 *  Premite loguear un usuario ya registrado
	 *
	 *@param  nUsuario  Nombre del usuario
	 *@param  pswd      Contraseña del usuario
	 *@return           devuelve si se ha podido realizar la conexión
	 */
	public boolean login(String nUsuario, String pswd);
}
