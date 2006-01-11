package sist_mensajeria.emisor;
import interfaz_remota.Mensaje;
import java.util.Hashtable;
import java.util.Vector;


public class AlmacenMensajes{
	// Tabla que guarda el numero del ultimo mensaje global que ha leido el usuario
	/**
	 * @uml.property  name="buzon"
	 * @uml.associationEnd  qualifier="ID:java.lang.String java.lang.Integer"
	 */
	Hashtable buzon;
	// Vector de mensajes
	/**
	 * @uml.property  name="mensajesGlobales"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="interfaz_remota.Mensaje"
	 */
	Vector mensajesGlobales;
	/**
	 * @uml.property  name="numMensajesGlobales"
	 */
	int numMensajesGlobales;
	// Tabla que guarda los mensajes privados
	/**
	 * @uml.property  name="mensajesPrivados"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" elementType="interfaz_remota.Mensaje" qualifier="ID:java.lang.String java.util.Vector"
	 */
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
