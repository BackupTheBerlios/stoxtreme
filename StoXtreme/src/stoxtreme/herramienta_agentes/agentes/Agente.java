package stoxtreme.herramienta_agentes.agentes;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.cliente.gui.HerramientaAgentesTableModel;
import stoxtreme.herramienta_agentes.ConsolaAgentes;
import stoxtreme.herramienta_agentes.MonitorAgentes;
import stoxtreme.herramienta_agentes.agentes.comportamiento.*;
import stoxtreme.herramienta_agentes.agentes.decisiones.*;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.BuzonMensajes;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.MensajeACL;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.PlantillaMensajes;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.RecepcionMensaje;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.servidor.Servidor;

public class Agente extends Thread{
	private IDAgente ID;
	private Perceptor p;
	private BuzonMensajes buzon;
	private OperacionesPendientes opPendientes;
	private ComportamientoAgente comportamiento;
	private ModeloSocial modeloSocial;
	private ModeloPsicologico modeloPsicologico;
	private EstadoCartera estadoCartera;
	private boolean alive;
	private Stoxtreme conexionBolsa;
	private ConsolaAgentes consolaAgentes;
	private double ganancias = 0.0;
	private EstadoBolsa estadoBolsa;
	private HerramientaAgentesTableModel modeloTabla;
	private StringBuffer output = new StringBuffer();
	private String estado;
	
	public Agente (Stoxtreme conexionBolsa, EstadoBolsa estado, ConsolaAgentes consolaAgentes, HerramientaAgentesTableModel modeloTabla, ParametrosSocial ps, ParametrosPsicologicos pp){
		ID = new IDAgente();
		p = new Perceptor();
		this.modeloTabla = modeloTabla;
		
		modeloSocial = new ModeloSocial(ps);
		modeloPsicologico = new ModeloPsicologico(pp);
		estadoBolsa = estado;
		estadoCartera = new EstadoCartera();
		
		this.conexionBolsa = conexionBolsa;
		this.consolaAgentes = consolaAgentes;
		
		opPendientes = new OperacionesPendientes();
		p.setAgente(this);
		p.setOperacionesPendientes(opPendientes);
		p.setEstadoBolsa(estadoBolsa);
		p.setEstadoCartera(estadoCartera);
		
		this.alive = true;
		buzon = new BuzonMensajes(ID);
	}
	
	public void addComportamiento(ComportamientoAgente c){
		this.comportamiento = c;
		p.addGeneradorDecisiones(comportamiento);
		
		comportamiento.setModeloPsicologico(modeloPsicologico);
		comportamiento.setModeloSocial(modeloSocial);
		comportamiento.setEstadoBolsa(estadoBolsa);
		comportamiento.setEstadoCartera(estadoCartera);
		comportamiento.setOperacionesPendientes(opPendientes);
		comportamiento.configureAll();
		
		Decision d = comportamiento.getDecisionEspera();
		d.setAgente(this);
		d.insertarEnMonitor();
	}

	// Analiza los cambios en la bolsa y cambia la estrategia
	public void run(){
		while(alive){
			// Nadie le interrumpe mientras toma las
			// decisiones
			ArrayList<Decision> decisiones;
			String nuevoEstado;
			synchronized(this){
				comportamiento.generaDecisionesAll();
				decisiones = comportamiento.getDecisiones();
				
				nuevoEstado = comportamiento.getEstadoComportamiento();
			}
			
			Iterator<Decision> itDec = decisiones.iterator();
			while(itDec.hasNext()){
				Decision actual = itDec.next();
				actual.setAgente(this);
				actual.insertarEnMonitor();
				imprime(actual);
			}
			decisiones.clear();
			Decision espera = comportamiento.getDecisionEspera();
			espera.setAgente(this);
			espera.insertarEnMonitor();
			imprime(espera);
			
			if(!nuevoEstado.equals(estado)){
				estado = nuevoEstado;
				fireUpdateTable();
			}
			
			try {
				synchronized(this){
					wait();
				}
			} catch (InterruptedException e) {
				alive = false;
				e.printStackTrace();
			}
		}
	}
	
	
	public void setAlive(boolean estado){
		alive = estado;
	}
	public void insertarOperacion(Operacion o){
		if(alive){
			String empresa = o.getEmpresa();
			int tipo = o.getTipoOp();
			int numAcciones = o.getCantidad();
			double precio = o.getPrecio();

			try {
				int i = conexionBolsa.insertarOperacion(this.ID.toString(), o);
				if(i!= -1){
					opPendientes.insertaOperacionPendiente(i, empresa, tipo, numAcciones, precio);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void cancelarOperacion(int operacion){
		if(alive){
			try {
				conexionBolsa.cancelarOperacion(this.ID.toString(),operacion);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getIDString(){
		return ID.toString();
	}
	
	public void abandonarModelo(){
		if(alive){
			setAlive(false);
			modeloTabla.eliminaAgente(this);
		}
	}
	
	public void enviarMensaje(MensajeACL m){
		m.setSender(ID);
		buzon.send(m);
	}

	public void imprime(Decision decision) {
		consolaAgentes.insertarAccion(getIDString(), decision.toString());
		output.append(decision.toString());
		output.append('\n');
	}

	public Perceptor getPerceptor() {
		return p;
	}
	
	public String getEstado(){
		return estado;
	}
	
	public String getStringComportamiento(){
		return this.comportamiento.getNombreComportamiento();
	}
	
	public double getGanancias(){
		return this.ganancias;
	}

	public void altaServicio(String servicio) {
		buzon.altaServicio(ID, servicio);
	}

	public void bajaServicio(String servicio) {
		buzon.bajaServicio(ID, servicio);
	}

	public IDAgente getIDAgente() {
		return ID;
	}

	public void recibeMensaje(int n, PlantillaMensajes p2, RecepcionMensaje r) {
		buzon.receive(n, p2, r);
	}

	public void requestServicio(String servicio, ArrayList<IDAgente> proveedores) {
		 ArrayList<IDAgente> lista = buzon.getAgentesServicio(servicio);
		 for(int i=0; i<lista.size(); i++){
			 proveedores.add(lista.get(i));
		 }
	}

	public ModeloSocial getModeloSocial() {
		return this.modeloSocial;
	}

	public void decrementaBeneficio(double d) {
		ganancias-=d;
		fireUpdateTable();
	}

	public void incrementaBeneficio(double d) {
		ganancias+=d;
		fireUpdateTable();
	}
	
	public void fireUpdateTable(){
		modeloTabla.cambioEnAgente(getIDString());
	}
	
	public StringBuffer getOutput(){
		return output;
	}
}
