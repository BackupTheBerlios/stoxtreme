package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;
import org.jgap.Chromosome;
import org.jgap.InvalidConfigurationException;

public class Regla{
	// Una regla es una codificación termanaria:
	// 001101##1
	// Unos parametros para esa codificacion
	// y una accion.
	private Codificacion codificacion;
	private double precio;
	private int tipoOp;
	
	// si encaja(Cadena, Parametros, Mundo) -> Accion
	// tanto la cadena como los parametros evolucionan
	
	
	public Regla(Chromosome cromosoma){
		
	}
	
	public Regla(){
		codificacion = new Codificacion();
		codificacion.rellenaAleatorio();
	}
	
	public boolean encaja(Mundo mundo){
		return false;
	}
	
	public Chromosome getChromosome(){
		return null;
	}

	public double getBonificacion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double similitud(Regla regla) {
		// TODO Auto-generated method stub
		return 0;
	}
}
