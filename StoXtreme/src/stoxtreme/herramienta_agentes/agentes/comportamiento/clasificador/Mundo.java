package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;

import java.util.ArrayList;
import java.util.Hashtable;

public class Mundo {
	private Hashtable<String,ArrayList<Double>> precio;
	
	public static double redondeo(double numero, int nDecimales){
		return Math.floor((Math.pow(10, nDecimales)*numero)+0.5)/Math.pow(10, nDecimales);
	}
	
	public Mundo() {
		precio = new Hashtable<String,ArrayList<Double>>(); 
	}
	
	public double getPrecio(String empresa, int time){
		return precio.get(empresa).get(time);
	}

	public void registraPrecio(int time, String empresa, double precioEmpresa) {
		if(!precio.containsKey(empresa)){
			precio.put(empresa, new ArrayList<Double>());
		}
		precio.get(empresa).add(time, precioEmpresa);
	}
}
