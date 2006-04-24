package stoxtreme.herramienta_agentes.agentes.decisiones;
import stoxtreme.herramienta_agentes.agentes.Agente;

public class CancelarOperacion extends Decision{
	private int idOp;
	
	public CancelarOperacion(int idOperacion){
		super();
		idOp = idOperacion;
	}
	
	public void ejecuta() {
		System.err.println(idOp);
		agente.cancelarOperacion(idOp);
	}
	
	public String toString(){
		return "Cancela operacion "+idOp;
	}
}
