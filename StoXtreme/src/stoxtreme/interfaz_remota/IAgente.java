package stoxtreme.interfaz_remota;

/**
 *  Interfaz del agente
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public interface IAgente {
	// Los agentes normales no se registran ni hacen login, es el usuario
	/**
	 *  Inserta el agente una operación
	 *
	 *@param  id  Id de la operación
	 *@param  op  Operación introducida
	 *@return     devuelve el entero que ha generado la inserción
	 */
	public int insertaOperacion(String id, Operacion op);


	/**
	 *  Cancela una operación insertada previamente
	 *
	 *@param  idOp  Id de la operación
	 */
	public void cancelaOperacion(int idOp);


	/**
	 *  Modifica una operación insertada previamente
	 *
	 *@param  idOp     Id de la operación
	 *@param  nuevaOp  Nueva Operación introducida
	 */
	public void modificaOperacion(int idOp, Operacion nuevaOp);
}
