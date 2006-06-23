package stoxtreme.herramienta_agentes.agentes.comportamiento.inversores;

import java.util.ArrayList;
import java.util.Iterator;

import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.IntroducirOperacion;
import stoxtreme.herramienta_agentes.agentes.decisiones.RecibirMensaje;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.Performative;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.PlantillaMensajes;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.RecepcionMensaje;
import stoxtreme.interfaz_remota.Operacion;

public class ComportamientoRumor extends ComportamientoAgente{
	public void configure() {
		// Inicialmente insertamos en las decisiones en espera la de recibir
		// mensajes
		PlantillaMensajes p = new PlantillaMensajes();
		p.add(PlantillaMensajes.Campos.PERFORMATIVE, Performative.INFORM);
		decisiones.add(new RecibirMensaje(1,p, new RecepcionMensajeRumorPrecio(this)));
	}
	
	public void onSubePrecio(IDAgente emisor, String empresa, double porcentaje) {
		//int nAcciones = estadoCartera.numeroAccionesPosesion(empresa);
		//System.out.println("Recibido rumor de "+emisor+" sobre "+empresa+" de "+porcentaje);
		int nAcciones = 100;
		int numAccionesVenta = (int)(nAcciones * this.modeloSocial.getFiabilidad(emisor));
		double precio = estadoBolsa.getPrecioActualEmpresa(empresa);
			
		Operacion op = new Operacion(
				null, 
				Operacion.COMPRA,
				numAccionesVenta,
				empresa,
				precio);
		decisiones.add(new IntroducirOperacion(op));
		// Como hemos recibido uno lo configuramos para otro
		PlantillaMensajes p = new PlantillaMensajes();
		p.add(PlantillaMensajes.Campos.PERFORMATIVE, Performative.INFORM);
		decisiones.add(new RecibirMensaje(1, p, new RecepcionMensajeRumorPrecio(this)));
	}

	public void generacionDecisiones() {
	}

	public void onBajaPrecio(IDAgente emisor, String empresa, double porcentaje) {
//		int nAcciones = estadoCartera.numeroAccionesPosesion(empresa);
		//System.out.println("Recibido rumor de "+emisor+" sobre "+empresa+" de "+porcentaje);
		int nAcciones = 100;
		int numAccionesVenta = (int)(nAcciones * this.modeloSocial.getFiabilidad(emisor));
		double precio = estadoBolsa.getPrecioActualEmpresa(empresa);
			
		Operacion op = new Operacion(
				null, 
				Operacion.VENTA,
				numAccionesVenta,
				empresa, 
				precio);
		
		decisiones.add(new IntroducirOperacion(op));
		
		// Como hemos recibido uno lo configuramos para otro
		PlantillaMensajes p = new PlantillaMensajes();
		p.add(PlantillaMensajes.Campos.PERFORMATIVE, Performative.INFORM);
		decisiones.add(new RecibirMensaje(1, p, new RecepcionMensajeRumorPrecio(this)));
	}
	
	private class RecepcionMensajeRumorPrecio extends RecepcionMensaje{
		ComportamientoRumor c;
		public RecepcionMensajeRumorPrecio(ComportamientoRumor c){
			this.c = c;
		}
		
		public void ejecuta() {
			String s = mensajeRecibido.getContenidoString();
			String[] contenido = s.split(",");
			double porcentaje = Double.parseDouble(contenido[1]);
			if(porcentaje > 1.0)
				c.onSubePrecio(mensajeRecibido.getSender(), contenido[0], porcentaje);
			else
				c.onBajaPrecio(mensajeRecibido.getSender(), contenido[0], porcentaje);
		}
	}

	public String getNombreComportamiento() {
		return "Rumor";
	}
}
