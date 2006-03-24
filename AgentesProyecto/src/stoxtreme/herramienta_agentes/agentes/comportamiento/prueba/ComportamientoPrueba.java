package stoxtreme.herramienta_agentes.agentes.comportamiento.prueba;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.EstadoBolsa;
import stoxtreme.herramienta_agentes.Operacion;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.*;

public class ComportamientoPrueba extends ComportamientoAgente{
	public ComportamientoPrueba(EstadoBolsa estadoBolsa, ParametrosSocial ps, ParametrosPsicologicos pp) {
		super(estadoBolsa, ps, pp);
	}
	
	public ArrayList<Decision> tomaDecisiones() {
		ArrayList<Decision> decisiones = new ArrayList<Decision>();
		//if(estadoBolsa.getPrecioActualEmpresa("Endesa")>10.0)
		decisiones.add(new IntroducirOperacion(agente, new Operacion(agente.getIDString(), "Endesa", Operacion.COMPRA, 100, 10.0)));
		decisiones.add(new Espera(agente, 10));
		return decisiones;
	}
}
