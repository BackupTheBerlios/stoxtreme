package stoxtreme.servidor.gestion_usuarios;

import java.util.ArrayList;

import stoxtreme.servidor.gui.MainFrameAdmin;

/**
 *  Description of the Class
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public class UsuariosConectados {
	ArrayList<String> conectados;


	//Constructora
	/**
	 *  Constructor for the UsuariosConectados object
	 */
	public UsuariosConectados() {
		conectados = new ArrayList<String>();
	}


	//Comprueba si el usuario ya esta conectado
	/**
	 *  Description of the Method
	 *
	 *@param  id  Description of Parameter
	 *@return     Description of the Returned Value
	 */
	public boolean yaConectado(String id) {
		return this.conectados.contains(id);
	}


	//A�ade el usuario a la lista de conectados
	/**
	 *  Description of the Method
	 *
	 *@param  id  Description of Parameter
	 */
	public void insertaUsuario(String id) {
		this.conectados.add(id);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  id  Description of Parameter
	 */
	public void quitarUsuario(String id) {
		this.conectados.remove(id);
	}
}

