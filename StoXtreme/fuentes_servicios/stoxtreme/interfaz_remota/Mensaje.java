package stoxtreme.interfaz_remota;

import java.util.ArrayList;

public class Mensaje {
	private int idMensaje;
	private String emisor;
	private ArrayList receptores;
	private String tipoMensaje;
	private String contenido;
	
	public Mensaje(){
		
	}
	
	public Mensaje(String tipo, String contenido){
		this.tipoMensaje = tipo;
		this.contenido = contenido;
	}
	
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getContenido() {
		return contenido;
	}
}
