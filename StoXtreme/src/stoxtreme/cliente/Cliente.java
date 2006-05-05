package stoxtreme.cliente;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import org.apache.log4j.PropertyConfigurator;
import stoxtreme.cliente.gui.MainFrameCliente;
import stoxtreme.cliente.gui.DialogoInicial;
import stoxtreme.cliente.infoLocal.InfoLocal;
import stoxtreme.herramienta_agentes.HerramientaAgentes;
import stoxtreme.herramienta_agentes.ParametrosAgentes;
import stoxtreme.interfaz_remota.*;
import stoxtreme.servicio_web.StoxtremeServiceLocator;
import stoxtreme.sistema_mensajeria.receptor.*;

public class Cliente{
	private static final String URLBASE = "http://localhost:8080/";
	private static final String URLAXIS = URLBASE+"axis/services/";
	private static final String URLCONF = URLBASE+"Stoxtreme/config/";
	
	private String nUsuario;
	private String password;
	private Stoxtreme servicio;
	private EstadoBolsa eBolsa;
	private OperacionesPendientes opPendientes;
	private CarteraAcciones cartera;
	private MainFrameCliente gui;
	private DialogoInicial identificacion;
	private Stoxtreme servidor;
	private ReceptorMensajes receptor;
	private HerramientaAgentes hAgentes;
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("conf/log4j.properties");
		Cliente c = new Cliente(URLAXIS+"StoXtreme");
		try {
			c.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Cliente(String url){
		try{
			StoxtremeServiceLocator locator = new StoxtremeServiceLocator();
			servidor = locator.getStoXtreme(new URL(url));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void init() throws Exception{
		/*boolean login = servidor.login(usuario, pass);
		if(!login){
			boolean reg = servidor.registro(usuario, pass);
			if(!reg) throw new Exception("Fallo en el registro");
			login = servidor.login(usuario, pass);
			if(!login) throw new Exception("Fallo en el login");
		}*/
		
		//Eliminamos el DialogoInicial porque ya he identificado al usuario
//		identificacion.dispose();
		boolean conectado=false;
		DialogoInicial ini= new DialogoInicial();
		while(!conectado){
			ini.setOperacion(0);
			ini.setModal(true);
			ini.setVisible(true);
			// Pone la ventana siempre en lo mas alto
			ini.setAlwaysOnTop(true);
			int op =ini.getOperacion();
			String user = ini.getusuario();
			String psw = ini.getpass();
			
			//Si cierra la ventana, salimos
			if(op==0){
				System.exit(0);
			}
			//Si hace login
			if(op==1){
				if(user.trim().equals("")||(psw.trim().equals("")))
					JOptionPane.showMessageDialog(ini, "Por favor, rellene todos los campos",
							"Revise sus datos",JOptionPane.WARNING_MESSAGE);
				else{
					try{
						boolean login=servidor.login(user.trim(),psw.trim());
						if (!login)
							JOptionPane.showMessageDialog(ini, "El usuario no existe o el password es erroneo",
									"Error",JOptionPane.ERROR_MESSAGE);
						else{
							conectado=true;
						}
							
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(ini, "El servidor parece estar caido. \n Intentelo de nuevo mas tarde",
								"Error de conexion",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			//Si crea un nuevo usuario
			if(op==2){
				if (user.trim().equals("") ||psw.trim().equals(""))
					JOptionPane.showMessageDialog(ini, "Por favor, rellene todos los campos",
							"Revise sus datos",JOptionPane.WARNING_MESSAGE);
				else{
					if(!validar(user.trim()) || !validar(psw.trim()))
						JOptionPane.showMessageDialog(ini, "Su nombre de usuario o password \n contienen caracteres no validos. \n Solo pueden tener numeros, letras y '_'",
								"Revise sus datos",JOptionPane.WARNING_MESSAGE);
					else{
						try{
							boolean registro=servidor.registro(user.trim(),psw.trim());
							if (!registro)
								JOptionPane.showMessageDialog(ini, "Ya existe un usuario con ese nombre",
									"Error",JOptionPane.ERROR_MESSAGE);
							else{
								servidor.login(user,psw);
								conectado=true;
							}
						}
						catch(Exception ex){
							JOptionPane.showMessageDialog(ini, "El servidor parece estar caido. \n Intentelo de nuevo mas tarde",
									"Error de conexion",JOptionPane.ERROR_MESSAGE);
						}
					}
					
				}
			}
			this.nUsuario = user; this.password = psw;
		}
		
		this.obtenerFicheros();
		opPendientes = new OperacionesPendientes();
		cartera = new CarteraAcciones();
		eBolsa = new EstadoBolsa(new InfoLocal());
		ParametrosAgentes parametros = new ParametrosAgentes();
		hAgentes = new HerramientaAgentes(nUsuario, eBolsa, parametros);
		gui = new MainFrameCliente(this, cartera.getMCartera(), opPendientes.getMOpPendientes(), eBolsa.getMAcciones());
		gui.init();
		gui.pack();
		gui.setVisible(true);
		// Lo ultimo que hacemos es dar de alta en el receptor
		receptor = new ReceptorMensajes(nUsuario, ReceptorMensajes.WEB_SERVICE, URLAXIS+"StoXtremeMsg");
		receptor.addListener(new ManejadorMensajes(this));
	}
	
	public void iniciarHerramientaAgentes(){
		hAgentes.start(servidor, eBolsa);
	}
	
	public void insertarOperacion(Operacion op)throws Exception{
		int idOp = servidor.insertarOperacion(nUsuario, op);
		opPendientes.inserta(idOp, op);
	}
	
	public void cancelarOperacion(int op)throws Exception{
		servidor.cancelarOperacion(nUsuario, op);
	}
	
	public void notificaOperacion(int idOp, int cantidad){
		if(idOp>0){
			Operacion o = opPendientes.dameOperacion(idOp);
			cartera.actualiza(o, cantidad);
		}
		int vOp = Math.abs(idOp);
		opPendientes.quitaOperacion(vOp);
	}
	
	public void notificaCancelacion(int idOp){
		opPendientes.quitaOperacion(idOp);
	}
	
	public String getNUsuario() {
		return nUsuario;
	}
	
	public void deslogea() throws Exception{
		System.out.println("Deslogea");
		servidor.login(nUsuario, password);
		System.out.println("Espero a que pare el receptor");
		receptor.paraReceptor();
		System.out.println("Fin del cliente.");
		System.exit(0);
	}
	public void obtenerFicheros() throws Exception{
		URL url = null;
		FileOutputStream fos = null; 
		InputStreamReader isr = null;
		Reader in = null;
		StringBuffer buffer = null;
		OutputStreamWriter osw = null;
		//Lista de nombres de ficheros de configuracion
		String[] ficheros={"empresas","endesa","antena3","repsol","telecinco"};
		int contador=0;
		while(ficheros.length>contador){
			try {
				//TODO Cambiar la URL
				url= new  URL(URLCONF+ficheros[contador]+".xml");
				//TODO Cambiar la ruta de destino si procede
				
				File f = new File("./conf/cliente/"+ficheros[contador]+".xml");
				System.err.println(f.getAbsolutePath());
				//f.createNewFile();
				fos = new FileOutputStream(f);
				isr = new InputStreamReader(url.openStream());
				in = new BufferedReader(isr);
				buffer = new StringBuffer();
				int ch;
				while((ch =in.read())>-1){
					buffer.append((char)ch);
				}
				osw = new OutputStreamWriter(fos);
				osw.append(buffer);
			} 
			 catch (Exception e) {
				e.printStackTrace();
			} 
			if(osw != null)
				osw.close();
			if(in != null)
				in.close();
			if(fos != null)
				fos.close();
			if(isr!=null)
				isr.close();
			contador++;
		}
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
}
