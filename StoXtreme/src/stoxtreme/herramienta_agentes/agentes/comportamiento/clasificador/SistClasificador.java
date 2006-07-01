package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;
import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;

public class SistClasificador{
	private ArrayList<Regla> reglas;
	private int numReglas;
	
	public SistClasificador(int numReglas) {
		this.numReglas = numReglas;
		reglas = new ArrayList<Regla>();
	}
	
	public void iniciaAleatorio(){
		for(int i=0; i<numReglas; i++){
			Regla regla = new Regla();
			reglas.add(regla);
		}
	}

	public Decision encajaReglas(Mundo mundoClasificador) {
		// Primero consigo las reglas que encajen en el mundo
		// Con las reglas que han encajado tenemos que pujar
		
		return null;
	}
	
	public int getNumReglas(){
		return reglas.size();
	}
	
	public Regla getRegla(int i){
		return reglas.get(i);
	}

	public void addRegla(Regla r) {
		reglas.add(r);
	}

	public double aptitudSimilitud(Regla regla) {
		double acumulado = 0.0;
		for(int i=0; i<reglas.size(); i++){
			double bonificacion = reglas.get(i).getBonificacion();
			double similitud = reglas.get(i).similitud(regla);
			acumulado += bonificacion*similitud;
		}
		return acumulado;
	}
}
