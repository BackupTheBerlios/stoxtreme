package servidor.objeto_bolsa.operaciones;

public class InfoOperacion {
	private String IDAgente;
	private int IDOperacion;
	private int numAcciones;
	
	public void setIDAgente(String iDAgente) {
		IDAgente = iDAgente;
	}
	
	public String getIDAgente() {
		return IDAgente;
	}
	
	public void setIDOperacion(int iDOperacion) {
		IDOperacion = iDOperacion;
	}
	
	public int getIDOperacion() {
		return IDOperacion;
	}
	
	public void setNumAcciones(int numAcciones) {
		this.numAcciones = numAcciones;
	}
	
	public int getNumAcciones() {
		return numAcciones;
	}
}
