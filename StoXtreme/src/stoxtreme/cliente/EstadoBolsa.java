package stoxtreme.cliente;

import java.util.ArrayList;

import stoxtreme.cliente.gui.ModeloEmpresas;
import stoxtreme.cliente.gui.ModeloPrecioAccionesGrafico;

public class EstadoBolsa {
	private ModeloPrecioAccionesGrafico mAcciones;
	private ModeloEmpresas mEmpresas;
	
	public EstadoBolsa(){
		ArrayList<String> empresas = new ArrayList<String>();
		empresas.add("ENDESA");
		empresas.add("REPSOL");
		empresas.add("ANTENA3");
		empresas.add("TELECINCO");
		mAcciones = new ModeloPrecioAccionesGrafico(empresas);
		mEmpresas = new ModeloEmpresas(empresas);
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
	
}
