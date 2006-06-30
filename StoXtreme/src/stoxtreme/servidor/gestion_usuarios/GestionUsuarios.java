package stoxtreme.servidor.gestion_usuarios;

import java.util.Enumeration;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.gui.ModeloListaUsuariosConectados;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Description of the Class
 *
 *@author    Iv�n G�mez Edo, Itziar P�rez Garc�a, Alonso Javier Torres
 */
public class GestionUsuarios extends ModeloListaUsuariosConectados {
	private UsuariosConectados conectados;
	private UsuariosRegistrados registrados;


	/**
	 *  Constructor for the GestionUsuarios object
	 *
	 *@param  fichReg  Description of Parameter
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


	/*
	 *  Comprueba que el usuario esta dado de alta, que la contrase�a es correcta
	 *  y que no estaba ya conectado.
	 */
	/**
	 *  Description of the Method
	 *
	 *@param  id   Description of Parameter
	 *@param  psw  Description of Parameter
	 *@return      Description of the Returned Value
	 */
	public boolean conectaUsuario(String id, String psw) {
		//Si esta registrado y la contrese�a coincide y no esta conectado,lo conecta
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
	 *  Description of the Method
	 *
	 *@param  id  Description of Parameter
	 *@return     Description of the Returned Value
	 */
	public boolean desconectaUsuario(String id) {
		setEstadoUsuario(id, false);
		AlmacenMensajes.getInstance().enviaMensaje(new Mensaje("FIN", "FIN", id));
		return true;
	}


	/*
	 *  Si el id de usuario no existe, se a�ade a la tabla hash
	 *  y al arbol DOM (para que los cambios queden reflejados
	 *  en el fichero registrados.xml)
	 */
	/**
	 *  Description of the Method
	 *
	 *@param  id   Description of Parameter
	 *@param  psw  Description of Parameter
	 *@return      Description of the Returned Value
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
