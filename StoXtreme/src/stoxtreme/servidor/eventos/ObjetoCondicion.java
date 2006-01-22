package stoxtreme.servidor.eventos;

import java.io.*;
import java.util.*;
import servidor.eventos.evaluador.*;
import stoxtreme.servidor.eventos.evaluador.Evaluador;
import stoxtreme.servidor.eventos.evaluador.ParseException;

public class ObjetoCondicion {
	/**
	 * @uml.property  name="descripcion"
	 */
	private String descripcion;
	/**
	 * @uml.property  name="variablesUsadas"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private ArrayList variablesUsadas;
	/**
	 * @uml.property  name="estadoVars"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Object" qualifier="s:java.lang.String java.lang.Object"
	 */
	private Hashtable estadoVars;
	/**
	 * @uml.property  name="valorAnterior"
	 */
	private boolean valorAnterior;
	/**
	 * @uml.property  name="unavez"
	 */
	private boolean unavez;
	/**
	 * @uml.property  name="e"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Evaluador e;
	/**
	 * @uml.property  name="valorCambiado"
	 */
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
