package stoxtreme.herramienta_agentes.agentes;

import java.util.EnumMap;

/**
 * Clase que encapsula los parametros del modelo social para los agentes.
 * 
 * @author Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ParametrosSocial {
	/**
	 * Mapa con los atributos de los parametros sociales
	 */
	private EnumMap<Parametro,String> parametros = 
		new EnumMap<Parametro,String>(Parametro.class);
	
	/**
	 * Enumerado de los parametros
	 */
	public enum Parametro{
		FIABILIDAD_RUMOR, // La cantidad que se fia de los rumores
		NUMERO_CONOCIDOS, // Numero de agentes que conoce
		MANIPULABILIDAD,   // Como es de manipulable
		INCREMENTO_FIABILIDAD,
		DECREMENTO_FIABILIDAD,
	}
	
	/**
	 * Cambia el valor del del parametro pasado por parametro
	 * por el valor.
	 * 
	 * @param parametro  Parametro que deseamos cambiar en el modelo
	 * @param valor      Valor asignado
	 */
	public void setParametro(String parametro, String valor){
		Parametro param = Parametro.valueOf(parametro);
		parametros.put(param, valor);
	}
	
	/**
	 * Obtiene el valor del parametro como un valor entero
	 * 
	 * @param parametro  Parametro del que deseamos obtener el valor
	 * @return			 Valor entero que se obtiene del parametro
	 */
	public int getParamInt(Parametro parametro){
		if(!parametros.containsKey(parametro))
			return 0;
		try {
			return Integer.parseInt(parametros.get(parametro));
		} catch (NumberFormatException e) {
			// Que de belleza hay en el mundo
			return (int)Double.parseDouble(parametros.get(parametro));
		}
	}
	
	/**
	 * Obtiene el valor del parametro como un valor real
	 * 
	 * @param parametro  Parametro del que deseamos obtener el valor
	 * @return			 Valor real que se obtiene del parametro
	 */
	public double getParamDouble(Parametro parametro){
		if(!parametros.containsKey(parametro))
			return 0.0;
		return Double.parseDouble(parametros.get(parametro));
	}
	
	/**
	 * Obtiene el valor del parametro como una cadena de caracteres
	 * 
	 * @param parametro  Parametro del que deseamos obtener el valor
	 * @return			 Valor de cadena que se obtiene del parametro
	 */
	public String getParamString(Parametro parametro){
		return parametros.get(parametro);
	}
}
