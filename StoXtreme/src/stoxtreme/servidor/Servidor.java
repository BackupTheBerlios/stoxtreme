package stoxtreme.servidor;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.interfaz_remota.Administrador;
import stoxtreme.interfaz_remota.Stoxtreme;

import java.rmi.RemoteException;
import java.util.Hashtable;

public class Servidor implements Administrador, Stoxtreme{
	/**/
	private static Servidor _instance = new Servidor();
	public static Servidor getInstance(){
		return _instance;
	}
	/**/
	private static int IDS = 0;
	private Hashtable objetosBolsa=new Hashtable();
	private DatosEmpresas de;
	
	private Servidor(){
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
	public void iniciarServidor() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	public void pararServidor() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	public void iniciaSesion() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	public void finalizaSesion() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	public void showGUI() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	public void hideGUI() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
