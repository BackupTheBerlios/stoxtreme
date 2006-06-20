package stoxtreme.herramienta_agentes.agentes.comportamiento;

import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;

public class ModeloPsicologico {
	private ParametrosPsicologicos p;
	
	public ModeloPsicologico(ParametrosPsicologicos p){
		this.p = p;
	}
	
	public int numeroCompraAcciones(){
		if(	p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_COMPRA) 
			>
			p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_COMPRA)
		)
			return 0;
		
		int recomendado = p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_COMPRA)+
		(int)((p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MAXIMO_ACCIONES_COMPRA) -
				p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_COMPRA)) * Math.random());
		return recomendado;
	}
	
	public int numeroVentaAcciones(){
		if(	p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_VENTA) 
				>
				p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_VENTA)
			)
				return 0;
			
			int recomendado = p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_VENTA)+
			(int)((p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MAXIMO_ACCIONES_VENTA) -
					p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MINIMO_ACCIONES_VENTA)) * Math.random());
			return recomendado;
	}
	
	public double precioCompraAcciones(double precioActual){
		double procentajeRecomendado = (double)
			(p.getParamDouble(ParametrosPsicologicos.Parametro.PORCENTAJE_MAXIMO_COMPRA)*Math.random());
		double precio = (1 - procentajeRecomendado)*precioActual;
		return Math.ceil(precio*100)/100;
	}
	
	public double precioVentaAcciones(double precioActual){
		double porcentajeRecomendado = (double)
			(p.getParamDouble(ParametrosPsicologicos.Parametro.PORCENTAJE_MAXIMO_VENTA)*Math.random());
		double precio = (1+porcentajeRecomendado)*precioActual;
		return Math.ceil(precio*100)/100;
	}

	public double porcentajeSubidaPrecio() {
		return p.getParamDouble(ParametrosPsicologicos.Parametro.PORCENTAJE_SUBIDA_PRECIO);
	}

	public double porcentajeBajadaPrecio() {
		return p.getParamDouble(ParametrosPsicologicos.Parametro.PORCENTAJE_BAJADA_PRECIO);
	}

	public int numeroMaximoCancelaciones() {
		return (int)(p.getParamInt(ParametrosPsicologicos.Parametro.NUMERO_MAXIMO_CANCELACIONES)*Math.random());
	}

	public int getTiempoEspera() {
		return p.getParamInt(ParametrosPsicologicos.Parametro.TIEMPO_ESPERA);
	}

	public double precioRecomendacion() {
		return p.getParamDouble(ParametrosPsicologicos.Parametro.PRECIO_RECOMENDACION);
	}

	public double precioCompraRecomendacion() {
		return p.getParamDouble(ParametrosPsicologicos.Parametro.PRECIO_COMPRA_RECOMENDACION);
	}
}
