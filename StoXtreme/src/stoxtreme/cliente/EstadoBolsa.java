package stoxtreme.cliente;

import java.util.ArrayList;
import java.util.Hashtable;

import cern.jet.random.engine.MersenneTwister;

import stoxtreme.cliente.gui.ModeloEmpresas;
import stoxtreme.cliente.gui.ModeloPrecioAccionesGrafico;
import stoxtreme.cliente.infoLocal.InfoLocal;

public class EstadoBolsa {
	private Hashtable<String,Double> precios;
	private ModeloPrecioAccionesGrafico mAcciones;
	private ArrayList<String> empresas;
	private ModeloEmpresas mEmpresas;
	
	public EstadoBolsa(InfoLocal info){
		this.empresas = info.getEmpresas(); 
		mAcciones = new ModeloPrecioAccionesGrafico(info.getEmpresas());
		mEmpresas = new ModeloEmpresas(info);
		precios = new Hashtable<String,Double>();
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
	}

	public double getPrecioActualEmpresa(String empresa) {
		return precios.get(empresa);
	}

	public String dameEmpresaAleatoria() {
		int i = MersenneTwister.makeDefault().nextInt();
		return empresas.get(i);
	}
}
