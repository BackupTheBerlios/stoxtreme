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
 *@author    Chris Seguin
 */
public class EstadoBolsa {
	private Hashtable<String, Double> preciosIniciales;
	private Hashtable<String, Double> preciosActuales;
	private ModeloPrecioAccionesGrafico mAcciones;
	private ArrayList<String> empresas;
	private ModeloEmpresas mEmpresas;


	/**
	 *  Constructor for the EstadoBolsa object
	 *
	 *@param  info  Description of Parameter
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
	 *  Sets the MAcciones attribute of the EstadoBolsa object
	 *
	 *@param  acciones  The new MAcciones value
	 */
	public void setMAcciones(ModeloPrecioAccionesGrafico acciones) {
		mAcciones = acciones;
	}


	/**
	 *  Sets the MEmpresas attribute of the EstadoBolsa object
	 *
	 *@param  empresas  The new MEmpresas value
	 */
	public void setMEmpresas(ModeloEmpresas empresas) {
		mEmpresas = empresas;
	}


	/**
	 *  Gets the MAcciones attribute of the EstadoBolsa object
	 *
	 *@return    The MAcciones value
	 */
	public ModeloPrecioAccionesGrafico getMAcciones() {
		return mAcciones;
	}


	/**
	 *  Gets the MEmpresas attribute of the EstadoBolsa object
	 *
	 *@return    The MEmpresas value
	 */
	public ModeloEmpresas getMEmpresas() {
		return mEmpresas;
	}


	/**
	 *  Gets the PrecioActualEmpresa attribute of the EstadoBolsa object
	 *
	 *@param  empresa  Description of Parameter
	 *@return          The PrecioActualEmpresa value
	 */
	public double getPrecioActualEmpresa(String empresa) {
		return preciosActuales.get(empresa);
	}


	/**
	 *  Gets the Empresas attribute of the EstadoBolsa object
	 *
	 *@return    The Empresas value
	 */
	public ArrayList<String> getEmpresas() {
		return this.empresas;
	}


	/**
	 *  Gets the PorcentajeDiferencia attribute of the EstadoBolsa object
	 *
	 *@param  empresa  Description of Parameter
	 *@return          The PorcentajeDiferencia value
	 */
	public double getPorcentajeDiferencia(String empresa) {
		double inicial = preciosIniciales.get(empresa);
		double actual = preciosActuales.get(empresa);

		double diferencia = (actual - inicial) / actual;
		diferencia = redondeo(diferencia, 3);
		return diferencia;
	}


	/**
	 *  Gets the PrecioAperturaEmpresa attribute of the EstadoBolsa object
	 *
	 *@param  empresa  Description of Parameter
	 *@return          The PrecioAperturaEmpresa value
	 */
	public double getPrecioAperturaEmpresa(String empresa) {
		return preciosIniciales.get(empresa);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa      Description of Parameter
	 *@param  nuevoPrecio  Description of Parameter
	 */
	public void cambiaValor(String empresa, double nuevoPrecio) {
		mAcciones.insertaValor(empresa, nuevoPrecio);
		preciosActuales.put(empresa, nuevoPrecio);
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String dameEmpresaAleatoria() {
		int i = (int) (MersenneTwister.makeDefault().nextDouble() * empresas.size());
		return empresas.get(i);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  numero      Description of Parameter
	 *@param  nDecimales  Description of Parameter
	 *@return             Description of the Returned Value
	 */
	public static double redondeo(double numero, int nDecimales) {
		return Math.floor((Math.pow(10, nDecimales) * numero) + 0.5) / Math.pow(10, nDecimales);
	}
}
