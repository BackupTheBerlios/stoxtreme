package stoxtreme.servidor;
import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.interfaz_remota.Administrador;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.servidor.eventos.SistemaEventos;
import stoxtreme.servidor.gestion_usuarios.GestionUsuarios;
import stoxtreme.servidor.gui.MainFrameAdmin;
import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

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

/**
 *  Esta clase se ocupa de tramitar las peticiones de los usuarios
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Servidor implements Administrador, Stoxtreme {
	boolean iniciado = false;
	/*
	 *  COMPONENTES DEL SERVIDOR
	 */
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

	private Hashtable<Integer, ObjetoBolsa> mapaIDs = new Hashtable<Integer, ObjetoBolsa>();
	/*
	 *
	 */
	private static Servidor _instance = new Servidor();
	/*
	 *
	 */
	private static int IDS = 0;


	//Constructora
	/**
	 *  Constructor for the Servidor object
	 */
	private Servidor() {

	}


	/**
	 *  Gets the VariablesSistema attribute of the Servidor object
	 *
	 *@return    The VariablesSistema value
	 */
	public VariablesSistema getVariablesSistema() {
		return variables;
	}


	/**
	 *  Gets the GUI attribute of the Servidor object
	 *
	 *@return    The GUI value
	 */
	public JFrame getGUI() {
		return this.guiAdmin;
	}


	/**
	 *  Login del usuario
	 *
	 *@param  usr  Nombre de usuario
	 *@param  psw  contraseña
	 *@return      El número de empresas que hay en la simulación
	 */
	public int login(String usr, String psw) {
		boolean b = gestorUsuarios.conectaUsuario(usr, psw);
		if (!b) {
			return -1;
		}
		else {
			return Integer.parseInt(param.getNumEmpresas());
		}
	}


	/**
	 *  Registra a un nuevo usuario
	 *
	 *@param  usr  Nombre de usuario
	 *@param  psw  Contraseña
	 *@return      Un booleano que indica si toda ha ido bien
	 */
	public boolean registro(String usr, String psw) {
		return gestorUsuarios.registraUsuario(usr, psw);
	}


	/**
	 *  Inserta una operación en la cola del ObjetoBolsa correspondiente
	 *
	 *@param  usuario  Id del usuario que envía la operacion
	 *@param  o        Operación a insertar
	 *@return          El id de operación
	 */
	public int insertarOperacion(String usuario, Operacion o) {

		try {
			IDS++;
			String empresa = o.getEmpresa();
			objetosBolsa.get(empresa.toUpperCase()).insertaOperacion(usuario, IDS, o);
			regOperaciones.insertarOperacion(o);
			mapaIDs.put(IDS, objetosBolsa.get(empresa));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return IDS;
	}


	/**
	 *  Cancela una operación previamente insertada
	 *
	 *@param  usuario  Id del usuario que cancela la operación
	 *@param  idop     Id de la operación a cancelar
	 */
	public void cancelarOperacion(String usuario, int idop) {
		try {
			mapaIDs.get(idop).cancelarOperacion(idop);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *  Inicia el servidor de aplicaciones tomcat
	 *
	 *@exception  Exception  Excepción al iniciar Tomcat
	 */
	public void iniciarTomcat() throws Exception {
		PropertyConfigurator.configure("conf/log4j.properties");
		String path = new File(".").getAbsolutePath();
		Host host = null;
		Engine engine = null;

		System.setProperty("catalina.base", path);

		webServer = new Embedded();
		engine = webServer.createEngine();
		engine.setDefaultHost("localhost");
		host = webServer.createHost("localhost", path + "/webapps");
		host.setAutoDeploy(true);
		host.setDeployOnStartup(true);
		engine.addChild(host);

		//Context context = embedded.createContext("", path+"/webapps/ROOT");
		Context axisContext = webServer.createContext("/axis", path + "/webapps/axis");
		Context confContext = webServer.createContext("/Stoxtreme/config", path + "/conf");

		//host.addChild(context);
		host.addChild(axisContext);
		host.addChild(confContext);

		webServer.addEngine(engine);

		Connector connector = webServer.createConnector((InetAddress) null, 8080, false);
		webServer.addConnector(connector);

		try {
			webServer.start();
		}
		catch (LifecycleException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}


	/**
	 *  Inicia el servidor (Tomcat, interfaz gráfica)
	 *
	 *@exception  RemoteException  Excepción al iniciar el servicio
	 */
	public void iniciarServidor() throws RemoteException {
		try {
			iniciarTomcat();
		}
		catch (Exception e1) {

		}
		guiAdmin = new MainFrameAdmin();
		guiAdmin.setServidor(this);
		guiAdmin.init();

		guiAdmin.addWindowListener(
					new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							try {
								pararServidor();
							}
							catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
					});

	}


	/**
	 *  Para el servidor (para el reloj, finaliza la sesión en curso y para el Tomcat)
	 *
	 *@exception  RemoteException  Description of Exception
	 */
	public void pararServidor() throws RemoteException {
		if (reloj != null) {
			reloj.pararReloj();
		}
		finalizaSesion();
		try {
			webServer.stop();
		}
		catch (LifecycleException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}


	/**
	 *  Inicia una nueva sesión (carga los parámetros, inicia los ObjetoBolsa, el sistema de eventos y el reloj)
	 *
	 *@exception  RemoteException  Description of Exception
	 */
	public void iniciaSesion() throws RemoteException {
		// Consigue los parametros
		param = new ParametrosServidor();
		if (!guiAdmin.getOpciones().getValor("         Numero de empresas").equals("")) {
			param.modificarParams("numeroEmpresas", new Integer(guiAdmin.getOpciones().getValor("         Numero de empresas")).intValue());
		}
		if (!guiAdmin.getOpciones().getValor("Tick").equals("")) {
			param.modificarParams("tick", new Double(guiAdmin.getOpciones().getValor("Tick")));
		}
		if (!guiAdmin.getOpciones().getValor("Tiempo de fluctuacion").equals("")) {
			param.modificarParams("tiempo", new Long(guiAdmin.getOpciones().getValor("Tiempo de fluctuacion")));
		}
		if (guiAdmin.getOpciones().getValor("Fichero de empresas") != null) {
			param.modificarParams("ficheroEmpresas", guiAdmin.getOpciones().getValor("Fichero de empresas"));
		}
		if (guiAdmin.getOpciones().getValor("Fichero de usuarios") != null) {
			param.modificarParams("ficheroRegistrados", guiAdmin.getOpciones().getValor("Fichero de usuarios"));
		}

		gestorUsuarios = new GestionUsuarios(param.getFicheroRegistrados());
		guiAdmin.setModeloUsuarios(gestorUsuarios);
		objetosBolsa = new Hashtable<String, ObjetoBolsa>();
		datosEmpresa = new DatosEmpresas();
		variables = new VariablesSistema();
		guiAdmin.setModeloVariables(variables);
		objetosBolsa = datosEmpresa.creaObjetosBolsa(param);

		Enumeration<String> e = objetosBolsa.keys();
		while (e.hasMoreElements()) {
			objetosBolsa.get(e.nextElement()).setVariablesSistema(variables,
					param);
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
		guiAdmin.iniciaGUISesion();

		reloj = new Reloj(param.getTiempo());
		// Insertamos en el reloj los objetos bolsa
		Enumeration eObBols = objetosBolsa.keys();
		while (eObBols.hasMoreElements()) {
			reloj.addListener(objetosBolsa.get(eObBols.nextElement()));
		}
		reloj.addListener(variables);
		//}

		try {
			//sistEventos.insertarEvento("tiempo==5", "dihola_alonso", false);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		// Empezar cogiendo el tiempo de paso
		// Crear un Timer que ejecute run cada tiempo de paso
		reloj.iniciarReloj();
		// Inicia la sesion
	}


	/**
	 *  Para el reloj, pausando la sesión
	 */
	public void pausarSesion() {
		reloj.pararReloj();
	}


	/**
	 *  Vuelve a activar el reloj, reanudando la sesión
	 */
	public void reanudarSesion() {
		reloj.reanudarReloj();
	}


	/**
	 *  Termina la simulación en curso
	 *
	 *@exception  RemoteException  Description of Exception
	 */
	public void finalizaSesion() throws RemoteException {
		AlmacenMensajes.getInstance().insertarMensajeGlobal(new Mensaje("FINSESION", "FINSESION", Mensaje.GLOBAL));
		// TODO: FALTA REINICIAR
	}


	/**
	 *  Muestra la interfaz gráfica de administrador
	 *
	 *@exception  RemoteException  Description of Exception
	 */
	public void showGUI() throws RemoteException {
		guiAdmin.setVisible(true);
	}


	/**
	 *  Oculta la interfaz gráfica de administrador
	 *
	 *@exception  RemoteException  Description of Exception
	 */
	public void hideGUI() throws RemoteException {
		guiAdmin.setVisible(false);
	}


	/**
	 *  Gets the Instance attribute of the Servidor class
	 *
	 *@return    The Instance value
	 */
	public static Servidor getInstance() {
		return _instance;
	}


	/**
	 *  The main program for the Servidor class
	 *
	 *@param  argv  The command line arguments
	 */
	public static void main(String[] argv) {
		try {
			Servidor.getInstance().iniciarServidor();
			Servidor.getInstance().showGUI();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	static {
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
