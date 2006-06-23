package stoxtreme.herramienta_agentes.agentes.comportamiento.prueba;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.*;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.cliente.infoLocal.*;

public class ComportamientoPrueba extends ComportamientoAgente{
	public void configure() {
		
	}
	
	public void generacionDecisiones() {
		ParserInfoLocal parser= new ParserInfoLocal();
		DatoHistorico dh =parser.getDatoHistorico("endesa","02/01/2004");
		Double precio1=dh.getPrecioCierre();
		dh=parser.getDatoHistorico("endesa", "09/12/2004");
		Double precio2=dh.getPrecioCierre();
		if(precio2>precio1)
			decisiones.add(new IntroducirOperacion(new Operacion(null,Operacion.COMPRA,100,"ENDESA",new InfoLocal().getPrecioInicial("ENDESA"))));
	}

	public String getNombreComportamiento() {
		return "Prueba";
	}
}
