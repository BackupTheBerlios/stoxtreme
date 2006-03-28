package stoxtreme.herramienta_agentes;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.GeneradorParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.GeneradorParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.comportamiento.inversores.ComportamientoBroker;
import stoxtreme.herramienta_agentes.agentes.comportamiento.prueba.ComportamientoPrueba;

public class HerramientaAgentes extends HerramientaAgentesPanel implements ConexionBolsa{
	private static int IDS=0;
	private ParametrosAgentes parametros;
	private ArrayList<Agente> agentes;
	private MonitorAgentes monitor;
	private EstadoBolsa bolsa;
	private Notificador notif;
	
	

	public HerramientaAgentes(String nombreUsuario, EstadoBolsa bolsa, ParametrosAgentes parametros){
		IDAgente.setUsuario(nombreUsuario);
		this.parametros = parametros;
		this.bolsa = bolsa;
		this.notif = new Notificador();
	}
	
	public int insertarOperacion(String id, Operacion o) {
		o.setIDOp(IDS);
		super.insertarOperacion(o);
		mapIDPr.put(IDS, o.getIDAgente());
		return IDS++;
	}
	
	private Hashtable<Integer, String> mapIDPr = new Hashtable<Integer,String>();
	public void cancelaOperacion(int operacion) {
		super.cancelarOperacion(mapIDPr.get(operacion), operacion);
	}
	
	public void start(){
		//int nAgentes = (Integer)parametros.get(ParametrosAgentes.Parametro.NUM_AGENTES);
		int nAgentes = 3;
		monitor = new MonitorAgentes(this);
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
	
	
	public static void main(String[] args){
		ParametrosAgentes parametros = new ParametrosAgentes();
		EstadoBolsa bolsa = new EstadoBolsa();
		bolsa.insertaEmpresa("Empresa1", 10.0);
		bolsa.insertaEmpresa("Empresa2", 20.0);
		bolsa.insertaEmpresa("Empresa3", 30.0);
		bolsa.insertaEmpresa("Empresa4", 40.0);
		
		HerramientaAgentes hAgentes = new HerramientaAgentes("alonso", bolsa, parametros);
		
		JFrame frame = new JFrame("Agentes");
		frame.setPreferredSize(new Dimension(400, 400));
		
		frame.getContentPane().add(hAgentes);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		hAgentes.start();
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
}
