package stoxtreme.interfaz_remota;

/**
 *  Interfaz del agente
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public interface IAgente {
	// Los agentes normales no se registran ni hacen login, es el usuario
	/**
	 *  Inserta el agente una operaci�n
	 *
	 *@param  id  Id de la operaci�n
	 *@param  op  Operaci�n introducida
	 *@return     devuelve el entero que ha generado la inserci�n
	 */
	public int insertaOperacion(String id, Operacion op);


	/**
	 *  Cancela una operaci�n insertada previamente
	 *
	 *@param  idOp  Id de la operaci�n
	 */
	public void cancelaOperacion(int idOp);


	/**
	 *  Modifica una operaci�n insertada previamente
	 *
	 *@param  idOp     Id de la operaci�n
	 *@param  nuevaOp  Nueva Operaci�n introducida
	 */
	public void modificaOperacion(int idOp, Operacion nuevaOp);
}
