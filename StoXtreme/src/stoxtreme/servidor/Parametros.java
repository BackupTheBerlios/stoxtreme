package stoxtreme.servidor;

public class Parametros {
	private long tiempo;
	private String ficheroEmpresas;
	
	public Parametros(){
		
	}

	public void setTiempo(long tiempo){
		this.tiempo = tiempo;
	}
	
	public long getTiempo(){
		return tiempo;
	}

	public void setFicheroEmpresas(String s){
		ficheroEmpresas = s;
	}

	public String getFicheroEmpresas() {
		return ficheroEmpresas;
	}
}
