package stoxtreme.herramienta_agentes.agentes.decisiones;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.interfaz_remota.Operacion;

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
		return "Introducir operacion "+o;
	}
}
