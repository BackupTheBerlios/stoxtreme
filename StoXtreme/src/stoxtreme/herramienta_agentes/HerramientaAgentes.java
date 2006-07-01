package stoxtreme.herramienta_agentes;

import java.util.ArrayList;
import java.util.Hashtable;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.cliente.gui.FakeInternalFrame;
import stoxtreme.cliente.gui.HerramientaAgentesPanel;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.ConstructorAgentes;
import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.BuzonMensajes;
import stoxtreme.interfaz_remota.Stoxtreme;

/**
 *  Description of the Class
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public class HerramientaAgentes extends HerramientaAgentesPanel implements TimerListener {
	private ArrayList<Agente> agentes;
	private MonitorAgentes monitor;
	private Notificador notif;
	
	private Hashtable<Integer, String> mapIDPr = new Hashtable<Integer, String>();


	/**
	 *  Constructor for the HerramientaAgentes object
	 *
	 *@param  nombreUsuario  Description of Parameter
	 *@param  bolsa          Description of Parameter
	 */
	public HerramientaAgentes(
			String nombreUsuario,
			EstadoBolsa bolsa){
		IDAgente.setUsuario(nombreUsuario);
		this.notif = new Notificador();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  parametros          Description of Parameter
	 *@param  ficheroConfAgentes  Description of Parameter
	 *@param  servidor            Description of Parameter
	 *@param  eBolsa              Description of Parameter
	 *@exception  Exception       Description of Exception
	 */

	private double max_agentes;
	private double min_agentes;
	private double min_tespera;
	private double max_tespera;
	private String tespera_distrib;
	private double max_gasto;
	private double ratio_respawn;
	private double atenuacion_rumor;
	
	private ConstructorAgentes constructor;
	
	public void start(ParametrosAgentes parametros, String ficheroConfAgentes, Stoxtreme servidor, EstadoBolsa eBolsa) throws Exception {
		int tCiclo = parametros.getInt(ParametrosAgentes.Parametro.TCICLO);
		monitor = new MonitorAgentes(servidor, this, tCiclo);
		monitor.addTimerListener(this);
		Decision.setMonitor(monitor);
		BuzonMensajes.setMonitor(monitor);

		constructor = new ConstructorAgentes();
		agentes = constructor.construyeAgentes(
				monitor.getConexionBolsa(),
				eBolsa, monitor.getConsolaAgentes(),
				notif, modeloTabla,
				ficheroConfAgentes, parametros);
		
		max_agentes = constructor.getMaxAgentes();
		min_agentes = constructor.getMinAgentes();
		min_tespera = constructor.getMinTEspera();
		max_tespera = constructor.getMaxTEspera();
		tespera_distrib = constructor.getDistribucionEspera();
		max_gasto = constructor.getMaxGasto();
		ratio_respawn = constructor.getRatioRewspanwn();
		atenuacion_rumor = constructor.getAtenuacionRumor();
			
		monitor.start();
		super.addListaAgentes(agentes);
	}


	/**
	 *  Adds the specified Notificador listener to receive Notificador events
	 *  from this component. If listener l is null, no exception is thrown and no
	 *  action is performed.
	 *
	 *@param  id  Contains the NotificadorListener for NotificadorEvent data.
	 *@param  n   Contains the NotificadorListener for NotificadorEvent data.
	 */
	public void addNotificadorListener(String id, ListenerNotificador n) {
		notif.addListener(id, n);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idDestino  Description of Parameter
	 *@param  idOp       Description of Parameter
	 *@param  cantidad   Description of Parameter
	 *@param  precio     Description of Parameter
	 */
	public void notificarOperacion(String idDestino, int idOp, int cantidad, double precio) {
		notif.notificar(idDestino, idOp, cantidad, precio);
		insertarNotificacion(idDestino, "Operacion efectuada: " + idOp + "(" + cantidad + "," + precio + ")");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idDestino  Description of Parameter
	 *@param  idOp       Description of Parameter
	 */
	public void notificarCancelacion(String idDestino, int idOp) {
		notif.notificarCancelacion(idDestino, idOp);
		insertarNotificacion(idDestino, "Cancelacion efectuada: " + idOp);
	}


	/**
	 *  Description of the Method
	 */
	public void pausarAgentes() {
		monitor.pausar();
	}


	/**
	 *  Description of the Method
	 */
	public void reanudarAgentes() {
		monitor.reanudar();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  parametros          Description of Parameter
	 *@param  ficheroConfAgentes  Description of Parameter
	 *@param  servidor            Description of Parameter
	 *@param  bolsa               Description of Parameter
	 */
	public void reiniciar(ParametrosAgentes parametros, String ficheroConfAgentes, Stoxtreme servidor, EstadoBolsa bolsa) {
		for (int i = 0; i < agentes.size(); i++) {
			agentes.get(i).abandonarModelo();
		}
		limpiarGUI();

		try {
			synchronized (monitor) {
				constructor = new ConstructorAgentes();
				agentes = constructor.construyeAgentes(
						monitor.getConexionBolsa(),
						bolsa, monitor.getConsolaAgentes(),
						notif, modeloTabla,
						ficheroConfAgentes, parametros);

				super.addListaAgentes(agentes);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  tick  Description of Parameter
	 */
	public void onTick(int tick) {
		int numAgentes = agentes.size();
		// Cada multiplo de 20 borra el agente con menos dinero
		if(numAgentes > min_agentes && tick != 0 && tick%20 == 0){
			eliminaPeorAgente();
		}
		double aleatorio = ConstructorAgentes.random.nextDouble();
		if(aleatorio <= ratio_respawn && numAgentes < max_agentes){
			respawn();
		}
		incrementaTick(tick);
	}


	private void respawn() {
		// Generamos un nuevo agente del tipo que toque
		Agente agente = constructor.nuevoAgente();
		for (int i = 0; i < agente.getModeloSocial().getNumConocidos(); i++) {
			int r = (int) (Math.random()) * agentes.size();
			agente.getModeloSocial().addConocido(agentes.get(r).getIDAgente());
		}
		synchronized (monitor) {
			modeloTabla.addAgente(agente);
			monitor.addAgente(agente);
			agente.start();
		}
		monitor.getConsolaAgentes().insertarAccion(agente.getIDString(), "Entra en bolsa");
	}

	private void eliminaPeorAgente() {
		int iMin = -1;
		double cantidadMinima = Double.MAX_VALUE;
		for(int i=0; i<agentes.size(); i++){
			double cantidad = agentes.get(i).getGanancias();
			if(cantidad < cantidadMinima){
				cantidadMinima = cantidad;
				iMin = i;
			}
		}
		Agente agente = agentes.get(iMin);
		agente.abandonarModelo();
		System.err.println("Eliminamos el agente: "+agente.getIDString());
	}
}
