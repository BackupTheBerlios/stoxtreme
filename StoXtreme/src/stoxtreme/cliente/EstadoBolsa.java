package stoxtreme.cliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import cern.jet.random.engine.MersenneTwister;

import stoxtreme.cliente.gui.ModeloEmpresas;
import stoxtreme.cliente.gui.ModeloPrecioAccionesGrafico;
import stoxtreme.cliente.infoLocal.InfoLocal;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class EstadoBolsa {
	private Hashtable<String, Double> preciosIniciales;
	private Hashtable<String, Double> preciosActuales;
	private ModeloPrecioAccionesGrafico mAcciones;
	private ArrayList<String> empresas;
	private ModeloEmpresas mEmpresas;


	/**
	 *  contructor para EstadoBolsa 
	 *
	 *@param  info  parametro que obtiene información de local
	 */
	public EstadoBolsa(InfoLocal info) {
		this.empresas = info.getEmpresas();
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(System.currentTimeMillis());
		mAcciones = new ModeloPrecioAccionesGrafico(info.getEmpresas(), c1.getTime());
		mEmpresas = new ModeloEmpresas(info);
		preciosIniciales = new Hashtable<String, Double>();
		preciosActuales = new Hashtable<String, Double>();

		for (int i = 0; i < empresas.size(); i++) {
			preciosIniciales.put(empresas.get(i), info.getPrecioInicial(empresas.get(i)));
			preciosActuales.put(empresas.get(i), info.getPrecioInicial(empresas.get(i)));
		}
	}


	/**
	 *  coloca a MAcciones un valor 
	 *
	 *@param  acciones  nuevo valor de MAcciones
	 */
	public void setMAcciones(ModeloPrecioAccionesGrafico acciones) {
		mAcciones = acciones;
	}


	/**
	 *  coloca a MEmpresas un valor
	 *
	 *@param  empresas  nuevo valor de MEmpresas
	 */
	public void setMEmpresas(ModeloEmpresas empresas) {
		mEmpresas = empresas;
	}


	/**
	 *  Nos da el modelo de acciones disponible
	 *
	 *@return    valor de MAcciones
	 */
	public ModeloPrecioAccionesGrafico getMAcciones() {
		return mAcciones;
	}


	/**
	 * Nos devuelve el modelo de empresas
	 *
	 *@return    valor de MEmpresas
	 */
	public ModeloEmpresas getMEmpresas() {
		return mEmpresas;
	}


	/**
	 *  obtenemos el precio actual de una determinada empresa
	 *
	 *@param  empresa  empresa a consultar
	 *@return          devolvemos precio actual
	 */
	public double getPrecioActualEmpresa(String empresa) {
		return preciosActuales.get(empresa);
	}


	/**
	 *  Nos devuelve la lista de todas las empresas
	 *
	 *@return    valor de Empresas
	 */
	public ArrayList<String> getEmpresas() {
		return this.empresas;
	}


	/**
	 *  Calcula el porcentaje que hay de subida o bajada en una accion
	 *
	 *@param  empresa  empresa seleccionada
	 *@return          valor del porcentaje
	 */
	public double getPorcentajeDiferencia(String empresa) {
		double inicial = preciosIniciales.get(empresa);
		double actual = preciosActuales.get(empresa);

		double diferencia = (actual - inicial) / actual;
		diferencia = redondeo(diferencia, 3);
		return diferencia;
	}


	/**
	 *  Nos devuelve el precio de apertura de la empresa seleccionada
	 *
	 *@param  empresa  empresa seleccionada
	 *@return          Precio Apertura de la Empresa
	 */
	public double getPrecioAperturaEmpresa(String empresa) {
		return preciosIniciales.get(empresa);
	}


	/**
	 *  realiza un cambio del precio actual en una empresa
	 *
	 *@param  empresa      empresa seleccionada
	 *@param  nuevoPrecio  nuevo precio
	 */
	public void cambiaValor(String empresa, double nuevoPrecio) {
		mAcciones.insertaValor(empresa, nuevoPrecio);
		preciosActuales.put(empresa, nuevoPrecio);
	}


	/**
	 *  Selecciona una empresa al azar
	 *
	 *@return    Devuelve el nombre d ela empresa
	 */
	public String dameEmpresaAleatoria() {
		int i = (int) (MersenneTwister.makeDefault().nextDouble() * empresas.size());
		return empresas.get(i);
	}


	/**
	 *  Redondea un número para que tenga un determinado número de decimales
	 *
	 *@param  numero      Número a redondear
	 *@param  nDecimales  Número de decimales
	 *@return             Devuelve el número redondeado
	 */
	public static double redondeo(double numero, int nDecimales) {
		return Math.floor((Math.pow(10, nDecimales) * numero) + 0.5) / Math.pow(10, nDecimales);
	}
}
