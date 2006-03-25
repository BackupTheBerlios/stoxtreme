package stoxtreme.servidor.gestion_usuarios;

import java.util.Enumeration;

import stoxtreme.servidor.gui.ModeloListaUsuariosConectados;

public class GestionUsuarios extends ModeloListaUsuariosConectados{
	private UsuariosConectados conectados;
	private UsuariosRegistrados registrados;
	
	public GestionUsuarios(String fichReg){
		super();
		conectados = new UsuariosConectados();
		registrados = new UsuariosRegistrados(fichReg);
		
		Enumeration<String> usrRegs = registrados.dameUsuarios();
		while(usrRegs.hasMoreElements())
			super.addUsuario(usrRegs.nextElement());
	}
	
	/* Comprueba que el usuario esta dado de alta, que la contraseña es correcta 
	 * y que no estaba ya conectado.
	 */
	public boolean conectaUsuario(String id, String psw){
		//Si esta registrado y la contreseña coincide y no esta conectado,lo conecta
		if (registrados.existeUsuario(id) && registrados.compruebaPsw(id,psw) && !conectados.yaConectado(id)){
			conectados.insertaUsuario(id);
			super.setEstadoUsuario(id, true);
			return true;
			//Si esta registrado y conectado y hace login otra vez, se desconecta
		}else
			if (registrados.existeUsuario(id) && conectados.yaConectado(id)){
				conectados.quitarUsuario(id);
				this.desconectaUsuario(id);
				return true;
			}else
				return false;
	}
	
	public boolean desconectaUsuario(String id){
		setEstadoUsuario(id, false);
		return true;
	}
	/* Si el id de usuario no existe, se añade a la tabla hash
	 * y al arbol DOM (para que los cambios queden reflejados
	 * en el fichero registrados.xml)
	 */
	public boolean registraUsuario(String id, String psw){
		if (!registrados.existeUsuario(id)){
			registrados.insertaUsuario(id,psw);
			registrados.insertaEnDOM(id,psw);
			//TODO es ineficiente que vuelque cada vez que se registra uno
			//habria que hacerlo al salir del servidor
			registrados.vuelcaEnFichero();
			addUsuario(id);
			return true;
		}else
			return false;
		
	}
}
