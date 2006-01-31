package stoxtreme.servidor;
import stoxtreme.interfaz_remota.Operacion;
import java.util.Hashtable;

public class Servidor {
	private static int IDS = 0;
	private Hashtable objetosBolsa=new Hashtable();
	private DatosEmpresas de;
	
	public Servidor(){
		de=new DatosEmpresas();
		objetosBolsa=de.creaObjetosBolsa("conf/empresas.xml");
		}
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
	
	public void insertarOBolsa(String nombre, float cotizacion, String ficheroInf){
		
	}
	public static void main(String[] argv){
		Servidor server=new Servidor();
	}
}
