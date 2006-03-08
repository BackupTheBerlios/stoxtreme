package stoxtreme.servidor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import stoxtreme.servidor.eventos.SistemaEventos;

public class VariablesSistema implements RelojListener{
	public static String VAR_TIEMPO = "Tiempo";
	public static String VAR_TICK = "Tick";
	
	private Hashtable<String, Object> variables;
	private SistemaEventos eventos;
	private ArrayList<VariablesListener> listeners;
	
	public VariablesSistema(Parametros p){
		variables = new Hashtable<String, Object>();
		listeners = new ArrayList<VariablesListener>();
	}

	public void paso() {
		int t = (Integer)variables.get(VAR_TIEMPO);
		cambiaVariable(VAR_TIEMPO, t+1);
	}

	public float getTick() {
		return (Float)variables.get(VAR_TICK);
	}

	public double getPrecioInicial(String nombreEmpresa) {
		// TODO ¿¿¿Esto iria aqui???
		return 0;
	}
	
	public void addListener(VariablesListener listener){
		listeners.add(listener);
	}
	
	public void removeListener(VariablesListener listener){
		listeners.remove(listener);
	}
	
	public void cambiaVariable(String var, Object value){
		variables.put(var, value);
		Iterator<VariablesListener> it = listeners.iterator();
		while(it.hasNext()) it.next().cambioEstadoVariable(var, value);
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
