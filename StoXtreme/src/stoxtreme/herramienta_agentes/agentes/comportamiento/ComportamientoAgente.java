package stoxtreme.herramienta_agentes.agentes.comportamiento;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.herramienta_agentes.agentes.EstadoCartera;
import stoxtreme.herramienta_agentes.agentes.OperacionesPendientes;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.herramienta_agentes.agentes.decisiones.Espera;

public abstract class ComportamientoAgente {
	protected EstadoBolsa estadoBolsa;
	protected EstadoCartera estadoCartera;
	protected ModeloSocial modeloSocial;
	protected ModeloPsicologico modeloPsicologico;
	protected OperacionesPendientes operacionesPendientes;
	protected Hashtable<Integer, Decision> decisionesEsperaNotificacion;
	protected int numeroCancelaciones = 0;
	private ArrayList<ComportamientoAgente> subComportamientos;
	protected ArrayList<Decision> decisiones;
	protected String estado = "Desconocido";
	
	public ComportamientoAgente() {
		decisionesEsperaNotificacion = new Hashtable<Integer, Decision>();
		numeroCancelaciones = 0;
		decisiones = new ArrayList<Decision>();
	}

	public EstadoBolsa getEstadoBolsa() {
		return estadoBolsa;
	}

	public EstadoCartera getCartera() {
		return estadoCartera;
	}

	public void setOperacionesPendientes(OperacionesPendientes op) {
		this.operacionesPendientes = op;
		if(subComportamientos != null){
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while(it.hasNext()){
				it.next().setOperacionesPendientes(op);
			}
		}
	}
	
	public void configureAll(){
		configure();
		if(subComportamientos != null){
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while(it.hasNext()){
				it.next().configureAll();
			}
		}
	}
	public void generaDecisionesAll(){
		synchronized(decisiones){
			generacionDecisiones();
			if(subComportamientos != null){
				Iterator<ComportamientoAgente> it = subComportamientos.iterator();
				while(it.hasNext()){
					it.next().generaDecisionesAll();
				}
			}
		}
	}
	
	public void addComportamiento(ComportamientoAgente c){
		if(subComportamientos == null)
			subComportamientos = new ArrayList<ComportamientoAgente>();
		c.setEstadoBolsa(estadoBolsa);
		c.setEstadoCartera(estadoCartera);
		c.setModeloPsicologico(modeloPsicologico);
		c.setModeloSocial(modeloSocial);
		c.setOperacionesPendientes(operacionesPendientes);
		subComportamientos.add(c);
	}
	
	public void quitaComportamiento(ComportamientoAgente c){
		subComportamientos.remove(c);
		if(subComportamientos.size()==0)
			subComportamientos = null;
	}
	
	public void generaDecisionAPartirNotificacion(int notificacion, Decision decision) {
		decisionesEsperaNotificacion.put(notificacion, decision);
	}

	public void notifica(int notificacion){
		if(decisionesEsperaNotificacion.containsKey(notificacion)){
			Decision d = decisionesEsperaNotificacion.remove(notificacion);
			synchronized(decisiones){
				decisiones.add(d);
			}
		}
		// Avisa a todos los subcomportamientos
		if(subComportamientos!= null){
			Iterator<ComportamientoAgente> c = subComportamientos.iterator();
			while(c.hasNext()){
				c.next().notifica(notificacion);
			}
		}
	}

	public Decision getDecisionEspera() {
		return new Espera(modeloPsicologico.getTiempoEspera());
	}

	public EstadoCartera getEstadoCartera() {
		return estadoCartera;
	}

	public void setEstadoCartera(EstadoCartera estadoCartera) {
		this.estadoCartera = estadoCartera;
		if(subComportamientos != null){
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while(it.hasNext()){
				it.next().setEstadoCartera(estadoCartera);
			}
		}
	}

	public ModeloPsicologico getModeloPsicologico() {
		return modeloPsicologico;
	}

	public void setModeloPsicologico(ModeloPsicologico modeloPsicologico) {
		this.modeloPsicologico = modeloPsicologico;
		if(subComportamientos != null){
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while(it.hasNext()){
				it.next().setModeloPsicologico(modeloPsicologico);
			}
		}
	}

	public ModeloSocial getModeloSocial() {
		return modeloSocial;
	}

	public void setModeloSocial(ModeloSocial modeloSocial) {
		this.modeloSocial = modeloSocial;
		if(subComportamientos != null){
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while(it.hasNext()){
				it.next().setModeloSocial(modeloSocial);
			}
		}
	}

	public OperacionesPendientes getOperacionesPendientes() {
		return operacionesPendientes;
	}

	public void setEstadoBolsa(EstadoBolsa estadoBolsa) {
		this.estadoBolsa = estadoBolsa;
		if(subComportamientos != null){
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while(it.hasNext()){
				it.next().setEstadoBolsa(estadoBolsa);
			}
		}
	}
	
	public ArrayList<Decision> getDecisiones(){
		if(subComportamientos != null){
			Iterator<ComportamientoAgente> its = subComportamientos.iterator();
			ArrayList<Decision> decSubs = its.next().getDecisiones();
			decisiones.addAll(decSubs);
			decSubs.clear();
		}
		return decisiones;
	}
	
	public String getEstadoComportamiento(){
		return estado;
	}

	abstract public void generacionDecisiones();
	abstract public void configure();
	abstract public String getNombreComportamiento();
}
	