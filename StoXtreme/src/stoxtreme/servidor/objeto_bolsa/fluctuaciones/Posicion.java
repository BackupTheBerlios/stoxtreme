package stoxtreme.servidor.objeto_bolsa.fluctuaciones;

/**
 *  
 *
 *@author     Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 *@version    1.0
 */
public class Posicion {
	private String idAgente;
	private int numeroDeAcciones;
	private int idOp;


	/**
	 *  Constructor del objeto Posicion
	 *
	 *@param  id  Id del Agente
	 *@param  nA  Numero de acciones
	 *@param  iO  Id operacion
	 */
	public Posicion(String id, int nA, int iO) {
		idAgente = id;
		numeroDeAcciones = nA;
		idOp = iO;
	}


	/**
	 *  Mutador del atributo Precio
	 *
	 *@param  id  El nuevo valor de Precio
	 */
	public void setPrecio(String id) {
		idAgente = id;
	}


	/**
	 *  Mutador del atributo NumeroDeAcciones
	 *
	 *@param  nA  El nuevo valor de NumeroDeAcciones
	 */
	public void setNumeroDeAcciones(int nA) {
		numeroDeAcciones = nA;
	}


	/**
	 *  Mutador del atributo IdOperacion
	 *
	 *@param  iO  El nuevo valor de IdOperacion
	 */
	public void setIdOperacion(int iO) {
		idOp = iO;
	}


	/**
	 *  Accesor del atributo Precio
	 *
	 *@return    El valor de Precio
	 */
	public String getPrecio() {
		return idAgente;
	}


	/**
	 *  Accesor del atributo NumeroDeAcciones
	 *
	 *@return    El valor de NumeroDeAcciones
	 */
	public int getNumeroDeAcciones() {
		return numeroDeAcciones;
	}


	/**
	 *  Accesor del atributo IdOperacion
	 *
	 *@return    El valor de IdOperacion
	 */
	public int getIdOperacion() {
		return idOp;
	}


	/**
	 *  Accesor del atributo IDAgente
	 *
	 *@return    El valor de IDAgente
	 */
	public String getIDAgente() {
		return idAgente;
	}


	/**
	 * 	Devuelve una representacion en String del objeto
	 *
	 *@return    Representacion en String del objeto
	 */
	public String toString() {
		return idAgente + " " + numeroDeAcciones + " " + idOp;
	}

}
