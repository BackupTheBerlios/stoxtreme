package stoxtreme.servidor.gestion_usuarios;

public class GestionUsuarios {
	/**
	 * @uml.property  name="conectados"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private UsuariosConectados conectados;
	/**
	 * @uml.property  name="registrados"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
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
