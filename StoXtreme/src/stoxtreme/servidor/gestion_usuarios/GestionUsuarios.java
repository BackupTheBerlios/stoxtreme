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
	
	public boolean conectaUsuario(String ID, String psw){
		// TODO
		return true;
	}
	
	public boolean registraUsuario(String ID, String psw){
		// TODO
		return true;
	}
}
