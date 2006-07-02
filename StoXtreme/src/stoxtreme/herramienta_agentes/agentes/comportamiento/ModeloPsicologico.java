package stoxtreme.herramienta_agentes.agentes.comportamiento;

import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ModeloPsicologico {
	private ParametrosPsicologicos p;


	/**
	 *  Constructor for the ModeloPsicologico object
	 *
	 *@param  p  Description of Parameter
	 */
	public ModeloPsicologico(ParametrosPsicologicos p) {
		this.p = p;
	}


	/**
	 *  Gets the TiempoEspera attribute of the ModeloPsicologico object
	 *
	 *@return    The TiempoEspera value
	 */
	public int getTiempoEspera() {
		return p.getParamInt(ParametrosPsicologicos.Parametro.TIEMPO_ESPERA);
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public int numeroCompraAcciones() {
		if (p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_COMPRA)
				>
				p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_COMPRA)
				) {
			return 0;
		}

		int recomendado = p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_COMPRA) +
				(int) ((p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MAXIMO_ACCIONES_COMPRA) -
				p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_COMPRA)) * Math.random());
		return recomendado;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public int numeroVentaAcciones() {
		if (p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_VENTA)
				>
				p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_VENTA)
				) {
			return 0;
		}

		int recomendado = p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_VENTA) +
				(int) ((p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MAXIMO_ACCIONES_VENTA) -
				p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_VENTA)) * Math.random());
		return recomendado;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  precioActual  Description of Parameter
	 *@return               Description of the Returned Value
	 */
	public double precioCompraAcciones(double precioActual) {
		double procentajeRecomendado = (double)
				(p.getParamDouble(ParametrosPsicologicos.Parametro.PORCENTAJE_MAXIMO_COMPRA) * Math.random());
		double precio = (1 - procentajeRecomendado) * precioActual;
		return Math.ceil(precio * 100) / 100;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  precioActual  Description of Parameter
	 *@return               Description of the Returned Value
	 */
	public double precioVentaAcciones(double precioActual) {
		double porcentajeRecomendado = (double)
				(p.getParamDouble(ParametrosPsicologicos.Parametro.PORCENTAJE_MAXIMO_VENTA) * Math.random());
		double precio = (1 + porcentajeRecomendado) * precioActual;
		return Math.ceil(precio * 100) / 100;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public double porcentajeSubidaPrecio() {
		return p.getParamDouble(ParametrosPsicologicos.Parametro.PORCENTAJE_SUBIDA_PRECIO);
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public double porcentajeBajadaPrecio() {
		return p.getParamDouble(ParametrosPsicologicos.Parametro.PORCENTAJE_BAJADA_PRECIO);
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public int numeroMaximoCancelaciones() {
		return (int) (p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MAXIMO_CANCELACIONES) * Math.random());
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public double precioRecomendacion() {
		return p.getParamDouble(ParametrosPsicologicos.Parametro.PRECIO_RECOMENDACION);
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public double precioCompraRecomendacion() {
		return p.getParamDouble(ParametrosPsicologicos.Parametro.PRECIO_COMPRA_RECOMENDACION);
	}


	public int getNumeroMaximoCompra() {
		return p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MAXIMO_ACCIONES_COMPRA);
	}


	public int getNumeroMinimoCompra() {
		return p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_COMPRA);
	}
	
	public int getNumeroMaximoVenta() {
		return p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MAXIMO_ACCIONES_VENTA);
	}


	public int getNumeroMinimoVenta() {
		return p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_VENTA);
	}
}
