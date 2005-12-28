package servidor.objeto_bolsa.informacion;

import java.util.Date;
import java.util.Vector;

public class Cuenta {
	private double ganancia;
	private double perdidas;
	private Date fecha;
	
	public Cuenta(Date fecha, double ganancia, double perdida){
		this.fecha = fecha;
		this.ganancia = ganancia;
		this.perdidas = perdida;
	}

	public void setGanancia(double ganancia) {
		this.ganancia = ganancia;
	}

	public double getGanancia() {
		return ganancia;
	}

	public void setPerdidas(double perdidas) {
		this.perdidas = perdidas;
	}

	public double getPerdidas() {
		return perdidas;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}
}
