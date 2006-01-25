package stoxtreme.sistema_mensajeria.emisor;
import java.util.Hashtable;
import java.util.ArrayList;

import stoxtreme.interfaz_remota.Mensaje;


public class AlmacenMensajes{
	// Tabla que guarda el numero del ultimo mensaje global que ha leido el usuario
	Hashtable<String, Integer> buzon;
	// Vector de mensajes
	ArrayList<Mensaje> mensajesGlobales;
	int numMensajesGlobales;
	// Tabla que guarda los mensajes privados
	Hashtable<String, ArrayList<Mensaje>> mensajesPrivados;
	ArrayList<Mensaje> listaActual;
	
	public AlmacenMensajes(){
		numMensajesGlobales = 0;
		buzon = new Hashtable<String, Integer>();
		mensajesGlobales = new ArrayList<Mensaje>();
		mensajesPrivados = new Hashtable<String, ArrayList<Mensaje>>();
	}
	
	public synchronized boolean isNuevos(String ID){
		return buzon.get(ID) != numMensajesGlobales ||
				mensajesPrivados.get(ID).size() != 0;
	}
	
	public synchronized void insertarMensajeGlobal(Mensaje m){
		mensajesGlobales.add(numMensajesGlobales, m);
		numMensajesGlobales ++;
	}
	
	public synchronized void insertarMensajePrivado(String ID, Mensaje m){
		mensajesPrivados.get(ID).add(m);
	}
	
	public synchronized void darAlta(String ID){
		buzon.put(ID, 0);
		mensajesPrivados.put(ID, new ArrayList<Mensaje>());
	}
	
	public synchronized Mensaje getSiguienteMensaje(String ID){
		Mensaje m = null;
		if(mensajesPrivados.get(ID).size() > 0){
			m = mensajesPrivados.get(ID).remove(0);
		}
		else{ 
			int ultimo = buzon.get(ID);
			if(buzon.get(ID) < mensajesGlobales.size()){
				m = mensajesGlobales.get(ultimo);
				buzon.put(ID, ultimo+1);
			}
		}
		return m;
	}
}
