package stoxtreme.servidor.eventos;

import java.io.*;
import java.util.*;

import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.eventos.evaluador.Evaluador;
import stoxtreme.servidor.eventos.evaluador.ParseException;

public class ObjetoCondicion {
	private String descripcion;
	private ArrayList variablesUsadas;
	private VariablesSistema vSistema;
	private boolean valorAnterior;
	private boolean unavez;
	private Evaluador e;
	private boolean valorCambiado;
	
	public ObjetoCondicion(String descripcion, VariablesSistema variables) throws ParseException{
		this(descripcion, variables, false);
	}
	
	public ObjetoCondicion(String descripcion, VariablesSistema variables, boolean unavez) throws ParseException{
		this.descripcion = descripcion;
		this.unavez = unavez;
		this.vSistema = variables;
		
		e = new Evaluador(descripcion);
		
		valorAnterior = e.evalua(variables);
		variablesUsadas = e.getVariablesUsadas();
		
		for(int i=0; i<variablesUsadas.size(); i++){
			String vi = (String)variablesUsadas.get(i);
		}
		valorCambiado = false;
	}
	
	public void cambiaVariable(String s, Object valor){
		if(variablesUsadas.contains(s)){
			valorCambiado = true;
		}
	}
	public boolean evalua(){
		if(valorCambiado){
			try {
				boolean b = e.evalua(vSistema);
				valorCambiado = false;
				return b;
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

	public String getDescripcion() {
		return descripcion;
	}
}
