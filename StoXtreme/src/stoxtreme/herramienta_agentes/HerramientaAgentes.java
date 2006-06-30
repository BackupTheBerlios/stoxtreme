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
 *@author    Chris Seguin
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
			EstadoBolsa bolsa) {
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
	public void start(ParametrosAgentes parametros, String ficheroConfAgentes, Stoxtreme servidor, EstadoBolsa eBolsa) throws Exception {
		int tCiclo = parametros.getInt(ParametrosAgentes.Parametro.TCICLO);
		monitor = new MonitorAgentes(servidor, this, tCiclo);
		monitor.addTimerListener(this);
		Decision.setMonitor(monitor);
		BuzonMensajes.setMonitor(monitor);

		ConstructorAgentes constructor = new ConstructorAgentes();
		agentes = constructor.construyeAgentes(
				monitor.getConexionBolsa(),
				eBolsa, monitor.getConsolaAgentes(),
				notif, modeloTabla,
				ficheroConfAgentes, parametros);
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
				ConstructorAgentes constructor = new ConstructorAgentes();
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
		incrementaTick(tick);
		//		frame.setTitle("Ciclo ")
	}
}
