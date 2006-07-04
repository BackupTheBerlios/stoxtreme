package stoxtreme.servidor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.gui.ModeloTablaPrecioAcciones;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Guarda una representación del estado actual de la bolsa
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
@SuppressWarnings("serial")
public class EstadoBolsa extends ModeloTablaPrecioAcciones implements VariablesListener {
	private Hashtable<String, String> escucha;


	/**
	 *  Constructor del objeto EstadoBolsa
	 *
	 *@param  datos  Array que contiene los nombres de las empresas que cotizan en la sesión actual
	 */
	public EstadoBolsa(DatosEmpresas datos) {
		super();
		ArrayList<String> nombres = datos.getNombresEmpresas();
		Iterator<String> it = nombres.iterator();
		escucha = new Hashtable<String, String>();
		while (it.hasNext()) {
			String empresa = it.next();
			super.insertarEmpresa(empresa);
			escucha.put("PRECIO_" + empresa.toUpperCase(), empresa);
		}
	}


	/**
	 *  Cambia el estado de las variables del sistema asociadas a los precios de las acciones.
	 *  También genera un mensaje global para informar a todos los usuarios.
	 *
	 *@param  var    Variable del sistema que cambia de estado
	 *@param  value  Nuevo estado al que pasa la variable
	 */
	public void cambioEstadoVariable(String var, Object value) {
		if (escucha.containsKey(var)) {
			super.cambiaPrecioAccion(escucha.get(var), ((Double) value).doubleValue());
			// Notifica a todos los oyentes del sistema
			String c = escucha.get(var) + "," + ((Double) value).toString();
			Mensaje m = new Mensaje(c, "CAMBIO_PRECIO", Mensaje.GLOBAL);
			AlmacenMensajes.getInstance().insertarMensajeGlobal(m);
		}
	}
}
