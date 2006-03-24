package stoxtreme.herramienta_agentes.agentes;

import java.util.ArrayList;
import java.util.Iterator;

import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;

public class GeneradorParametrosPsicologicos {
	private ArrayList<ParametrosPsicologicos> lista;
	
	public GeneradorParametrosPsicologicos(int numAgentes) {
		lista = new ArrayList<ParametrosPsicologicos>(numAgentes);
		for(int i=0; i<numAgentes; i++){
			lista.add(i, new ParametrosPsicologicos());
		}
	}

	public ParametrosPsicologicos get(int i) {
		return lista.get(i);
	}
	
	public void generarTiempoEspera(double media, double desviacionTipica){
		MersenneTwister generadorNumAleatorios = new MersenneTwister();
		Normal distribucion = 
			new Normal(media, desviacionTipica, generadorNumAleatorios);
		Iterator<ParametrosPsicologicos> it = lista.iterator();
		while(it.hasNext())
			it.next().setTiempoEspera((int)Math.floor(distribucion.nextDouble()));
	}

	public void generaNumeroAcciones(int minimoCompra, int maximoCompra, int minimoVenta, int maximoVenta, int maximoCancelaciones){
		Iterator<ParametrosPsicologicos> it = lista.iterator();
		while(it.hasNext()){
			ParametrosPsicologicos actual = it.next();
			actual.setNumeroMaximoAccionesCompra(maximoCompra);
			actual.setNumeroMinimoAccionesCompra(minimoCompra);
			actual.setNumeroMaximoAccionesVenta(minimoVenta);
			actual.setNumeroMinimoAccionesVenta(minimoVenta);
			actual.setNumeroMaximoCancelaciones(maximoCancelaciones);
		}
	}
	
	public void generaPorcentajes(
			double subida, double bajada, 
			double minCompra, double maxCompra, 
			double minVenta, double maxVenta)
	{
		Iterator<ParametrosPsicologicos> it = lista.iterator();
		while(it.hasNext()){
			ParametrosPsicologicos actual = it.next();
			actual.setPorcentajeSubidaPrecio(subida);
			actual.setPorcentajeBajadaPrecio(bajada);
			actual.setPorcentajeMaximoCompra(maxCompra);
			actual.setPorcentajeMinimoCompra(minCompra);
			actual.setPorcentajeMaximoVenta(maxVenta);
			actual.setPorcentajeMinimoVenta(minVenta);
		}
	}
}
