package stoxtreme.herramienta_agentes.agentes.comportamiento;

import java.util.ArrayList;
import java.util.HashMap;

import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;

public class ModeloSocial {
	private ParametrosSocial p;
	private ArrayList<IDAgente> conocidos;
	private HashMap<IDAgente, Double> fiabilidad;
	private int nConocidos;
	
	public ModeloSocial(ParametrosSocial p){
		this.p = p;
		nConocidos = p.getParamInt(ParametrosSocial.Parametro.NUMERO_CONOCIDOS);
		conocidos = new ArrayList<IDAgente>();
	}

	public double getFiabilidad(IDAgente emisor) {
		double modificador = 1.0;
		if(fiabilidad != null && fiabilidad.containsKey(emisor))
			modificador = fiabilidad.get(emisor);
		return p.getParamDouble(ParametrosSocial.Parametro.FIABILIDAD_RUMOR)*modificador;
	}
	
	public void addConocido(IDAgente agente){
		conocidos.add(agente);
	}
	
	public void addConocido(IDAgente agente, double modificador){
		if(fiabilidad == null)
			fiabilidad = new HashMap<IDAgente,Double>();
		fiabilidad.put(agente,modificador);
		conocidos.add(agente);
	}
	
	public void incrementarConfianza(IDAgente agente){
		double antiguo = fiabilidad.containsKey(agente)?fiabilidad.get(agente):1.0;
		double incremento = p.getParamDouble(ParametrosSocial.Parametro.INCREMENTO_FIABILIDAD);
		fiabilidad.put(agente, antiguo*(1+incremento));
	}
	
	public void decrementarConfianza(IDAgente agente){
		double antiguo = fiabilidad.containsKey(agente)?fiabilidad.get(agente):1.0;
		double decremento = p.getParamDouble(ParametrosSocial.Parametro.DECREMENTO_FIABILIDAD);
		fiabilidad.put(agente, antiguo*decremento);
	}
	
	public void removeConocido(IDAgente agente){
		conocidos.remove(agente);
	}
	
	public IDAgente getConocidoAleatorio(){
		int r = (int)Math.random()*conocidos.size();
		return conocidos.get(r);
	}
	
	public boolean hagoCasoAnuncio(){
		double r = Math.random();
		return (r > p.getParamDouble(ParametrosSocial.Parametro.MANIPULABILIDAD));
	}

	public int getNumConocidos() {
		return nConocidos;
	}
}
