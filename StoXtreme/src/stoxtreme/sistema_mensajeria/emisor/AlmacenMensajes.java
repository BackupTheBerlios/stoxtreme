package stoxtreme.sistema_mensajeria.emisor;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.TreeSet;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.StoxtremeMensajes;

/**
 *  Almacen de los mensajes que el emisor desea enviar
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class AlmacenMensajes implements StoxtremeMensajes {
	/*
	 *
	 */
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
	/*
	 *  Metodos estaticos para el acceso singletone
	 */
	private static AlmacenMensajes _instance = new AlmacenMensajes();


	/**
	 *  Constructor de AlmacenMensajes
	 */
	private AlmacenMensajes() {
		numMensajesGlobales = 0;
		buzon = new Hashtable<String, Integer>();
		mensajesGlobales = new ArrayList<Mensaje>();
		mensajesPrivados = new Hashtable<String, ArrayList<Mensaje>>();
		esperando = new Hashtable<String, Object>();
	}


	/**
	 *  Obtiene el siguiente Mensaje a partir de un id
	 *
	 *@param  ID  Id para la búsqueda
	 *@return     Mensaje obtenido
	 */
	public Mensaje getSiguienteMensaje(String ID) {
		try {
			Mensaje m = null;
			Object cerrojo = new Object();
			esperando.put(ID, cerrojo);
			while (!isNuevos(ID)) {
				synchronized (cerrojo) {
					cerrojo.wait();
				}
			}
			esperando.remove(ID);
			synchronized (this) {
				if (mensajesPrivados.get(ID).size() > 0) {
					m = mensajesPrivados.get(ID).remove(0);
				}
				else {
					int ultimo = buzon.get(ID);
					if (buzon.get(ID) < mensajesGlobales.size()) {
						m = mensajesGlobales.get(ultimo);
						buzon.put(ID, ultimo + 1);
					}
				}
			}
			return m;
		}
		catch (InterruptedException e) {
			return new Mensaje("ERROR", "ERROR", ID);
		}
	}


	/**
	 *  Inicia el buzón cuando se da de alta el cliente
	 *
	 *@param  ID  Id asignado al cliente
	 */
	public void altaUsuario(String ID) {
		buzon.put(ID, 0);
		mensajesPrivados.put(ID, new ArrayList<Mensaje>());
	}


	/**
	 *  Envia un mensaje privado o global
	 *
	 *@param  m  Mensaje a enviar
	 */
	public void enviaMensaje(Mensaje m) {
		if (m.getDestinatario().equals(Mensaje.GLOBAL)) {
			insertarMensajeGlobal(m);
		}
		else {
			insertarMensajePrivado(m.getDestinatario(), m);
		}
	}


	/**
	 *  Para el hilo que permite realizar consultas de sus buzones
	 *
	 *@param  id  Id del cliente a parar
	 */
	public void pararHiloConsulta(String id) {
		if (esperando.containsKey(id)) {
			synchronized (esperando.get(id)) {
				esperando.get(id).notify();
			}
		}
	}


	/**
	 *  Se inserta un mensaje que va dirigido a todos los usuarios conectados
	 *
	 *@param  m  Mensaje a enviar
	 */
	public synchronized void insertarMensajeGlobal(Mensaje m) {
		mensajesGlobales.add(numMensajesGlobales, m);
		numMensajesGlobales++;
		Enumeration<String> ids = esperando.keys();
		while (ids.hasMoreElements()) {
			String id = (String) ids.nextElement();
			Object o = esperando.get(id);
			if (o != null) {
				synchronized (o) {
					o.notify();
				}
			}
		}
	}


	/**
	 *  Se inserta un mensaje que va dirigido a un usuario determinado
	 *
	 *@param  ID  Id del usuario
	 *@param  m   Mensaje a enviar
	 */
	public synchronized void insertarMensajePrivado(String ID, Mensaje m) {
		String usuario = ID;
		if (ID.contains("#")) {
			String[] s = ID.split("#");
			usuario = s[0];
		}
		//System.out.println("Insertar mensaje para: "+ID);
		mensajesPrivados.get(usuario).add(m);
		if (esperando.containsKey(usuario)) {
			//System.out.println("Despierto a: "+ID);
			Object o = esperando.get(usuario);
			synchronized (o) {
				o.notify();
			}
		}
	}


	/**
	 *  Comprueba si se han introducido mensajes nuevos
	 *
	 *@param  ID  Id del cliente
	 *@return     Devuelve verdadero o falso
	 */
	private synchronized boolean isNuevos(String ID) {
		return buzon.get(ID) != numMensajesGlobales ||
				mensajesPrivados.get(ID).size() != 0;
	}


	/**
	 *  Devuelve la instancia del Almacen
	 *
	 *@return    Valor de la instancia
	 */
	public static AlmacenMensajes getInstance() {
		return _instance;
	}


	/**
	 *  Hilo para un lector del almacen de mensajes
	 *
	 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
	 */
	private static class Lector extends Thread {
		AlmacenMensajes almacen;
		String nombre;


		/**
		 *  Constructor del Lector
		 *
		 *@param  nombre   Nombre del Lector
		 *@param  almacen  Alamcen asociado
		 */
		public Lector(String nombre, AlmacenMensajes almacen) {
			this.almacen = almacen;
			this.nombre = nombre;
			almacen.altaUsuario(nombre);
		}


		/**
		 *  Metodo del Lector que ejecuta periódicamente
		 */
		public void run() {
			Mensaje m = null;
			while (true) {
				m = almacen.getSiguienteMensaje(nombre);
			}
		}
	}

}
