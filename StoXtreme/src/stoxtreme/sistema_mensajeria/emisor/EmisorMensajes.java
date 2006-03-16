package stoxtreme.sistema_mensajeria.emisor;
import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
import stoxtreme.sistema_mensajeria.receptor.ReceptorMensajes;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;

// Proporciona al servicio web lo necesario pero tambien en local
public class EmisorMensajes implements StoxtremeMensajes{
	/**/
	private static EmisorMensajes _instance;
	public static StoxtremeMensajes getInstance() {
		return _instance;
	}
	/**/
	
	private ArrayList listaMensajes;
	private Hashtable usuarios;
	
	public EmisorMensajes(){
		listaMensajes= new ArrayList();
		listaMensajes.add(new Mensaje("Hola mundo", "Tipo", Mensaje.GLOBAL));
		listaMensajes.add(new Mensaje("Hola mundo", "Tipo", "alonso"));
		usuarios = new Hashtable();
	}
	
	public void altaUsuario(String usuario){
		usuarios.put(usuario, new Integer(0));
	}
	
	public void nuevoMensaje(Mensaje m){
		listaMensajes.add(m);
	}
	
	public Mensaje getSiguienteMensaje(String usuario){
		try{
			int ultimoIndice = ((Integer)usuarios.get(usuario)).intValue();
			Mensaje m = null;
			while(ultimoIndice == listaMensajes.size()-1){
				wait();
			}
			m = (Mensaje)listaMensajes.get(ultimoIndice);
			usuarios.put(usuario, new Integer(ultimoIndice+1));
			return m;
		}
		catch(InterruptedException e){
			return new Mensaje("Error en el mensaje", "ERROR", usuario);
		}
	}

	public void enviaMensaje(Mensaje mensaje) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	
}
