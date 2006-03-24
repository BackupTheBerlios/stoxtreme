package stoxtreme.herramienta_agentes.agentes.comportamiento.inversores;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.EstadoBolsa;
import stoxtreme.herramienta_agentes.MonitorAgentes;
import stoxtreme.herramienta_agentes.Operacion;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.CancelarOperacion;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.herramienta_agentes.agentes.decisiones.Espera;
import stoxtreme.herramienta_agentes.agentes.decisiones.IntroducirOperacion;


public class ComportamientoBroker extends ComportamientoAgente{
	public ComportamientoBroker(EstadoBolsa bolsa, ParametrosSocial ps, ParametrosPsicologicos pp){
		super(bolsa, ps, pp);
	}
	
	public Operacion generaCompra(String empresa){
		int tipoOp = Operacion.COMPRA;
		int cantidad = modeloPsicologico.numeroCompraAcciones();
		double precio = modeloPsicologico.precioCompraAcciones( 
			estadoBolsa.getPrecioActualEmpresa(empresa));
						
		return new Operacion(agente.getIDString(), empresa, tipoOp, cantidad, precio);
	}
	
	public Operacion generaVenta(String empresa){
		int tipoOp = Operacion.VENTA;
		//int cantidad = modeloPsicologico.numeroVentaAcciones();
		int cantidad = estadoCartera.numeroAccionesPosesion(empresa);
		double precio = modeloPsicologico.precioVentaAcciones( 
			estadoBolsa.getPrecioActualEmpresa(empresa));
						
		return new Operacion(agente.getIDString(), empresa, tipoOp, cantidad, precio);	
	}
	
	public ArrayList<Decision> tomaDecisiones() {
		ArrayList<Decision> lista = listaDecisiones;
		listaDecisiones = new ArrayList<Decision>();
		
		if(!operacionesPendientes.hayOperacionesPendientes()){
			// Genera una nueva operacion
			String empresa = estadoBolsa.dameEmpresaAleatoria();
			Operacion o;
			int tipo = (int)(Math.random()*2 + 1);
			
			if(tipo == Operacion.COMPRA || !estadoCartera.tieneAccionesPosesion(empresa)){
				o = generaCompra(empresa);
				//System.out.println("COMPRA "+o.getIDAgente()+" "+o.getPrecio());
			}
			else{ // No tiene acciones de esa empresa
				o = generaVenta(empresa);
				//System.out.println("VENTA "+o.getIDAgente()+" "+o.getPrecio());
			}
			if(o.getNumeroAcciones() >0)
				lista.add(new IntroducirOperacion(agente, o));
		}
		else{
			int idOp =
				operacionesPendientes.dameOperacionAleatoria();
			if(!decisionesEsperaNotificacion.contains(-idOp)){
				if(numeroCancelaciones < modeloPsicologico.numeroMaximoCancelaciones()){
					double precio = operacionesPendientes.getPrecioOperacion(idOp);
					int tipo = operacionesPendientes.getTipoOperacion(idOp);
				
					int cantidad;
					if(tipo == Operacion.COMPRA){
						precio = precio*(1 + modeloPsicologico.porcentajeSubidaPrecio());
						cantidad = modeloPsicologico.numeroCompraAcciones();
					}
					else{
						precio = precio*(1 - modeloPsicologico.porcentajeBajadaPrecio());
						cantidad = modeloPsicologico.numeroVentaAcciones();
					}
					String empresa = operacionesPendientes.getEmpresaOperacion(idOp);
		
					Operacion op = new Operacion(agente.getIDString(), empresa, tipo, cantidad, precio);
					generaDecisionAPartirNotificacion(-idOp, new IntroducirOperacion(agente, op));

					
				}
				else{
					numeroCancelaciones = 0;
				}
				lista.add(new CancelarOperacion(agente, idOp));
				numeroCancelaciones++;
			}
		}
			
		lista.add(new Espera(agente, (int)Math.floor(modeloPsicologico.getTiempoEspera())));
		return lista;
	}
}
