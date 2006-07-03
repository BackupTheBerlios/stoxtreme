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
	 *  Constructor for the Posicion object
	 *
	 *@param  id  Description of Parameter
	 *@param  nA  Description of Parameter
	 *@param  iO  Description of Parameter
	 */
	public Posicion(String id, int nA, int iO) {
		idAgente = id;
		numeroDeAcciones = nA;
		idOp = iO;
	}


	/**
	 *  Sets the Precio attribute of the Posicion object
	 *
	 *@param  id  The new Precio value
	 */
	public void setPrecio(String id) {
		idAgente = id;
	}


	/**
	 *  Sets the NumeroDeAcciones attribute of the Posicion object
	 *
	 *@param  nA  The new NumeroDeAcciones value
	 */
	public void setNumeroDeAcciones(int nA) {
		numeroDeAcciones = nA;
	}


	/**
	 *  Sets the IdOperacion attribute of the Posicion object
	 *
	 *@param  iO  The new IdOperacion value
	 */
	public void setIdOperacion(int iO) {
		idOp = iO;
	}


	/**
	 *  Gets the Precio attribute of the Posicion object
	 *
	 *@return    The Precio value
	 */
	public String getPrecio() {
		return idAgente;
	}


	/**
	 *  Gets the NumeroDeAcciones attribute of the Posicion object
	 *
	 *@return    The NumeroDeAcciones value
	 */
	public int getNumeroDeAcciones() {
		return numeroDeAcciones;
	}


	/**
	 *  Gets the IdOperacion attribute of the Posicion object
	 *
	 *@return    The IdOperacion value
	 */
	public int getIdOperacion() {
		return idOp;
	}


	/**
	 *  Gets the IDAgente attribute of the Posicion object
	 *
	 *@return    The IDAgente value
	 */
	public String getIDAgente() {
		return idAgente;
	}


	/**
	 *  Converts to a String representation of the object.
	 *
	 *@return    A string representation of the object.
	 */
	public String toString() {
		return idAgente + " " + numeroDeAcciones + " " + idOp;
	}

}
