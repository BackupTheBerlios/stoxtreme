package prueba;

import interfaz_remota.Mensaje;

import java.net.MalformedURLException;

import sist_mensajeria.receptor.MessageListener;
import sist_mensajeria.receptor.SistemaMensajesReceptor;

public class PruebaCliente implements MessageListener{

	public static void main(String[] args) {
		PruebaCliente pc = new PruebaCliente();
		SistemaMensajesReceptor smr;
		try {
			smr = new SistemaMensajesReceptor("http://localhost:8080", "ID1");
			smr.subscribir(pc);
		} catch (MalformedURLException e) {
			System.out.println("Error, URL mal formada" + e.getMessage());
		}
	}

	public void onMessage(Mensaje m) {
		System.out.println(m.getMsg());
	}
}
