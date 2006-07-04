package stoxtreme.interfaz_remota;

/**
 *  Interfaz de usuario
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface IUsuario {
	/**
	 *  Premite registrar un nuevo usuario
	 *
	 *@param  nUsuario  Nombre del nuevo usuario
	 *@param  pswd      Contrase�a del nuevo usuario
	 *@return           devuelve si se ha podido realizar el registro
	 */
	public boolean registro(String nUsuario, String pswd);


	/**
	 *  Premite loguear un usuario ya registrado
	 *
	 *@param  nUsuario  Nombre del usuario
	 *@param  pswd      Contrase�a del usuario
	 *@return           devuelve si se ha podido realizar la conexi�n
	 */
	public boolean login(String nUsuario, String pswd);
}
