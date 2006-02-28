package stoxtreme.servidor.superusuario.GUI;

public class Evento {
	private String condicion;
	private String accion;
	
	public Evento(String condicion, String accion){
		this.setCondicion(condicion);
		this.setAccion(accion);
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getAccion() {
		return accion;
	}
}
