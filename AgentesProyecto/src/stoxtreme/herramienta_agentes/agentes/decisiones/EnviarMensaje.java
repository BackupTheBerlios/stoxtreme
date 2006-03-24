package stoxtreme.herramienta_agentes.agentes.decisiones;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.Mensaje;

public class EnviarMensaje extends Decision{
	private Mensaje mensaje;
	
	public EnviarMensaje(Agente a, Mensaje m){
		super(a, 0);
		this.mensaje = m;
	}
	
	public void ejecuta() {
		agente.enviarMensaje(mensaje);
	}
}
