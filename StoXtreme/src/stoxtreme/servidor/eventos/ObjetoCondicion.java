package stoxtreme.servidor.eventos;

import java.util.*;

import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.eventos.evaluador.Evaluador;
import stoxtreme.servidor.eventos.evaluador.ParseException;

public class ObjetoCondicion {
	private String condicion;
	private ArrayList variablesUsadas;
	private VariablesSistema vSistema;
	private boolean valorAnterior;
	private boolean unavez;
	private Evaluador e;
	private boolean valorCambiado;
	private boolean activado;
	
	public ObjetoCondicion(String descripcion, VariablesSistema variables) throws ParseException{
		this(descripcion, variables, false, true);
	}
	
	public ObjetoCondicion(String descripcion, VariablesSistema variables, boolean unavez, boolean activado) throws ParseException{
		this.condicion = descripcion;
		this.unavez = unavez;
		this.vSistema = variables;
		
		e = new Evaluador(descripcion);
		
		valorAnterior = e.evalua(variables);
		variablesUsadas = e.getVariablesUsadas();
		
		for(int i=0; i<variablesUsadas.size(); i++){
			String vi = (String)variablesUsadas.get(i);
		}
		valorCambiado = false;
		this.activado = activado;
	}
	public boolean isActivado(){
		return activado;
	}
	public void setActivado(boolean b){
		this.activado = b;
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
		return condicion;
	}
}
