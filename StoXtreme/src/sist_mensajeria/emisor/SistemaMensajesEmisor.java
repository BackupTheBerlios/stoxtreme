package sist_mensajeria.emisor;

import interfaz_remota.Mensaje;
import java.util.Vector;

// Es basicamente una interfaz remota al almacen de
// mensajes local del servidor
public class SistemaMensajesEmisor {
	// Metodos SINGLETONE
	static private SistemaMensajesEmisor sme = null;
	static private AlmacenMensajes a = null;
	
	static public SistemaMensajesEmisor getReferenciaGlobalEmisor(){
		if(sme == null){
			a = new AlmacenMensajes();
			sme = new SistemaMensajesEmisor(a);
		}
		return sme;
	}
	static public AlmacenMensajes getReferenciaGlobalAlmacen(){
		if(sme == null){
			a = new AlmacenMensajes();
			sme = new SistemaMensajesEmisor(a);
		}
		return a;
	}
	
	// Metodos del objeto
	/**
	 * @uml.property  name="almacen"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
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
