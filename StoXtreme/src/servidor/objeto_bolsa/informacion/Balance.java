package servidor.objeto_bolsa.informacion;

import java.util.Vector;

public class Balance {
	/**
	 * @uml.property  name="numeroEmpleados"
	 */
	private int numeroEmpleados;
	/**
	 * @uml.property  name="efectivo"
	 */
	private double efectivo;
	/**
	 * @uml.property  name="bienes"
	 */
	private double bienes; // Precio de los bienes en euros
	/**
	 * @param numeroEmpleados  The numeroEmpleados to set.
	 * @uml.property  name="numeroEmpleados"
	 */
	public void setNumeroEmpleados(int numeroEmpleados) {
		this.numeroEmpleados = numeroEmpleados;
	}
	/**
	 * @return  Returns the numeroEmpleados.
	 * @uml.property  name="numeroEmpleados"
	 */
	public int getNumeroEmpleados() {
		return numeroEmpleados;
	}
	/**
	 * @param efectivo  The efectivo to set.
	 * @uml.property  name="efectivo"
	 */
	public void setEfectivo(double efectivo) {
		this.efectivo = efectivo;
	}
	/**
	 * @return  Returns the efectivo.
	 * @uml.property  name="efectivo"
	 */
	public double getEfectivo() {
		return efectivo;
	}
	/**
	 * @param bienes  The bienes to set.
	 * @uml.property  name="bienes"
	 */
	public void setBienes(double bienes) {
		this.bienes = bienes;
	}
	/**
	 * @return  Returns the bienes.
	 * @uml.property  name="bienes"
	 */
	public double getBienes() {
		return bienes;
	}
	
}
