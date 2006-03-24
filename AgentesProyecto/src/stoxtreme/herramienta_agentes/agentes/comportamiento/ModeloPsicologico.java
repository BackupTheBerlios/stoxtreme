package stoxtreme.herramienta_agentes.agentes.comportamiento;

import stoxtreme.herramienta_agentes.agentes.ParametrosPsicologicos;

public class ModeloPsicologico {
	private ParametrosPsicologicos p;
	
	public ModeloPsicologico(ParametrosPsicologicos p){
		this.p = p;
	}

	public int numeroCompraAcciones(){
		if(p.getNumeroMinimoAccionesCompra() > p.getNumeroMaximoAccionesCompra())
			return 0;
		int recomendado = p.getNumeroMinimoAccionesCompra()+
		(int)((p.getNumeroMaximoAccionesCompra() -
					p.getNumeroMinimoAccionesCompra()) * Math.random());
		return recomendado;
	}
	
	public int numeroVentaAcciones(){
		if(p.getNumeroMinimoAccionesVenta() > p.getNumeroMaximoAccionesVenta())
			return 0;
		int recomendado = p.getNumeroMinimoAccionesVenta()+
		(int)((p.getNumeroMaximoAccionesVenta() -
					p.getNumeroMinimoAccionesVenta()) * Math.random());
		return recomendado;
	}
	
	public double precioCompraAcciones(double precioActual){
		double procentajeRecomendado = (double)
			(p.getPorcentajeMaximoCompra() * Math.random());
		return (1 - procentajeRecomendado)*precioActual;
	}
	
	public double precioVentaAcciones(double precioActual){
		double porcentajeRecomendado = (double)
			(p.getPorcentajeMaximoVenta() * Math.random());
		return (1+porcentajeRecomendado)*precioActual;
	}

	public double porcentajeSubidaPrecio() {
		return p.getPorcentajeSubidaPrecio();
	}

	public double porcentajeBajadaPrecio() {
		return p.getPorcentajeBajadaPrecio();
	}

	public int numeroMaximoCancelaciones() {
		return (int) (p.getNumeroMaximoCancelaciones()*Math.random());
	}

	public int getTiempoEspera() {
		return p.getTiempoEspera();
	}
}
