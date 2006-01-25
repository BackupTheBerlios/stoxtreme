package stoxtreme.servidor;
import stoxtreme.interfaz_remota.Operacion;

public class Servidor {
	private static int IDS = 0;
	
	public boolean login(String usr, String psw){
		System.out.println("REGISTRO USUARIO " + usr + " " + psw);
		return true;
	}
	
	public boolean registro(String usr, String psw){
		System.out.println("LOGIN USUARIO " + usr + " " + psw);
		return true;
	}
	
	public synchronized int insertarOperacion(String usuario, Operacion o){
		System.out.println("USUARIO "+usuario+" INSERTA OPERACION");
		return IDS++;
	}
	
	public void cancelarOperacion(String usuario, int i){
		System.out.println("USUARIO "+usuario+" CANCELA OPERACION");
	}
}
