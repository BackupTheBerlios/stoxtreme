package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Set;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class PlantillaMensajes {

	private EnumMap<Campos, Object> map;


	/**
	 *  Constructor for the PlantillaMensajes object
	 */
	public PlantillaMensajes() {
		map = new EnumMap<Campos, Object>(Campos.class);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  m  Description of Parameter
	 *@return    Description of the Returned Value
	 */
	public boolean coincide(MensajeACL m) {
		boolean coincide = true;

		if (map.containsKey(Campos.PERFORMATIVE)) {
			coincide &= (m.getPerformative() != null && m.getPerformative().equals(map.get(Campos.PERFORMATIVE)));
		}
		if (map.containsKey(Campos.PROTOCOL)) {
			coincide &= (m.getProtocol() != null && m.getProtocol().equals(map.get(Campos.PROTOCOL)));
		}
		if (map.containsKey(Campos.SENDER)) {
			coincide &= (m.getSender() != null && m.getSender().equals(map.get(Campos.SENDER)));
		}
		if (map.containsKey(Campos.REPLY_TO)) {
			coincide &= (m.getPerformative() != null && m.getPerformative().equals(map.get(Campos.REPLY_TO)));
		}
		if (map.containsKey(Campos.RECEIVER)) {
			coincide &= (m.getReceiver() != null && m.getReceiver().contains(map.get(Campos.RECEIVER)));
		}
		if (map.containsKey(Campos.CONTENIDO)) {
			if (m.getContenidoString() != null) {
				coincide &= m.getContenidoString().equals(map.get(Campos.CONTENIDO));
			}
			else {
				coincide &= (m.getContenidoObjeto() != null && m.getContenidoObjeto().equals(map.get(Campos.CONTENIDO)));
			}
		}
		if (map.containsKey(Campos.CONVERSATION_ID)) {
			coincide &= (m.getPerformative() != null && m.getPerformative().equals(map.get(Campos.CONVERSATION_ID)));
		}
		if (map.containsKey(Campos.REPLY_WITH)) {
			coincide &= (m.getPerformative() != null && m.getPerformative().equals(map.get(Campos.REPLY_WITH)));
		}
		if (map.containsKey(Campos.IN_REPLY_TO)) {
			coincide &= (m.getPerformative() != null && m.getPerformative().equals(map.get(Campos.IN_REPLY_TO)));
		}
		return coincide;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  campo      Description of Parameter
	 *@param  contenido  Description of Parameter
	 */
	public void add(Campos campo, Object contenido) {
		map.put(campo, contenido);
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		StringBuffer strbuf = new StringBuffer();
		Set<Campos> claves = map.keySet();
		Iterator<Campos> it = claves.iterator();

		while (it.hasNext()) {
			Campos campo = it.next();
			strbuf.append("(");
			strbuf.append(campo);
			strbuf.append(",");
			strbuf.append(map.get(campo));
			strbuf.append(")");
		}
		return strbuf.toString();
	}


	/**
	 *  Description of Enumeration
	 *
	 *@author    Chris Seguin
	 */
	public enum Campos { PERFORMATIVE, PROTOCOL, SENDER, REPLY_TO, RECEIVER, CONTENIDO, CONVERSATION_ID, REPLY_WITH, IN_REPLY_TO
			 }
}
