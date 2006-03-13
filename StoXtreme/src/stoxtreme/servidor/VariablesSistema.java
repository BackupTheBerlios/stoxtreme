package stoxtreme.servidor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Iterator;

import stoxtreme.servidor.gui.ModeloTablaVariables;
import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;;

public class VariablesSistema extends ModeloTablaVariables implements RelojListener{
	/* TODAS LAS VARIABLES EN MAYUSCULAS!! NECESARIO PARA EL PARSER DE LOS EVENTOS*/
	public static String VAR_TIEMPO = "TIEMPO";
	
	private Hashtable<String, Object> variables;
	private ArrayList<VariablesListener> listeners;
	
	public VariablesSistema(ParametrosServidor p){
		variables = new Hashtable<String, Object>();
		listeners = new ArrayList<VariablesListener>();
		cambiaVariable(VAR_TIEMPO,0L);
	}

	public void paso() {
		long t = (Long)variables.get(VAR_TIEMPO);
		cambiaVariable(VAR_TIEMPO, t+1);
//		System.out.println(variables.get(VAR_TIEMPO));
	}

	public void addListener(VariablesListener listener){
		listeners.add(listener);
	}
	
	public void removeListener(VariablesListener listener){
		listeners.remove(listener);
	}
	
	public void cambiaVariable(String var, Object value){
		if(!variables.containsKey(var)){
			super.insertarVariable(var, value);
		}
		else{
			super.cambiaVariable(var, value);
		}
		variables.put(var, value);
		Iterator<VariablesListener> it = listeners.iterator();
		while(it.hasNext()) it.next().cambioEstadoVariable(var, value);
	}
	
	public void insertaPrecios(Hashtable<String,ObjetoBolsa> objetosBolsa){
		Enumeration e=objetosBolsa.keys();
		String clave=null;
		while (e.hasMoreElements()){
			clave=e.nextElement().toString();
			String cInsert = clave;
			cInsert = cInsert.toUpperCase();
			cInsert = "PRECIO_"+cInsert;
			this.cambiaVariable(cInsert,objetosBolsa.get(clave).getCotizacion());		}
	}
	
	public Object getValue(String variable){
		return variables.get(variable);
	}
	
	public void setValue(String variable, Object valor){
		variables.put(variable, valor);
	}

	public boolean hasVariable(String variable) {
		return variables.containsKey(variable);
	}
}
