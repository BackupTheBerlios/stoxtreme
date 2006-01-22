package stoxtreme.herramienta_agentes.agentes;
import stoxtreme.herramienta_agentes.MonitorAgentes;
import stoxtreme.interfaz_remota.IInformacion;
import stoxtreme.herramienta_agentes.*;
import stoxtreme.interfaz_remota.*;

public class AgentePrueba extends Thread{
	private MonitorAgentes monitor;
	
	public AgentePrueba(MonitorAgentes ma, IInformacion info){
		monitor = ma;
	}
	
	public void run(){
		
	}
}
