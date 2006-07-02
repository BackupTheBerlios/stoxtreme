package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;
import org.jgap.BaseGene;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;

public class GenPrueba extends BaseGene{
	private Codificacion cod;
	
	public GenPrueba()throws Exception{
		super(AGenetico.configuration);
		this.cod = new Codificacion();
	}
	public GenPrueba(Codificacion cod) throws Exception{
		super(AGenetico.configuration);
		this.cod = cod;
	}
	
	protected Object getInternalValue() {
		return cod;
	}

	protected Gene newGeneInternal() {
		Codificacion cod = new Codificacion();
		cod.rellenaAleatorio();
		GenPrueba gen;
		try {
			gen= new GenPrueba(cod);
		} catch (Exception e) {
			gen = null;
			e.printStackTrace();
		}
		return gen;
	}

	public void applyMutation(int index, double cantidad) {
		cod.cambiaCaracter(index);
	}

	public String getPersistentRepresentation() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public void setAllele(Object cod) {
		this.cod = (Codificacion)cod;
	}

	public void setToRandomValue(RandomGenerator random) {
		cod.rellenaAleatorio();
	}

	public void setValueFromPersistentRepresentation(String arg0) throws UnsupportedOperationException, UnsupportedRepresentationException {
		throw new UnsupportedOperationException();
	}

	public int compareTo(Object otra) {
		if(otra.equals(cod)){
			return 0;
		}
		else return 1;
	}
	
	private static class FuncionAptitud extends FitnessFunction{
		protected double evaluate(IChromosome crom) {
			GenPrueba gen = (GenPrueba)crom.getGene(0);
			Codificacion cod = gen.getCodificacion();
			int cont = 0;
			
			for(int i=0; i<cod.size(); i++){
				char c = cod.getCharAt(i);
				if(c == '#') cont++;
			}
			return cont;
		}
	}
	
	public Codificacion getCodificacion() {
		return cod;
	}
	
	public String toString(){
		return cod.toString();
	}
}
