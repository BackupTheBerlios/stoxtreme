package stoxtreme.herramienta_agentes.agentes.comportamiento.inversores;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.IDAgente;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.comportamiento.informadores.AgenteAnalisisFundamental;
import stoxtreme.herramienta_agentes.agentes.decisiones.MandarMensaje;
import stoxtreme.herramienta_agentes.agentes.decisiones.RecibirMensaje;
import stoxtreme.herramienta_agentes.agentes.decisiones.RequestServicio;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.MensajeACL;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.Performative;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.PlantillaMensajes;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.Protocol;
import stoxtreme.herramienta_agentes.agentes.interaccion_agentes.RecepcionMensaje;
import sun.misc.PerformanceLogger;

public class ComportamientoCompraRecomendacion extends ComportamientoAgente{
	private ArrayList<IDAgente> listaProveedores;
	private boolean pendienteRespuesta;
	private int ultimoEnviado;
	
	public void configure() {
		// RECEPCION DEL AGREE
		PlantillaMensajes pAgree = new PlantillaMensajes();
		pAgree.add(PlantillaMensajes.Campos.PERFORMATIVE, Performative.AGREE);
		pAgree.add(PlantillaMensajes.Campos.PROTOCOL, Protocol.REQUEST);
		
		RecepcionMensaje rAgree = new RecepcionMensaje(){
			public void ejecuta() {
				onRecepcionAgree();
			}
		};
		decisiones.add(new RecibirMensaje(2, pAgree, rAgree));
		
		// RECEPCION DEL FAILURE
		PlantillaMensajes pFailure = new PlantillaMensajes();
		pFailure.add(PlantillaMensajes.Campos.PERFORMATIVE, Performative.FAILURE);
		pFailure.add(PlantillaMensajes.Campos.PROTOCOL, Protocol.REQUEST);
		
		RecepcionMensaje rFailure = new RecepcionMensaje(){
			public void ejecuta() {
				onRecepcionFailure();
			}
		};
		decisiones.add(new RecibirMensaje(1, pFailure, rFailure));
		
		// RECEPCION DEL INFORM
		PlantillaMensajes pInform = new PlantillaMensajes();
		pInform.add(PlantillaMensajes.Campos.PERFORMATIVE, Performative.INFORM);
		pInform.add(PlantillaMensajes.Campos.PROTOCOL, Protocol.REQUEST);
		
		RecepcionMensaje rInform = new RecepcionMensaje(){
			public void ejecuta() {
				String msg = mensajeRecibido.getContenidoString();
				IDAgente ids = mensajeRecibido.getSender();
				onRecepcionInform(ids, msg);
			}
		};
		decisiones.add(new RecibirMensaje(1, pInform, rInform));
		
		listaProveedores = new ArrayList<IDAgente>();
//		decisiones.add(new RequestServicio(AgenteAnalisisFundamental.NOMBRE_SERVICIO, listaProveedores));
		pendienteRespuesta = false;
		ultimoEnviado = -1;
	}
	
	public void generacionDecisiones() {
		if(listaProveedores.size() > 0){
			if(!pendienteRespuesta){
				pendienteRespuesta = true;
				MensajeACL mensaje = new MensajeACL();
				mensaje.setPerformative(Performative.REQUEST);
				mensaje.setProtocol(Protocol.REQUEST);
				String empresa = estadoBolsa.dameEmpresaAleatoria();
				mensaje.setContenidoString(empresa+","+modeloPsicologico.precioCompraRecomendacion());
				//decisiones.add(new ReservarDinero(modeloPsicologico.precioCompraRecomendacion()));
				mensaje.addReceiver(listaProveedores.get((++ultimoEnviado)%listaProveedores.size()));
				decisiones.add(new MandarMensaje(false, mensaje));
			}
		}
		else{
			decisiones.add(new RequestServicio(AgenteAnalisisFundamental.NOMBRE_SERVICIO, listaProveedores));
		}
	}
	
	public void onRecepcionAgree(){
		pendienteRespuesta = false;
	}
	
	public void onRecepcionFailure(){
		pendienteRespuesta = false;
	}
	
	public void onRecepcionInform(IDAgente ids, String msg){
		//FIXME: alonso mola
		System.out.println(ids.toString()+" "+msg);
	}

	public String getNombreComportamiento() {
		return "Compra Recomendacion";
	}
}
