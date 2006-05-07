package stoxtreme.herramienta_agentes;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.cliente.ManejadorMensajes;
import stoxtreme.cliente.gui.HerramientaAgentesPanel;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.GeneradorParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.GeneradorParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.comportamiento.inversores.ComportamientoBroker;
import stoxtreme.herramienta_agentes.agentes.comportamiento.prueba.ComportamientoPrueba;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.BuzonMensajes;
import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.servicio_web.StoxtremeServiceLocator;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;
import stoxtreme.sistema_mensajeria.receptor.ReceptorMensajes;

public class HerramientaAgentes extends HerramientaAgentesPanel implements TimerListener,IMensajeriaListener{
	private ParametrosAgentes parametros;
	private ArrayList<Agente> agentes;
	private MonitorAgentes monitor;
	private Notificador notif;
	JFrame frame;
	
	public HerramientaAgentes(){
		super();
	}

	public HerramientaAgentes(
			String nombreUsuario, 
			EstadoBolsa bolsa, 
			ParametrosAgentes parametros){
		IDAgente.setUsuario(nombreUsuario);
		this.parametros = parametros;
		this.notif = new Notificador();
		//receptor = new ReceptorMensajes("alonso", ReceptorMensajes.WEB_SERVICE, URLAXIS+"StoXtremeMsg");
//		receptor.addListener(this);
		agentes = new ArrayList<Agente>();
	}
	
	private Hashtable<Integer, String> mapIDPr = new Hashtable<Integer,String>();
	
	public void start(Stoxtreme servidor, EstadoBolsa eBolsa){
		//int nAgentes = (Integer)parametros.get(ParametrosAgentes.Parametro.NUM_AGENTES);
		int nAgentes = 3;
		monitor = new MonitorAgentes(servidor, this);
		monitor.addTimerListener(this);
		monitor.start();
		Decision.setMonitor(monitor);
		BuzonMensajes.setMonitor(monitor);
		
		GeneradorParametrosPsicologicos gPP = new GeneradorParametrosPsicologicos(nAgentes);
		gPP.generarTiempoEspera(20.0, 5.0);
		gPP.generaNumeroAcciones(10, 100, 10, 100, 3);
		gPP.generaPorcentajes(0.1, 0.1, 0.01, 0.1, 0.01, 0.1);

		GeneradorParametrosSocial gPS = new GeneradorParametrosSocial(nAgentes);
		
		for (int i=0; i<nAgentes; i++){
			String id = "Agente"+i;
			ParametrosPsicologicos pp = gPP.get(i);
			ParametrosSocial ps = gPS.get(i);
			ComportamientoAgente comportamiento1 = new ComportamientoBroker();
			
			Agente agente = new Agente(monitor.getConexionBolsa(), eBolsa, monitor.getConsolaAgentes(), ps, pp);
			notif.addListener(agente.getIDString(), agente.getPerceptor());
			agente.addComportamiento(comportamiento1);
			agente.start();
			agentes.add(agente);
		}
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

	protected void nOperacion(String id, int idOp, int cantidad, double precio) {
		notif.notificar(id, idOp, cantidad, precio);
	}

	protected void nCancelacion(String id, int idOp) {
		notif.notificarCancelacion(id, idOp);
	}

	public void onTick(int tick) {
		//frame.setTitle("Agentes: "+tick);
	}

	public void onMensaje(Mensaje m) {
		System.out.println(m.getTipoMensaje()+" "+m.getContenido());
		if(m.getTipoMensaje().equals("NOTIFICACION_OPERACION")){
			String[] mss = m.getContenido().split(",");
			int idOp = Integer.parseInt(mss[0]);
			int cantidad = Integer.parseInt(mss[1]);
			double precio = Double.parseDouble(mss[2]);
			notif.notificar(m.getDestinatario(), idOp, cantidad, precio);
		}
		else if(m.getTipoMensaje().equals("NOTIFICACION_CANCELACION")){
			int idOp = Integer.parseInt(m.getContenido());
			notif.notificarCancelacion(m.getDestinatario(), idOp);
		}
		else if(m.getTipoMensaje().equals("CAMBIO_PRECIO")){
			String[] mss = m.getContenido().split(",");
			String empresa = mss[0];
			double precio = Double.parseDouble(mss[1]);
		}
		else{
			
		}
	}
}
