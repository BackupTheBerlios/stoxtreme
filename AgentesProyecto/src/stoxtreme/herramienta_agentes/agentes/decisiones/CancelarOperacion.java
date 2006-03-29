package stoxtreme.herramienta_agentes.agentes.decisiones;
import stoxtreme.herramienta_agentes.agentes.Agente;

public class CancelarOperacion extends Decision{
	private int idOp;
	
	public CancelarOperacion(Agente a, int idOperacion){
		super(a, 1);
		idOp = idOperacion;
	}
	
	public void ejecuta() {
		agente.cancelarOperacion(idOp);
	}
	
	public String toString(){
		return "Cancela operacion "+idOp;
	}
}
