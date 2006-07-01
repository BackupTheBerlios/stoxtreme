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

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Agente extends Thread {
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


	/**
	 *  Constructor for the Agente object
	 *
	 *@param  conexionBolsa   Description of Parameter
	 *@param  estado          Description of Parameter
	 *@param  consolaAgentes  Description of Parameter
	 *@param  modeloTabla     Description of Parameter
	 *@param  ps              Description of Parameter
	 *@param  pp              Description of Parameter
	 */
	public Agente(Stoxtreme conexionBolsa, EstadoBolsa estado, ConsolaAgentes consolaAgentes, HerramientaAgentesTableModel modeloTabla, ParametrosSocial ps, ParametrosPsicologicos pp) {
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


	/**
	 *  Sets the Alive attribute of the Agente object
	 *
	 *@param  estado  The new Alive value
	 */
	public void setAlive(boolean estado) {
		alive = estado;
	}


	/**
	 *  Gets the IDString attribute of the Agente object
	 *
	 *@return    The IDString value
	 */
	public String getIDString() {
		return ID.toString();
	}


	/**
	 *  Gets the Perceptor attribute of the Agente object
	 *
	 *@return    The Perceptor value
	 */
	public Perceptor getPerceptor() {
		return p;
	}


	/**
	 *  Gets the Estado attribute of the Agente object
	 *
	 *@return    The Estado value
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 *  Gets the StringComportamiento attribute of the Agente object
	 *
	 *@return    The StringComportamiento value
	 */
	public String getStringComportamiento() {
		return this.comportamiento.getIdentificador();
	}


	/**
	 *  Gets the Ganancias attribute of the Agente object
	 *
	 *@return    The Ganancias value
	 */
	public double getGanancias() {
		return this.ganancias;
	}


	/**
	 *  Gets the IDAgente attribute of the Agente object
	 *
	 *@return    The IDAgente value
	 */
	public IDAgente getIDAgente() {
		return ID;
	}


	/**
	 *  Gets the ModeloSocial attribute of the Agente object
	 *
	 *@return    The ModeloSocial value
	 */
	public ModeloSocial getModeloSocial() {
		return this.modeloSocial;
	}


	/**
	 *  Gets the Output attribute of the Agente object
	 *
	 *@return    The Output value
	 */
	public StringBuffer getOutput() {
		return output;
	}


	/**
	 *  Adds a feature to the Comportamiento attribute of the Agente object
	 *
	 *@param  c  The feature to be added to the Comportamiento attribute
	 */
	public void addComportamiento(ComportamientoAgente c) {
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
	/**
	 *  Main processing method for the Agente object
	 */
	public synchronized void run() {
		while (alive) {
			// Nadie le interrumpe mientras toma las
			// decisiones
			ArrayList<Decision> decisiones;
			String nuevoEstado;

			comportamiento.generaDecisionesAll();
			decisiones = comportamiento.getDecisiones();
			synchronized (decisiones) {
				nuevoEstado = comportamiento.getEstadoComportamiento();
				Iterator<Decision> itDec = decisiones.iterator();
				while (itDec.hasNext()) {
					Decision actual = itDec.next();
					actual.setAgente(this);
					actual.insertarEnMonitor();
					imprime(actual);
				}
				decisiones.clear();
			}
			Decision espera = comportamiento.getDecisionEspera();
			espera.setAgente(this);
			espera.insertarEnMonitor();
			imprime(espera);

			if (!nuevoEstado.equals(estado)) {
				estado = nuevoEstado;
				fireUpdateTable();
			}

			try {
				wait();
			}
			catch (InterruptedException e) {
				alive = false;
				e.printStackTrace();
			}
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  o  Description of Parameter
	 */
	public void insertarOperacion(Operacion o) {
		if (alive) {
			String empresa = o.getEmpresa();
			int tipo = o.getTipoOp();
			int numAcciones = o.getCantidad();
			double precio = o.getPrecio();

			try {
				int i = conexionBolsa.insertarOperacion(this.ID.toString(), o);
				if (i != -1) {
					opPendientes.insertaOperacionPendiente(i, empresa, tipo, numAcciones, precio);
				}
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  operacion  Description of Parameter
	 */
	public void cancelarOperacion(int operacion) {
		if (alive) {
			try {
				conexionBolsa.cancelarOperacion(this.ID.toString(), operacion);
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 *  Description of the Method
	 */
	public void abandonarModelo() {
		if (alive) {
			imprime("Abandono la bolsa");
			setAlive(false);
			modeloTabla.eliminaAgente(this);
		}
	}



	/**
	 *  Description of the Method
	 *
	 *@param  m  Description of Parameter
	 */
	public void enviarMensaje(MensajeACL m) {
		m.setSender(ID);
		buzon.send(m);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  decision  Description of Parameter
	 */
	public void imprime(Decision decision) {
		consolaAgentes.insertarAccion(getIDString(), decision.toString());
		output.append(decision.toString());
		output.append('\n');
	}
	
	private void imprime(String string) {
		consolaAgentes.insertarAccion(getIDString(), string);
		output.append(string);
		output.append('\n');
	}

	/**
	 *  Description of the Method
	 *
	 *@param  servicio  Description of Parameter
	 */
	public void altaServicio(String servicio) {
		buzon.altaServicio(ID, servicio);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  servicio  Description of Parameter
	 */
	public void bajaServicio(String servicio) {
		buzon.bajaServicio(ID, servicio);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  n   Description of Parameter
	 *@param  p2  Description of Parameter
	 *@param  r   Description of Parameter
	 */
	public void recibeMensaje(int n, PlantillaMensajes p2, RecepcionMensaje r) {
		buzon.receive(n, p2, r);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  servicio     Description of Parameter
	 *@param  proveedores  Description of Parameter
	 */
	public void requestServicio(String servicio, ArrayList<IDAgente> proveedores) {
		ArrayList<IDAgente> lista = buzon.getAgentesServicio(servicio);
		for (int i = 0; i < lista.size(); i++) {
			proveedores.add(lista.get(i));
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  d  Description of Parameter
	 */
	public void decrementaBeneficio(double d) {
		ganancias -= d;
		fireUpdateTable();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  d  Description of Parameter
	 */
	public void incrementaBeneficio(double d) {
		ganancias += d;
		fireUpdateTable();
	}


	/**
	 *  Description of the Method
	 */
	public void fireUpdateTable() {
		modeloTabla.cambioEnAgente(getIDString());
	}
}
