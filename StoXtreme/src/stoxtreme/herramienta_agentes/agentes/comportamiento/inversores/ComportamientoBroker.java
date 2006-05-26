package stoxtreme.herramienta_agentes.agentes.comportamiento.inversores;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.MonitorAgentes;
import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;
import stoxtreme.herramienta_agentes.agentes.ParametrosSocial;
import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.CancelarOperacion;
import stoxtreme.herramienta_agentes.agentes.decisiones.Decision;
import stoxtreme.herramienta_agentes.agentes.decisiones.Espera;
import stoxtreme.herramienta_agentes.agentes.decisiones.IntroducirOperacion;
import stoxtreme.interfaz_remota.Operacion;


public class ComportamientoBroker extends ComportamientoAgente{
	public double redondeo(double numero, int nDecimales){
		return Math.floor((Math.pow(10, nDecimales)*numero)+0.5)/Math.pow(10, nDecimales);
	}
	public Operacion generaCompra(String empresa){
		int tipoOp = Operacion.COMPRA;
		int cantidad = modeloPsicologico.numeroCompraAcciones();
		double precio = modeloPsicologico.precioCompraAcciones( 
			estadoBolsa.getPrecioActualEmpresa(empresa));
		precio = redondeo(precio,2);				
		return new Operacion(null, tipoOp, cantidad, empresa, precio);
	}
	
	public Operacion generaVenta(String empresa){
		int tipoOp = Operacion.VENTA;
		//int cantidad = modeloPsicologico.numeroVentaAcciones();
		int cantidad = estadoCartera.numeroAccionesPosesion(empresa);
		double precio = modeloPsicologico.precioVentaAcciones( 
			estadoBolsa.getPrecioActualEmpresa(empresa));
		precio = redondeo(precio,2);				
		return new Operacion(null, tipoOp, cantidad, empresa, precio);	
	}
	
	public void configure() {
	}

	public void generacionDecisiones() {
		//Genera una nueva operacion
		String empresa = estadoBolsa.dameEmpresaAleatoria();
		
		if(!operacionesPendientes.hayOperacionesPendientes(empresa)){
			Operacion o;
			int tipo = (int)(Math.random()*2 + 1);
			
			if(tipo == Operacion.COMPRA || !estadoCartera.tieneAccionesPosesion(empresa)){
				o = generaCompra(empresa);
				estado ="Genero compra";
				//System.out.println("COMPRA "+o.getIDAgente()+" "+o.getPrecio());
			}
			else{ // No tiene acciones de esa empresa
				o = generaVenta(empresa);
				estado ="Genero venta";
				//System.out.println("VENTA "+o.getIDAgente()+" "+o.getPrecio());
			}
			if(o.getCantidad() >0)
				decisiones.add(new IntroducirOperacion(o));
		}
		else{
			int idOp = operacionesPendientes.dameOperacionAleatoria();

			if(!operacionesPendientes.isPendienteCancelacion(-idOp)){
				decisiones.add(new CancelarOperacion(idOp));
				// Una cancelacion es una operacion pendiente
				
				if(numeroCancelaciones < modeloPsicologico.numeroMaximoCancelaciones()) {
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
					Operacion op = new Operacion(null, tipo, cantidad, empresa, precio);
					generaDecisionAPartirNotificacion(-idOp, new IntroducirOperacion(op));
					estado = "Genera cancelacion";
					numeroCancelaciones++;
				}
				else {
					numeroCancelaciones = 0;
				}
			}
		}
	}

	public String getEstadoComportamiento() {
		return estado;
	}
}
