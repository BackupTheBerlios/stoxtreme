package stoxtreme.servidor.superusuario;
import stoxtreme.servidor.Parametros;
import stoxtreme.servidor.Servidor;

// Clase principal en el servidor, arranca el mismo y aparte proporciona
// la interfaz grafica

public class Superusuario {
	SuperusuarioGUI gui;
	
	public void Superusuario(){
		gui = new SuperusuarioGUI();
	}
}
