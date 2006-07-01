package stoxtreme.herramienta_agentes.agentes.comportamiento.informadores;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.MandarMensaje;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.MensajeACL;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.Performative;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class AgenteRumores extends ComportamientoAgente {
	/**
	 *  Description of the Method
	 */
	public void configure() {

	}


	/**
	 *  Description of the Method
	 */
	public void generacionDecisiones() {
		if (Math.random() < 0.1) {
			MensajeACL m = new MensajeACL();
			m.addReceiver(IDAgente.BROADCAST);
			m.setPerformative(Performative.INFORM);
			String empresa = estadoBolsa.dameEmpresaAleatoria();

			if (Math.random() > 0.5) {
				m.setContenidoString(empresa + ",1.8");
			}
			else {
				m.setContenidoString(empresa + ",0.8");
			}

			MandarMensaje decision = new MandarMensaje(false, m);
			decisiones.add(decision);
		}
	}
}
