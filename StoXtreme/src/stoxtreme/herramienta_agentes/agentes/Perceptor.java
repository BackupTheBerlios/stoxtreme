package stoxtreme.herramienta_agentes.agentes;

import java.util.ArrayList;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.herramienta_agentes.ListenerNotificador;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.interfaz_remota.Operacion;

/**
 *  Objeto de percepcion de los agentes.
 *  Esta pendiente de los cambios tanto en la bolsa como en la cartera
 *  para notificar estos cambios en las estructuras internas del agente.
 *
 *  @author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Perceptor implements ListenerNotificador {
	/**
	 * Estado global de la bolsa
	 */
	private EstadoBolsa estadoBolsa = null;
	
	/**
	 * Estado de la cartera del agente
	 */
	private EstadoCartera estadoCartera = null;
	
	/**
	 * Operaciones pendientes de notificacion
	 */
	private OperacionesPendientes opPendientes = null;
	
	/**
	 * Comportamientos a los que debe avisar el notificador
	 */
	private ArrayList<ComportamientoAgente> gDecisiones = null;
	
	/**
	 * Agente al que esta asociado el perceptor
	 */
	private Agente agente;

	/**
	 *  Constructor del objeto perceptor
	 */
	public Perceptor() {
		gDecisiones = new ArrayList<ComportamientoAgente>();
	}


	/**
	 *  Inserta el estado de la bolsa en el objeto de percepcion
	 *
	 *  @param  estado  El estado global de la bolsa
	 */
	public void setEstadoBolsa(EstadoBolsa estado) {
		estadoBolsa = estado;
	}

	/**
	 *  Inserta el estado de la cartera correspondiente con el perceptor
	 *
	 *  @param  estado  El estado de la cartera del agente
	 */
	public void setEstadoCartera(EstadoCartera estado) {
		estadoCartera = estado;
	}

	/**
	 *  Inserta las operaciones pendientes del agente
	 *
	 *  @param  opPendientes  Las operaciones pendientes
	 */
	public void setOperacionesPendientes(OperacionesPendientes opPendientes) {
		this.opPendientes = opPendientes;
	}

	/**
	 *  Sets the Agente attribute of the Perceptor object
	 *
	 *  @param  agente  The new Agente value
	 */
	public void setAgente(Agente agente) {
		this.agente = agente;
	}


	/**
	 *  Inserta un nuevo comportamiento dentro del perceptor
	 *
	 *  @param  gDeci  Comportamiento que queremos insertar
	 */
	public void addGeneradorDecisiones(ComportamientoAgente gDeci) {
		this.gDecisiones.add(gDeci);
	}

	/**
	 *  Metodo para la notificacion de una realizacion de una operacion
	 *
	 *  @param  idOp      Identificador de la operacion realizada
	 *  @param  cantidad  Cantidad de acciones cruzadas en la operacion
	 *  @param  precio    Precio al que se han cruzado las operaciones
	 */
	public void onNotificacionOp(int idOp, int cantidad, double precio) {
		if (estadoBolsa != null && estadoCartera != null &&
				opPendientes != null && gDecisiones.size() != 0) {
			String empresa = opPendientes.getEmpresaOperacion(idOp);
			int tipoOp = opPendientes.getTipoOperacion(idOp);

			if (empresa == null) {
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
	 *  Metodo invocado en la cancelacion de una operacion
	 *
	 *  @param  idOp  Identificador de la operación cancelada
	 */
	public void onCancelacionOp(int idOp) {
		opPendientes.cancelaOp(idOp);
		for (int i = 0; i < gDecisiones.size(); i++) {
			gDecisiones.get(i).notifica(-idOp);
		}
	}
}
