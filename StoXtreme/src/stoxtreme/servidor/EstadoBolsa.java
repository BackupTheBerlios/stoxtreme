package stoxtreme.servidor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.gui.ModeloTablaPrecioAcciones;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

public class EstadoBolsa extends ModeloTablaPrecioAcciones implements VariablesListener{
	private Hashtable<String, String> escucha;
	
	public EstadoBolsa(DatosEmpresas datos){
		super();
		ArrayList<String> nombres = datos.getNombresEmpresas();
		Iterator<String> it = nombres.iterator();
		escucha = new Hashtable<String, String>();
		while(it.hasNext()){
			String empresa = it.next();
			super.insertarEmpresa(empresa);
			escucha.put("PRECIO_"+empresa.toUpperCase(), empresa);
		}
	}

	public void cambioEstadoVariable(String var, Object value) {
		if(escucha.containsKey(var)){
			super.cambiaPrecioAccion(escucha.get(var), ((Double)value).intValue());
			// Notifica a quien interese
			//Mensaje m = new Mensaje("","CAMBIO_PRECIO");
			//AlmacenMensajes.getInstance().insertarMensajeGlobal(m);
		}
	}
}
