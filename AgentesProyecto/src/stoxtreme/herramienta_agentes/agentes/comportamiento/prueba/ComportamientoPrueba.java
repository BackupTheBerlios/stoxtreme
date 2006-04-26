package stoxtreme.herramienta_agentes.agentes.comportamiento.prueba;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.EstadoBolsa;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.*;
import stoxtreme.interfaz_remota.Operacion;

public class ComportamientoPrueba extends ComportamientoAgente{
	public ComportamientoPrueba() {
		super();
	}
	
	public ArrayList<Decision> generacionDecisiones() {
		ArrayList<Decision> decisiones = new ArrayList<Decision>();
		//if(estadoBolsa.getPrecioActualEmpresa("Endesa")>10.0)
		decisiones.add(new IntroducirOperacion(new Operacion(null,Operacion.COMPRA, 100, "Endesa", 10.0)));
		return decisiones;
	}
}
