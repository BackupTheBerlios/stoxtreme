package stoxtreme.herramienta_agentes;

import java.util.EnumMap;

public class ParametrosAgentes{
	public enum Parametro{NUM_AGENTES,TIEMPO_ESPERA,TCICLO};
	private EnumMap<Parametro, Object> parametros;
	
	public ParametrosAgentes(){
		parametros = new EnumMap<Parametro, Object>(Parametro.class);
	}

	public void set(Parametro p, Object v){
		parametros.put(p, v);
	}
	
	public int getInt(Parametro p){
		return (Integer)parametros.get(p);
	}
	public double getDouble(Parametro p){
		return (Double)parametros.get(p);
	}
}
