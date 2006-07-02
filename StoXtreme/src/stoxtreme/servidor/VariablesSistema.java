package stoxtreme.servidor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Iterator;

import stoxtreme.servidor.gui.ModeloTablaVariables;
import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;

/**
 *  Clase que representa a los objetos sobre los que se pueden añadir eventos
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
@SuppressWarnings("serial")
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
	 *@param  variable  Variable a cambiar
	 *@param  valor     El valor de la variable
	 */
	public void setValue(String variable, double valor) {
		variables.put(variable, valor);
	}


	/**
	 *  Gets the Value attribute of the VariablesSistema object
	 *
	 *@param  variable  La variable que me interesa
	 *@return           El valor de dicha variable
	 */
	public Object getValue(String variable) {
		return variables.get(variable);
	}


	/**
	 *  Ejecuta un paso sobre la variable VAR_TIEMPO
	 */
	public void paso() {
		long t = (long) variables.get(VAR_TIEMPO).longValue();
		cambiaVariable(VAR_TIEMPO, t + 1);
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
	 *  Si la variable ya existia, cambia su valor. Sino, la inserta y la inicializa.
	 *
	 *@param  var    Variable a cambiar
	 *@param  value  Nuevo valor
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
	 *  Añade los precios de todas las empresas a la lista de variables
	 *
	 *@param  objetosBolsa  Los ObjetoBolsa (empresas) de la simualación
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
	 *  Metodo para ver si una variable ya existe
	 *
	 *@param  variable  Nombre de la variable
	 *@return           Un booleano que me indica si ya existia
	 */
	public boolean hasVariable(String variable) {
		return variables.containsKey(variable);
	}
}
