package servidor.eventos;

import java.util.*;
import java.io.*;
import servidor.eventos.evaluador.*;

public class ObjetoCondicion {
	private String descripcion;
	private ArrayList variablesUsadas;
	private Hashtable estadoVars;
	private boolean valorAnterior;
	private boolean unavez;
	private Evaluador e;
	private boolean valorCambiado;
	
	public ObjetoCondicion(String descripcion, Hashtable variables) throws ParseException{
		this(descripcion, variables, false);
	}
	
	public ObjetoCondicion(String descripcion, Hashtable variables, boolean unavez) throws ParseException{
		this.descripcion = descripcion;
		this.unavez = unavez;
		
		e = new Evaluador(descripcion);
		
		valorAnterior = e.evalua(variables);
		variablesUsadas = e.getVariablesUsadas();
		
		estadoVars= new Hashtable();
		
		for(int i=0; i<variablesUsadas.size(); i++){
			String vi = (String)variablesUsadas.get(i);
			estadoVars.put(vi, variables.get(vi));
		}
		valorCambiado = false;
	}
	
	public void cambiaVariable(String s, Object valor){
		if(variablesUsadas.contains(s)){
			valorCambiado = true;
			estadoVars.put(s, valor);
		}
	}
	public boolean evalua(){
		if(valorCambiado){
			try {
				return e.evalua(estadoVars);
			} catch (ParseException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			return false;
		}
		else
			return valorAnterior;
	}
	
	public boolean isUnaVez(){
		return this.unavez;
	}
}
