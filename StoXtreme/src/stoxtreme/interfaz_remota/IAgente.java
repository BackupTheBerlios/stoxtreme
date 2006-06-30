package stoxtreme.interfaz_remota;

/**
 *  Description of the Interface
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface IAgente {
	//public boolean registro(String nUsuario, String pswd);
	//public boolean login(String nUsuario, String pswd);
	// Los agentes normales no se registran ni hacen login, es el usuario
	/**
	 *  Description of the Method
	 *
	 *@param  id  Description of Parameter
	 *@param  op  Description of Parameter
	 *@return     Description of the Returned Value
	 */
	public int insertaOperacion(String id, Operacion op);


	/**
	 *  Description of the Method
	 *
	 *@param  idOp  Description of Parameter
	 */
	public void cancelaOperacion(int idOp);


	/**
	 *  Description of the Method
	 *
	 *@param  idOp     Description of Parameter
	 *@param  nuevaOp  Description of Parameter
	 */
	public void modificaOperacion(int idOp, Operacion nuevaOp);
}
