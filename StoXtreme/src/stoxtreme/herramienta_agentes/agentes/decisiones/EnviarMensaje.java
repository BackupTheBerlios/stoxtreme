package stoxtreme.herramienta_agentes.agentes.decisiones;

import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.MensajeACL;

public class EnviarMensaje extends Decision{
	private MensajeACL mensaje;
	
	public EnviarMensaje(MensajeACL m){
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
