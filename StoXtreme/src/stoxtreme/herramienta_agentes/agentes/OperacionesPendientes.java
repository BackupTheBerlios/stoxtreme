package stoxtreme.herramienta_agentes.agentes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.HashSet;

import stoxtreme.interfaz_remota.Operacion;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class OperacionesPendientes {
	Hashtable<Integer, String> empresasPendientes;
	Hashtable<Integer, OperacionLocal> comprasPendientes;
	Hashtable<Integer, OperacionLocal> ventasPendientes;
	HashSet<Integer> cancelacionPendiente;


	/**
	 *  Constructor for the OperacionesPendientes object
	 */
	public OperacionesPendientes() {
		empresasPendientes = new Hashtable<Integer, String>();
		comprasPendientes = new Hashtable<Integer, OperacionLocal>();
		ventasPendientes = new Hashtable<Integer, OperacionLocal>();
		cancelacionPendiente = new HashSet<Integer>();
	}


	/**
	 *  Gets the PendienteCancelacion attribute of the OperacionesPendientes
	 *  object
	 *
	 *@param  nCancelacion  Description of Parameter
	 *@return               The PendienteCancelacion value
	 */
	public boolean isPendienteCancelacion(int nCancelacion) {
		return cancelacionPendiente.contains(nCancelacion);
	}


	/**
	 *  Gets the EmpresaOperacion attribute of the OperacionesPendientes object
	 *
	 *@param  idOp  Description of Parameter
	 *@return       The EmpresaOperacion value
	 */
	public String getEmpresaOperacion(int idOp) {
		return empresasPendientes.get(idOp);
	}


	/**
	 *  Gets the TipoOperacion attribute of the OperacionesPendientes object
	 *
	 *@param  idOp  Description of Parameter
	 *@return       The TipoOperacion value
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
	 *  Gets the PrecioOperacion attribute of the OperacionesPendientes object
	 *
	 *@param  idOp  Description of Parameter
	 *@return       The PrecioOperacion value
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
	 *  Gets the OperacionesPendientes attribute of the OperacionesPendientes
	 *  object
	 *
	 *@return    The OperacionesPendientes value
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
	 *  Description of the Method
	 *
	 *@param  operacion    Description of Parameter
	 *@param  empresa      Description of Parameter
	 *@param  tipoOp       Description of Parameter
	 *@param  numAcciones  Description of Parameter
	 *@param  precio       Description of Parameter
	 */
	public void insertaOperacionPendiente(int operacion, String empresa, int tipoOp, int numAcciones, double precio) {
		empresasPendientes.put(operacion, empresa);
		Hashtable<Integer, OperacionLocal> tabla;
		if (tipoOp == Operacion.COMPRA) {
			tabla = comprasPendientes;
		}
		else {
			// Operacion.VENTA
			tabla = ventasPendientes;
		}
		tabla.put(operacion, new OperacionLocal(empresa, numAcciones, precio));
	}


	/**
	 *  Description of the Method
	 *
	 *@param  nCancelacion  Description of Parameter
	 */
	public void insertarCancelacionPendiente(int nCancelacion) {
		cancelacionPendiente.add(nCancelacion);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOp  Description of Parameter
	 */
	public void cancelaOp(int idOp) {
		comprasPendientes.remove(idOp);
		ventasPendientes.remove(idOp);
		empresasPendientes.remove(idOp);
		cancelacionPendiente.remove(idOp);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  empresa  Description of Parameter
	 *@return          Description of the Returned Value
	 */
	public boolean hayOperacionesPendientes(String empresa) {
		return empresasPendientes.contains(empresa);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOp      Description of Parameter
	 *@param  cantidad  Description of Parameter
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
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
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
	 *  Description of the Method
	 *
	 *@param  id  Description of Parameter
	 *@return     Description of the Returned Value
	 */
	public boolean estaOperacion(int id) {
		boolean estaC = comprasPendientes.containsKey(id);
		boolean estaV = ventasPendientes.containsKey(id);
		return estaC || estaV;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
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
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	private class OperacionLocal {
		private String empresa;
		private int cantidad;
		private double precio;


		/**
		 *  Constructor for the OperacionLocal object
		 *
		 *@param  empresa   Description of Parameter
		 *@param  cantidad  Description of Parameter
		 *@param  precio    Description of Parameter
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
