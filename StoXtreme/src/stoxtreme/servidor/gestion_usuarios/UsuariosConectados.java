package stoxtreme.servidor.gestion_usuarios;

import java.util.ArrayList;

public class UsuariosConectados {
	ArrayList conectados;	
	
	//Constructora
	public UsuariosConectados(){
		conectados=new ArrayList();
	}
	
	//Comprueba si el usuario ya esta conectado
	public boolean yaConectado(String id){
		return conectados.contains(id);
	}
	
	//Añade el usuario a la lista de conectados
	public void insertaUsuario(String id){
		conectados.add(id);
	}
}

