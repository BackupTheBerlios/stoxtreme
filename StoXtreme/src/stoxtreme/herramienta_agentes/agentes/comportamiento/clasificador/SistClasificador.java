package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;
import java.util.ArrayList;
import java.util.TreeSet;

public class SistClasificador{
	private ArrayList<Regla> reglas;
	private TreeSet<Integer> ejecutadas;
	private int numReglas;
	private String empresa;
		
	public SistClasificador(String empresa, int numReglas) {
		this.numReglas = numReglas;
		this.empresa = empresa;
		reglas = new ArrayList<Regla>();
		ejecutadas = new TreeSet<Integer>();
	}
	
	public void iniciaAleatorio(){
		for(int i=0; i<numReglas; i++){
			Regla regla = new Regla();
			reglas.add(regla);
		}
	}

	public Regla encajaReglas(int ciclo, Mundo mundoClasificador) {
		// Primero consigo las reglas que encajen en el mundo
		ArrayList<Regla> encaje = new ArrayList<Regla>();
		for(int i=0; i<reglas.size(); i++){
			if(reglas.get(i).encaja(empresa, ciclo, mundoClasificador)){
				encaje.add(reglas.get(i));
			}
		}
		// Con las reglas que han encajado, devolvemos la que mejor tiene
		// y la quitamos prioridad
		
		return null;
	}
	
	public int getNumReglas(){
		return reglas.size();
	}
	
	public Regla getRegla(int i){
		return reglas.get(i);
	}

	public void addRegla(Regla r) {
		reglas.add(r);
	}

	public double aptitudSimilitud(Regla regla) {
		double acumulado = 0.0;
		for(int i=0; i<reglas.size(); i++){
			double bonificacion = reglas.get(i).getBeneficio();
			double similitud = reglas.get(i).similitud(regla);
			acumulado += bonificacion*similitud;
		}
		return acumulado;
	}
	
	public double damePrecision(Regla regla){
		return 0.0;
	}

	public boolean todasLasReglasEjecutadas() {
		return ejecutadas.size() == numReglas;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void asignaPesosCompra(Regla regla, double d) {
		// TODO Auto-generated method stub
		
	}

	public void asignaPesosVenta(Regla regla, double d) {
		// TODO Auto-generated method stub
		
	}
}
