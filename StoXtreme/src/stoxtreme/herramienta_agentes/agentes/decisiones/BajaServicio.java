package stoxtreme.herramienta_agentes.agentes.decisiones;

public class BajaServicio extends Decision{
	private String servicio;

	public BajaServicio(String servicio) {
		this.servicio = servicio;
	}
	
	public void ejecuta() {
		agente.bajaServicio(servicio);
	}
}
