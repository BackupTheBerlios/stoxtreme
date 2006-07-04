package stoxtreme.cliente;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import stoxtreme.cliente.gui.MainFrameCliente;
import stoxtreme.cliente.gui.DialogoInicial;
import stoxtreme.cliente.infoLocal.InfoLocal;
import stoxtreme.cliente.infoLocal.XMLDownloader;
import stoxtreme.herramienta_agentes.HerramientaAgentes;
import stoxtreme.herramienta_agentes.ParametrosAgentes;
import stoxtreme.interfaz_remota.*;
import stoxtreme.servicio_web.StoxtremeServiceLocator;
import stoxtreme.sistema_mensajeria.receptor.*;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class Cliente {
	private String nUsuario;
	private String password;
	private EstadoBolsa eBolsa;
	private OperacionesPendientes opPendientes;
	private CarteraAcciones cartera;
	private MainFrameCliente gui;
	private Stoxtreme servidor;
	private ReceptorMensajes receptor;
	private HerramientaAgentes hAgentes;
	private int numEmpresas;
	private static final String URLAXIS = "/axis/services/";
	private static final String URLCONF = "/Stoxtreme/config/";
	private static final String FICH_CONF_AGENTES = "conf/confAgentes.xml";


	/**
	 *  Nos dice el número de usuario
	 *
	 *@return    valor de NUsuario 
	 */
	public String getNUsuario() {
		return nUsuario;
	}
	static{
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("conf/log4j.properties");
		Cliente c = new Cliente();
		try {
			c.init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 *  obtiene los ficheros de las empresas del cliente
	 *
	 *@param  fichEmpresas  fichero descriptor de las empresas
	 *@return               una lista de nombre de variables
	 */
	public String[] getNombreFicheros(String fichEmpresas) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//Lista de nombres de ficheros de configuracion
		String[] ficheros = new String[numEmpresas];
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(fichEmpresas));
			NodeList nl = document.getElementsByTagName("emp");
			String ruta = null;
			//Obtengo los nombres de todas las empresas
			for (int i = 0; nl != null && i < numEmpresas; i++) {
				ruta = ((Element) nl.item(i)).getTextContent().trim();
				String[] nombre = ruta.split("/");
				ficheros[i] = nombre[1].toString();
			}
		}
		catch (SAXException sxe) {
			// Error generated during parsing
			Exception x = sxe;
			if (sxe.getException() != null) {
				x = sxe.getException();
			}
			x.printStackTrace();

		}
		catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();
		}
		catch (IOException ioe) {
			// I/O error
			ioe.printStackTrace();
		}
		return ficheros;
	}


	/**
	 *  Obtenemos el estado de la bolsa
	 *
	 *@return    Valor de EstadoBolsa
	 */
	public EstadoBolsa getEstadoBolsa() {
		return eBolsa;
	}


	/**
	 *  Inicialización del cliente
	 *
	 *@exception  Exception  Lanza excepciones en caso de tener algún problema con el servidor
	 */
	public void init() throws Exception {
		boolean conectado = false;
		Object despertador = new Object();
		DialogoInicial dialogoInicial = new DialogoInicial(despertador);

		while (!conectado) {
			dialogoInicial.setOperacion(0);
			dialogoInicial.setVisible(true);

			synchronized (despertador) {
				despertador.wait();
			}

			///
			int op = dialogoInicial.getOperacion();
			String user = dialogoInicial.getusuario();
			String psw = dialogoInicial.getpass();
			URL direccion = new URL(dialogoInicial.getDireccionServidor() + URLAXIS + "StoXtreme");
			// Con la direccion conectamos al servidor
			StoxtremeServiceLocator locator = new StoxtremeServiceLocator();
			servidor = locator.getStoXtreme(direccion);

			//Si cierra la ventana, salimos
			if (op == 0) {
				System.exit(0);
			}
			//Si hace login
			if (op == 1) {
				if (user.trim().equals("") || (psw.trim().equals(""))) {
					JOptionPane.showMessageDialog(dialogoInicial, "Por favor, rellene todos los campos",
							"Revise sus datos", JOptionPane.WARNING_MESSAGE);
				}
				else {
					try {
						int login = servidor.login(user.trim(), psw.trim());
						if (login == -1) {
							JOptionPane.showMessageDialog(dialogoInicial, "El usuario no existe o el password es erroneo",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else {
							numEmpresas = login;
							conectado = true;
						}

					}
					catch (Exception ex) {
						JOptionPane.showMessageDialog(dialogoInicial, "El servidor parece estar caido. \n Intentelo de nuevo mas tarde",
								"Error de conexion", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			//Si crea un nuevo usuario
			if (op == 2) {
				if (user.trim().equals("") || psw.trim().equals("")) {
					JOptionPane.showMessageDialog(dialogoInicial, "Por favor, rellene todos los campos",
							"Revise sus datos", JOptionPane.WARNING_MESSAGE);
				}
				else {
					if (!validar(user.trim()) || !validar(psw.trim())) {
						JOptionPane.showMessageDialog(dialogoInicial, "Su nombre de usuario o password \n contienen caracteres no validos. \n Solo pueden tener numeros, letras y '_'",
								"Revise sus datos", JOptionPane.WARNING_MESSAGE);
					}
					else {
						try {
							boolean registro = servidor.registro(user.trim(), psw.trim());
							if (!registro) {
								JOptionPane.showMessageDialog(dialogoInicial, "Ya existe un usuario con ese nombre",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
							else {
								numEmpresas = servidor.login(user, psw);
								conectado = true;
							}
						}
						catch (Exception ex) {
							JOptionPane.showMessageDialog(dialogoInicial, "El servidor parece estar caido. \n Intentelo de nuevo mas tarde",
									"Error de conexion", JOptionPane.ERROR_MESSAGE);
						}
					}

				}
			}
			this.nUsuario = user;
			this.password = psw;
		}

		System.err.println("Obteniendo ficheros");
		this.obtenerFicheros(dialogoInicial.getDireccionServidor());
		System.err.println("Creando Operaciones Pendientes");
		opPendientes = new OperacionesPendientes();
		System.err.println("Creando Cartera");
		cartera = new CarteraAcciones();
		System.err.println("Creando Estado de la Bolsa: " + numEmpresas);
		InfoLocal.crearInstancia("./conf/cliente/empresas.xml", numEmpresas);
		InfoLocal infoLocal = InfoLocal.getInstance();
		eBolsa = new EstadoBolsa(infoLocal);
		System.err.println("Creando Parametros");
		System.err.println("Creando Agentes");
		hAgentes = new HerramientaAgentes(nUsuario);
		gui = new MainFrameCliente(this, cartera.getMCartera(), opPendientes.getMOpPendientes(), eBolsa, hAgentes);
		hAgentes.setFrame(gui);
		gui.init();
		gui.pack();
		gui.setVisible(true);
		// Lo ultimo que hacemos es dar de alta en el receptor
		receptor = new ReceptorMensajes(nUsuario, ReceptorMensajes.WEB_SERVICE, dialogoInicial.getDireccionServidor() + URLAXIS + "StoXtremeMsg");
		receptor.addListener(new ManejadorMensajes(this));
	}


	/**
	 *  Iniciamos los agentes asociados al cliente
	 *
	 *@param  parametros  Parametros seleccionados por el cliente
	 */
	public void iniciarHerramientaAgentes(ParametrosAgentes parametros) {
		try {
			hAgentes.start(parametros, FICH_CONF_AGENTES, servidor, eBolsa);
		}
		catch (Exception e) {
			System.err.println("Error al iniciar los agentes. Exit");
			e.printStackTrace();
			try {
				desloguea();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}


	/**
	 *  Inserta una operacion realizada por el cliente
	 *
	 *@param  op             Tipo de operación
	 *@exception  Exception  Lanza una excepcion si hay problema con el servidor
	 */
	public void insertarOperacion(Operacion op) throws Exception {
		int idOp = servidor.insertarOperacion(nUsuario, op);
		opPendientes.inserta(idOp, op);
	}


	/**
	 *  Cancela una operacion realizada por el cliente
	 *
	 *@param  op             Tipo de operación
	 *@exception  Exception  Lanza una excepcion si hay problema con el servidor
	 */
	public void cancelarOperacion(int op) throws Exception {
		servidor.cancelarOperacion(nUsuario, op);
	}


	/**
	 *  Realiza la notificación de una operacion realizada
	 *
	 *@param  idDestino  Id del usuario destino
	 *@param  idOp       Id de la operación destino
	 *@param  cantidad   Cantidad de acciones
	 *@param  precio     Precio de las acciones
	 */
	public void notificaOperacion(String idDestino, int idOp, int cantidad, double precio) {
		if (nUsuario.equals(idDestino)) {
			Operacion o = opPendientes.dameOperacion(idOp);
			cartera.actualiza(o, cantidad);
			int vOp = Math.abs(idOp);
			opPendientes.quitaOperacion(vOp);
		}
		else {
			hAgentes.notificarOperacion(idDestino, idOp, cantidad, precio);
		}
	}


	/**
	 *  Realiza la cancelación de una operacion realizada
	 *
	 *@param  idDestino  Id del usuario destino
	 *@param  idOp       Id de la operación destino
	 */
	public void notificaCancelacion(String idDestino, int idOp) {
		if (nUsuario.equals(idDestino)) {
			opPendientes.quitaOperacion(idOp);
		}
		else {
			hAgentes.notificarCancelacion(idDestino, idOp);
		}
	}


	/**
	 *  Desconexión del cliente
	 *
	 *@exception  Exception  Por si hay conflicto con el servidor
	 */
	public void desloguea() throws Exception {
		System.out.println("Deslogea");
		servidor.login(nUsuario, password);
		System.out.println("Fin del cliente.");
		System.exit(0);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  direccion      Description of Parameter
	 *@exception  Exception  Description of Exception
	 */
	public void obtenerFicheros(String direccion) throws Exception {
		XMLDownloader.download(new File("./conf/cliente/empresas.xml"), new URL(direccion + URLCONF + "empresas.xml"));
		String[] fichEmpresas = this.getNombreFicheros("./conf/cliente/empresas.xml");
		XMLDownloader.downloadAll("./conf/cliente", direccion + URLCONF, fichEmpresas);
	}


	/**
	 *  Comprueba si la cadena introducida es correcta
	 *  
	 *
	 *@param  s  Cadena a comprobar
	 *@return    Nos dice si es valido o no
	 */
	public boolean validar(String s) {
		boolean valido = true;
		final char[] chars = s.toCharArray();
		for (int x = 0; x < chars.length; x++) {
			final char c = chars[x];
			if (!((c >= 'a') && (c <= 'z')) && !((c >= 'A') && (c <= 'Z')) && !((c >= '0') && (c <= '9')) && !(c == '_')) {
				valido = false;
			}
		}
		return valido;
	}


	/**
	 *  Nos muestra una ventana con un mensaje
	 *
	 *@param  contenido  Contenido del mensaje
	 */
	public void informar(String contenido) {
		if (gui != null) {
			JOptionPane.showMessageDialog(gui, contenido);
		}
	}


	/**
	 *  Detiene los agentes creados por el cliente
	 */
	public void detenerHerramientaAgentes() {
		hAgentes.pausarAgentes();
	}


	/**
	 *  Reanuda los agentes creados que estaban pausados
	 */
	public void reanudarHerramientaAgentes() {
		hAgentes.reanudarAgentes();
	}


	/**
	 *  Vuelve a construir una herramienta de agentes 
	 *
	 *@param  parametros  Parámetros seleccionados por el cliente
	 */
	public void reiniciarHerramientaAgentes(ParametrosAgentes parametros) {
		try {
			hAgentes.reiniciar(parametros, FICH_CONF_AGENTES, servidor, eBolsa);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *  En caso que el servidor haya terminado inesperadamente se notifica al cliente
	 */
	public void finSimulacion() {
		try {
			JOptionPane.showMessageDialog(gui, "El servidor ha finalizado inesperadamente");
			desloguea();

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
