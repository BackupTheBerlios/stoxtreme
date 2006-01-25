package stoxtreme.sistema_mensajeria.emisor;
import stoxtreme.interfaz_remota.Mensaje;
import java.util.ArrayList;
import java.util.Hashtable;

// Proporciona al servicio web lo necesario pero tambien en local
public class EmisorMensajes {
	private ArrayList listaMensajes;
	private Hashtable usuarios;

	public EmisorMensajes(){
		listaMensajes= new ArrayList();
		listaMensajes.add(new Mensaje("Hola mundo"));
		usuarios = new Hashtable();
	}
	
	public void altaUsuario(String usuario){
		usuarios.put(usuario, new Integer(0));
	}
	
	public void nuevoMensajeGlobal(Mensaje m){
		listaMensajes.add(m);
	}
	
	public Mensaje siguienteMensaje(String usuario){
		int ultimoIndice = ((Integer)usuarios.get(usuario)).intValue();
		Mensaje m = null;
		if(ultimoIndice < listaMensajes.size()-1){
			m = (Mensaje)listaMensajes.get(ultimoIndice);
			usuarios.put(usuario, new Integer(ultimoIndice+1));
		}
		return m;
	}
}
