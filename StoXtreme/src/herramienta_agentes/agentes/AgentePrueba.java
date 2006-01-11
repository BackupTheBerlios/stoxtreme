package herramienta_agentes.agentes;
import herramienta_agentes.*;
import interfaz_remota.*;

public class AgentePrueba extends Thread{
	/**
	 * @uml.property  name="monitor"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private MonitorAgentes monitor;
	
	public AgentePrueba(MonitorAgentes ma, IInformacion info){
		monitor = ma;
	}
	
	public void run(){
		
	}
}
