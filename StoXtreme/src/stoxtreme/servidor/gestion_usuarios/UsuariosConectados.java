package stoxtreme.servidor.gestion_usuarios;

import java.util.ArrayList;

import stoxtreme.servidor.gui.MainFrameAdmin;

/**
 * 	Clase que guarda los usuarios conectados al sistema en cada momento
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class UsuariosConectados {
	ArrayList<String> conectados;


	/**
	 *  Constructordel objeto UsuariosConectados
	 */
	public UsuariosConectados() {
		conectados = new ArrayList<String>();
	}


	/**
	 *  Comprueba si el usuario ya esta conectado
	 *
	 *@param  id  Id de usuario
	 *@return     Booleano que indica si el usuario estaba ya conectado o no
	 */
	public boolean yaConectado(String id) {
		return this.conectados.contains(id);
	}


	/**
	 *  Añade el usuario a la lista de conectados
	 *
	 *@param  id  Id de usuario
	 */
	public void insertaUsuario(String id) {
		this.conectados.add(id);
	}


	/**
	 *  Quita al usuario de la lista de conectados
	 *
	 *@param  id  Id de usuario
	 */
	public void quitarUsuario(String id) {
		this.conectados.remove(id);
	}
}

