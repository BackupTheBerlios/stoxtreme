package stoxtreme.herramienta_agentes.agentes.comportamiento;

import java.util.ArrayList;
import java.util.HashMap;

import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ModeloSocial {
	private ParametrosSocial p;
	private ArrayList<IDAgente> conocidos;
	private HashMap<IDAgente, Double> fiabilidad;
	private int nConocidos;


	/**
	 *  Constructor for the ModeloSocial object
	 *
	 *@param  p  Description of Parameter
	 */
	public ModeloSocial(ParametrosSocial p) {
		this.p = p;
		nConocidos = p.getParamInt(ParametrosSocial.Parametro.NUMERO_CONOCIDOS);
		conocidos = new ArrayList<IDAgente>();
	}


	/**
	 *  Gets the Fiabilidad attribute of the ModeloSocial object
	 *
	 *@param  emisor  Description of Parameter
	 *@return         The Fiabilidad value
	 */
	public double getFiabilidad(IDAgente emisor) {
		double modificador = 1.0;
		if (fiabilidad != null && fiabilidad.containsKey(emisor)) {
			modificador = fiabilidad.get(emisor);
		}
		return p.getParamDouble(ParametrosSocial.Parametro.FIABILIDAD_RUMOR) * modificador;
	}


	/**
	 *  Gets the ConocidoAleatorio attribute of the ModeloSocial object
	 *
	 *@return    The ConocidoAleatorio value
	 */
	public IDAgente getConocidoAleatorio() {
		int r = (int) Math.random() * conocidos.size();
		return conocidos.get(r);
	}


	/**
	 *  Gets the NumConocidos attribute of the ModeloSocial object
	 *
	 *@return    The NumConocidos value
	 */
	public int getNumConocidos() {
		return nConocidos;
	}


	/**
	 *  Adds a feature to the Conocido attribute of the ModeloSocial object
	 *
	 *@param  agente  The feature to be added to the Conocido attribute
	 */
	public void addConocido(IDAgente agente) {
		conocidos.add(agente);
	}


	/**
	 *  Adds a feature to the Conocido attribute of the ModeloSocial object
	 *
	 *@param  agente       The feature to be added to the Conocido attribute
	 *@param  modificador  The feature to be added to the Conocido attribute
	 */
	public void addConocido(IDAgente agente, double modificador) {
		if (fiabilidad == null) {
			fiabilidad = new HashMap<IDAgente, Double>();
		}
		fiabilidad.put(agente, modificador);
		conocidos.add(agente);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  agente  Description of Parameter
	 */
	public void incrementarConfianza(IDAgente agente) {
		double antiguo = fiabilidad.containsKey(agente) ? fiabilidad.get(agente) : 1.0;
		double incremento = p.getParamDouble(ParametrosSocial.Parametro.INCREMENTO_FIABILIDAD);
		fiabilidad.put(agente, antiguo * (1 + incremento));
	}


	/**
	 *  Description of the Method
	 *
	 *@param  agente  Description of Parameter
	 */
	public void decrementarConfianza(IDAgente agente) {
		double antiguo = fiabilidad.containsKey(agente) ? fiabilidad.get(agente) : 1.0;
		double decremento = p.getParamDouble(ParametrosSocial.Parametro.DECREMENTO_FIABILIDAD);
		fiabilidad.put(agente, antiguo * decremento);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  agente  Description of Parameter
	 */
	public void removeConocido(IDAgente agente) {
		conocidos.remove(agente);
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public boolean hagoCasoAnuncio() {
		double r = Math.random();
		return (r > p.getParamDouble(ParametrosSocial.Parametro.MANIPULABILIDAD));
	}
}
