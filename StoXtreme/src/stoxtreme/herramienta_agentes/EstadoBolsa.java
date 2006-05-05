package stoxtreme.herramienta_agentes;
import java.util.ArrayList;

import java.util.Hashtable;


public class EstadoBolsa {
	private ArrayList<String> nombreEmpresas;
	private Hashtable<String, Double> precios;
	private String empresaFocus;
	
	public EstadoBolsa(){
		nombreEmpresas = new ArrayList<String>();
		precios = new Hashtable<String, Double>();
	}
	
	public void cambioPrecioAccion(String empresa, double nuevoPrecio) {
		precios.put(empresa, nuevoPrecio);
	}

	public String dameEmpresaAleatoria() {
		int indiceAleatorio = (int)(Math.random()*nombreEmpresas.size());
		return nombreEmpresas.get(indiceAleatorio);
	}

	public double getPrecioActualEmpresa(String empresa) {
		return precios.get(empresa);
	}
	
	public void setEmpresaFocus(String empresa){
		empresaFocus = empresa;
	}
	
	public Object execute(){
		return new Double(getSValue());
	}
	
	public double getSValue() {
		//System.out.println(getPrecioActualEmpresa(empresaFocus));
		return getPrecioActualEmpresa(empresaFocus);
	}
	
	public void insertaEmpresa(String nombre, double precioInicial){
		nombreEmpresas.add(nombre);
		precios.put(nombre, precioInicial);
	}
	
	/////////////////////////////////////
	private static EstadoBolsa instanciaGlobal = null;
	public static EstadoBolsa getInstanciaGlobal(){
		if(instanciaGlobal == null){
			instanciaGlobal = new EstadoBolsa();
		}
		return instanciaGlobal;
	}
	/////////////////////////////////////
}
