package stoxtreme.servidor.gestion_usuarios;

public class GestionUsuarios {
	private UsuariosConectados conectados;
	private UsuariosRegistrados registrados;
	
	public GestionUsuarios(){
		conectados = new UsuariosConectados();
		registrados = new UsuariosRegistrados();
	}
	public void leeDatos() throws Exception{
		// TODO Deberia leer los datos de los usuarios registrados
		registrados.leeDatos();
	}
	/* 
	 * 
	 */
	public boolean conectaUsuario(String id, String psw){
		// TODO
		return true;
	}
	/* Si el id de usuario no existe, se añade a la tabla hash
	 * y al fichero XML
	 */
	public boolean registraUsuario(String id, String psw){
		if (!registrados.existeUsuario(id)){
			registrados.añadeUsuario(id,psw);
			//TODO Añadir al XML
			return true;
		}else
			return false;
		
	}
}
