package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

import stoxtreme.herramienta_agentes.agentes.IDAgente;

public class IDConversacion {
	private static int ids = 0;
	private String ID = new String("Conversation"+ (++ids));
		
	private IDConversacion(String id){
		this.ID = id;
	}
	
	public boolean equals(Object o){
		return (o instanceof IDAgente) 
			&& ((IDConversacion)o).getID().equals(ID);
	}
	
	private String getID(){
		return ID;
	}
}
