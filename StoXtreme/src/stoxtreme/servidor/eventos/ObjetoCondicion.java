package stoxtreme.servidor.eventos;

import java.util.*;

import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.eventos.evaluador.Evaluador;
import stoxtreme.servidor.eventos.evaluador.ParseException;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ObjetoCondicion {
	private String condicion;
	private ArrayList variablesUsadas;
	private VariablesSistema vSistema;
	private boolean valorAnterior;
	private boolean unavez;
	private Evaluador e;
	private boolean valorCambiado;
	private boolean activado;


	/**
	 *  Constructor for the ObjetoCondicion object
	 *
	 *@param  descripcion         Description of Parameter
	 *@param  variables           Description of Parameter
	 *@exception  ParseException  Description of Exception
	 */
	public ObjetoCondicion(String descripcion, VariablesSistema variables) throws ParseException {
		this(descripcion, variables, false, true);
	}


	/**
	 *  Constructor for the ObjetoCondicion object
	 *
	 *@param  descripcion         Description of Parameter
	 *@param  variables           Description of Parameter
	 *@param  unavez              Description of Parameter
	 *@param  activado            Description of Parameter
	 *@exception  ParseException  Description of Exception
	 */
	public ObjetoCondicion(String descripcion, VariablesSistema variables, boolean unavez, boolean activado) throws ParseException {
		this.condicion = descripcion;
		this.unavez = unavez;
		this.vSistema = variables;

		e = new Evaluador(descripcion);

		valorAnterior = e.evalua(variables);
		variablesUsadas = e.getVariablesUsadas();

		for (int i = 0; i < variablesUsadas.size(); i++) {
			String vi = (String) variablesUsadas.get(i);
		}
		valorCambiado = false;
		this.activado = activado;
	}


	/**
	 *  Sets the Activado attribute of the ObjetoCondicion object
	 *
	 *@param  b  The new Activado value
	 */
	public void setActivado(boolean b) {
		this.activado = b;
	}


	/**
	 *  Gets the Activado attribute of the ObjetoCondicion object
	 *
	 *@return    The Activado value
	 */
	public boolean isActivado() {
		return activado;
	}


	/**
	 *  Gets the UnaVez attribute of the ObjetoCondicion object
	 *
	 *@return    The UnaVez value
	 */
	public boolean isUnaVez() {
		return this.unavez;
	}


	/**
	 *  Gets the Descripcion attribute of the ObjetoCondicion object
	 *
	 *@return    The Descripcion value
	 */
	public String getDescripcion() {
		return condicion;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  s      Description of Parameter
	 *@param  valor  Description of Parameter
	 */
	public void cambiaVariable(String s, Object valor) {
		if (variablesUsadas.contains(s)) {
			valorCambiado = true;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public boolean evalua() {
		if (valorCambiado) {
			try {
				boolean b = e.evalua(vSistema);
				valorCambiado = false;
				return b;
			}
			catch (ParseException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			return false;
		}
		else {
			return valorAnterior;
		}
	}
}
