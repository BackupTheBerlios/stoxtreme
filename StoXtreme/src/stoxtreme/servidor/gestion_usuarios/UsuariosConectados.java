package stoxtreme.servidor.gestion_usuarios;

import java.util.ArrayList;

public class UsuariosConectados {
	ArrayList <String> conectados;	
	
	//Constructora
	public UsuariosConectados(){
		conectados=new ArrayList <String>();
	}
	
	//Comprueba si el usuario ya esta conectado
	public boolean yaConectado(String id){
		return this.conectados.contains(id);
	}
	
	//Añade el usuario a la lista de conectados
	public void insertaUsuario(String id){
		this.conectados.add(id);
	}
}

