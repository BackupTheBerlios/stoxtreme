package sist_mensajeria.emisor;
import interfaz_remota.Mensaje;

import java.util.Vector;
import java.util.Hashtable;


public class AlmacenMensajes{
	// Tabla que guarda el numero del ultimo mensaje global que ha leido el usuario
	Hashtable buzon;
	// Vector de mensajes
	Vector mensajesGlobales;
	int numMensajesGlobales;
	// Tabla que guarda los mensajes privados
	Hashtable mensajesPrivados;
	
	public AlmacenMensajes(){
		numMensajesGlobales = 0;
		buzon = new Hashtable();
		mensajesGlobales = new Vector();
		mensajesPrivados = new Hashtable();
	}
	
	public synchronized boolean isNuevos(String ID){
		return ((Integer)buzon.get(ID)).intValue() != numMensajesGlobales ||
				((Vector)mensajesPrivados.get(ID)).size() != 0;
	}
	
	public synchronized void insertarMensajeGlobal(Mensaje m){
		mensajesGlobales.add(numMensajesGlobales, m);
		numMensajesGlobales ++;
	}
	
	public synchronized void insertarMensajePrivado(String ID, Mensaje m){
		((Vector)mensajesPrivados.get(ID)).add(m);
	}
	
	public synchronized void darAlta(String ID){
		buzon.put(ID, new Integer(0));
		mensajesPrivados.put(ID, new Vector());
	}
	
	public synchronized Vector getMensajesHashtable(String ID){
		Vector mensajesHash = new Vector();
		Vector mensajes = (Vector)mensajesPrivados.get(ID);
		mensajesPrivados.put(ID, new Vector());
		
		for (int i = 0; i<mensajes.size(); i++){
			Mensaje m = (Mensaje)mensajes.get(i); 
			mensajesHash.add(m.toHashtable());
		}
		
		int mInicial = ((Integer)buzon.get(ID)).intValue();
		
		for (int i = mInicial; i < numMensajesGlobales; i++){
			Mensaje m = (Mensaje)mensajesGlobales.get(i);
			mensajesHash.add(m.toHashtable());
		}	
		
		buzon.put(ID, new Integer(numMensajesGlobales));
		return mensajesHash;
	}
}
