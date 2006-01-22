package stoxtreme.servidor.objeto_bolsa.informacion;

import java.util.Date;
import java.util.Vector;

public class Cuenta {
	/**
	 * @uml.property  name="ganancia"
	 */
	private double ganancia;
	/**
	 * @uml.property  name="perdidas"
	 */
	private double perdidas;
	/**
	 * @uml.property  name="fecha"
	 */
	private Date fecha;
	
	public Cuenta(Date fecha, double ganancia, double perdida){
		this.fecha = fecha;
		this.ganancia = ganancia;
		this.perdidas = perdida;
	}

	/**
	 * @param ganancia  The ganancia to set.
	 * @uml.property  name="ganancia"
	 */
	public void setGanancia(double ganancia) {
		this.ganancia = ganancia;
	}

	/**
	 * @return  Returns the ganancia.
	 * @uml.property  name="ganancia"
	 */
	public double getGanancia() {
		return ganancia;
	}

	/**
	 * @param perdidas  The perdidas to set.
	 * @uml.property  name="perdidas"
	 */
	public void setPerdidas(double perdidas) {
		this.perdidas = perdidas;
	}

	/**
	 * @return  Returns the perdidas.
	 * @uml.property  name="perdidas"
	 */
	public double getPerdidas() {
		return perdidas;
	}

	/**
	 * @param fecha  The fecha to set.
	 * @uml.property  name="fecha"
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return  Returns the fecha.
	 * @uml.property  name="fecha"
	 */
	public Date getFecha() {
		return fecha;
	}
}
