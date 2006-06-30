package stoxtreme.servidor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Iterator;

import stoxtreme.servidor.gui.ModeloTablaVariables;
import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class VariablesSistema extends ModeloTablaVariables implements RelojListener {

	private Hashtable<String, Double> variables;
	private ArrayList<VariablesListener> listeners;
	/*
	 *  TODAS LAS VARIABLES EN MAYUSCULAS!! NECESARIO PARA EL PARSER DE LOS EVENTOS
	 */
	/**
	 *  Description of the Field
	 */
	public static String VAR_TIEMPO = "TIEMPO";


	/**
	 *  Constructor for the VariablesSistema object
	 */
	public VariablesSistema() {
		variables = new Hashtable<String, Double>();
		listeners = new ArrayList<VariablesListener>();
		cambiaVariable(VAR_TIEMPO, 0L);
	}


	/**
	 *  Sets the Value attribute of the VariablesSistema object
	 *
	 *@param  variable  The new Value value
	 *@param  valor     The new Value value
	 */
	public void setValue(String variable, double valor) {
		variables.put(variable, valor);
	}


	/**
	 *  Gets the Value attribute of the VariablesSistema object
	 *
	 *@param  variable  Description of Parameter
	 *@return           The Value value
	 */
	public Object getValue(String variable) {
		return variables.get(variable);
	}


	/**
	 *  Description of the Method
	 */
	public void paso() {
		long t = (long) variables.get(VAR_TIEMPO).longValue();
		cambiaVariable(VAR_TIEMPO, t + 1);
//		System.out.println(variables.get(VAR_TIEMPO));
	}


	/**
	 *  Adds the specified listener to receive events from this component. If
	 *  listener l is null, no exception is thrown and no action is performed.
	 *
	 *@param  listener  The feature to be added to the attribute
	 */
	public void addListener(VariablesListener listener) {
		listeners.add(listener);
	}


	/**
	 *  Removes the specified listener so that it no longer receives events from
	 *  this component. This method performs no function, nor does it throw an
	 *  exception, if the listener specified by the argument was not previously
	 *  added to this component. If listener l is null, no exception is thrown
	 *  and no action is performed.
	 *
	 *@param  listener  Contains the Listener for Event data.
	 */
	public void removeListener(VariablesListener listener) {
		listeners.remove(listener);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  var    Description of Parameter
	 *@param  value  Description of Parameter
	 */
	public void cambiaVariable(String var, double value) {
		if (!variables.containsKey(var)) {
			super.insertarVariable(var, value);
		}
		else {
			super.cambiaVariable(var, value);
		}
		variables.put(var, value);
		Iterator<VariablesListener> it = listeners.iterator();
		while (it.hasNext()) {
			it.next().cambioEstadoVariable(var, value);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  objetosBolsa  Description of Parameter
	 */
	public void insertaPrecios(Hashtable<String, ObjetoBolsa> objetosBolsa) {
		Enumeration e = objetosBolsa.keys();
		String clave = null;
		while (e.hasMoreElements()) {
			clave = e.nextElement().toString();
			String cInsert = clave;
			cInsert = cInsert.toUpperCase();
			cInsert = "PRECIO_" + cInsert;
			this.cambiaVariable(cInsert, objetosBolsa.get(clave).getCotizacion());
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  variable  Description of Parameter
	 *@return           Description of the Returned Value
	 */
	public boolean hasVariable(String variable) {
		return variables.containsKey(variable);
	}
}
