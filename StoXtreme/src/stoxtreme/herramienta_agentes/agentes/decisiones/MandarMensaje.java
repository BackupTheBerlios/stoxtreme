package stoxtreme.herramienta_agentes.agentes.decisiones;

import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.MensajeACL;

public class MandarMensaje extends Decision{
	private MensajeACL mensaje;
	private boolean privado;
	
	public MandarMensaje(boolean privado, MensajeACL mensaje) {
		super();
		this.mensaje = mensaje;
		this.privado = privado;
	}

	public void ejecuta() {
		if(!privado){
			mensaje.setSender(agente.getIDAgente());
		}
		agente.enviarMensaje(mensaje);
	}
	
}
