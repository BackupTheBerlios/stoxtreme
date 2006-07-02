package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.impl.DefaultConfiguration;

@SuppressWarnings("serial")
public class AGenetico {
	private static SistClasificador _antiguo;
	public static Configuration configuration = null;
	
	public static void creaConfiguracion(){
		if(configuration != null) return;
		try {
			configuration = new DefaultConfiguration();
			Chromosome sampleChromosome = new Chromosome(configuration, new Gene[]{new GenPrueba()});
			configuration.setSampleChromosome(sampleChromosome);
			configuration.setPopulationSize(100);
			configuration.setFitnessFunction(new FuncionAptitud());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SistClasificador generaNuevasReglas(SistClasificador antiguo){
		SistClasificador nuevo = null;
		_antiguo = antiguo;
		try{
			// Creo la poblacion inicial con los cromosomas de las reglas
			IChromosome[] poblacion = new IChromosome[antiguo.getNumReglas()];
			for(int i=0; i<antiguo.getNumReglas(); i++){
				poblacion[i] = antiguo.getRegla(i).getChromosome();
			}
			Genotype genotipo = new Genotype(configuration, new Population(configuration, poblacion));
			
			// Ahora la hago evolucionar 50 generaciones
			genotipo.evolve(50);
			
			// Ahora cojo las 10 mejores reglas y sustituyen a las antiguas
			List lista = genotipo.getFittestChromosomes(10);
			nuevo = new SistClasificador(10);
			for(int i=0; i<lista.size(); i++){
				Chromosome crom = (Chromosome)lista.get(i);
				Regla r = new Regla(crom);
				nuevo.addRegla(r);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		// Devuelvo el valor del nuevo sistema clasificador
		return nuevo;
	}
	
	private static class FuncionAptitud extends FitnessFunction{
		protected double evaluate(IChromosome crom) {
			// Generamos la regla a partir del cromosoma
			Regla regla = new Regla((Chromosome)crom);
			// Calculamos la aptitud en funcion de la similitud
			// con las reglas y sus pesos
			double aptitud = _antiguo.aptitudSimilitud(regla);
			return aptitud;
		}
	}
}
