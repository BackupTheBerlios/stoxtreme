package stoxtreme.herramienta_agentes.agentes;

import java.util.EnumMap;

/**
 * Clase que encapsula los parametros del modelo psicologico para los agentes.
 * 
 * @author Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ParametrosPsicologicos {
	/**
	 * Mapa con los atributos de los parametros sociales
	 */
	private EnumMap<Parametro,String> parametros = 
		new EnumMap<Parametro,String>(Parametro.class);
	
	/**
	 * Enumerado de los parametros
	 */
	public enum Parametro{
		TIEMPO_ESPERA,
		NUMERO_MAXIMO_ACCIONES_COMPRA,
		NUMERO_MINIMO_ACCIONES_COMPRA,
		NUMERO_MAXIMO_ACCIONES_VENTA,
		NUMERO_MINIMO_ACCIONES_VENTA,
		NUMERO_MAXIMO_CANCELACIONES,
	
		PORCENTAJE_MAXIMO_COMPRA,
		PORCENTAJE_MAXIMO_VENTA,
		PORCENTAJE_MINIMO_COMPRA,
		PORCENTAJE_MINIMO_VENTA,
		PORCENTAJE_SUBIDA_PRECIO,
		PORCENTAJE_BAJADA_PRECIO, 
		PRECIO_RECOMENDACION, 
		PRECIO_COMPRA_RECOMENDACION,
		
	}
	
	/**
	 * Cambia el valor del del parametro pasado por parametro
	 * por el valor.
	 * 
	 * @param parametro  Parametro que deseamos cambiar en el modelo
	 * @param valor      Valor asignado
	 */
	public void setParametro(String p, String v){
		Parametro param = Parametro.valueOf(p);
		parametros.put(param, v);
	}
	
	/**
	 * Obtiene el valor del parametro como un valor entero
	 * 
	 * @param parametro  Parametro del que deseamos obtener el valor
	 * @return			 Valor entero que se obtiene del parametro
	 */
	public int getParamInt(Parametro p){
		return (int)Double.parseDouble(parametros.get(p));
	}
	
	/**
	 * Obtiene el valor del parametro como un valor real
	 * 
	 * @param parametro  Parametro del que deseamos obtener el valor
	 * @return			 Valor real que se obtiene del parametro
	 */
	public double getParamDouble(Parametro p){
		return Double.parseDouble(parametros.get(p));
	}
	
	/**
	 * Obtiene el valor del parametro como una cadena de caracteres
	 * 
	 * @param parametro  Parametro del que deseamos obtener el valor
	 * @return			 Valor de cadena que se obtiene del parametro
	 */
	public String getParamString(Parametro p){
		return parametros.get(p);
	}
}
