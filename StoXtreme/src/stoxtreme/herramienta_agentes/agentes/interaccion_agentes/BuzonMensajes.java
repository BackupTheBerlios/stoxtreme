package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import stoxtreme.herramienta_agentes.MonitorAgentes;
import stoxtreme.herramienta_agentes.agentes.IDAgente;

// Los buzones son objetos locales del agente, pero que se comunican entre
// ellos mediante una instancia global
public class BuzonMensajes {
	private static Hashtable<IDAgente, BuzonMensajes> buzones = new Hashtable<IDAgente, BuzonMensajes>(); 
	// Planificador del modelo para poder enviar el mensaje
	private static MonitorAgentes monitor = null;
	private static ArrayList<String> serviciosDisponibles = new ArrayList<String>();
	private static Hashtable<String, ArrayList<IDAgente>> servicioAgente = new Hashtable<String, ArrayList<IDAgente>>();
	
	// METODOS DE CADA BUZON
	// Mensajes ordenados
	private ArrayList<MensajeACL> mensajesPendientes;
	// Acciones a al espera de mensajes
	private IDAgente propietarioBuzon;
	private LinkedList<RecepcionesPendientes> recepcionesPendientes;
	
	
	public static void setMonitor(MonitorAgentes monitor){
		BuzonMensajes.monitor = monitor;
	}
	
	public static BuzonMensajes getBuzon(IDAgente id){
		return buzones.get(id);
	}
	
	public BuzonMensajes(IDAgente id){
		this.recepcionesPendientes = new LinkedList<RecepcionesPendientes>();
		this.mensajesPendientes = new ArrayList<MensajeACL>();
		this.propietarioBuzon = id;
		buzones.put(id, this);
	}
	
	
	public void send(MensajeACL m){
		ArrayList<IDAgente> ids = m.getReceiver();
		if(ids.contains(IDAgente.BROADCAST)){
			Enumeration<IDAgente> keysBuzones = buzones.keys();
			while(keysBuzones.hasMoreElements())
				buzones.get(keysBuzones.nextElement()).insertaMensaje(m);
		}
		else{
			Iterator<IDAgente> iterator = ids.iterator();
			while(iterator.hasNext()){
				buzones.get(iterator.next()).insertaMensaje(m);
			}
		}
	}
	
	public void receive(int numero, PlantillaMensajes p, RecepcionMensaje action){
		RecepcionesPendientes recepcion = new RecepcionesPendientes(numero, p, action);
		int i=0;
		while(i<mensajesPendientes.size() && recepcion.isRestantes()){
			MensajeACL m = mensajesPendientes.get(i);
			if(recepcion.getPlantilla().coincide(m)){
				recepcion.addMensaje(m);
				monitor.addDecision(recepcion.getAccion());
				mensajesPendientes.remove(i);
			}
			else{
				i++;
			}
		}

		if(recepcion.isRestantes()){
			recepcionesPendientes.add(recepcion);
		}
	}
	
	// Si al insertar un mensaje modifica alguna condicion inserta en el planificador
	// la accion correspondiente
	private void insertaMensaje(MensajeACL m){
		boolean encontradaRecepcion = false;
		
		Collections.shuffle(recepcionesPendientes);
		for(int i=recepcionesPendientes.size()-1; i>=0; i--){
			RecepcionesPendientes rp = recepcionesPendientes.get(i);
			if(rp.getPlantilla().coincide(m)){
				encontradaRecepcion = true;
				rp.addMensaje(m);
				monitor.addDecision(rp.getAccion());

				if(!rp.isRestantes()){
					// Si se han acabado borra la recepcion
					recepcionesPendientes.remove(i);
				}
			}
		}
		
		if(!encontradaRecepcion){
			// Espera hasta el momento en el que escuche por el mensaje
			mensajesPendientes.add(m);
		}
	}
	
	private class RecepcionesPendientes{
		private int numMensajesRestantes; 
		private PlantillaMensajes plantilla;
		private ArrayList<RecepcionMensaje> acciones;
		
		public RecepcionesPendientes(int numMensajes, PlantillaMensajes plantilla, RecepcionMensaje accion) {
			this.numMensajesRestantes = numMensajes;
			this.plantilla = plantilla;
			this.acciones = new ArrayList<RecepcionMensaje>();
			try{
				for(int i=0; i<numMensajes; i++){
					RecepcionMensaje clon = (RecepcionMensaje)accion.clone();
					this.acciones.add(clon);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		
		}
		
		public void addMensaje(MensajeACL m) {
			acciones.get(numMensajesRestantes-1).setMensaje(m);
		}

		public boolean isRestantes(){
			return numMensajesRestantes>0;
		}
		
		public RecepcionMensaje getAccion() {
			RecepcionMensaje r = acciones.get(numMensajesRestantes-1);
			numMensajesRestantes--;
			return r;
		}

		public int getNumMensajes() {
			return numMensajesRestantes;
		}
		public PlantillaMensajes getPlantilla() {
			return plantilla;
		}
		public void setPlantilla(PlantillaMensajes plantilla) {
			this.plantilla = plantilla;
		}
	}

	public void altaServicio(IDAgente id, String servicio) {
		if(!servicioAgente.containsKey(servicio)) {
			ArrayList<IDAgente> lista = new ArrayList<IDAgente>();
			lista.add(id);
			servicioAgente.put(servicio, lista);
			serviciosDisponibles.add(servicio);
		}
		else {
			servicioAgente.get(servicio).add(id);
		}
	}

	public void bajaServicio(IDAgente id, String servicio) {
		servicioAgente.get(servicio).remove(id);
		if(servicioAgente.get(servicio).size()== 0){
			servicioAgente.remove(servicio);
			serviciosDisponibles.remove(servicio);
		}
	}
	
	public ArrayList<IDAgente> getAgentesServicio(String servicio) {
		if(servicioAgente.containsKey(servicio)){
			return servicioAgente.get(servicio);
		}
		else{
			return new ArrayList<IDAgente>();
		}
	}
}
