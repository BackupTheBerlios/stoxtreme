package stoxtreme.servidor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.gui.ModeloTablaPrecioAcciones;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class EstadoBolsa extends ModeloTablaPrecioAcciones implements VariablesListener {
	private Hashtable<String, String> escucha;


	/**
	 *  Constructor for the EstadoBolsa object
	 *
	 *@param  datos  Description of Parameter
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
	 *  Description of the Method
	 *
	 *@param  var    Description of Parameter
	 *@param  value  Description of Parameter
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
