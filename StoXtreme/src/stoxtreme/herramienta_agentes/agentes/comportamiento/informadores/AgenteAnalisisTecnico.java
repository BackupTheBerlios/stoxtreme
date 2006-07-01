package stoxtreme.herramienta_agentes.agentes.comportamiento.informadores;

import java.util.ArrayList;
import java.util.Hashtable;

import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.AltaServicio;
import stoxtreme.herramienta_agentes.agentes.decisiones.MandarMensaje;
import stoxtreme.herramienta_agentes.agentes.decisiones.RecibirMensaje;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.MensajeACL;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.Performative;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.PlantillaMensajes;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.Protocol;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.RecepcionMensaje;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class AgenteAnalisisTecnico extends ComportamientoAgente {

	private double precioRecomendacion;
	/**
	 *  Description of the Field
	 */
	public static String NOMBRE_SERVICIO = "analisis_tecnico";


	/**
	 *  Description of the Method
	 */
	public void generacionDecisiones() {

	}


	/**
	 *  Description of the Method
	 *
	 *@param  reply    Description of Parameter
	 *@param  empresa  Description of Parameter
	 *@param  agente   Description of Parameter
	 */
	public void darRecomendacion(String reply, String empresa, IDAgente agente) {
//		if(!MediadorOperaciones.getMediadorGlobal().getBanco().retiraDinero(precioRecomendacion,
//				agente.toString())){
//			MensajeACL m = new MensajeACL();
//			m.addReceiver(agente);
//			m.setReply_with(reply);
//			m.setProtocol(Protocol.REQUEST);
//			m.setPerformative(Performative.FAILURE);
//			decisiones.add(new MandarMensaje(false, m));
//			return;
//		}

		MensajeACL m = new MensajeACL();
		m.addReceiver(agente);
		m.setIn_reply_to(reply);
		m.setContenidoString(empresa + ",1.3");
		m.setProtocol(Protocol.REQUEST);
		m.setPerformative(Performative.INFORM);
		decisiones.add(new MandarMensaje(false, m));
	}


	/**
	 *  Description of the Method
	 */
	public void configure() {
		this.precioRecomendacion = modeloPsicologico.precioRecomendacion();

		RecepcionMensaje recepcion =
			new RecepcionMensaje() {
				public void ejecuta() {
					String with = mensajeRecibido.getReply_with();
					String contenido = mensajeRecibido.getContenidoString();
					IDAgente envio = mensajeRecibido.getSender();

					if (!contenido.contains(",")) {
						MensajeACL m = new MensajeACL();
						m.addReceiver(envio);
						m.setReply_with(with);
						m.setProtocol(Protocol.REQUEST);
						m.setPerformative(Performative.FAILURE);
						decisiones.add(new MandarMensaje(false, m));
					}
					String[] split = contenido.split(",");
					String empresa = split[0];
					double precio = Double.parseDouble(split[1]);

					if (envio == null) {
						envio = mensajeRecibido.getReply_to();
					}

					if (precio < precioRecomendacion) {
						MensajeACL m = new MensajeACL();
						m.addReceiver(envio);
						m.setReply_with(with);
						m.setProtocol(Protocol.REQUEST);
						m.setPerformative(Performative.FAILURE);
						decisiones.add(new MandarMensaje(false, m));
					}
					else {
						MensajeACL m = new MensajeACL();
						m.addReceiver(envio);
						m.setReply_with(with);
						m.setProtocol(Protocol.REQUEST);
						m.setPerformative(Performative.AGREE);
						decisiones.add(new MandarMensaje(false, m));
					}
					darRecomendacion(with, empresa, envio);
				}
			};
		PlantillaMensajes plantilla = new PlantillaMensajes();
		plantilla.add(PlantillaMensajes.Campos.PERFORMATIVE,
				Performative.REQUEST);
		plantilla.add(PlantillaMensajes.Campos.PROTOCOL, Protocol.REQUEST);
		decisiones.add(new RecibirMensaje(2, plantilla, recepcion));

		decisiones.add(new AltaServicio(NOMBRE_SERVICIO));
	}
}
