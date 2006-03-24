package stoxtreme.herramienta_agentes.agentes.comportamiento;

import java.util.ArrayList;
import java.util.Hashtable;

import stoxtreme.herramienta_agentes.EstadoBolsa;
import stoxtreme.herramienta_agentes.Operacion;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.OperacionesPendientes;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.decisiones.*;


public abstract class ComportamientoAgente {
	protected Agente agente;
	protected EstadoBolsa estadoBolsa;
	protected EstadoCartera estadoCartera;
	protected ModeloSocial modeloSocial;
	protected ModeloPsicologico modeloPsicologico;
	protected OperacionesPendientes operacionesPendientes;
	protected Hashtable<Integer, Decision> decisionesEsperaNotificacion;
	protected ArrayList<Decision> listaDecisiones;
	protected int numeroCancelaciones = 0;
	
	public ComportamientoAgente(EstadoBolsa estadoBolsa, ParametrosSocial ps, ParametrosPsicologicos pp) {
		modeloSocial = new ModeloSocial(ps);
		modeloPsicologico = new ModeloPsicologico(pp);
		this.estadoBolsa = estadoBolsa;
		estadoCartera = new EstadoCartera();
		decisionesEsperaNotificacion = new Hashtable<Integer, Decision>();
		listaDecisiones = new ArrayList<Decision>();
		numeroCancelaciones = 0;
	}

	public EstadoBolsa getEstadoBolsa() {
		return estadoBolsa;
	}

	public EstadoCartera getCartera() {
		return estadoCartera;
	}

	public void setOperacionesPendientes(OperacionesPendientes op) {
		this.operacionesPendientes = op;
	}
	
	abstract public ArrayList<Decision> tomaDecisiones();

	public void generaDecisionAPartirNotificacion(int notificacion, Decision decision) {
		decisionesEsperaNotificacion.put(notificacion, decision);
	}

	public void notifica(int notificacion){
		if(decisionesEsperaNotificacion.containsKey(notificacion)){
			Decision d = decisionesEsperaNotificacion.remove(notificacion);
			listaDecisiones.add(d);
		}
	}

	public void setAgente(Agente agente) {
		this.agente = agente;
	}

	public int getTiempoEspera() {
		return modeloPsicologico.getTiempoEspera();
	}
}