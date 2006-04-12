package stoxtreme.herramienta_agentes;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.GeneradorParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.GeneradorParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.comportamiento.inversores.ComportamientoBroker;
import stoxtreme.herramienta_agentes.agentes.comportamiento.prueba.ComportamientoPrueba;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.servicio_web.StoxtremeServiceLocator;

public class HerramientaAgentes extends HerramientaAgentesPanel implements TimerListener{
	private ParametrosAgentes parametros;
	private ArrayList<Agente> agentes;
	private MonitorAgentes monitor;
	private EstadoBolsa bolsa;
	private Notificador notif;
	JFrame frame;
	
	public HerramientaAgentes(){
		super();
	}

	public HerramientaAgentes(String nombreUsuario, EstadoBolsa bolsa, ParametrosAgentes parametros){
		IDAgente.setUsuario(nombreUsuario);
		this.parametros = parametros;
		this.bolsa = bolsa;
		this.notif = new Notificador();
	}
	
	private Hashtable<Integer, String> mapIDPr = new Hashtable<Integer,String>();
	
	public void start(Stoxtreme servidor){
		frame = new JFrame("Agentes: 0");
		frame.setPreferredSize(new Dimension(400, 400));
		
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//int nAgentes = (Integer)parametros.get(ParametrosAgentes.Parametro.NUM_AGENTES);
		int nAgentes = 3;
		monitor = new MonitorAgentes(servidor, this);
		monitor.addTimerListener(this);
		monitor.start();
		
		GeneradorParametrosPsicologicos gPP = new GeneradorParametrosPsicologicos(nAgentes);
		gPP.generarTiempoEspera(20.0, 5.0);
		gPP.generaNumeroAcciones(10, 100, 10, 100, 3);
		gPP.generaPorcentajes(0.1, 0.1, 0.01, 0.1, 0.01, 0.1);

		GeneradorParametrosSocial gPS = new GeneradorParametrosSocial(nAgentes);
		
		for (int i=0; i<nAgentes; i++){
			String id = "Agente"+i;
			ParametrosPsicologicos pp = gPP.get(i);
			ParametrosSocial ps = gPS.get(i);
			ComportamientoAgente comportamiento1 = new ComportamientoBroker(bolsa, ps, pp);
			
			Agente agente = new Agente(monitor);
			agente.addComportamiento(comportamiento1);
			agente.start();
		}
	}
	
	private static final String URLAXIS = "http://localhost:8080/axis/services/";
	public static void main(String[] args){
		try{
			ParametrosAgentes parametros = new ParametrosAgentes();
			EstadoBolsa bolsa = new EstadoBolsa();
			bolsa.insertaEmpresa("ENDESA", 10.0);
			bolsa.insertaEmpresa("TELECINCO", 20.0);
			bolsa.insertaEmpresa("ANTENA3", 30.0);
			bolsa.insertaEmpresa("REPSOL", 40.0);
			
			HerramientaAgentes hAgentes = new HerramientaAgentes("alonso", bolsa, parametros);
			
			
			StoxtremeServiceLocator locator = new StoxtremeServiceLocator();
			Stoxtreme stoxtreme = locator.getStoXtreme(new URL(URLAXIS+"StoXtreme"));
			hAgentes.start(stoxtreme);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
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
		frame.setTitle("Agentes: "+tick);
	}
}
