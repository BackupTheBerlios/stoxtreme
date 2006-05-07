package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;

public abstract class RecepcionMensaje extends Decision{
	protected ArrayList<MensajeACL> listaMensajes;
	
	public RecepcionMensaje(){
		super();
		listaMensajes = new ArrayList<MensajeACL>();
	}
	
	public void addMensaje(MensajeACL m){
		listaMensajes.add(m);
	}
	
	public void removeMensaje(MensajeACL m){
		listaMensajes.remove(m);
	}
}
