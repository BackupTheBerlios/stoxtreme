package stoxtreme.cliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import cern.jet.random.engine.MersenneTwister;

import stoxtreme.cliente.gui.ModeloEmpresas;
import stoxtreme.cliente.gui.ModeloPrecioAccionesGrafico;
import stoxtreme.cliente.infoLocal.InfoLocal;

public class EstadoBolsa {
	private Hashtable<String,Double> preciosIniciales;
	private Hashtable<String,Double> preciosActuales;
	private ModeloPrecioAccionesGrafico mAcciones;
	private ArrayList<String> empresas;
	private ModeloEmpresas mEmpresas;
	
	public EstadoBolsa(InfoLocal info){
		this.empresas = info.getEmpresas(); 
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(System.currentTimeMillis());
		mAcciones = new ModeloPrecioAccionesGrafico(info.getEmpresas(),c1.getTime());
		mEmpresas = new ModeloEmpresas(info);
		preciosIniciales = new Hashtable<String,Double>();
		preciosActuales = new Hashtable<String,Double>();
		
		for(int i=0; i<empresas.size(); i++){
			preciosIniciales.put(empresas.get(i), info.getPrecioInicial(empresas.get(i)));
			preciosActuales.put(empresas.get(i), info.getPrecioInicial(empresas.get(i)));
		}
	}

	public ModeloPrecioAccionesGrafico getMAcciones() {
		return mAcciones;
	}

	public void setMAcciones(ModeloPrecioAccionesGrafico acciones) {
		mAcciones = acciones;
	}

	public ModeloEmpresas getMEmpresas() {
		return mEmpresas;
	}

	public void setMEmpresas(ModeloEmpresas empresas) {
		mEmpresas = empresas;
	}

	public void cambiaValor(String empresa, double nuevoPrecio) {
		mAcciones.insertaValor(empresa, nuevoPrecio);
		preciosActuales.put(empresa, nuevoPrecio);
	}

	public double getPrecioActualEmpresa(String empresa) {
		return preciosActuales.get(empresa);
	}

	public String dameEmpresaAleatoria() {
		int i = (int)(MersenneTwister.makeDefault().nextDouble()*empresas.size());
		return empresas.get(i);
	}

	public ArrayList<String> getEmpresas() {
		return this.empresas;
	}
	
	public double getPorcentajeDiferencia(String empresa){
		double inicial = preciosIniciales.get(empresa);
		double actual = preciosActuales.get(empresa);
		
		double diferencia = (actual - inicial)/actual;
		diferencia = redondeo(diferencia, 3); 
		return diferencia;
	}
	public static double redondeo(double numero, int nDecimales){
		return Math.floor((Math.pow(10, nDecimales)*numero)+0.5)/Math.pow(10, nDecimales);
	}
	public double getPrecioAperturaEmpresa(String empresa) {
		return preciosIniciales.get(empresa);
	}
}
