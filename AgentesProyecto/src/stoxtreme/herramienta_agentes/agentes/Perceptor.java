package stoxtreme.herramienta_agentes.agentes;

import stoxtreme.herramienta_agentes.EstadoBolsa;
import stoxtreme.herramienta_agentes.ListenerNotificador;
import stoxtreme.herramienta_agentes.MonitorAgentes;
import stoxtreme.herramienta_agentes.Operacion;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.comportamiento.EstadoCartera;

public class Perceptor implements ListenerNotificador{
	private EstadoBolsa estadoBolsa = null;
	private EstadoCartera estadoCartera = null;
	private OperacionesPendientes opPendientes = null;
	private ComportamientoAgente gDecisiones = null;
	
	public void setEstadoBolsa(EstadoBolsa estado){
		estadoBolsa = estado;
	}
	
	public void setEstadoCartera(EstadoCartera estado){
		estadoCartera = estado;
	}
	
	public void setOperacionesPendientes(OperacionesPendientes opPendientes){
		this.opPendientes = opPendientes;
	}
	
	public void setGeneradorDecisiones(ComportamientoAgente gDeci){
		this.gDecisiones = gDeci;
	}
	
	public void onCambioPrecioAccion(String empresa, double nuevoPrecio) {
		estadoBolsa.cambioPrecioAccion(empresa, nuevoPrecio);
	}

	public void onNotificacionOp(int idOp, int cantidad, double precio) {
		if(	estadoBolsa != null && estadoCartera != null &&
			opPendientes != null && gDecisiones != null){
			String empresa = opPendientes.getEmpresaOperacion(idOp);
			int tipoOp = opPendientes.getTipoOperacion(idOp);
					
			switch (tipoOp){
				case Operacion.COMPRA:
					estadoCartera.insertaAcciones(empresa, cantidad);
					break;
				case Operacion.VENTA:
					estadoCartera.quitaAcciones(empresa, cantidad);
					break;
			}
			
			opPendientes.restaAcciones(idOp, cantidad);
		}
	}

	public void onCancelacionOp(int idOp) {
		opPendientes.cancelaOp(idOp);
		gDecisiones.notifica(-idOp);
	}
}
