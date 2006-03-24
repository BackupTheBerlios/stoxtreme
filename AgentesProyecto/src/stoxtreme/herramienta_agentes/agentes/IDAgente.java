package stoxtreme.herramienta_agentes.agentes;

public class IDAgente {
	private static int IDS=0;
	private static String idUsuario;
	public static void setUsuario(String id){
		idUsuario = id;
	}
	public static IDAgente BROADCAST = new IDAgente("BROADCAST");
	
	private String idAgente;
	public IDAgente(){
		idAgente = "Agente"+(++IDS);
	}
	
	private IDAgente(String idAgente){
		this.idAgente = idAgente;
	}
	
	public String toString(){
		return idUsuario+"#"+idAgente;
	}
}
