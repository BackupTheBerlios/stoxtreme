package stoxtreme.interfaz_remota;

/**
 *  Description of the Interface
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface IUsuario {
	/**
	 *  Description of the Method
	 *
	 *@param  nUsuario  Description of Parameter
	 *@param  pswd      Description of Parameter
	 *@return           Description of the Returned Value
	 */
	public boolean registro(String nUsuario, String pswd);


	/**
	 *  Description of the Method
	 *
	 *@param  nUsuario  Description of Parameter
	 *@param  pswd      Description of Parameter
	 *@return           Description of the Returned Value
	 */
	public boolean login(String nUsuario, String pswd);
}
