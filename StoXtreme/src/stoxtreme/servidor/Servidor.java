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
import stoxtreme.servicio_web.AdministradorServiceLocator;
import stoxtreme.servidor.eventos.SistemaEventos;
import stoxtreme.servidor.gestion_usuarios.GestionUsuarios;
import stoxtreme.servidor.gui.MainFrameAdmin;
import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFrame;

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
	private DatosEmpresas datosEmpresa;
	// Variables del sistema
	private VariablesSistema variables;
	// Sistema de eventos
	private SistemaEventos sistEventos;
	// Reloj de sincronizacion del sistema
	private Reloj reloj;
	// Gestor de los usuarios
	private GestionUsuarios gestorUsuarios;
	// Parametros del sistema
	private ParametrosServidor param;
	// Registro Operaciones
	private RegistroOperaciones regOperaciones;
	// Interfaz grafica
	private MainFrameAdmin guiAdmin;
	// Estado de la bolsa
	private EstadoBolsa estadoBolsa;
	
	//Constructora
	private Servidor(){
		
	}
	public boolean login(String usr, String psw){
		boolean b = gestorUsuarios.conectaUsuario(usr,psw);
		if(b) AlmacenMensajes.getInstance().altaUsuario(usr);
		return b;
	}
	
	public boolean registro(String usr, String psw){
		return gestorUsuarios.registraUsuario(usr,psw);
	}
	
	public int insertarOperacion(String usuario, Operacion o){
		System.out.println("USUARIO "+usuario+" INSERTA OPERACION");
		IDS++;
		String empresa = o.getEmpresa();
		//objetosBolsa.get(empresa).insertaOperacion(usuario, IDS, o);
		regOperaciones.insertarOperacion(o);
		return IDS;
	}
	
	public void cancelarOperacion(String usuario, int i){
		System.out.println("USUARIO "+usuario+" CANCELA OPERACION");
	}
	
	public static void main(String[] argv){
		try {
			AdministradorServiceLocator  locator= new AdministradorServiceLocator();
			Administrador servidor =locator.getStoXtremeAdmin();
			/*Lanzamiento estatico del servidor para pruebas*/
			servidor.iniciarServidor();
			servidor.showGUI();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void iniciarServidor() throws RemoteException {
		// TODO Aqui debe ir toda la ejecucion
		/* TODO: Añadir al reloj todos los listener
		 * 	- Objetos Bolsa
		 *  - Variables del sistema
		 */
		guiAdmin = new MainFrameAdmin();
		param = new ParametrosServidor();
		gestorUsuarios=new GestionUsuarios(param.getFicheroRegistrados());
		guiAdmin.setModeloUsuarios(gestorUsuarios);
		objetosBolsa = new Hashtable<String, ObjetoBolsa>();
		datosEmpresa=new DatosEmpresas();
		variables = new VariablesSistema(param);
		guiAdmin.setModeloVariables(variables);
		objetosBolsa=datosEmpresa.creaObjetosBolsa(param);
		
		Enumeration<String> e = objetosBolsa.keys();
		while(e.hasMoreElements()){
			objetosBolsa.get(e.nextElement()).setVariablesSistema(variables, param);
		}
		
		sistEventos = new SistemaEventos(variables);
		guiAdmin.setModeloEventos(sistEventos);
		regOperaciones = new RegistroOperaciones();
		guiAdmin.setModeloOperaciones(regOperaciones);
		estadoBolsa = new EstadoBolsa(datosEmpresa);
		variables.addListener(sistEventos);
		variables.addListener(estadoBolsa);
		variables.insertaPrecios(objetosBolsa);
		guiAdmin.setModeloPrecios(estadoBolsa);
		guiAdmin.init();
		/*TODO SALE AL CERRAR!!! CAMBIAR ESTO DESPUES DE PROBARLO*/
		guiAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		reloj = new Reloj(param.getTiempo());
		// Insertamos en el reloj los objetos bolsa
		
		Enumeration eObBols = objetosBolsa.keys();
		while(eObBols.hasMoreElements()){
			reloj.addListener(objetosBolsa.get(eObBols.nextElement()));
		}
		reloj.addListener(variables);
		
		try{
			//sistEventos.insertarEvento("tiempo==5", "dihola_alonso", false);
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
		guiAdmin.setVisible(true);
	}
	public void hideGUI() throws RemoteException {
		// TODO Auto-generated method stub
		guiAdmin.setVisible(false);
	}
}
