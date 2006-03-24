package stoxtreme.herramienta_agentes;

import java.util.EnumMap;

public class ParametrosAgentes{
	public enum Parametro{NUM_AGENTES,TIEMPO_ESPERA};
	private EnumMap<Parametro, Object> parametros;
	
	public ParametrosAgentes(){
		parametros = new EnumMap<Parametro, Object>(Parametro.class);
	}

	public void set(Parametro p, Object v){
		parametros.put(p, v);
	}
	
	public Object get(Parametro p){
		return parametros.get(p);
	}
}
