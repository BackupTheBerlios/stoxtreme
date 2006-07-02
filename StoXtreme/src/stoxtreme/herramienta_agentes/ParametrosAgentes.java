package stoxtreme.herramienta_agentes;

import java.util.EnumMap;

/**
 *  Clase que encapsula todos los parámetros del contenedor local de
 *  agentes
 *
 *  @author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ParametrosAgentes {
	/**
	 *  Mapa de enumerados con los valores de los parametros
	 */
	private EnumMap<Parametro, Object> parametros;
	
	/**
	 *  Enumerado con los parametros del sistema de agentes
	 */
	public enum Parametro { NUM_AGENTES, TIEMPO_ESPERA, TCICLO }
	
	/**
	 *  Constructor por defecto de los parametros de los agentes.
	 *  Genera el mapa de enumerados vacio.
	 */
	public ParametrosAgentes() {
		parametros = new EnumMap<Parametro, Object>(Parametro.class);
	}

	/**
	 *  Da valor al parametro p con el valor v
	 *
	 *  @param  p  Parametro
	 *  @param  v  Valor
	 */
	public void set(Parametro p, Object v) {
		parametros.put(p, v);
	}

	/**
	 *  Obtiene el valor del parametro
	 *  Devuelve un valor entero
	 *
	 *  @param  p  Parametro del que deseamos obtener el valor
	 *  @return    Valor del parametro p en la configuracion
	 */
	public int getInt(Parametro p) {
		return (Integer) parametros.get(p);
	}

	/**
	 *  Obtiene el valor del parametro
	 *  Devuelve un valor real
	 *
	 *  @param  p  Parametro del que deseamos obtener el valor
	 *  @return    Valor del parametro p en la configuracion
	 */
	public double getDouble(Parametro p) {
		return (Double) parametros.get(p);
	}
}
