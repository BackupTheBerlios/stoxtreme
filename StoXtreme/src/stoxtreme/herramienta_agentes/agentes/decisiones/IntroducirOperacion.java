package stoxtreme.herramienta_agentes.agentes.decisiones;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.interfaz_remota.Operacion;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class IntroducirOperacion extends Decision {
	private Operacion o;


	/**
	 *  Constructor for the IntroducirOperacion object
	 *
	 *@param  op  Description of Parameter
	 */
	public IntroducirOperacion(Operacion op) {
		super();
		o = op;
	}


	/**
	 *  Sets the Agente attribute of the IntroducirOperacion object
	 *
	 *@param  agente  The new Agente value
	 */
	public void setAgente(Agente agente) {
		super.setAgente(agente);
		o.setIdEmisor(agente.getIDString());
	}


	/**
	 *  Description of the Method
	 */
	public void ejecuta() {
		String tipo;
		if (o.getTipoOp() == Operacion.COMPRA) {
			tipo = "compra";
		}
		else {
			tipo = "Venta";
		}
//		System.err.println(tipo+" "+o.getPrecio()+" "+o.getCantidad());
		agente.insertarOperacion(o);
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return "Introducir operacion " + o;
	}
}
