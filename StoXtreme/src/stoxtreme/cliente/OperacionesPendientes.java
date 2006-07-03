package stoxtreme.cliente;

import stoxtreme.cliente.gui.ModeloOpPendientes;
import stoxtreme.interfaz_remota.Operacion;

/**
 *  Description of the Class
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public class OperacionesPendientes {
	private ModeloOpPendientes mOpPendientes;


	/**
	 *  Constructor de OperacionesPendientes
	 */
	public OperacionesPendientes() {
		mOpPendientes = new ModeloOpPendientes();
	}


	/**
	 *  sustituye MOpPendientes por nuevas operaciones pendientes
	 *
	 *@param  opPendientes  Nuevo valor de MOpPendientes
	 */
	public void setMOpPendientes(ModeloOpPendientes opPendientes) {
		mOpPendientes = opPendientes;
	}


	/**
	 *  Obtiene MOpPendientes
	 *
	 *@return    Valor de MOpPendientes
	 */
	public ModeloOpPendientes getMOpPendientes() {
		return mOpPendientes;
	}


	/**
	 *  Inserta operaci�n pendiente
	 *
	 *@param  idOp  Id de la operaci�n
	 *@param  op    Detalle de la operaci�n
	 */
	public void inserta(int idOp, Operacion op) {
		mOpPendientes.insertarOperacion(op, idOp);
	}


	/**
	 *  Devuelve los detalles de la operaci�n de un determinado id
	 *
	 *@param  idOp  Id de la operaci�n deseada
	 *@return       Devuelve los detalles de la operaci�n
	 */
	public Operacion dameOperacion(int idOp) {
		return mOpPendientes.getOperacion(idOp);
	}


	/**
	 *  Elimina una operaci�n
	 *
	 *@param  idOp  Id de la operaci�n
	 */
	public void quitaOperacion(int idOp) {
		mOpPendientes.borrarOperacion(idOp);
	}


	/**
	 *  Determina si una operaci�n est� pendiente o ya ha sido ejecutada
	 *
	 *@param  idOp  Id de la operacion a evaluar
	 *@return       Nos confirma su estado
	 */
	public boolean estaPendiente(int idOp) {
		return mOpPendientes.contains(idOp);
	}
}
