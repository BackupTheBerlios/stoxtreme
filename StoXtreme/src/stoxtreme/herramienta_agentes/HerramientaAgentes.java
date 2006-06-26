package stoxtreme.herramienta_agentes;

import java.util.ArrayList;
import java.util.Hashtable;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.cliente.gui.HerramientaAgentesPanel;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.ConstructorAgentes;
import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.BuzonMensajes;
import stoxtreme.interfaz_remota.Stoxtreme;

public class HerramientaAgentes extends HerramientaAgentesPanel implements TimerListener{
	private ArrayList<Agente> agentes;
	private MonitorAgentes monitor;
	private Notificador notif;
	
	public HerramientaAgentes(
			String nombreUsuario, 
			EstadoBolsa bolsa){
		IDAgente.setUsuario(nombreUsuario);
		this.notif = new Notificador();
		//receptor = new ReceptorMensajes("alonso", ReceptorMensajes.WEB_SERVICE, URLAXIS+"StoXtremeMsg");
//		receptor.addListener(this);
	}
	
	private Hashtable<Integer, String> mapIDPr = new Hashtable<Integer,String>();
	
	public void start(ParametrosAgentes parametros, String ficheroConfAgentes, Stoxtreme servidor, EstadoBolsa eBolsa) throws Exception{
		int tCiclo = parametros.getInt(ParametrosAgentes.Parametro.TCICLO);
		monitor = new MonitorAgentes(servidor, this, tCiclo);
		monitor.addTimerListener(this);
		Decision.setMonitor(monitor);
		BuzonMensajes.setMonitor(monitor);
		
		ConstructorAgentes constructor = new ConstructorAgentes();
		agentes = constructor.construyeAgentes(
				monitor.getConexionBolsa(), 
				eBolsa, monitor.getConsolaAgentes(),
				notif,modeloTabla,
				ficheroConfAgentes, parametros);
		monitor.start();
		super.addListaAgentes(agentes);
	}
	
	//private static final String URLAXIS = "http://localhost:8080/axis/services/";
//	public static void main(String[] args){
//		try{
//			ParametrosAgentes parametros = new ParametrosAgentes();
//			EstadoBolsa bolsa = EstadoBolsa.getInstanciaGlobal();
//			bolsa.insertaEmpresa("ENDESA", 10.0);
//			bolsa.insertaEmpresa("TELECINCO", 20.0);
//			bolsa.insertaEmpresa("ANTENA3", 30.0);
//			bolsa.insertaEmpresa("REPSOL", 40.0);
//			
//			HerramientaAgentes hAgentes = new HerramientaAgentes("alonso", bolsa, parametros);
//			
//			JFrame frame = new JFrame("Agentes: 0");
//			
//			frame.getContentPane().add(hAgentes);
//			frame.setSize(new Dimension(800, 600));
//			frame.setVisible(true);
//			
//			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			StoxtremeServiceLocator locator = new StoxtremeServiceLocator();
//			Stoxtreme stoxtreme = locator.getStoXtreme(new URL(URLAXIS+"StoXtreme"));
//			hAgentes.start(stoxtreme);
//			
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	public void addNotificadorListener(String id, ListenerNotificador n) {
		notif.addListener(id, n);
	}

//	protected void nOperacion(String id, int idOp, int cantidad, double precio) {
//		notif.notificar(id, idOp, cantidad, precio);
//	}
//
//	protected void nCancelacion(String id, int idOp) {
//		notif.notificarCancelacion(id, idOp);
//	}

	public void onTick(int tick) {
		//frame.setTitle("Agentes: "+tick);
	}

	public void notificarOperacion(String idDestino, int idOp, int cantidad, double precio) {
		notif.notificar(idDestino, idOp, cantidad, precio);
		insertarNotificacion(idDestino, "Operacion efectuada: "+idOp+"("+cantidad+","+precio+")");
	}

	public void notificarCancelacion(String idDestino, int idOp) {
		notif.notificarCancelacion(idDestino, idOp);
		insertarNotificacion(idDestino, "Cancelacion efectuada: "+idOp);
	}

	public void pausarAgentes() {
		monitor.pausar();
	}

	public void reanudarAgentes() {
		monitor.reanudar();
	}

	
	public void reiniciar(ParametrosAgentes parametros, String ficheroConfAgentes, Stoxtreme servidor, EstadoBolsa bolsa) {
		for(int i=0; i<agentes.size(); i++){
			agentes.get(i).abandonarModelo();
		}
		limpiarGUI();
		
		try {
			synchronized (monitor) {
				ConstructorAgentes constructor = new ConstructorAgentes();
				agentes = constructor.construyeAgentes(
						monitor.getConexionBolsa(), 
						bolsa, monitor.getConsolaAgentes(),
						notif,modeloTabla,
						ficheroConfAgentes, parametros);
				
				super.addListaAgentes(agentes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
