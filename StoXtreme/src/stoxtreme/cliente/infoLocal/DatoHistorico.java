package stoxtreme.cliente.infoLocal;

import java.util.Date;

public class DatoHistorico {
	private String empresa; 
	private String fecha;
	private double precio_cierre;
	private int volumen;
	private double efectivo;
	private double precio_inicio;
	private double precio_maximo;
	private double precio_minimo;
	private double precio_medio;
	
	
	public DatoHistorico(){
	}
	
	public DatoHistorico getDato(String empresa, String fecha){
		ParserInfoLocal parser=new ParserInfoLocal();
		DatoHistorico dh=new DatoHistorico();
		dh=parser.creaDatoHistorico(empresa, fecha);
		return dh;
	}
	
	//setters
	public void setEmpresa(String e){
		this.empresa=e;
	}	
	
	public void setFecha(String d){
		this.fecha=d;
	}
	
	public void setPrecioCierre(double pc){
		this.precio_cierre=pc;
	}
	
	public void setPrecioInicio(double pi){
		this.precio_inicio=pi;
	}
	
	public void setPrecioMaximo(double pmax){
		this.precio_maximo=pmax;
	}
	
	public void setPrecioMinimo(double pmin){
		this.precio_minimo=pmin;
	}
	
	public void setPrecioMedio(double pmed){
		this.precio_medio=pmed;
	}
	
	public void setEfectivo(double efe){
		this.efectivo=efe;
	}
	
	public void setVolumen(int v){
		this.volumen=v;
	}
	//getters
	public String getEmpresa(){
		return this.empresa;
	}	
	
	public String getFecha(){
		return this.fecha;
	}
	
	public double getPrecioCierre(){
		return this.precio_cierre;
	}
	
	public double getPrecioInicio(){
		return this.precio_inicio;
	}
	
	public double getPrecioMaximo(){
		return this.precio_maximo;
	}
	
	public double getPrecioMinimo(){
		return this.precio_minimo;
	}
	
	public double getPrecioMedio(){
		return this.precio_medio;
	}
	
	public double getEfectivo(){
		return this.efectivo;
	}
	
	public int getVolumen(){
		return this.volumen;
	}

}
