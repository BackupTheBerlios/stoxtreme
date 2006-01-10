package herramienta_agentes;

import interfaz_remota.IAgente;
import interfaz_remota.Operacion;
import java.util.*;
import herramienta_agentes.agentes.*;

public class HerramientaAgentes extends Thread{
	ArrayList listaAgentes;
	MonitorAgentes monitor;
	
	public HerramientaAgentes(ParametrosAgentes parametros){
		
	}
	
	public void cambiaParametros(ParametrosAgentes nParam){
		
	}
	
	public void run(){
		Iterator it = listaAgentes.iterator();
		
		while(it.hasNext()){
			((AgentePrueba)it.next()).start();
		}
		
		monitor.start();
		while(true){
			
		}
	}
}
