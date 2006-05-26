package stoxtreme.herramienta_agentes.agentes.decisiones;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.IDAgente;


public class RequestServicio extends Decision{
	private String servicio;
	private ArrayList<IDAgente> proveedores;
	
	public RequestServicio(String nombre_servicio, ArrayList<IDAgente> proveedores) {
		this.servicio = nombre_servicio;
		this.proveedores = proveedores;
	}

	public void ejecuta() {
		agente.requestServicio(servicio, proveedores);
	}
}
