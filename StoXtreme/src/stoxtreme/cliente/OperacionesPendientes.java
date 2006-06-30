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
	 *  Constructor for the OperacionesPendientes object
	 */
	public OperacionesPendientes() {
		mOpPendientes = new ModeloOpPendientes();
	}


	/**
	 *  Sets the MOpPendientes attribute of the OperacionesPendientes object
	 *
	 *@param  opPendientes  The new MOpPendientes value
	 */
	public void setMOpPendientes(ModeloOpPendientes opPendientes) {
		mOpPendientes = opPendientes;
	}


	/**
	 *  Gets the MOpPendientes attribute of the OperacionesPendientes object
	 *
	 *@return    The MOpPendientes value
	 */
	public ModeloOpPendientes getMOpPendientes() {
		return mOpPendientes;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOp  Description of Parameter
	 *@param  op    Description of Parameter
	 */
	public void inserta(int idOp, Operacion op) {
		mOpPendientes.insertarOperacion(op, idOp);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOp  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Operacion dameOperacion(int idOp) {
		return mOpPendientes.getOperacion(idOp);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOp  Description of Parameter
	 */
	public void quitaOperacion(int idOp) {
		mOpPendientes.borrarOperacion(idOp);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOp  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public boolean estaPendiente(int idOp) {
		return mOpPendientes.contains(idOp);
	}
}
