package stoxtreme.herramienta_agentes.agentes.decisiones;
import stoxtreme.herramienta_agentes.agentes.Agente;
import stoxtreme.interfaz_remota.Operacion;

public class IntroducirOperacion extends Decision{
	private Operacion o;
	
	public IntroducirOperacion(Operacion op){
		super();
		o = op;
	}
	
	public void ejecuta() {
		String tipo;
		if(o.getTipoOp() == Operacion.COMPRA){
			tipo = "compra";
		}
		else{
			tipo = "Venta";
		}
//		System.err.println(tipo+" "+o.getPrecio()+" "+o.getCantidad());
		agente.insertarOperacion(o);
	}
	
	public String toString(){
		return "Introducir operacion "+o;
	}
	
	public void setAgente(Agente agente) {
		super.setAgente(agente);
		o.setIdEmisor(agente.getIDString());
	}
}
