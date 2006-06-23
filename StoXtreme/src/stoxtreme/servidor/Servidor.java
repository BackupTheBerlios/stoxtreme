package stoxtreme.servidor;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.interfaz_remota.Administrador;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.servidor.eventos.SistemaEventos;
import stoxtreme.servidor.gestion_usuarios.GestionUsuarios;
import stoxtreme.servidor.gui.MainFrameAdmin;
import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Embedded;
import org.apache.log4j.PropertyConfigurator;

public class Servidor implements Administrador, Stoxtreme{
	/**/
	private static Servidor _instance = new Servidor();
	
	public static Servidor getInstance(){
		return _instance;
	}
	static{
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}	
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
	// Servidor Tomcat
	private Embedded webServer;
	
	//Constructora
	private Servidor(){
		
	}
	public boolean login(String usr, String psw){
		boolean b = gestorUsuarios.conectaUsuario(usr,psw);
		return b;
	}
	
	public boolean registro(String usr, String psw){
		return gestorUsuarios.registraUsuario(usr,psw);
	}
	
	public int insertarOperacion(String usuario, Operacion o){
		
		try{
			System.out.println("USUARIO "+usuario+" INSERTA OPERACION");
			IDS++;
			String empresa = o.getEmpresa();
			objetosBolsa.get(empresa.toUpperCase()).insertaOperacion(usuario, IDS, o);
			regOperaciones.insertarOperacion(o);
			mapaIDs.put(IDS, objetosBolsa.get(empresa));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return IDS;
	}
	
	private Hashtable<Integer, ObjetoBolsa> mapaIDs = new Hashtable<Integer,ObjetoBolsa>();
	public void cancelarOperacion(String usuario, int idop){
		try{
			mapaIDs.get(idop).cancelarOperacion(idop);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void iniciarTomcat() throws Exception{
		PropertyConfigurator.configure("conf/log4j.properties");
		String path = new File(".").getAbsolutePath();
		Host host = null;
		Engine engine = null;
		
		System.setProperty("catalina.base", path);
		
		webServer = new Embedded();
		engine = webServer.createEngine();
		engine.setDefaultHost("localhost");
		host = webServer.createHost("localhost", path+"/webapps");
		host.setAutoDeploy(true);
		host.setDeployOnStartup(true);
		engine.addChild(host);
		
		//Context context = embedded.createContext("", path+"/webapps/ROOT");
		Context axisContext = webServer.createContext("/axis", path+"/webapps/axis");
		Context confContext = webServer.createContext("/Stoxtreme/config", path+"/conf");
		
		//host.addChild(context);
		host.addChild(axisContext);
		host.addChild(confContext);
		
		webServer.addEngine(engine);
		
		Connector connector = webServer.createConnector((InetAddress)null,8080,false);
		webServer.addConnector(connector);
		
		try {
			webServer.start();
		} catch (LifecycleException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void main(String[] argv){
		try {
			Servidor.getInstance().iniciarServidor();
			Servidor.getInstance().showGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void iniciarServidor() throws RemoteException{
		try {
			iniciarTomcat();
		} catch (Exception e1) {
			
		}
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
		guiAdmin.setServidor(this);
		guiAdmin.init();
		
		/*TODO SALE AL CERRAR!!! CAMBIAR ESTO DESPUES DE PROBARLO*/
		guiAdmin.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				try {
					pararServidor();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		
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
	}
	

	public void pararServidor() throws RemoteException {
		reloj.pararReloj();
		try {
			webServer.stop();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public void iniciaSesion() throws RemoteException {
		// Consigue los parametros
		reloj.iniciarReloj();
		// Inicia la sesion
	}

	public void pausarSesion(){
		reloj.pararReloj();
	}
	
	public void reanudarSesion(){
		reloj.reanudarReloj();
	}
	
	public void finalizaSesion() throws RemoteException {
		// TODO Finaliza
	}
	
	public void showGUI() throws RemoteException {
		guiAdmin.setVisible(true);
	}
	public void hideGUI() throws RemoteException {
		guiAdmin.setVisible(false);
	}
	public VariablesSistema getVariablesSistema() {
		return variables;
	}
	public JFrame getGUI() {
		return this.guiAdmin;
	}
}
