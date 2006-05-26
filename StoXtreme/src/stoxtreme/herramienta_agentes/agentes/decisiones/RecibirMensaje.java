package stoxtreme.herramienta_agentes.agentes.decisiones;

import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.PlantillaMensajes;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.RecepcionMensaje;

public class RecibirMensaje extends Decision{
	private RecepcionMensaje r;
	private PlantillaMensajes p;
	private int n;
	
	public RecibirMensaje(int n, PlantillaMensajes p, RecepcionMensaje r) {
		super();
		this.n = n;
		this.p = p;
		this.r = r;
	}

	public void ejecuta() {
		agente.recibeMensaje(n, p, r);
	}
}
