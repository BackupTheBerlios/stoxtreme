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
import stoxtreme.servidor.eventos.SistemaEventos;
import stoxtreme.servidor.gestion_usuarios.GestionUsuarios;
import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;
import stoxtreme.servidor.superusuario.GUI.MainFrameAdmin;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;

public class Servidor implements Administrador, Stoxtreme{
	/**/
	private static Servidor _instance = new Servidor();
	
	public static Servidor getInstance(){
		return _instance;
	}
	
	/**/
	private static int IDS = 0;
	/* COMPONENTES DEL SERVIDOR */
	// Empresas dentro de la bolsa
	private Hashtable<String, ObjetoBolsa> objetosBolsa;
	// Datos de las empresas
	private DatosEmpresas de;
	// Variables del sistema
	private VariablesSistema variables;
	// Sistema de eventos
	private SistemaEventos sistEventos;
	// Reloj de sincronizacion del sistema
	private Reloj reloj;
	// Gestor de los usuarios
	private GestionUsuarios gestorUsuarios;
	// Parametros del sistema
	private Parametros p;
	
	//Constructora
	private Servidor(){
		p = new Parametros();
		gestorUsuarios=new GestionUsuarios(p.getFicheroRegistrados());
		objetosBolsa = new Hashtable<String, ObjetoBolsa>();
		de=new DatosEmpresas();
		variables = new VariablesSistema(p);
		objetosBolsa=de.creaObjetosBolsa(p.getFicheroEmpresas(), variables);
		variables.insertaPrecios(objetosBolsa);
		this.sistEventos = new SistemaEventos(variables);
		reloj = new Reloj(p.getTiempo());
	}
	public boolean login(String usr, String psw){
		return gestorUsuarios.conectaUsuario(usr,psw);
	}
	
	public boolean registro(String usr, String psw){
		return gestorUsuarios.registraUsuario(usr,psw);
	}
	
	public int insertarOperacion(String usuario, Operacion o){
		System.out.println("USUARIO "+usuario+" INSERTA OPERACION");
		IDS++;
		String empresa = o.getEmpresa();
		objetosBolsa.get(empresa).insertaOperacion(usuario, IDS, o);
		return IDS;
	}
	
	public void cancelarOperacion(String usuario, int i){
		System.out.println("USUARIO "+usuario+" CANCELA OPERACION");
	}
	
	public static void main(String[] argv){
		try {
			/*Lanzamiento estatico del servidor para pruebas*/
			Servidor serv = Servidor.getInstance();
			serv.iniciarServidor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void iniciarServidor() throws RemoteException {
		// TODO Aqui debe ir toda la ejecucion
		/* TODO: Añadir al reloj todos los listener
		 * 	- Objetos Bolsa
		 *  - Variables del sistema
		 */
		// Insertamos en el reloj los objetos bolsa
		Enumeration e = objetosBolsa.keys();
		while(e.hasMoreElements()){
			reloj.addListener(objetosBolsa.get(e.nextElement()));
		}
		reloj.addListener(variables);
		
		try{
			sistEventos.insertarEvento("tiempo>2", "dihola", false);
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
		// Empezar cogiendo el tiempo de paso
		// Crear un Timer que ejecute run cada tiempo de paso
		reloj.iniciarReloj();
	}
	
	public void pararServidor() throws RemoteException {
		// TODO Parar el Timer y destruir lo necesario
		reloj.pararReloj();
	}
	
	public void iniciaSesion() throws RemoteException {
		// TODO Mirar cual es la siguiente sesion (apertur, cierre)
		reloj.reanudarReloj();
	}
	public void finalizaSesion() throws RemoteException {
		// TODO Finaliza
		reloj.pararReloj();
	}
	public void showGUI() throws RemoteException {
		// TODO solo un gui.show
	}
	public void hideGUI() throws RemoteException {
		// TODO Auto-generated method stub
	}
}
