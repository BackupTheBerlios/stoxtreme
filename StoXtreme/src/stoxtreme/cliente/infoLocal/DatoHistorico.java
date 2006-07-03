package stoxtreme.cliente.infoLocal;

/**
 *  Describe los componentes de un dato
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
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
	 *  Constructor de DatoHistorico
	 */
	public DatoHistorico() {

	}


	//setters
	/**
	 *  Pone la nueva Empresa
	 *
	 *@param  e  Nuevo valor de Empresa
	 */
	public void setEmpresa(String e) {
		this.empresa = e;
	}


	/**
	 *  Pone la nueva Fecha attribute
	 *
	 *@param  d  Nuevo valor de Fecha
	 */
	public void setFecha(String d) {
		this.fecha = d;
	}


	/**
	 *  Pone el nuevo PrecioCierre
	 *
	 *@param  pc  Nuevo valor de PrecioCierre
	 */
	public void setPrecioCierre(double pc) {
		this.precio_cierre = pc;
	}


	/**
	 *  Pone el nuevo PrecioInicio
	 *
	 *@param  pi  Nuevo valor de PrecioInicio
	 */
	public void setPrecioInicio(double pi) {
		this.precio_inicio = pi;
	}


	/**
	 *  Pone el nuevo PrecioMaximo
	 *
	 *@param  pmax  Nuevo valor de PrecioMaximo
	 */
	public void setPrecioMaximo(double pmax) {
		this.precio_maximo = pmax;
	}


	/**
	 *  Pone el nuevo PrecioMinimo
	 *
	 *@param  pmin  Nuevo valor de PrecioMinimo
	 */
	public void setPrecioMinimo(double pmin) {
		this.precio_minimo = pmin;
	}


	/**
	 *  Pone el nuevo PrecioMedio
	 *
	 *@param  pmed  Nuevo valor de PrecioMedio
	 */
	public void setPrecioMedio(double pmed) {
		this.precio_medio = pmed;
	}


	/**
	 *  Pone el nuevo Efectivo
	 *
	 *@param  efe  Nuevo valor de Efectivo
	 */
	public void setEfectivo(double efe) {
		this.efectivo = efe;
	}


	/**
	 *  Pone el nuevo Volumen
	 *
	 *@param  v  Nuevo valor de Volumen
	 */
	public void setVolumen(int v) {
		this.volumen = v;
	}


	//getters

	/**
	 *  Obtiene el valor de Empresa
	 *
	 *@return    Valor de Empresa
	 */
	public String getEmpresa() {
		return this.empresa;
	}


	/**
	 *  Obtiene el valor de Fecha
	 *
	 *@return    Valor de Fecha
	 */
	public String getFecha() {
		return this.fecha;
	}


	/**
	 *  Obtiene el valor de PrecioCierre
	 *
	 *@return    Valor de PrecioCierre
	 */
	public double getPrecioCierre() {
		return this.precio_cierre;
	}


	/**
	 *  Obtiene el valor de PrecioInicio
	 *
	 *@return    Valor de PrecioInicio
	 */
	public double getPrecioInicio() {
		return this.precio_inicio;
	}


	/**
	 *  Obtiene el valor de PrecioMaximo
	 *
	 *@return    Valor de PrecioMaximo
	 */
	public double getPrecioMaximo() {
		return this.precio_maximo;
	}


	/**
	 *  Obtiene el valor de PrecioMinimo
	 *
	 *@return    Valor de PrecioMinimo
	 */
	public double getPrecioMinimo() {
		return this.precio_minimo;
	}


	/**
	 *  Obtiene el valor de PrecioMedio
	 *
	 *@return    Valor de PrecioMedio
	 */
	public double getPrecioMedio() {
		return this.precio_medio;
	}


	/**
	 *  Obtiene el valor de Efectivo
	 *
	 *@return    Valor de Efectivo
	 */
	public double getEfectivo() {
		return this.efectivo;
	}


	/**
	 *  Obtiene el valor de Volumen
	 *
	 *@return    Valor de Volumen
	 */
	public int getVolumen() {
		return this.volumen;
	}

}
