package stoxtreme.herramienta_agentes.agentes;

import java.util.ArrayList;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.herramienta_agentes.ListenerNotificador;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.interfaz_remota.Operacion;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Perceptor implements ListenerNotificador {
	private EstadoBolsa estadoBolsa = null;
	private EstadoCartera estadoCartera = null;
	private OperacionesPendientes opPendientes = null;
	private ArrayList<ComportamientoAgente> gDecisiones = null;
	private Agente agente;


	/**
	 *  Constructor for the Perceptor object
	 */
	public Perceptor() {
		gDecisiones = new ArrayList<ComportamientoAgente>();
	}


	/**
	 *  Sets the EstadoBolsa attribute of the Perceptor object
	 *
	 *@param  estado  The new EstadoBolsa value
	 */
	public void setEstadoBolsa(EstadoBolsa estado) {
		estadoBolsa = estado;
	}


	/**
	 *  Sets the EstadoCartera attribute of the Perceptor object
	 *
	 *@param  estado  The new EstadoCartera value
	 */
	public void setEstadoCartera(EstadoCartera estado) {
		estadoCartera = estado;
	}


	/**
	 *  Sets the OperacionesPendientes attribute of the Perceptor object
	 *
	 *@param  opPendientes  The new OperacionesPendientes value
	 */
	public void setOperacionesPendientes(OperacionesPendientes opPendientes) {
		this.opPendientes = opPendientes;
	}


	/**
	 *  Sets the Agente attribute of the Perceptor object
	 *
	 *@param  agente  The new Agente value
	 */
	public void setAgente(Agente agente) {
		this.agente = agente;
	}


	/**
	 *  Adds a feature to the GeneradorDecisiones attribute of the Perceptor
	 *  object
	 *
	 *@param  gDeci  The feature to be added to the GeneradorDecisiones attribute
	 */
	public void addGeneradorDecisiones(ComportamientoAgente gDeci) {
		this.gDecisiones.add(gDeci);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOp      Description of Parameter
	 *@param  cantidad  Description of Parameter
	 *@param  precio    Description of Parameter
	 */
	public void onNotificacionOp(int idOp, int cantidad, double precio) {
		if (estadoBolsa != null && estadoCartera != null &&
				opPendientes != null && gDecisiones.size() != 0) {
			String empresa = opPendientes.getEmpresaOperacion(idOp);
			int tipoOp = opPendientes.getTipoOperacion(idOp);

			if (empresa == null) {
				//System.err.println("EMPRESA NULL!!! "+ idOp+" "+cantidad+" "+precio);
				return;
			}

			switch (tipoOp) {
				case Operacion.COMPRA:
					estadoCartera.insertaAcciones(empresa, cantidad);
					agente.decrementaBeneficio(cantidad * precio);
					break;
				case Operacion.VENTA:
					estadoCartera.quitaAcciones(empresa, cantidad);
					agente.incrementaBeneficio(cantidad * precio);
					break;
			}

			opPendientes.restaAcciones(idOp, cantidad);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOp  Description of Parameter
	 */
	public void onCancelacionOp(int idOp) {
		opPendientes.cancelaOp(idOp);
		for (int i = 0; i < gDecisiones.size(); i++) {
			gDecisiones.get(i).notifica(-idOp);
		}
	}
}
