package servidor.superusuario;
import servidor.Parametros;
import servidor.Servidor;
import sist_mensajeria.emisor.*;

// Clase principal en el servidor, arranca el mismo y aparte proporciona
// la interfaz grafica

public class Superusuario {
	/**
	 * @uml.property  name="gui"
	 * @uml.associationEnd  
	 */
	SuperusuarioGUI gui;
	
	public static void main(String[] args){
		try{
			new Superusuario().init();
		}
		catch(Exception e){
			System.err.println("Fallo en el sistema, razon:");
			e.printStackTrace();
		}
	}
	
	public void Superusuario(){
		gui = new SuperusuarioGUI();
	}
	
	public void init() throws Exception{
		// TODO
		// Crea el servidor e inicializa la conexión remota
		Parametros p = new Parametros();
		Servidor servidor = new Servidor(p);
	}
}
