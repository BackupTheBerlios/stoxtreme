/*
 * TODO:
 * 	- Falta todo lo de los parametros
 *  - Falta la ejecucion
 *  - Falta con la interfaz grafica
 */

package stoxtreme.servidor;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.interfaz_remota.Administrador;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.servidor.superusuario.GUI.MainFrameAdmin;

import java.rmi.RemoteException;
import java.util.Hashtable;

public class Servidor implements Administrador, Stoxtreme, Runnable{
	/**/
	private static Servidor _instance = new Servidor();
	public static Servidor getInstance(){
		return _instance;
	}
	/**/
	private static int IDS = 0;
	private Hashtable objetosBolsa=new Hashtable();
	private DatosEmpresas de;
	private VariablesSistema variables;
	
	// TODO falta meter las variables del sistema y los parametros
	private Servidor(){
		de=new DatosEmpresas();
		objetosBolsa=de.creaObjetosBolsa("conf/empresas.xml", variables);
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
	
	public static void main(String[] argv){
		Servidor server=new Servidor();
	}
	public void iniciarServidor() throws RemoteException {
		// TODO Aqui debe ir toda la ejecucion
		// Empezar cogiendo el tiempo de paso
		// Crear un Timer que ejecute run cada tiempo de paso
	}
	
	public void pararServidor() throws RemoteException {
		// TODO Parar el Timer y destruir lo necesario
	}
	
	public void iniciaSesion() throws RemoteException {
		// TODO Mirar cual es la siguiente sesion (apertur, cierre)
		
	}
	public void finalizaSesion() throws RemoteException {
		// TODO Finaliza
		
	}
	public void showGUI() throws RemoteException {
		// TODO solo un gui.show
	}
	public void hideGUI() throws RemoteException {
		// TODO Auto-generated method stub
	}
	
	public void run(){
		// TODO AQUI VA LO GORDO
	}
}
