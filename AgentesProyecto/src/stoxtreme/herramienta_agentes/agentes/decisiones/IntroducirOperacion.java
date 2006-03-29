package stoxtreme.herramienta_agentes.agentes.decisiones;
import stoxtreme.herramienta_agentes.Operacion;
import stoxtreme.herramienta_agentes.agentes.Agente;

public class IntroducirOperacion extends Decision{
	private Operacion o;
	
	public IntroducirOperacion(Agente a, Operacion op){
		super(a, 1);
		o = op;
	}
	
	public void ejecuta() {
		agente.insertarOperacion(o);
	}
	
	public String toString(){
		return "Introducir operacion "+o.datosImportantes();
	}
}
