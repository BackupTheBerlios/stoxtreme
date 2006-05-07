package stoxtreme.cliente;

import stoxtreme.cliente.gui.ModeloOpPendientes;
import stoxtreme.interfaz_remota.Operacion;

public class OperacionesPendientes {
	private ModeloOpPendientes mOpPendientes;
	
	public OperacionesPendientes(){
		mOpPendientes = new ModeloOpPendientes();
	}

	public ModeloOpPendientes getMOpPendientes() {
		return mOpPendientes;
	}

	public void setMOpPendientes(ModeloOpPendientes opPendientes) {
		mOpPendientes = opPendientes;
	}

	public void inserta(int idOp, Operacion op) {
		mOpPendientes.insertarOperacion(op, idOp);
	}

	public Operacion dameOperacion(int idOp) {
		return mOpPendientes.getOperacion(idOp);
	}

	public void quitaOperacion(int idOp) {
		mOpPendientes.borrarOperacion(idOp);
	}

	public boolean estaPendiente(int idOp) {
		return mOpPendientes.contains(idOp);
	}
}
