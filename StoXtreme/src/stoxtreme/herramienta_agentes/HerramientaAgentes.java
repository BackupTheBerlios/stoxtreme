package stoxtreme.herramienta_agentes;

import java.util.ArrayList;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.cliente.gui.HerramientaAgentesPanel;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.ConstructorAgentes;
import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.BuzonMensajes;
import stoxtreme.interfaz_remota.Stoxtreme;

/**
 *  Clase principal de la herramienta de agentes
 *
 *  @author  Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
@SuppressWarnings("serial")
public class HerramientaAgentes extends HerramientaAgentesPanel implements TimerListener {
	/**
	 * Lista de los agentes en la herramienta 
	 */
	private ArrayList<Agente> agentes;
	
	/**
	 * Monitor de los agentes que controla la toma de decisiones y la
	 * ejecucion de las mismas 
	 */
	private MonitorAgentes monitor;
	
	/**
	 * Notificador de las operaciones a los agentes
	 */
	private Notificador notif;
	
	/**
	 *  Constructor de la herramienta de agentes
	 *
	 *@param  nombreUsuario  Nombre de usuario, necesario para los identificadores
	 */
	public HerramientaAgentes(String nombreUsuario){
		IDAgente.setUsuario(nombreUsuario);
		this.notif = new Notificador();
	}

	/**
	 * Numero máximo de agentes en el contenedor
	 */
	private double max_agentes;
	
	/**
	 * Numero minimo de agentes en el contenedor
	 */
	private double min_agentes;
	
	/**
	 * Tiempo minimo de espera
	 */
	private double min_tespera;
	
	/**
	 * Tiempo maximo de espera
	 */
	private double max_tespera;
	
	/**
	 * Distribucion usada por los tiempos de espera
	 */
	private String tespera_distrib;
	
	/**
	 * Gasto máximo permitido a los agentes
	 */
	private double max_gasto;
	
	/**
	 * Probabilidad de que un agente entre en la bolsa
	 */
	private double ratio_respawn;
	
	/**
	 * Atenuación que sufre un rumor al pasar de agente a agente
	 */
	private double atenuacion_rumor;
	
	/**
	 * Constructor de los agentes.
	 *   - Crea inicialmente todos los agentes.
	 *   - Inserta nuevos agentes.
	 */
	private ConstructorAgentes constructor;
	
	/**
	 *  Comienza el contenedor de agentes
	 *
	 *  @param  parametros          Parametros del contenedor de agentes
	 *  @param  ficheroConfAgentes  Ruta al fichero de configuracion de agentes
	 *  @param  servidor            Referencia a la interfaz remota
	 *  @param  eBolsa              Estado 
	 *  @exception  Exception       Description of Exception
	 */
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
	 *  Inserta un oyente en el notificador. Ante un evento el notificador
	 *  le notificará de la realizacion de la operacion.W
	 *
	 *  @param  id  Identificador del observador
	 *  @param  listener  Observador que deseamos añadir al notificador
	 */
	public void addNotificadorListener(String id, ListenerNotificador n) {
		notif.addListener(id, n);
	}

	/**
	 *  Notifica una operación al observador concreto
	 *
	 *  @param  id        Identificador del observador a informar
	 *  @param  idOp      Identificador de la operacion que notifica
	 *  @param  cantidad  Cantidad de acciones cruzadas
	 *  @param  precio    Precio de cruce
	 */
	public void notificarOperacion(String idDestino, int idOp, int cantidad, double precio) {
		notif.notificar(idDestino, idOp, cantidad, precio);
		insertarNotificacion(idDestino, "Operacion efectuada: " + idOp + "(" + cantidad + "," + precio + ")");
	}

	/**
	 *  Notifica una cancelacion de una operación al observador
	 *
	 *  @param  id    Identificador del observador a informar
	 *  @param  idOp  Identificador de la operacion cancelada
	 */
	public void notificarCancelacion(String idDestino, int idOp) {
		notif.notificarCancelacion(idDestino, idOp);
		insertarNotificacion(idDestino, "Cancelacion efectuada: " + idOp);
	}

	/**
	 *  Pausa en el sistema de agentes
	 */
	public void pausarAgentes() {
		monitor.pausar();
	}

	/**
	 *  Reauna la simulacion despues de una pausa
	 */
	public void reanudarAgentes() {
		monitor.reanudar();
	}


	/**
	 *  Reinicia el sistema de agentes
	 *
	 *  @param  parametros          Parametros del sistema de agentes
	 *  @param  ficheroConfAgentes  Ruta del fichero de configuracion de agentes
	 *  @param  servidor            Referencia al objeto remoto de servidor
	 *  @param  bolsa               Estado global de la bolsa
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
	 * Recepcion de un tick de reloj
	 *
	 * @param  tick  tick de tiempo recibido
	 * 
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

	/**
	 *  Genera un nuevo agente.
	 *
	 */
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

	/**
	 *  Elimina el peor agente de la simulacion si este sobrepasa el 
	 *  limite permitido
	 *
	 */
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
		if(cantidadMinima < -max_gasto){
			Agente agente = agentes.get(iMin);
			agente.abandonarModelo();
		}
	}
}
