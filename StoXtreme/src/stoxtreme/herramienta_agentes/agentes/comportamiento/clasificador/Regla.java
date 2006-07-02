package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;
import org.jgap.Chromosome;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;

public class Regla{
	// Una regla es una codificación ternanaria:
	// 001101##1
	// Unos parametros para esa codificacion
	// y una accion.
	private Codificacion codificacion;
	private double beneficio;
	
	public static int COMPRA = 1;
	public static int VENTA = 2;
	
	// si encaja(Cadena, Parametros, Mundo) -> Accion
	// tanto la cadena como los parametros evolucionan
	
	
	public Regla(Chromosome cromosoma){
		GenPrueba gen = (GenPrueba)cromosoma.getGene(0);
		this.codificacion = gen.getCodificacion();
		init();
	}
	
	public Regla(Codificacion codificacion){
		super();
		this.codificacion = codificacion;
		init();
	}
	
	public Regla(){
		codificacion = new Codificacion();
		codificacion.rellenaAleatorio();
		init();
	}
	
	public void init(){
		beneficio = 0;
	}
	
	public boolean encaja(int time, Mundo mundo){
		boolean encaja = true;
		
		double p10Ciclos = mundo.getPrecio(time -10);
		double p30Ciclos = mundo.getPrecio(time - 30);
		double pInicio = mundo.getPrecio(0);
		double pActual = mundo.getPrecio(time);
		
		if(codificacion.getCharAt(0) != '#'){
			double diferencia = p10Ciclos - pActual;
			if(codificacion.getCharAt(0)=='1'){
				encaja &= diferencia/pActual > 0.25;
			}
			else{
				encaja &= !(diferencia/pActual > 0.25);
			}
		}
		
		if(codificacion.getCharAt(1) != '#'){
			double diferencia = p10Ciclos - pActual;
			if(codificacion.getCharAt(1)=='1'){
				encaja &= diferencia/pActual > 0.5;
			}
			else{
				encaja &= !(diferencia/pActual > 0.5);
			}		
		}
		
		if(codificacion.getCharAt(2) != '#'){
			double diferencia = p10Ciclos - pActual;
			if(codificacion.getCharAt(0)=='1'){
				encaja &= diferencia/pActual < -0.25;
			}
			else{
				encaja &= !(diferencia/pActual < -0.25);
			}
		}
		
		if(codificacion.getCharAt(3) != '#'){
			double diferencia = p10Ciclos - pActual;
			if(codificacion.getCharAt(0)=='1'){
				encaja &= diferencia/pActual < -0.5;
			}
			else{
				encaja &= !(diferencia/pActual < -0.5);
			}	
		}
		
		if(codificacion.getCharAt(4) != '#'){
			double diferencia = p30Ciclos - pActual;
			if(codificacion.getCharAt(0)=='1'){
				encaja &= diferencia/pActual > 0.25;
			}
			else{
				encaja &= !(diferencia/pActual > 0.25);
			}
		}
		
		if(codificacion.getCharAt(5) != '#'){
			double diferencia = p30Ciclos - pActual;
			if(codificacion.getCharAt(1)=='1'){
				encaja &= diferencia/pActual > 0.5;
			}
			else{
				encaja &= !(diferencia/pActual > 0.5);
			}		
		}
		
		if(codificacion.getCharAt(6) != '#'){
			double diferencia = p30Ciclos - pActual;
			if(codificacion.getCharAt(0)=='1'){
				encaja &= diferencia/pActual < -0.25;
			}
			else{
				encaja &= !(diferencia/pActual < -0.25);
			}
		}
		
		if(codificacion.getCharAt(7) != '#'){
			double diferencia = p30Ciclos - pActual;
			if(codificacion.getCharAt(0)=='1'){
				encaja &= diferencia/pActual < -0.5;
			}
			else{
				encaja &= !(diferencia/pActual < -0.5);
			}	
		}
		
		if(codificacion.getCharAt(8) != '#'){
			double diferencia = pInicio - pActual;
			if(codificacion.getCharAt(0)=='1'){
				encaja &= diferencia/pActual > 0.5;
			}
			else{
				encaja &= !(diferencia/pActual > 0.5);
			}
		}
		
		if(codificacion.getCharAt(9) != '#'){
			double diferencia = pInicio - pActual;
			if(codificacion.getCharAt(0)=='1'){
				encaja &= diferencia/pActual < -0.5;
			}
			else{
				encaja &= !(diferencia/pActual < -0.5);
			}
		}
		
		return encaja;
	}
	
	public Chromosome getChromosome(){
		Chromosome c = null;
		try {
			c = new Chromosome(AGenetico.configuration);
			c.setGenes(new Gene[]{
					new GenPrueba(this.codificacion)
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public double getBeneficio() {
		return beneficio;
	}

	public double similitud(Regla regla) {
		return codificacion.similitud(regla.getCodificacion());
	}
	
	private Codificacion getCodificacion() {
		return codificacion;
	}

	public int getAccion(){
		char c = codificacion.getCharAt(codificacion.size()-1);
		if(c == '1'){
			return COMPRA;
		}
		else 
		if(c == '0'){
			return VENTA;
		}
		else{
			if(Codificacion.random.nextDouble() < 0.5){
				return COMPRA;
			}
			else{
				return VENTA;
			}
		}
	}
	
	public double getPrecision(){
		return codificacion.getNumComodines()/codificacion.size();
	}

	public void addBeneficio(double d) {
		beneficio += d;
	}
}
