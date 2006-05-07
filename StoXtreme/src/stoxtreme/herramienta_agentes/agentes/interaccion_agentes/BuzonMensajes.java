package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;

import stoxtreme.herramienta_agentes.MonitorAgentes;
import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;

// Los buzones son objetos locales del agente, pero que se comunican entre
// ellos mediante una instancia global
public class BuzonMensajes {
	// TODO esto mejor que una hastabla normal deberia ser una clase mas compleja
	// para poder gestionar las conexiones remotas si se quisiera hacer mas completo
	private static Hashtable<IDAgente, BuzonMensajes> buzones = new Hashtable<IDAgente, BuzonMensajes>(); 
	// Planificador del modelo para poder enviar el mensaje
	private static MonitorAgentes monitorAgentes = null; 
	private static ArrayList<String> serviciosDisponibles = new ArrayList<String>();
	private static Hashtable<String, ArrayList<IDAgente>> servicioAgente = new Hashtable<String, ArrayList<IDAgente>>();
	
	// METODOS DE CADA BUZON
	// Mensajes ordenados
	private ArrayList<MensajeACL> mensajes;
	// Acciones a al espera de mensajes
	private IDAgente propietarioBuzon;
	private ArrayList<RecepcionesPendientes> recepcionesPendientes;
	
	
	public BuzonMensajes(IDAgente id){
		this.recepcionesPendientes = new ArrayList<RecepcionesPendientes>();
		this.mensajes = new ArrayList<MensajeACL>();
		this.propietarioBuzon = id;
		buzones.put(id, this);
		if(monitorAgentes == null)
			monitorAgentes = _monitor;
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
		recepcionesPendientes.add(new RecepcionesPendientes(numero, p, action));
	}
	
	// Si al insertar un mensaje modifica alguna condicion inserta en el planificador
	// la accion correspondiente
	private void insertaMensaje(MensajeACL m){
		mensajes.add(m);
		
		Iterator<RecepcionesPendientes> it = recepcionesPendientes.iterator();
		
		while(it.hasNext()){
			RecepcionesPendientes rp = it.next();
			if(rp.getPlantilla().coincide(m)){
				rp.getAccion().addMensaje(m);
				if(!rp.isRestantes()){
					monitorAgentes.addDecision(rp.getAccion());
				}
			}
		}
	}
	
	private class RecepcionesPendientes{
		private int numMensajesRestantes;
		private PlantillaMensajes plantilla;
		private RecepcionMensaje accion;
		
		public RecepcionesPendientes(int numMensajes, PlantillaMensajes plantilla, RecepcionMensaje accion) {
			this.numMensajesRestantes = numMensajes;
			this.plantilla = plantilla;
			this.accion = accion;
		}
		
		public boolean isRestantes(){
			return numMensajesRestantes<0;
		}
		
		public void restaNMensajes(){
			numMensajesRestantes--;
		}
		
		public RecepcionMensaje getAccion() {
			return accion;
		}
		public void setAccion(RecepcionMensaje accion) {
			this.accion = accion;
		}
		public int getNumMensajes() {
			return numMensajesRestantes;
		}
		public void setNumMensajes(int numMensajes) {
			this.numMensajesRestantes = numMensajes;
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

	private static MonitorAgentes _monitor;
	public static void setMonitor(MonitorAgentes monitor) {
		_monitor = monitor;
	}
}
