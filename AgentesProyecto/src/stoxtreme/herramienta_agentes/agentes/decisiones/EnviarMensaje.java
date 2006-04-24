package stoxtreme.herramienta_agentes.agentes.decisiones;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.Mensaje;

public class EnviarMensaje extends Decision{
	private Mensaje mensaje;
	
	public EnviarMensaje(Mensaje m){
		super();
		this.mensaje = m;
	}
	
	public void ejecuta() {
		agente.enviarMensaje(mensaje);
	}
	
	public String toString(){
		return "Envio un mensaje "+ mensaje;
	}
}
