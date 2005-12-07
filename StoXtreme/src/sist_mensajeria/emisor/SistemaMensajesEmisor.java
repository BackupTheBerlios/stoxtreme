package sist_mensajeria.emisor;

import java.util.Vector;
import sist_mensajeria.mensajes.Mensaje;

// Es basicamente una interfaz remota al almacen de
// mensajes local del servidor
public class SistemaMensajesEmisor {
	AlmacenMensajes almacen;
	
	public SistemaMensajesEmisor(AlmacenMensajes almacen){
		this.almacen = almacen;
	}
	public boolean isNuevos(String ID){
		return almacen.isNuevos(ID);
	}
	
	public Vector getNuevos(String ID){
		Vector v = almacen.getMensajesHashtable(ID);
		return v;
	}
}
