package stoxtreme.herramienta_agentes;

import stoxtreme.herramienta_agentes.agentes.*;
import java.util.*;

import stoxtreme.herramienta_agentes.agentes.AgentePrueba;
import stoxtreme.interfaz_remota.IAgente;
import stoxtreme.interfaz_remota.Operacion;

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
