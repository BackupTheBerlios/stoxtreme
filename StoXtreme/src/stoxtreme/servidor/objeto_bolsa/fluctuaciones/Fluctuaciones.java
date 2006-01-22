package stoxtreme.servidor.objeto_bolsa.fluctuaciones;
import servidor.objeto_bolsa.operaciones.*;
import stoxtreme.servidor.objeto_bolsa.operaciones.SistOperaciones;

public class Fluctuaciones {
	private SistOperaciones sistOp;
	public Fluctuaciones(SistOperaciones sop, float tick, float precioInicial){
		this.sistOp = sop;
	}

	public void paso() {

	}
}
