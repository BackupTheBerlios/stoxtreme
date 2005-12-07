package prueba;

import java.util.Hashtable;
import java.util.Vector;

import org.apache.xmlrpc.WebServer;

import sist_mensajeria.emisor.AlmacenMensajes;
import sist_mensajeria.emisor.SistemaMensajesEmisor;
import sist_mensajeria.mensajes.Mensaje;

public class PruebaServidor {
	public static void main(String[] args) {
		WebServer s = new WebServer(8080);
		AlmacenMensajes almacen = new AlmacenMensajes();
		SistemaMensajesEmisor sme = new SistemaMensajesEmisor(almacen);
		s.addHandler("sistMensajes", sme);
		s.start();
		
		almacen.darAlta("ID1");
		almacen.insertarMensajeGlobal(new Mensaje("Hola mundo"));
		almacen.insertarMensajePrivado("ID1", new Mensaje("Adios mundo"));
	}

}
