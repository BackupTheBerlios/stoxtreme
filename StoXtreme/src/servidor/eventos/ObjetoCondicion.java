package servidor.eventos;

import java.util.Hashtable;

public class ObjetoCondicion {
	String descripcion;
	Hashtable variables;
	boolean unavez;
	
	public ObjetoCondicion(String descripcion, Hashtable variables, boolean unavez) throws ExceptionMalformedEvent{
		this.descripcion = descripcion;
		this.variables = variables;
		this.unavez = unavez;
	}
	
	public boolean evalua(){
		return false;
	}
	
	public boolean isUnaVez(){
		return unavez;
	}
	
}
