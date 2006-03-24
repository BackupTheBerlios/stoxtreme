package stoxtreme.herramienta_agentes;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.GeneradorParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.GeneradorParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.comportamiento.inversores.ComportamientoBroker;
import stoxtreme.herramienta_agentes.agentes.comportamiento.prueba.ComportamientoPrueba;

public class HerramientaAgentes implements ConexionBolsa{
	private static int IDS=0;
	private ParametrosAgentes parametros;
	private ArrayList<Agente> agentes;
	private MonitorAgentes monitor;
	private EstadoBolsa bolsa;
	
	
	public HerramientaAgentes(EstadoBolsa bolsa, ParametrosAgentes parametros){
		this.parametros = parametros;
		this.bolsa = bolsa;
	}
	
	public int insertarOperacion(String id, Operacion o) {
		o.setIDOp(IDS);
		System.out.println("INSERTA OPERACION: "+o);
		return IDS++;
	}

	public void cancelaOperacion(int operacion) {
		System.out.println("Cancela operacion "+operacion);
	}
	
	public void init(){
		//int nAgentes = (Integer)parametros.get(ParametrosAgentes.Parametro.NUM_AGENTES);
		int nAgentes = 1;
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
//			ComportamientoAgente comportamiento1 = new ComportamientoBroker(bolsa, ps, pp);
			ComportamientoAgente comportamiento2 = new ComportamientoPrueba(bolsa, ps, pp);
			
			Agente agente = new Agente(monitor);
			agente.addComportamiento(comportamiento2);
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
		
		HerramientaAgentes hAgentes = new HerramientaAgentes(bolsa, parametros);
		hAgentes.init();
	}
}
