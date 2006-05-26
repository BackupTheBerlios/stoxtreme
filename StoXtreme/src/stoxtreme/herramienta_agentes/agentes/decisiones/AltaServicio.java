package stoxtreme.herramienta_agentes.agentes.decisiones;

public class AltaServicio extends Decision{
	private String servicio;
	
	public AltaServicio(String servicio) {
		this.servicio = servicio;
	}
	
	public void ejecuta() {
		agente.altaServicio(servicio);
	}
}
