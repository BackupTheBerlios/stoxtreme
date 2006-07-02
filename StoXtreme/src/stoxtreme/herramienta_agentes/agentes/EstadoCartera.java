package stoxtreme.herramienta_agentes.agentes;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class EstadoCartera {
	private Hashtable<String, Integer> cartera;
	/**
	 *  Constructor for the EstadoCartera object
	 */
	public EstadoCartera() {
		cartera = new Hashtable<String, Integer>();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa   Description of Parameter
	 *@param  cantidad  Description of Parameter
	 */
	public void insertaAcciones(String empresa, int cantidad) {
		int n = 0;
		if (cartera.containsKey(empresa)) {
			n = cartera.get(empresa).intValue();
		}
		cartera.put(empresa, cantidad + n);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa   Description of Parameter
	 *@param  cantidad  Description of Parameter
	 */
	public void quitaAcciones(String empresa, int cantidad) {
		int n = 0;
		if (cartera.containsKey(empresa)) {
			n = cartera.get(empresa);
		}
		cartera.put(empresa, n - cantidad);
		if (cantidad - n == 0) {
			cartera.remove(empresa);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@return          Description of the Returned Value
	 */
	public boolean tieneAccionesPosesion(String empresa) {
		return cartera.containsKey(empresa);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@return          Description of the Returned Value
	 */
	public int numeroAccionesPosesion(String empresa) {
		return cartera.containsKey(empresa) ? cartera.get(empresa) : 0;
	}
}
