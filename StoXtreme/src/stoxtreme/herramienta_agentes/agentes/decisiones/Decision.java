package stoxtreme.herramienta_agentes.agentes.decisiones;
import stoxtreme.herramienta_agentes.MonitorAgentes;
import stoxtreme.herramienta_agentes.agentes.Agente;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public abstract class Decision implements Comparable {
	/**
	 *  Description of the Field
	 */
	protected Agente agente;

	private int tEspera;
	private int tEjecucion;
	private static MonitorAgentes _monitor;


	/**
	 *  Constructor for the Decision object
	 */
	public Decision() {
		this.tEspera = 1;
	}


	/**
	 *  Constructor for the Decision object
	 *
	 *@param  tEspera  Description of Parameter
	 */
	public Decision(int tEspera) {
		this.tEspera = tEspera;
	}


	/**
	 *  Sets the Agente attribute of the Decision object
	 *
	 *@param  agente  The new Agente value
	 */
	public void setAgente(Agente agente) {
		this.agente = agente;
	}


	/**
	 *  Gets the TiempoEjecucion attribute of the Decision object
	 *
	 *@return    The TiempoEjecucion value
	 */
	public int getTiempoEjecucion() {
		return tEjecucion;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  o  Description of Parameter
	 *@return    Description of the Returned Value
	 */
	public int compareTo(Object o) {
		Decision d2 = (Decision) o;
		return getTiempoEjecucion() - d2.getTiempoEjecucion();
	}


	/**
	 *  Adds a feature to the TActual attribute of the Decision object
	 *
	 *@param  ciclo  The feature to be added to the TActual attribute
	 */
	public void addTActual(int ciclo) {
		this.tEjecucion = tEspera + ciclo;
	}


	/**
	 *  Description of the Method
	 */
	public void insertarEnMonitor() {
		_monitor.addDecision(this);
	}


	/**
	 *  Description of the Method
	 */
	public abstract void ejecuta();


	/**
	 *  Sets the Monitor attribute of the Decision class
	 *
	 *@param  monitor  The new Monitor value
	 */
	public static void setMonitor(MonitorAgentes monitor) {
		_monitor = monitor;
	}
}

