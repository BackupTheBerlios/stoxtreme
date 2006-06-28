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

public class Cliente{
	private static final String URLAXIS = "/axis/services/";
	private static final String URLCONF = "/Stoxtreme/config/";
	private static final String FICH_CONF_AGENTES = "conf/confAgentes.xml";
	
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
	
	public void init() throws Exception{
		boolean conectado=false;
		Object despertador = new Object();
		DialogoInicial dialogoInicial= new DialogoInicial(despertador);
		
		while(!conectado){
			dialogoInicial.setOperacion(0);
			dialogoInicial.setVisible(true);
			
			synchronized (despertador) {
				despertador.wait();
			}
			
			///
			int op =dialogoInicial.getOperacion();
			String user = dialogoInicial.getusuario();
			String psw = dialogoInicial.getpass();
			URL direccion = new URL(dialogoInicial.getDireccionServidor()+URLAXIS+"StoXtreme");
			// Con la direccion conectamos al servidor
			StoxtremeServiceLocator locator = new StoxtremeServiceLocator();
			servidor = locator.getStoXtreme(direccion);
			
			//Si cierra la ventana, salimos
			if(op==0){
				System.exit(0);
			}
			//Si hace login
			if(op==1){
				if(user.trim().equals("")||(psw.trim().equals("")))
					JOptionPane.showMessageDialog(dialogoInicial, "Por favor, rellene todos los campos",
							"Revise sus datos",JOptionPane.WARNING_MESSAGE);
				else{
					try{
						int login=servidor.login(user.trim(),psw.trim());
						if (login == -1)
							JOptionPane.showMessageDialog(dialogoInicial, "El usuario no existe o el password es erroneo",
									"Error",JOptionPane.ERROR_MESSAGE);
						else{
							numEmpresas=login;
							conectado=true;
						}
							
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(dialogoInicial, "El servidor parece estar caido. \n Intentelo de nuevo mas tarde",
								"Error de conexion",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			//Si crea un nuevo usuario
			if(op==2){
				if (user.trim().equals("") ||psw.trim().equals(""))
					JOptionPane.showMessageDialog(dialogoInicial, "Por favor, rellene todos los campos",
							"Revise sus datos",JOptionPane.WARNING_MESSAGE);
				else{
					if(!validar(user.trim()) || !validar(psw.trim()))
						JOptionPane.showMessageDialog(dialogoInicial, "Su nombre de usuario o password \n contienen caracteres no validos. \n Solo pueden tener numeros, letras y '_'",
								"Revise sus datos",JOptionPane.WARNING_MESSAGE);
					else{
						try{
							boolean registro=servidor.registro(user.trim(),psw.trim());
							if (!registro)
								JOptionPane.showMessageDialog(dialogoInicial, "Ya existe un usuario con ese nombre",
									"Error",JOptionPane.ERROR_MESSAGE);
							else{
								numEmpresas=servidor.login(user,psw);
								conectado=true;
							}
						}
						catch(Exception ex){
							JOptionPane.showMessageDialog(dialogoInicial, "El servidor parece estar caido. \n Intentelo de nuevo mas tarde",
									"Error de conexion",JOptionPane.ERROR_MESSAGE);
						}
					}
					
				}
			}
			this.nUsuario = user; this.password = psw;
		}
		
		System.err.println("Obteniendo ficheros");
		this.obtenerFicheros(dialogoInicial.getDireccionServidor());
		System.err.println("Creando Operaciones Pendientes");
		opPendientes = new OperacionesPendientes();
		System.err.println("Creando Cartera");
		cartera = new CarteraAcciones();
		System.err.println("Creando Estado de la Bolsa");
		InfoLocal.crearInstancia("./conf/cliente/empresas.xml", numEmpresas);
		InfoLocal infoLocal = InfoLocal.getInstance();
		eBolsa = new EstadoBolsa(infoLocal);
		System.err.println("Creando Parametros");
		System.err.println("Creando Agentes");
		hAgentes = new HerramientaAgentes(nUsuario, eBolsa);
		gui = new MainFrameCliente(this, cartera.getMCartera(), opPendientes.getMOpPendientes(), eBolsa, hAgentes);
		hAgentes.setFrame(gui);
		gui.init();
		gui.pack();
		gui.setVisible(true);
		// Lo ultimo que hacemos es dar de alta en el receptor
		receptor = new ReceptorMensajes(nUsuario, ReceptorMensajes.WEB_SERVICE, dialogoInicial.getDireccionServidor()+URLAXIS+"StoXtremeMsg");
		receptor.addListener(new ManejadorMensajes(this));
	}
	
	public void iniciarHerramientaAgentes(ParametrosAgentes parametros){
		try {
			hAgentes.start(parametros, FICH_CONF_AGENTES, servidor, eBolsa);
		} catch (Exception e) {
			System.err.println("Error al iniciar los agentes. Exit");
			e.printStackTrace();
			try {
				desloguea();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void insertarOperacion(Operacion op)throws Exception{
		int idOp = servidor.insertarOperacion(nUsuario, op);
		opPendientes.inserta(idOp, op);
	}
	
	public void cancelarOperacion(int op)throws Exception{
		servidor.cancelarOperacion(nUsuario, op);
	}
	
	public void notificaOperacion(String idDestino, int idOp, int cantidad, double precio){
		if(nUsuario.equals(idDestino)){
			Operacion o = opPendientes.dameOperacion(idOp);
			cartera.actualiza(o, cantidad);
			int vOp = Math.abs(idOp);
			opPendientes.quitaOperacion(vOp);
		}
		else{
			System.err.println(idDestino+" "+idOp+" "+cantidad+" "+precio);
			hAgentes.notificarOperacion(idDestino, idOp, cantidad, precio);
		}
	}
	
	public void notificaCancelacion(String idDestino, int idOp){
		if(nUsuario.equals(idDestino)){
			opPendientes.quitaOperacion(idOp);
		}
		else{
			hAgentes.notificarCancelacion(idDestino, idOp);
		}
	}
	
	public String getNUsuario() {
		return nUsuario;
	}
	
	public void desloguea() throws Exception{
		System.out.println("Deslogea");
		servidor.login(nUsuario, password);
		System.out.println("Fin del cliente.");
		System.exit(0);
	}
	public void obtenerFicheros(String direccion) throws Exception{
		XMLDownloader.download(new File("./conf/cliente/empresas.xml"), new URL(direccion+URLCONF+"empresas.xml"));
//		URL url = null;
//		FileOutputStream fos = null;
//		InputStreamReader isr = null;
//		Reader in = null;
//		StringBuffer buffer = null;
//		OutputStreamWriter osw = null;
//		try{
//			url=new URL();
//			File f=new File("./conf/cliente/empresas.xml");
//			System.err.println(f.getAbsolutePath());
//			fos = new FileOutputStream(f);
//			isr = new InputStreamReader(url.openStream());
//			in = new BufferedReader(isr);
//			buffer = new StringBuffer();
//			int ch;
//			while((ch =in.read())>-1){
//				buffer.append((char)ch);
//			}
//			osw = new OutputStreamWriter(fos);
//			osw.append(buffer);
//		} 
//		 catch (Exception e) {
//			e.printStackTrace();
//		} 
//		if(osw != null)
//			osw.close();
//		if(in != null)
//			in.close();
//		if(fos != null)
//			fos.close();
//		if(isr!=null)
//			isr.close();
		String[] fichEmpresas=this.getNombreFicheros("./conf/cliente/empresas.xml");
		XMLDownloader.downloadAll("./conf/cliente", direccion+URLCONF, fichEmpresas);
//		
//		int contador=0;
//		while(fichEmpresas.size()>contador){
//			try {
//				url= new  URL(direccion+URLCONF+fichEmpresas.get(contador));
//				
//				File f = new File("./conf/cliente/"+fichEmpresas.get(contador));
//				System.err.println(f.getAbsolutePath());
//				//f.createNewFile();
//				fos = new FileOutputStream(f);
//				isr = new InputStreamReader(url.openStream());
//				in = new BufferedReader(isr);
//				buffer = new StringBuffer();
//				int ch;
//				while((ch =in.read())>-1){
//					buffer.append((char)ch);
//				}
//				osw = new OutputStreamWriter(fos);
//				osw.append(buffer);
//			} 
//			 catch (Exception e) {
//				e.printStackTrace();
//			} 
//			if(osw != null)
//				osw.close();
//			if(in != null)
//				in.close();
//			if(fos != null)
//				fos.close();
//			if(isr!=null)
//				isr.close();
//			contador++;
//		}
	}
	
	public String[] getNombreFicheros(String fichEmpresas){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//Lista de nombres de ficheros de configuracion
		String[] ficheros = new String[numEmpresas];
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(fichEmpresas));
			NodeList nl = document.getElementsByTagName("emp");
			String ruta=null;
			//Obtengo los nombres de todas las empresas
			for (int i=0; nl!=null && i<numEmpresas;i++){
				ruta=((Element)nl.item(i)).getTextContent().trim();
				String[] nombre=ruta.split("/");
				ficheros[i] = nombre[1].toString();
			}
	    } catch (SAXException sxe) {
	       // Error generated during parsing
	       Exception  x = sxe;
	       if (sxe.getException() != null)
	           x = sxe.getException();
	       x.printStackTrace();

	    } catch (ParserConfigurationException pce) {
	       // Parser with specified options can't be built
	       pce.printStackTrace();
	    } catch (IOException ioe) {
	       // I/O error
	       ioe.printStackTrace();
	    }
	    return ficheros;
	}
	public boolean validar (String s){
		boolean valido=true;
		final char[] chars = s.toCharArray();
		for (int x = 0; x < chars.length; x++) {
			final char c = chars[x];
			if (!((c >= 'a') && (c <= 'z')) && !((c >= 'A') && (c <= 'Z')) && !((c >= '0') && (c <= '9')) && !(c=='_'))
				valido=false;				 
		}
		return valido;
	}
	
	public EstadoBolsa getEstadoBolsa() {
		return eBolsa;
	}
	
	public void informar(String contenido) {
		if(gui!= null){
			JOptionPane.showMessageDialog(gui,contenido);
		}
		else{
			System.out.println("Mensaje informacion: "+contenido);
		}
	}

	public void detenerHerramientaAgentes() {
		hAgentes.pausarAgentes();
	}

	public void reanudarHerramientaAgentes() {
		hAgentes.reanudarAgentes();
	}
	
	public void reiniciarHerramientaAgentes(ParametrosAgentes parametros) {
		try {
			hAgentes.reiniciar(parametros, FICH_CONF_AGENTES, servidor, eBolsa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finSimulacion() {
		try {
			JOptionPane.showMessageDialog(gui, "El servidor ha finalizado inesperadamente");
			desloguea();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
