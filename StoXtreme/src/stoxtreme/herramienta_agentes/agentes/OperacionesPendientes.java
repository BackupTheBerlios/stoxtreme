package stoxtreme.herramienta_agentes.agentes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.HashSet;

import stoxtreme.interfaz_remota.Operacion;

/**
 *  Clase de los objetos que controlan cuales son las operaciones pendientes
 *  que posee el agente en la bolsa
 *
 *  @author  Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class OperacionesPendientes {
	/**
	 *  Nombre de las empresas en las que invierte el agente.
	 *  La clave es el identificador de las operaciones y 
	 *  el valor es la empresa a la que esta asignada esa operacion.
	 */
	private Hashtable<Integer, String> empresasPendientes;
	
	/**
	 *  Guarda en esta estructura los datos del identificador de la 
	 *  operacion de compra guardada
	 */
	private Hashtable<Integer, OperacionLocal> comprasPendientes;
	
	/**
	 *  Gurada en esta estructura los datos del identificador de la
	 *  operacion de venta guardada
	 */
	private Hashtable<Integer, OperacionLocal> ventasPendientes;
	
	/**
	 * Tabla que guarda las cancelaciones a la espera de notificacion
	 */
	private HashSet<Integer> cancelacionPendiente;
	
	/**
	 *  Constructor de la clase de las operaciones pendientes.
	 *  Simplemente inicializa las estructuras de datos necesarias.
	 */
	public OperacionesPendientes() {
		empresasPendientes = new Hashtable<Integer, String>();
		comprasPendientes = new Hashtable<Integer, OperacionLocal>();
		ventasPendientes = new Hashtable<Integer, OperacionLocal>();
		cancelacionPendiente = new HashSet<Integer>();
	}


	/**
	 *  Obtiene si la cancelación por entrada esta a la espera de notificacion
	 *  o no.
	 *
	 *@param  nCancelacion  Identificador de la operación a cancelar
	 *@return               Cierto si esta esperando a ser notificada, falsa en otro caso
	 */
	public boolean isPendienteCancelacion(int nCancelacion) {
		return cancelacionPendiente.contains(nCancelacion);
	}


	/**
	 *  Obtiene la empresa asociada a la operacion asignada al identificador insertado por
	 *  entrada.
	 *
	 *  @param  idOp  Identificador de la operacion
	 *  @return       Nombre de la empresa asignada al identificador
	 */
	public String getEmpresaOperacion(int idOp) {
		return empresasPendientes.get(idOp);
	}

	/**
	 *  Obtiene el tipo de operacion asignada al identificador
	 *
	 *  @param  idOp  Identificador de la operacion
	 *  @return       Tipo del identificador de entrada
	 */
	public int getTipoOperacion(int idOp) {
		if (comprasPendientes.containsKey(idOp)) {
			return Operacion.COMPRA;
		}
		else {
			return Operacion.VENTA;
		}
	}


	/**
	 *  Obtiene el precio de la operacion insertada en la bolsa
	 *
	 *  @param  idOp  Identificador de la operacion
	 *  @return       Precio al que esta insertada la operacion
	 */
	public double getPrecioOperacion(int idOp) {
		if (comprasPendientes.containsKey(idOp)) {
			return comprasPendientes.get(idOp).getPrecio();
		}
		else {
			return ventasPendientes.get(idOp).getPrecio();
		}
	}


	/**
	 *  Obtiene un array con todas los identificadores de las operaciones a la 
	 *  espera de notificacion.
	 *
	 *  @return    Array con los identificadores a la espera de identificacion
	 */
	public int[] getOperacionesPendientes() {
		int[] opsPendientes = new int[empresasPendientes.size()];
		Enumeration<Integer> claves = empresasPendientes.keys();
		int i = 0;
		while (claves.hasMoreElements()) {
			opsPendientes[i++] = claves.nextElement();
		}
		return opsPendientes;
	}


	/**
	 *  Inserta una operacion en la lista de operaciones pendientes
	 *
	 *  @param  operacion    Identificador de la operacion
	 *  @param  empresa      Empresa sobre la que se realiza la operacion
	 *  @param  tipoOp       Tipo de operacion (compra o venta)
	 *  @param  numAcciones  Cantidad de acciones que maneja la operacion
	 *  @param  precio       Precio al que se inserta la operacion
	 */
	public void insertaOperacionPendiente(int operacion, String empresa, int tipoOp, int numAcciones, double precio) {
		empresasPendientes.put(operacion, empresa);
		Hashtable<Integer, OperacionLocal> tabla;
		if (tipoOp == Operacion.COMPRA) {
			tabla = comprasPendientes;
		}
		else {
			tabla = ventasPendientes;
		}
		tabla.put(operacion, new OperacionLocal(empresa, numAcciones, precio));
	}


	/**
	 *  Inserta la cancelacion en la lista de operaciones pendientes
	 *
	 *  @param  nCancelacion  Identificador de la operacion de la que esperamos la operacion
	 */
	public void insertarCancelacionPendiente(int nCancelacion) {
		cancelacionPendiente.add(nCancelacion);
	}


	/**
	 *  Notificacion de una cancelacion.
	 *  Borra el identificador de la lista de cancelaciones pendientes.
	 *
	 *  @param  idOp  Identificador de la operacion
	 */
	public void cancelaOp(int idOp) {
		comprasPendientes.remove(idOp);
		ventasPendientes.remove(idOp);
		empresasPendientes.remove(idOp);
		cancelacionPendiente.remove(idOp);
	}


	/**
	 *  Comprueba si hay operaciones pendientes de una empresa
	 *
	 *  @param  empresa  Nombre de la empresa que deseamos comprobar la existencia de operacion pendiente
	 *  @return          Cierto si hay una operacion pendiente de la empresa, falso en otro caso.
	 */
	public boolean hayOperacionesPendientes(String empresa) {
		return empresasPendientes.contains(empresa);
	}


	/**
	 *  Borra una cantidad determinada de acciones del numero de acciones
	 *  de la operacion pendiente.
	 *
	 *  @param  idOp      Identificador de la operacion
	 *  @param  cantidad  Numero de acciones
	 */
	public void restaAcciones(int idOp, int cantidad) {
		if (comprasPendientes.containsKey(idOp)) {
			comprasPendientes.get(idOp).setCantidad(comprasPendientes.get(idOp).getCantidad() - cantidad);
			if (comprasPendientes.get(idOp).getCantidad() == 0) {
				cancelaOp(idOp);
			}
		}
		else {
			ventasPendientes.get(idOp).setCantidad(ventasPendientes.get(idOp).getCantidad() - cantidad);
			if (ventasPendientes.get(idOp).getCantidad() == 0) {
				cancelaOp(idOp);
			}
		}
	}


	/**
	 *  Devuelve un identificador de una operacion pendiente aleatoria
	 *
	 *  @return  Identificador de la operacion
	 */
	public int dameOperacionAleatoria() {
		Enumeration<Integer> enu = empresasPendientes.keys();
		int n = (int) (Math.random() * (double) empresasPendientes.size());
		int actual = -1;
		for (int i = 0; i <= n; i++) {
			// Avanza n-1
			actual = enu.nextElement();
		}
		return actual;
	}

	/**
	 *  Comprueba si una operacion existe en las listas de operaciones pendientes
	 *
	 *  @param  id  Identificador de la operacion
	 *  @return     Cierto si la operacion esta a la espera de notificacion
	 */
	public boolean estaOperacion(int id) {
		boolean estaC = comprasPendientes.containsKey(id);
		boolean estaV = ventasPendientes.containsKey(id);
		return estaC || estaV;
	}


	/**
	 *  Obtenemos una lista de las operaciones a la espera de notificacion
	 *
	 *  @return    Lista con los identificadores de las operaciones a la espera de notificacion
	 */
	public ArrayList<Integer> dameOperacionesPendientes() {
		ArrayList<Integer> lista = new ArrayList<Integer>();
		Enumeration<Integer> idsc = comprasPendientes.keys();
		Enumeration<Integer> idsv = ventasPendientes.keys();

		while (idsc.hasMoreElements()) {
			lista.add(idsc.nextElement());
		}
		while (idsc.hasMoreElements()) {
			lista.add(idsv.nextElement());
		}
		return lista;
	}


	/**
	 *  Clase utilizada para encapsular los parametros necesarios para el funcionamiento de
	 *  la clase
	 */
	private class OperacionLocal {
		/**
		 * Empresa sobre la que se realiza la operacion
		 */
		private String empresa;
		
		/**
		 * Cantidad de acciones de la operacion
		 */
		private int cantidad;
		
		/**
		 * Precio de la operacion
		 */
		private double precio;


		/**
		 *  Constructor del objeto auxiliar
		 *
		 *  @param  empresa   Description of Parameter
		 *  @param  cantidad  Description of Parameter
		 *  @param  precio    Description of Parameter
		 */
		public OperacionLocal(String empresa, int cantidad, double precio) {
			setEmpresa(empresa);
			setCantidad(cantidad);
			setPrecio(precio);
		}


		/**
		 *  Sets the Empresa attribute of the OperacionLocal object
		 *
		 *@param  empresa  The new Empresa value
		 */
		public void setEmpresa(String empresa) {
			this.empresa = empresa;
		}


		/**
		 *  Sets the Precio attribute of the OperacionLocal object
		 *
		 *@param  precio  The new Precio value
		 */
		public void setPrecio(double precio) {
			this.precio = precio;
		}


		/**
		 *  Gets the Empresa attribute of the OperacionLocal object
		 *
		 *@return    The Empresa value
		 */
		public String getEmpresa() {
			return empresa;
		}


		/**
		 *  Gets the Precio attribute of the OperacionLocal object
		 *
		 *@return    The Precio value
		 */
		public double getPrecio() {
			return precio;
		}


		/**
		 *  Sets the Cantidad attribute of the OperacionLocal object
		 *
		 *@param  cantidad  The new Cantidad value
		 */
		private void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}


		/**
		 *  Gets the Cantidad attribute of the OperacionLocal object
		 *
		 *@return    The Cantidad value
		 */
		private int getCantidad() {
			return cantidad;
		}
	}
}
