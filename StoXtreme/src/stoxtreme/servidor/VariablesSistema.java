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
	 *  Guarda el número de pasos que se han dado
	 */
	public static String VAR_TIEMPO = "TIEMPO";


	/**
	 *  Constructor del objeto VariablesSistema
	 */
	public VariablesSistema() {
		variables = new Hashtable<String, Double>();
		listeners = new ArrayList<VariablesListener>();
		cambiaVariable(VAR_TIEMPO, 0L);
	}


	/**
	 *  Cambia el valor asociado a una variable
	 *
	 *@param  variable  Variable a cambiar
	 *@param  valor     El valor de la variable
	 */
	public void setValue(String variable, double valor) {
		variables.put(variable, valor);
	}


	/**
	 *  Obtiene el valor asociado a la variable
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
	 *  Añade el listener especificado  para que reciba eventos de este componente.
	 *  Si el listener l es nulo, no se lanza excepción y no se ejecuta ninguna acción.
	 *
	 *@param  listener  El listener a añadir
	 */
	public void addListener(VariablesListener listener) {
		listeners.add(listener);
	}


	/**
	 *  Elimina el componente especificado para que ya no reciva eventos de este componente.
	 *  Este metodo no tiene efecto, ni lanza excepción, si el listener especificado por el 
	 *  argumento no había sido añadido a este componente. Si el listener l es nulo
	 *  no se lanza excepcion y no se realiza ninguna accion
	 *
	 *@param  listener  El listener a eliminar
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
