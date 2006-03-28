package stoxtreme.sistema_mensajeria.emisor;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.TreeSet;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.StoxtremeMensajes;

public class AlmacenMensajes implements StoxtremeMensajes{
	/*Metodos estaticos para el acceso singletone*/
	private static AlmacenMensajes _instance = new AlmacenMensajes();
	public static AlmacenMensajes getInstance(){
		return _instance;
	}
	/**/
	// Tabla que guarda el numero del ultimo mensaje global que ha leido el usuario
	// Local a cada usuario (no problema concurrencia)
	private Hashtable<String, Integer> buzon;
	// Vector de mensajes globales
	private ArrayList<Mensaje> mensajesGlobales;
	private int numMensajesGlobales;
	// Tabla que guarda los mensajes privados
	private Hashtable<String, ArrayList<Mensaje>> mensajesPrivados;
	// Lista de todos usuarios esperando para los mensajes
	private Hashtable<String, Object> esperando;
	
	private AlmacenMensajes(){
		numMensajesGlobales = 0;
		buzon = new Hashtable<String, Integer>();
		mensajesGlobales = new ArrayList<Mensaje>();
		mensajesPrivados = new Hashtable<String, ArrayList<Mensaje>>();
		esperando = new Hashtable<String, Object>();
	}
	
	private synchronized boolean isNuevos(String ID){
		return buzon.get(ID) != numMensajesGlobales ||
				mensajesPrivados.get(ID).size() != 0;
	}
	
	public void altaUsuario(String ID){
		buzon.put(ID, 0);
		mensajesPrivados.put(ID, new ArrayList<Mensaje>());
	}
	
	public void enviaMensaje(Mensaje m){
		if (m.getDestinatario().equals(Mensaje.GLOBAL)){
			insertarMensajeGlobal(m);
		}
		else{
			insertarMensajePrivado(m.getDestinatario(), m);
		}
	}
	
	public void pararHiloConsulta(String id){
		if(esperando.containsKey(id)){
			synchronized(esperando.get(id)){
				esperando.get(id).notify();
			}
		}
	}
	
	public synchronized void insertarMensajeGlobal(Mensaje m){
		mensajesGlobales.add(numMensajesGlobales, m);
		numMensajesGlobales ++;
		Enumeration<String> ids = esperando.keys();
		while(ids.hasMoreElements()){
			String id = (String)ids.nextElement();
			System.out.println("Despierto a: "+id);
			Object o = esperando.get(id);
			synchronized(o){
				o.notify();
			}
		}
	}
	
	public synchronized void insertarMensajePrivado(String ID, Mensaje m){
		System.out.println("Insertar mensaje para: "+ID);
		mensajesPrivados.get(ID).add(m);
		if(esperando.containsKey(ID)){
			System.out.println("Despierto a: "+ID);
			Object o = esperando.get(ID);
			synchronized(o){
				o.notify();
			}
		}
	}
	
	public Mensaje getSiguienteMensaje(String ID){
		try {
			Mensaje m = null;
			Object cerrojo = new Object();
			esperando.put(ID, cerrojo);
			while(!isNuevos(ID)){
				synchronized (cerrojo) {
					System.out.println("Espero("+ID+")");
					cerrojo.wait();
				}
			}
			esperando.remove(ID);
			synchronized(this){
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
			}
			return m;
		} catch (InterruptedException e) {
			return new Mensaje("ERROR", "ERROR", ID);
		}
	}
	
	public static void main(String[] args){
		try {
			AlmacenMensajes almacen = AlmacenMensajes.getInstance();
			new Lector("alonso", almacen).start();
			new Lector("itziar", almacen).start();
			new Lector("ivan", almacen).start();
			
			almacen.enviaMensaje(new Mensaje("HOLA", "MGLOBAL", Mensaje.GLOBAL));
			System.out.println("Durmiendo global");
			Thread.currentThread().sleep(1000);
			System.out.println("Despierto global");
			almacen.enviaMensaje(new Mensaje("ADIOS", "MGLOBAL", Mensaje.GLOBAL));
			almacen.enviaMensaje(new Mensaje("HOLA", "MPRIV", "alonso"));
			System.out.println("Durmiendo privado");
			Thread.currentThread().sleep(1000);
			System.out.println("despierto privado");
			almacen.enviaMensaje(new Mensaje("ADIOS", "MPRIV", "alonso"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static class Lector extends Thread{
		AlmacenMensajes almacen;
		String nombre;
		public Lector(String nombre, AlmacenMensajes almacen){
			this.almacen = almacen;
			this.nombre = nombre;
			almacen.altaUsuario(nombre);
		}
		public void run(){
			Mensaje m = null;
			while(true){
				m = almacen.getSiguienteMensaje(nombre);
				System.out.println(nombre+":"+" mensaje("+m.getDestinatario()+")"+m.getTipoMensaje()+"-"+m.getContenido());
			}
		}
	}

}
