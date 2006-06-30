package stoxtreme.herramienta_agentes;

import java.util.EnumMap;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ParametrosAgentes {
	private EnumMap<Parametro, Object> parametros;


	/**
	 *  Constructor for the ParametrosAgentes object
	 */
	public ParametrosAgentes() {
		parametros = new EnumMap<Parametro, Object>(Parametro.class);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  p  Description of Parameter
	 *@param  v  Description of Parameter
	 */
	public void set(Parametro p, Object v) {
		parametros.put(p, v);
	}


	/**
	 *  Gets the Int attribute of the ParametrosAgentes object
	 *
	 *@param  p  Description of Parameter
	 *@return    The Int value
	 */
	public int getInt(Parametro p) {
		return (Integer) parametros.get(p);
	}


	/**
	 *  Gets the Double attribute of the ParametrosAgentes object
	 *
	 *@param  p  Description of Parameter
	 *@return    The Double value
	 */
	public double getDouble(Parametro p) {
		return (Double) parametros.get(p);
	}


	/**
	 *  Description of Enumeration
	 *
	 *@author    Chris Seguin
	 */
	public enum Parametro { NUM_AGENTES, TIEMPO_ESPERA, TCICLO }
}
