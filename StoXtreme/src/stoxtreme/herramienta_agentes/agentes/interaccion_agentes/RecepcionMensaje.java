package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;

public abstract class RecepcionMensaje extends Decision implements Cloneable{
	protected MensajeACL mensajeRecibido;
	
	public RecepcionMensaje(){

	}
	
	public void setMensaje(MensajeACL m){
		this.mensajeRecibido = m;
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
}
