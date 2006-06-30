package stoxtreme.herramienta_agentes.agentes.comportamiento.inversores;

import java.util.ArrayList;

import stoxtreme.herramienta_agentes.agentes.comportamiento.ComportamientoAgente;
import stoxtreme.herramienta_agentes.agentes.decisiones.CancelarOperacion;
import stoxtreme.herramienta_agentes.agentes.decisiones.IntroducirOperacion;
import stoxtreme.interfaz_remota.Operacion;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ComportamientoAleatorio extends ComportamientoAgente {

	/**
	 *  Gets the NombreComportamiento attribute of the ComportamientoAleatorio
	 *  object
	 *
	 *@return    The NombreComportamiento value
	 */
	public String getNombreComportamiento() {
		return "Aleatorio";
	}


	/**
	 *  Description of the Method
	 */
	public void configure() {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@return          Description of the Returned Value
	 */
	public Operacion generaCompra(String empresa) {
		int tipoOp = Operacion.COMPRA;
		int cantidad = modeloPsicologico.numeroCompraAcciones();
		double precio = modeloPsicologico.precioCompraAcciones(
				estadoBolsa.getPrecioActualEmpresa(empresa));
		precio = redondeo(precio, 2);
		return new Operacion(null, tipoOp, cantidad, empresa, precio);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@return          Description of the Returned Value
	 */
	public Operacion generaVenta(String empresa) {
		int tipoOp = Operacion.VENTA;
		//int cantidad = modeloPsicologico.numeroVentaAcciones();
		int cantidad = estadoCartera.numeroAccionesPosesion(empresa);
		cantidad = (int) (((double) cantidad) * Math.random());
		double precio = modeloPsicologico.precioVentaAcciones(
				estadoBolsa.getPrecioActualEmpresa(empresa));
		precio = redondeo(precio, 2);
		return new Operacion(null, tipoOp, cantidad, empresa, precio);
	}


	/**
	 *  Description of the Method
	 */
	public void generacionDecisiones() {
		//Genera una nueva operacion
		String empresa = estadoBolsa.dameEmpresaAleatoria();

		if (decisiones.size() == 0) {
			if (!operacionesPendientes.hayOperacionesPendientes(empresa)) {
				Operacion o;
				int tipo = (int) (Math.random() * 2 + 1);

				if (tipo == Operacion.COMPRA ||
						!estadoCartera.tieneAccionesPosesion(empresa) ||
						estadoBolsa.getPrecioActualEmpresa(empresa) < estadoBolsa.getPrecioAperturaEmpresa(empresa)) {
					o = generaCompra(empresa);
					estado = "Genera compra";
					//System.out.println("COMPRA "+o.getIDAgente()+" "+o.getPrecio());
				}
				else {
					// No tiene acciones de esa empresa
					o = generaVenta(empresa);
					estado = "Genera venta";
					//System.out.println("VENTA "+o.getIDAgente()+" "+o.getPrecio());
				}
				if (o.getCantidad() > 0) {
					decisiones.add(new IntroducirOperacion(o));
				}
			}
			else {
				int idOp = operacionesPendientes.dameOperacionAleatoria();

				if (!operacionesPendientes.isPendienteCancelacion(idOp)) {
					decisiones.add(new CancelarOperacion(idOp));
					estado = "Genera cancelacion";
					// Una cancelacion es una operacion pendiente

					if (numeroCancelaciones < modeloPsicologico.numeroMaximoCancelaciones()) {
						double precio = operacionesPendientes.getPrecioOperacion(idOp);
						int tipo = operacionesPendientes.getTipoOperacion(idOp);

						int cantidad;
						if (tipo == Operacion.COMPRA) {
							precio = precio * (1 + modeloPsicologico.porcentajeSubidaPrecio());
							cantidad = modeloPsicologico.numeroCompraAcciones();
						}
						else {
							precio = precio * (1 - modeloPsicologico.porcentajeBajadaPrecio());
							cantidad = estadoCartera.numeroAccionesPosesion(empresa);
							cantidad = (int) (((double) cantidad) * Math.random());
						}
						precio = redondeo(precio, 2);
						Operacion op = new Operacion(null, tipo, cantidad, empresa, precio);
						generaDecisionAPartirNotificacion(-idOp, new IntroducirOperacion(op));

						numeroCancelaciones++;
					}
					else {
						numeroCancelaciones = 0;
					}
				}
				else {
					int x = 0;
					x++;
				}
			}
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  numero      Description of Parameter
	 *@param  nDecimales  Description of Parameter
	 *@return             Description of the Returned Value
	 */
	public static double redondeo(double numero, int nDecimales) {
		return Math.floor((Math.pow(10, nDecimales) * numero) + 0.5) / Math.pow(10, nDecimales);
	}
}
