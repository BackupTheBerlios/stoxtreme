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
	
	public boolean conectaUsuario(String id, String psw){
		// TODO
		return true;
	}
	
	public boolean registraUsuario(String id, String psw){
		if (!registrados.existeUsuario(id)){
			//TODO Añadir a la tabla hash
			//TODO Añadir al XML
		}
		return true;
	}
}
