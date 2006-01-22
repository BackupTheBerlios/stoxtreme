package stoxtreme.servidor;

public class Parametros {
	/**
	 * @uml.property  name="tiempo"
	 */
	private long tiempo;
	/**
	 * @uml.property  name="ficheroEmpresas"
	 */
	private String ficheroEmpresas;
	
	public Parametros(){
		
	}
	/**
	 * @param tiempo  The tiempo to set.
	 * @uml.property  name="tiempo"
	 */
	public void setTiempo(long tiempo){
		this.tiempo = tiempo;
	}
	
	/**
	 * @return  Returns the tiempo.
	 * @uml.property  name="tiempo"
	 */
	public long getTiempo(){
		return tiempo;
	}

	/**
	 * @param ficheroEmpresas  The ficheroEmpresas to set.
	 * @uml.property  name="ficheroEmpresas"
	 */
	public void setFicheroEmpresas(String s){
		ficheroEmpresas = s;
	}
	/**
	 * @return  Returns the ficheroEmpresas.
	 * @uml.property  name="ficheroEmpresas"
	 */
	public String getFicheroEmpresas() {
		return ficheroEmpresas;
	}
	
}
