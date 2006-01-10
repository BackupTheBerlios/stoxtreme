package herramienta_agentes.agentes;
import interfaz_remota.*;
import herramienta_agentes.*;

public class AgentePrueba extends Thread{
	private MonitorAgentes monitor;
	
	public AgentePrueba(MonitorAgentes ma, IInformacion info){
		monitor = ma;
	}
	
	public void run(){
		
	}
}
