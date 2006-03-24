package stoxtreme.herramienta_agentes.agentes.interaccion_agentes;

public class IDAgente {
	private static int ids = 0;
	private String ID = new String("Agente"+ (++ids));
	public static final IDAgente BROADCAST = new IDAgente("broadcast");
	
	public IDAgente(){
		
	}
	
	private IDAgente(String id){
		this.ID = id;
	}
	
	public boolean equals(Object o){
		return (o instanceof IDAgente) 
			&& ((IDAgente)o).getID().equals(ID);
	}
	
	public boolean isBroadcast(){
		return ID.equals("broadcast");
	}
	
	private String getID(){
		return ID;
	}
	
	public String toString(){
		return getID();
	}
}
