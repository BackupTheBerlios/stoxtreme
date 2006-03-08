package stoxtreme.herramienta_agentes;

import java.util.*;

import stoxtreme.herramienta_agentes.agentes.AgentePrueba;

public class HerramientaAgentes{
	ArrayList<Agente> listaAgentes;
	MonitorAgentes monitor;
	
	public HerramientaAgentes(ParametrosAgentes parametros){
		monitor = new MonitorAgentes();
		listaAgentes = new ArrayList<Agente>();
		listaAgentes.add(new Agente(monitor, "AgentePrueba"));
	}
	
	public void cambiaParametros(ParametrosAgentes nParam){
		
	}
	
	public void start(){
		monitor.start();
		Iterator<Agente> it = listaAgentes.iterator();
		while(it.hasNext()) new Thread(it.next()).start();
	}
}
