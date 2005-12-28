package servidor.objeto_bolsa.informacion;

import java.util.Vector;
/*
Balance Sheet
Assets
	Current assets 
		Cash 
		*Marketable securities 
		*Accounts receivable - Dinero que le deben
		Net inventory - Stock de bienes y materiales
		*Other current assets 
			*Total current assets 
	Fixed assets (or property, plant, and equipment - PP&E) 
			Property 
			Plant & equipment 
		Gross PP&E 
			(Accumulated depreciation) 
		Net PP&E 

*/

public class Balance {
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
