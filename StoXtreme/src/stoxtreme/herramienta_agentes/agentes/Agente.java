package stoxtreme.herramienta_agentes.agentes;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.herramienta_agentes.ConsolaAgentes;
import stoxtreme.herramienta_agentes.MonitorAgentes;
import stoxtreme.herramienta_agentes.agentes.comportamiento.*;
import stoxtreme.herramienta_agentes.agentes.decisiones.*;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.BuzonMensajes;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.Mensaje;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.servidor.Servidor;
// Primer prototipo de agente broker.
// Inicialmente se le introduce el parametro correspondiente al tiempo de espera
// El broker, esperará a que transcurran tiempoEspera ciclos de ejecución y cuando
// supere el valor efectuará una acción aleatoria, dependiendo de la acción que hubiera
// realizado antes y siempre con el precio actual de mercado.
// PreStep - Analiza el entorno y guarda los datos necesrios
// Step - Con los datos anteriores modifica sus intenciones
// PostStep - Introduce la operación en el sistema correspondiendo con sus intenciones

public class Agente extends Thread{
	private IDAgente ID;
	private Perceptor p;
	private BuzonMensajes buzon;
	private OperacionesPendientes opPendientes;
	private ComportamientoAgente comportamiento;
	private BuzonMensajes buzonMensajes;
	private ModeloSocial modeloSocial;
	private ModeloPsicologico modeloPsicologico;
	private EstadoCartera estadoCartera;
	private boolean alive;
	private Stoxtreme conexionBolsa;
	private ConsolaAgentes consolaAgentes;
	private double ganancias = 0.0;
	private EstadoBolsa estadoBolsa;
	
	public Agente (Stoxtreme conexionBolsa, EstadoBolsa estado, ConsolaAgentes consolaAgentes, ParametrosSocial ps, ParametrosPsicologicos pp){
		ID = new IDAgente();
		p = new Perceptor();
		
		modeloSocial = new ModeloSocial(ps);
		modeloPsicologico = new ModeloPsicologico(pp);
		estadoBolsa = estado;
		estadoCartera = new EstadoCartera();
		
		this.conexionBolsa = conexionBolsa;
		this.consolaAgentes = consolaAgentes;
//		monitor.addNotificadorListener(ID.toString(), p);
		
		opPendientes = new OperacionesPendientes();
		p.setOperacionesPendientes(opPendientes);
//		p.setEstadoBolsa(estadoBolsa);
		p.setEstadoCartera(estadoCartera);
		
		this.alive = true;
		buzon = new BuzonMensajes(ID);
	}
	
	public void addComportamiento(ComportamientoAgente c){
		this.comportamiento = c;
		p.addGeneradorDecisiones(comportamiento);
		
		comportamiento.setModeloPsicologico(modeloPsicologico);
		comportamiento.setModeloSocial(modeloSocial);
		//comportamiento.setEstadoBolsa(estadoBolsa);
		comportamiento.setEstadoCartera(estadoCartera);
		comportamiento.setOperacionesPendientes(opPendientes);
		
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
			synchronized(this){
				decisiones = comportamiento.generacionDecisiones();
				Decision espera = comportamiento.getDecisionEspera();
				decisiones.add(espera);
			}
			
			Iterator<Decision> itDec = decisiones.iterator();
			while(itDec.hasNext()){
				Decision actual = itDec.next();
				actual.setAgente(this);
				actual.insertarEnMonitor();
				imprime(actual);
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
	
	
	public void setAlive(){
		alive = false;
	}
	public void insertarOperacion(Operacion o){
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
	
	public void cancelarOperacion(int operacion){
		try {
			conexionBolsa.cancelarOperacion(this.ID.toString(),operacion);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public String getIDString(){
		return ID.toString();
	}
	
	public void enviarMensaje(Mensaje m){
		m.setSender(ID);
		buzon.send(m);
	}

	public void imprime(Decision decision) {
		consolaAgentes.insertarAccion(getIDString(), decision.toString());
	}

	public Perceptor getPerceptor() {
		return p;
	}
	
	
	public String getEstado(){
		return "TODO!! ESTADO!!!";
	}
	
	public String getStringComportamiento(){
		return this.comportamiento.getClass().toString();
	}
	
	public double getGanancias(){
		return this.ganancias;
	}
}
