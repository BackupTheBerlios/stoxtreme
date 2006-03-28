package stoxtreme.herramienta_agentes.agentes;

import java.util.ArrayList;
import java.util.Iterator;

import stoxtreme.herramienta_agentes.*;
import stoxtreme.herramienta_agentes.agentes.comportamiento.*;
import stoxtreme.herramienta_agentes.agentes.decisiones.*;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.BuzonMensajes;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.Mensaje;
// Primer prototipo de agente broker.
// Inicialmente se le introduce el parametro correspondiente al tiempo de espera
// El broker, esperará a que transcurran tiempoEspera ciclos de ejecución y cuando
// supere el valor efectuará una acción aleatoria, dependiendo de la acción que hubiera
// realizado antes y siempre con el precio actual de mercado.
// PreStep - Analiza el entorno y guarda los datos necesrios
// Step - Con los datos anteriores modifica sus intenciones
// PostStep - Introduce la operación en el sistema correspondiendo con sus intenciones

public class Agente extends Thread{
	private BuzonMensajes buzon;
	private IDAgente ID;
	private Perceptor p;
	private OperacionesPendientes opPendientes;
	private ComportamientoAgente comportamiento;
	private MonitorAgentes monitor;
	private boolean alive;
	
	public Agente (MonitorAgentes monitor){
		ID = new IDAgente();
		p = new Perceptor();
		monitor.getConexionBolsa().addNotificadorListener(ID.toString(), p);
		opPendientes = new OperacionesPendientes();
		p.setOperacionesPendientes(opPendientes);
		this.monitor = monitor;
		this.alive = true;
		buzon = new BuzonMensajes(monitor, ID);
	}
	
	public void addComportamiento(ComportamientoAgente c){
		this.comportamiento = c;
		p.setEstadoBolsa(comportamiento.getEstadoBolsa());
		p.setEstadoCartera(comportamiento.getCartera());
		p.setGeneradorDecisiones(comportamiento);
		comportamiento.setOperacionesPendientes(opPendientes);
		comportamiento.setAgente(this);
	}

	// Analiza los cambios en la bolsa y cambia la estrategia
	public void run(){
		while(alive){
			// Nadie le interrumpe mientras toma las
			// decisiones
			ArrayList<Decision> decisiones;
			synchronized(this){
				decisiones = comportamiento.tomaDecisiones();
			}
			
			Iterator<Decision> itDec = decisiones.iterator();
			while(itDec.hasNext()){
				Decision actual = itDec.next();
				monitor.addDecision(actual);
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
		int tipo = o.getTipo();
		int numAcciones = o.getNumeroAcciones();
		double precio = o.getPrecio();
		int i = monitor.getConexionBolsa().insertarOperacion(this.ID.toString(), o);
		
		if(i!= -1){
			opPendientes.insertaOperacionPendiente(i, empresa, tipo, numAcciones, precio);
		}
	}
	
	public void cancelarOperacion(int operacion){
		monitor.getConexionBolsa().cancelaOperacion(operacion);
	}
	
	public String getIDString(){
		return ID.toString();
	}
	
	public void enviarMensaje(Mensaje m){
		m.setSender(ID);
		buzon.send(m);
	}
}
