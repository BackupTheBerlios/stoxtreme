package stoxtreme.servidor.gestion_usuarios;

public class GestionUsuarios {
	private UsuariosConectados conectados;
	private UsuariosRegistrados registrados;
	
	public GestionUsuarios(String fichReg){
		conectados = new UsuariosConectados();
		registrados = new UsuariosRegistrados(fichReg);
	}
	
	/* Comprueba que el usuario esta dado de alta, que la contraseña es correcta 
	 * y que no estaba ya conectado.
	 */
	public boolean conectaUsuario(String id, String psw){
		if (registrados.existeUsuario(id)&&registrados.compruebaPsw(id,psw)&&!conectados.yaConectado(id)){
			conectados.insertaUsuario(id);
			return true;
		}else
			return false;
	}
	
	/* Si el id de usuario no existe, se añade a la tabla hash
	 * y al arbol DOM (para que los cambios queden reflejados
	 * en el fichero registrados.xml)
	 */
	public boolean registraUsuario(String id, String psw){
		if (!registrados.existeUsuario(id)){
			registrados.insertaUsuario(id,psw);
			registrados.insertaEnDOM(id,psw);
			return true;
		}else
			return false;
		
	}
}
