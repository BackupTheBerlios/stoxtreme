package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;

public abstract class RecepcionMensaje extends Decision{
	protected ArrayList<Mensaje> listaMensajes;
	
	public RecepcionMensaje(Agente agente){
		super(agente, 0);
		listaMensajes = new ArrayList<Mensaje>();
	}
	
	public void addMensaje(Mensaje m){
		listaMensajes.add(m);
	}
	
	public void removeMensaje(Mensaje m){
		listaMensajes.remove(m);
	}
}
