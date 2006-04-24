package stoxtreme.herramienta_agentes.agentes.comportamiento;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import stoxtreme.herramienta_agentes.EstadoBolsa;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.OperacionesPendientes;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.decisiones.*;


public abstract class ComportamientoAgente {
	protected EstadoBolsa estadoBolsa;
	protected EstadoCartera estadoCartera;
	protected ModeloSocial modeloSocial;
	protected ModeloPsicologico modeloPsicologico;
	protected OperacionesPendientes operacionesPendientes;
	protected Hashtable<Integer, Decision> decisionesEsperaNotificacion;
	protected ArrayList<Decision> listaDecisiones;
	protected int numeroCancelaciones = 0;
	private ArrayList<ComportamientoAgente> subComportamientos;
	
	public ComportamientoAgente() {
		decisionesEsperaNotificacion = new Hashtable<Integer, Decision>();
		listaDecisiones = new ArrayList<Decision>();
		numeroCancelaciones = 0;
		subComportamientos = new ArrayList<ComportamientoAgente>();
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
	
	public ArrayList<Decision> generaDecisiones(){
		ArrayList<Decision> d = generacionDecisiones();
		if(subComportamientos != null){
			Iterator<ComportamientoAgente> it = subComportamientos.iterator();
			while(it.hasNext()){
				d.addAll(it.next().generaDecisiones());
			}
		}
		return d;
	}
	
	abstract public ArrayList<Decision> generacionDecisiones();
	
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
			listaDecisiones.add(d);
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
}