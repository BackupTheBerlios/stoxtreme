package stoxtreme.herramienta_agentes.agentes.comportamiento;
import java.util.Hashtable;

public class EstadoCartera {
	private Hashtable<String, Integer> cartera;
	
	public EstadoCartera(){
		cartera = new Hashtable<String, Integer>();
	}

	public void insertaAcciones(String empresa, int cantidad) {
		int n = 0;
		if(cartera.contains(empresa)){
			n = cartera.get(empresa).intValue();
		}
		cartera.put(empresa, cantidad+n);
	}

	public void quitaAcciones(String empresa, int cantidad) {
		int n = 0;
		if(cartera.contains(empresa)){
			n = cartera.get(empresa);
		}
		cartera.put(empresa, cantidad-n);
		if(cantidad-n == 0)
			cartera.remove(empresa);
	}

	public void actualiza() {
		
	}

	public boolean tieneAccionesPosesion(String empresa) {
		return cartera.containsKey(empresa);
	}

	public int numeroAccionesPosesion(String empresa) {
		return cartera.get(empresa);
	}

}
