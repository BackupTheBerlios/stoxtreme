package stoxtreme.herramienta_agentes.agentes.comportamiento;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.herramienta_agentes.agentes.EstadoCartera;
import stoxtreme.herramienta_agentes.agentes.OperacionesPendientes;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.herramienta_agentes.agentes.decisiones.Espera;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public abstract class ComportamientoAgente {
	/**
	 *  Description of the Field
	 */
	protected EstadoBolsa estadoBolsa;
	/**
	 *  Description of the Field
	 */
	protected EstadoCartera estadoCartera;
	/**
	 *  Description of the Field
	 */
	protected ModeloSocial modeloSocial;
	/**
	 *  Description of the Field
	 */
	protected ModeloPsicologico modeloPsicologico;
	/**
	 *  Description of the Field
	 */
	protected OperacionesPendientes operacionesPendientes;
	/**
	 *  Description of the Field
	 */
	protected Hashtable<Integer, Decision> decisionesEsperaNotificacion;
	/**
	 *  Description of the Field
	 */
	protected int numeroCancelaciones = 0;
	/**
	 *  Description of the Field
	 */
	protected ArrayList<Decision> decisiones;
	/**
	 *  Description of the Field
	 */
	protected String estado = "Desconocido";
	private ArrayList<ComportamientoAgente> subComportamientos;


	/**
	 *  Constructor for the ComportamientoAgente object
	 */
	public ComportamientoAgente() {
		decisionesEsperaNotificacion = new Hashtable<Integer, Decision>();
		numeroCancelaciones = 0;
		decisiones = new ArrayList<Decision>();
	}


	/**
	 *  Sets the OperacionesPendientes attribute of the ComportamientoAgente
	 *  object
	 *
	 *@param  op  The new OperacionesPendientes value
	 */
	public void setOperacionesPendientes(OperacionesPendientes op) {
		this.operacionesPendientes = op;
		if (subComportamientos != null) {
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while (it.hasNext()) {
				it.next().setOperacionesPendientes(op);
			}
		}
	}


	/**
	 *  Sets the EstadoCartera attribute of the ComportamientoAgente object
	 *
	 *@param  estadoCartera  The new EstadoCartera value
	 */
	public void setEstadoCartera(EstadoCartera estadoCartera) {
		this.estadoCartera = estadoCartera;
		if (subComportamientos != null) {
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while (it.hasNext()) {
				it.next().setEstadoCartera(estadoCartera);
			}
		}
	}


	/**
	 *  Sets the ModeloPsicologico attribute of the ComportamientoAgente object
	 *
	 *@param  modeloPsicologico  The new ModeloPsicologico value
	 */
	public void setModeloPsicologico(ModeloPsicologico modeloPsicologico) {
		this.modeloPsicologico = modeloPsicologico;
		if (subComportamientos != null) {
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while (it.hasNext()) {
				it.next().setModeloPsicologico(modeloPsicologico);
			}
		}
	}


	/**
	 *  Sets the ModeloSocial attribute of the ComportamientoAgente object
	 *
	 *@param  modeloSocial  The new ModeloSocial value
	 */
	public void setModeloSocial(ModeloSocial modeloSocial) {
		this.modeloSocial = modeloSocial;
		if (subComportamientos != null) {
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while (it.hasNext()) {
				it.next().setModeloSocial(modeloSocial);
			}
		}
	}


	/**
	 *  Sets the EstadoBolsa attribute of the ComportamientoAgente object
	 *
	 *@param  estadoBolsa  The new EstadoBolsa value
	 */
	public void setEstadoBolsa(EstadoBolsa estadoBolsa) {
		this.estadoBolsa = estadoBolsa;
		if (subComportamientos != null) {
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while (it.hasNext()) {
				it.next().setEstadoBolsa(estadoBolsa);
			}
		}
	}


	/**
	 *  Gets the EstadoBolsa attribute of the ComportamientoAgente object
	 *
	 *@return    The EstadoBolsa value
	 */
	public EstadoBolsa getEstadoBolsa() {
		return estadoBolsa;
	}


	/**
	 *  Gets the Cartera attribute of the ComportamientoAgente object
	 *
	 *@return    The Cartera value
	 */
	public EstadoCartera getCartera() {
		return estadoCartera;
	}


	/**
	 *  Gets the DecisionEspera attribute of the ComportamientoAgente object
	 *
	 *@return    The DecisionEspera value
	 */
	public Decision getDecisionEspera() {
		return new Espera(modeloPsicologico.getTiempoEspera());
	}


	/**
	 *  Gets the EstadoCartera attribute of the ComportamientoAgente object
	 *
	 *@return    The EstadoCartera value
	 */
	public EstadoCartera getEstadoCartera() {
		return estadoCartera;
	}


	/**
	 *  Gets the ModeloPsicologico attribute of the ComportamientoAgente object
	 *
	 *@return    The ModeloPsicologico value
	 */
	public ModeloPsicologico getModeloPsicologico() {
		return modeloPsicologico;
	}


	/**
	 *  Gets the ModeloSocial attribute of the ComportamientoAgente object
	 *
	 *@return    The ModeloSocial value
	 */
	public ModeloSocial getModeloSocial() {
		return modeloSocial;
	}


	/**
	 *  Gets the OperacionesPendientes attribute of the ComportamientoAgente
	 *  object
	 *
	 *@return    The OperacionesPendientes value
	 */
	public OperacionesPendientes getOperacionesPendientes() {
		return operacionesPendientes;
	}


	/**
	 *  Gets the Decisiones attribute of the ComportamientoAgente object
	 *
	 *@return    The Decisiones value
	 */
	public ArrayList<Decision> getDecisiones() {
		if (subComportamientos != null) {
			Iterator<ComportamientoAgente> its = subComportamientos.iterator();
			ArrayList<Decision> decSubs = its.next().getDecisiones();
			decisiones.addAll(decSubs);
			decSubs.clear();
		}
		return decisiones;
	}


	/**
	 *  Gets the EstadoComportamiento attribute of the ComportamientoAgente
	 *  object
	 *
	 *@return    The EstadoComportamiento value
	 */
	public String getEstadoComportamiento() {
		return estado;
	}


	/**
	 *  Gets the NombreComportamiento attribute of the ComportamientoAgente
	 *  object
	 *
	 *@return    The NombreComportamiento value
	 */
	public abstract String getNombreComportamiento();


	/**
	 *  Description of the Method
	 */
	public void configureAll() {
		configure();
		if (subComportamientos != null) {
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while (it.hasNext()) {
				it.next().configureAll();
			}
		}
	}


	/**
	 *  Description of the Method
	 */
	public void generaDecisionesAll() {
		synchronized (decisiones) {
			generacionDecisiones();
			if (subComportamientos != null) {
				Iterator<ComportamientoAgente> it = subComportamientos.iterator();
				while (it.hasNext()) {
					it.next().generaDecisionesAll();
				}
			}
		}
	}


	/**
	 *  Adds a feature to the Comportamiento attribute of the
	 *  ComportamientoAgente object
	 *
	 *@param  c  The feature to be added to the Comportamiento attribute
	 */
	public void addComportamiento(ComportamientoAgente c) {
		if (subComportamientos == null) {
			subComportamientos = new ArrayList<ComportamientoAgente>();
		}
		c.setEstadoBolsa(estadoBolsa);
		c.setEstadoCartera(estadoCartera);
		c.setModeloPsicologico(modeloPsicologico);
		c.setModeloSocial(modeloSocial);
		c.setOperacionesPendientes(operacionesPendientes);
		subComportamientos.add(c);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  c  Description of Parameter
	 */
	public void quitaComportamiento(ComportamientoAgente c) {
		subComportamientos.remove(c);
		if (subComportamientos.size() == 0) {
			subComportamientos = null;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  notificacion  Description of Parameter
	 *@param  decision      Description of Parameter
	 */
	public void generaDecisionAPartirNotificacion(int notificacion, Decision decision) {
		decisionesEsperaNotificacion.put(notificacion, decision);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  notificacion  Description of Parameter
	 */
	public void notifica(int notificacion) {
		if (decisionesEsperaNotificacion.containsKey(notificacion)) {
			Decision d = decisionesEsperaNotificacion.remove(notificacion);
			synchronized (decisiones) {
				decisiones.add(d);
			}
		}
		// Avisa a todos los subcomportamientos
		if (subComportamientos != null) {
			Iterator<ComportamientoAgente> c = subComportamientos.iterator();
			while (c.hasNext()) {
				c.next().notifica(notificacion);
			}
		}
	}


	/**
	 *  Description of the Method
	 */
	public abstract void generacionDecisiones();


	/**
	 *  Description of the Method
	 */
	public abstract void configure();
}
