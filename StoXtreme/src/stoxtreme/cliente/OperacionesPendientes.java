package stoxtreme.cliente;

import stoxtreme.cliente.gui.ModeloOpPendientes;
import stoxtreme.interfaz_remota.Operacion;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
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
	 *  Inserta operación pendiente
	 *
	 *@param  idOp  Id de la operación
	 *@param  op    Detalle de la operación
	 */
	public void inserta(int idOp, Operacion op) {
		mOpPendientes.insertarOperacion(op, idOp);
	}


	/**
	 *  Devuelve los detalles de la operación de un determinado id
	 *
	 *@param  idOp  Id de la operación deseada
	 *@return       Devuelve los detalles de la operación
	 */
	public Operacion dameOperacion(int idOp) {
		return mOpPendientes.getOperacion(idOp);
	}


	/**
	 *  Elimina una operación
	 *
	 *@param  idOp  Id de la operación
	 */
	public void quitaOperacion(int idOp) {
		mOpPendientes.borrarOperacion(idOp);
	}


	/**
	 *  Determina si una operación está pendiente o ya ha sido ejecutada
	 *
	 *@param  idOp  Id de la operacion a evaluar
	 *@return       Nos confirma su estado
	 */
	public boolean estaPendiente(int idOp) {
		return mOpPendientes.contains(idOp);
	}
}
