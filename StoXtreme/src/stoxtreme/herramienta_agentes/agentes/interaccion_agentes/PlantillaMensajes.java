package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import java.util.ArrayList;
import java.util.EnumMap;

public class PlantillaMensajes{
	public enum Campos{
		PERFORMATIVE, PROTOCOL, SENDER, REPLY_TO, RECEIVER, 
		CONTENIDO, CONVERSATION_ID, REPLY_WITH, IN_REPLY_TO
	};
		
	private EnumMap<Campos, Object> map;
	
	public PlantillaMensajes(){
		map = new EnumMap<Campos, Object>(Campos.class);
	}
	
	public boolean coincide(MensajeACL m){
		boolean coincide = true;
		
		if(map.containsKey(Campos.PERFORMATIVE)){
			coincide &= (m.getPerformative() != null && m.getPerformative().equals(map.get(Campos.PERFORMATIVE)));
		}
		if(map.containsKey(Campos.PROTOCOL)){
			coincide &= (m.getProtocol()!=null && m.getProtocol().equals(map.get(Campos.PROTOCOL)));
		}
		if(map.containsKey(Campos.SENDER)){
			coincide &= (m.getSender()!=null && m.getSender().equals(map.get(Campos.SENDER)));
		}
		if(map.containsKey(Campos.REPLY_TO)){
			coincide &= (m.getPerformative()!=null && m.getPerformative().equals(map.get(Campos.REPLY_TO)));
		}
		if(map.containsKey(Campos.RECEIVER)){
			coincide &= (m.getReceiver()!= null && m.getReceiver().contains(map.get(Campos.RECEIVER)));
		}
		if(map.containsKey(Campos.CONTENIDO)){
			if(m.getContenidoString()!=null)
				coincide &= m.getContenidoString().equals(map.get(Campos.CONTENIDO));
			else{
				coincide &= (m.getContenidoObject()!=null&&m.getContenidoObject().equals(map.get(Campos.CONTENIDO)));
			}
		}
		if(map.containsKey(Campos.CONVERSATION_ID)){
			coincide &= (m.getPerformative()!=null&&m.getPerformative().equals(map.get(Campos.CONVERSATION_ID)));
		}
		if(map.containsKey(Campos.REPLY_WITH)){
			coincide &= (m.getPerformative()!=null&&m.getPerformative().equals(map.get(Campos.REPLY_WITH)));
		}
		if(map.containsKey(Campos.IN_REPLY_TO)){
			coincide &= (m.getPerformative()!=null&&m.getPerformative().equals(map.get(Campos.IN_REPLY_TO)));
		}
		return coincide;
	}

	public void add(Campos campo, Object contenido) {
		map.put(campo, contenido);
	}
}