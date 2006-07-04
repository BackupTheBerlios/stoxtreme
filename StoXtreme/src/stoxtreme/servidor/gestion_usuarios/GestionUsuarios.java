package stoxtreme.servidor.gestion_usuarios;

import java.util.Enumeration;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.gui.ModeloListaUsuariosConectados;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Clase que gestiona los registros de nuevos usuarios y
 *  la conexión de usuarios ya existentes al sistema
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class GestionUsuarios extends ModeloListaUsuariosConectados {
	private UsuariosConectados conectados;
	private UsuariosRegistrados registrados;


	/**
	 *  Constructor del objeto GestionUsuarios
	 *
	 *@param  fichReg  Ruta del fichero que contiene los datos de usuario registrados
	 */
	public GestionUsuarios(String fichReg) {
		super();
		conectados = new UsuariosConectados();
		registrados = new UsuariosRegistrados(fichReg);

		Enumeration<String> usrRegs = registrados.dameUsuarios();
		while (usrRegs.hasMoreElements()) {
			String id = usrRegs.nextElement();
			super.addUsuario(id);
			AlmacenMensajes.getInstance().altaUsuario(id);
		}
	}


	/**
	 *  Comprueba que el usuario esta dado de alta, que la contraseña es correcta
	 *  y que no estaba ya conectado.
	 *
	 *@param  id   Id de usuario
	 *@param  psw  Contraseña
	 *@return      Un booleano que indica si la conexión puede realizarsa
	 */
	public boolean conectaUsuario(String id, String psw) {
		//Si esta registrado y la contreseña coincide y no esta conectado,lo conecta
		if (registrados.existeUsuario(id) && registrados.compruebaPsw(id, psw) && !conectados.yaConectado(id)) {
			conectados.insertaUsuario(id);
			super.setEstadoUsuario(id, true);
			return true;
			//Si esta registrado y conectado y hace login otra vez, se desconecta
		}
		else
				if (registrados.existeUsuario(id) && conectados.yaConectado(id)) {
			conectados.quitarUsuario(id);
			this.desconectaUsuario(id);
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 *  Desconecta al usuario del sistema
	 *
	 *@param  id  Id del usuario a desconectar
	 *@return     Booleano que indica si la desconixón se ha producido
	 */
	public boolean desconectaUsuario(String id) {
		setEstadoUsuario(id, false);
		AlmacenMensajes.getInstance().enviaMensaje(new Mensaje("FIN", "FIN", id));
		return true;
	}


	/**
	 *  Si el id de usuario no existe, se añade a la tabla hash
	 *  y al arbol DOM (para que los cambios queden reflejados
	 *  en el fichero de registrados)
	 *
	 *@param  id   Id del usuario
	 *@param  psw  Contraseña
	 *@return      Booleano que indica si el nombre de usuario es válido (no estaba repetido)
	 */
	public boolean registraUsuario(String id, String psw) {
		if (!registrados.existeUsuario(id)) {
			registrados.insertaUsuario(id, psw);
			registrados.insertaEnDOM(id, psw);
			registrados.vuelcaEnFichero();
			super.addUsuario(id);
			AlmacenMensajes.getInstance().altaUsuario(id);
			return true;
		}
		else {
			return false;
		}

	}
}
