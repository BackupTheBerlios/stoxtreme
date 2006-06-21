package stoxtreme.herramienta_agentes.agentes;

import java.util.ArrayList;

import stoxtreme.cliente.EstadoBolsa;
import stoxtreme.herramienta_agentes.ListenerNotificador;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.interfaz_remota.Operacion;

public class Perceptor implements ListenerNotificador{
	private EstadoBolsa estadoBolsa = null;
	private EstadoCartera estadoCartera = null;
	private OperacionesPendientes opPendientes = null;
	private ArrayList<ComportamientoAgente> gDecisiones = null;
	private Agente agente;
	
	public Perceptor() {
		gDecisiones = new ArrayList<ComportamientoAgente>();
	}
	
	public void setEstadoBolsa(EstadoBolsa estado){
		estadoBolsa = estado;
	}
	
	public void setEstadoCartera(EstadoCartera estado){
		estadoCartera = estado;
	}
	
	public void setOperacionesPendientes(OperacionesPendientes opPendientes){
		this.opPendientes = opPendientes;
	}
	
	public void addGeneradorDecisiones(ComportamientoAgente gDeci){
		this.gDecisiones.add(gDeci);
	}
	
	public void onNotificacionOp(int idOp, int cantidad, double precio) {
		if(	estadoBolsa != null && estadoCartera != null &&
			opPendientes != null && gDecisiones.size()!=0){
			String empresa = opPendientes.getEmpresaOperacion(idOp);
			int tipoOp = opPendientes.getTipoOperacion(idOp);
					
			switch (tipoOp){
				case Operacion.COMPRA:
					estadoCartera.insertaAcciones(empresa, cantidad);
					agente.decrementaBeneficio(cantidad*precio);
					break;
				case Operacion.VENTA:
					estadoCartera.quitaAcciones(empresa, cantidad);
					agente.incrementaBeneficio(cantidad*precio);
					break;
			}
			
			opPendientes.restaAcciones(idOp, cantidad);
		}
	}

	public void onCancelacionOp(int idOp) {
		opPendientes.cancelaOp(idOp);
		for(int i=0; i<gDecisiones.size(); i++){
			gDecisiones.get(i).notifica(-idOp);
		}
	}

	public void setAgente(Agente agente) {
		this.agente = agente;
	}
}
