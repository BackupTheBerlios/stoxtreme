package stoxtreme.servidor.gestion_usuarios;

public class GestionUsuarios {
	private UsuariosConectados conectados;
	private UsuariosRegistrados registrados;
	
	public GestionUsuarios(String fichReg){
		conectados = new UsuariosConectados();
		registrados = new UsuariosRegistrados(fichReg);
	}
	public void leeDatos() throws Exception{
		// TODO Deberia leer los datos de los usuarios registrados
		registrados.leeDatos();
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
	 * y al fichero XML.
	 */
	public boolean registraUsuario(String id, String psw){
		if (!registrados.existeUsuario(id)){
			registrados.insertaUsuario(id,psw);
			//TODO Añadir al XML
			return true;
		}else
			return false;
		
	}
}
