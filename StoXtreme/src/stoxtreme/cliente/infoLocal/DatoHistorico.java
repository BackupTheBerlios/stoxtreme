package stoxtreme.cliente.infoLocal;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
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


	/**
	 *  Constructor for the DatoHistorico object
	 */
	public DatoHistorico() {

	}


	//setters
	/**
	 *  Sets the Empresa attribute of the DatoHistorico object
	 *
	 *@param  e  The new Empresa value
	 */
	public void setEmpresa(String e) {
		this.empresa = e;
	}


	/**
	 *  Sets the Fecha attribute of the DatoHistorico object
	 *
	 *@param  d  The new Fecha value
	 */
	public void setFecha(String d) {
		this.fecha = d;
	}


	/**
	 *  Sets the PrecioCierre attribute of the DatoHistorico object
	 *
	 *@param  pc  The new PrecioCierre value
	 */
	public void setPrecioCierre(double pc) {
		this.precio_cierre = pc;
	}


	/**
	 *  Sets the PrecioInicio attribute of the DatoHistorico object
	 *
	 *@param  pi  The new PrecioInicio value
	 */
	public void setPrecioInicio(double pi) {
		this.precio_inicio = pi;
	}


	/**
	 *  Sets the PrecioMaximo attribute of the DatoHistorico object
	 *
	 *@param  pmax  The new PrecioMaximo value
	 */
	public void setPrecioMaximo(double pmax) {
		this.precio_maximo = pmax;
	}


	/**
	 *  Sets the PrecioMinimo attribute of the DatoHistorico object
	 *
	 *@param  pmin  The new PrecioMinimo value
	 */
	public void setPrecioMinimo(double pmin) {
		this.precio_minimo = pmin;
	}


	/**
	 *  Sets the PrecioMedio attribute of the DatoHistorico object
	 *
	 *@param  pmed  The new PrecioMedio value
	 */
	public void setPrecioMedio(double pmed) {
		this.precio_medio = pmed;
	}


	/**
	 *  Sets the Efectivo attribute of the DatoHistorico object
	 *
	 *@param  efe  The new Efectivo value
	 */
	public void setEfectivo(double efe) {
		this.efectivo = efe;
	}


	/**
	 *  Sets the Volumen attribute of the DatoHistorico object
	 *
	 *@param  v  The new Volumen value
	 */
	public void setVolumen(int v) {
		this.volumen = v;
	}


	//getters

	/**
	 *  Gets the Empresa attribute of the DatoHistorico object
	 *
	 *@return    The Empresa value
	 */
	public String getEmpresa() {
		return this.empresa;
	}


	/**
	 *  Gets the Fecha attribute of the DatoHistorico object
	 *
	 *@return    The Fecha value
	 */
	public String getFecha() {
		return this.fecha;
	}


	/**
	 *  Gets the PrecioCierre attribute of the DatoHistorico object
	 *
	 *@return    The PrecioCierre value
	 */
	public double getPrecioCierre() {
		return this.precio_cierre;
	}


	/**
	 *  Gets the PrecioInicio attribute of the DatoHistorico object
	 *
	 *@return    The PrecioInicio value
	 */
	public double getPrecioInicio() {
		return this.precio_inicio;
	}


	/**
	 *  Gets the PrecioMaximo attribute of the DatoHistorico object
	 *
	 *@return    The PrecioMaximo value
	 */
	public double getPrecioMaximo() {
		return this.precio_maximo;
	}


	/**
	 *  Gets the PrecioMinimo attribute of the DatoHistorico object
	 *
	 *@return    The PrecioMinimo value
	 */
	public double getPrecioMinimo() {
		return this.precio_minimo;
	}


	/**
	 *  Gets the PrecioMedio attribute of the DatoHistorico object
	 *
	 *@return    The PrecioMedio value
	 */
	public double getPrecioMedio() {
		return this.precio_medio;
	}


	/**
	 *  Gets the Efectivo attribute of the DatoHistorico object
	 *
	 *@return    The Efectivo value
	 */
	public double getEfectivo() {
		return this.efectivo;
	}


	/**
	 *  Gets the Volumen attribute of the DatoHistorico object
	 *
	 *@return    The Volumen value
	 */
	public int getVolumen() {
		return this.volumen;
	}

}
