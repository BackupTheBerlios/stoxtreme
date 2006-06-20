package stoxtreme.herramienta_agentes.agentes;

import java.util.EnumMap;

public class ParametrosSocial {
	private EnumMap<Parametro,String> parametros = new EnumMap<Parametro,String>(Parametro.class);
	
	public enum Parametro{
		FIABILIDAD_RUMOR, // La cantidad que se fia de los rumores
		NUMERO_CONOCIDOS, // Numero de agentes que conoce
		MANIPULABILIDAD,   // Como es de manipulable
		INCREMENTO_FIABILIDAD,
		DECREMENTO_FIABILIDAD,
	}
	
	public void setParametro(String p, String v){
		Parametro param = Parametro.valueOf(p);
		parametros.put(param, v);
	}
	
	public int getParamInt(Parametro p){
		if(!parametros.containsKey(p))
			return 0;
		try {
			return Integer.parseInt(parametros.get(p));
		} catch (NumberFormatException e) {
			// Que de belleza hay en el mundo
			return (int)Double.parseDouble(parametros.get(p));
		}
	}
	
	public double getParamDouble(Parametro p){
		if(!parametros.containsKey(p))
			return 0.0;
		return Double.parseDouble(parametros.get(p));
	}
	
	public String getParamString(Parametro p){
		return parametros.get(p);
	}
}
