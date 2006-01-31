package stoxtreme.servidor.objeto_bolsa.informacion;

import java.util.Vector;


public class Balance{
	private int numeroEmpleados;
	private double efectivo;
	private double bienes; // Precio de los bienes en euros

	public void setNumeroEmpleados(int numeroEmpleados) {
		this.numeroEmpleados = numeroEmpleados;
	}
	
	public int getNumeroEmpleados() {
		return numeroEmpleados;
	}
	
	public void setEfectivo(double efectivo) {
		this.efectivo = efectivo;
	}
	
	public double getEfectivo() {
		return efectivo;
	}
	
	public void setBienes(double bienes) {
		this.bienes = bienes;
	}
	
	public double getBienes() {
		return bienes;
	}
	
}
