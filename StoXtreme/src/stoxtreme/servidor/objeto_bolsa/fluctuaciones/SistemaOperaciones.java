package stoxtreme.servidor.objeto_bolsa.fluctuaciones;

import java.util.*;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.Servidor;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

/*
 *  - Todas las operaciones de una empresa llegan aqui
 *  - Esta clase tiene (al menos) una lista de compras y una lista de ventas
 *
 *  Introducir Operacion -> devuelve idOp
 *  Cancelar(idOp)
 *
 *  Paso() para el reloj. Cruza las operaciones
 *
 *  *Atributos
 *  Lista de compras y de ventas
 *
 *
 */
/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class SistemaOperaciones {
	/*
	 *  implements RelojListener
	 */
	private Hashtable listaCompras;
	private Hashtable listaVentas;
	private int nAccionesVenta;
	private double precioEstimado;
	private TreeSet idscompras;
	private TreeSet idsventas;


	/**
	 *  Constructor for the SistemaOperaciones object
	 *
	 *@param  nAccionesVenta  Description of Parameter
	 */
	public SistemaOperaciones(int nAccionesVenta) {
		listaCompras = new Hashtable();
		listaVentas = new Hashtable();
		this.nAccionesVenta = nAccionesVenta;
		this.precioEstimado = 1;
		idscompras = new TreeSet();
		idsventas = new TreeSet();
	}


	/**
	 *  Constructor for the SistemaOperaciones object
	 *
	 *@param  lC  Description of Parameter
	 *@param  lV  Description of Parameter
	 */
	public SistemaOperaciones(Hashtable lC, Hashtable lV) {
		this.listaCompras = lC;
		this.listaVentas = lV;
	}


	/**
	 *  Sets the AccionesVenta attribute of the SistemaOperaciones object
	 *
	 *@param  accionesVenta  The new AccionesVenta value
	 */
	public void setAccionesVenta(int accionesVenta) {
		nAccionesVenta = accionesVenta;
	}


	/**
	 *  Sets the PrecioEstimado attribute of the SistemaOperaciones object
	 *
	 *@param  precioEstimado  The new PrecioEstimado value
	 */
	public void setPrecioEstimado(double precioEstimado) {
		this.precioEstimado = precioEstimado;
	}


	/**
	 *  Sets the ListaCompras attribute of the SistemaOperaciones object
	 *
	 *@param  listaCompras  The new ListaCompras value
	 */
	public void setListaCompras(Hashtable listaCompras) {
		this.listaCompras = listaCompras;
	}


	/**
	 *  Sets the ListaVentas attribute of the SistemaOperaciones object
	 *
	 *@param  listaVentas  The new ListaVentas value
	 */
	public void setListaVentas(Hashtable listaVentas) {
		this.listaVentas = listaVentas;
	}


	/**
	 *  Gets the Compras attribute of the SistemaOperaciones object
	 *
	 *@return    The Compras value
	 */
	public Hashtable getCompras() {
		return listaCompras;
	}


	/**
	 *  Gets the Ventas attribute of the SistemaOperaciones object
	 *
	 *@return    The Ventas value
	 */
	public Hashtable getVentas() {
		return listaVentas;
	}


	/**
	 *  Gets the AccionesVenta attribute of the SistemaOperaciones object
	 *
	 *@return    The AccionesVenta value
	 */
	public int getAccionesVenta() {
		return nAccionesVenta;
	}


	/**
	 *  Gets the PrecioEstimado attribute of the SistemaOperaciones object
	 *
	 *@return    The PrecioEstimado value
	 */
	public double getPrecioEstimado() {
		return precioEstimado;
	}

	//TODO falta mirar a ver si tengo por variable lo que necesito

	/**
	 *  Description of the Method
	 *
	 *@param  idOperacion  Description of Parameter
	 *@param  agente       Description of Parameter
	 *@param  precio       Description of Parameter
	 *@param  numAcciones  Description of Parameter
	 *@param  precioA      Description of Parameter
	 *@param  tick         Description of Parameter
	 */
	public void introduceCompra(int idOperacion, String agente,
			double precio, int numAcciones, double precioA, double tick) {
		Vector compra = new Vector();
		Integer accionesTotales;
		Posicion p1;
		int idOp;
		Integer num = new Integer(numAcciones);
		if (listaCompras.containsKey(Double.toString(calculaPrecio(precioA, tick, precio)))) {
			compra = (Vector) listaCompras.get(Double.toString(calculaPrecio(precioA, tick, precio)));
			accionesTotales = (Integer) compra.firstElement();
			accionesTotales = Integer.valueOf(num.intValue() + accionesTotales.intValue());
			compra.setElementAt(accionesTotales, 0);
			p1 = new Posicion(agente, numAcciones, idOperacion);
			compra.add(p1);
		}
		else {
			p1 = new Posicion(agente, numAcciones, idOperacion);
			compra.add(num);
			compra.add(p1);
			listaCompras.put(Double.toString(calculaPrecio(precioA, tick, precio)), compra);

		}

		idscompras.add(idOperacion);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOperacion  Description of Parameter
	 *@param  agente       Description of Parameter
	 *@param  precio       Description of Parameter
	 *@param  numAcciones  Description of Parameter
	 *@param  precioA      Description of Parameter
	 *@param  tick         Description of Parameter
	 */
	public void introduceVenta(int idOperacion, String agente,
			double precio, int numAcciones, double precioA, double tick) {
		Vector venta = new Vector();
		Integer accionesTotales;
		Posicion p1;
		int idOp;
		Integer num = new Integer(numAcciones);
		if (listaVentas.containsKey(Double.toString(calculaPrecio(precioA, tick, precio)))) {
			venta = (Vector) listaVentas.get(Double.toString(calculaPrecio(precioA, tick, precio)));
			accionesTotales = (Integer) venta.firstElement();
			accionesTotales = Integer.valueOf(num.intValue() + accionesTotales.intValue());
			venta.setElementAt(accionesTotales, 0);
			p1 = new Posicion(agente, numAcciones, idOperacion);
			venta.add(p1);
		}
		else {
			p1 = new Posicion(agente, numAcciones, idOperacion);
			venta.add(num);
			venta.add(p1);
			listaVentas.put(Double.toString(calculaPrecio(precioA, tick, precio)), venta);

		}
		idsventas.add(idOperacion);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idOperacion  Description of Parameter
	 *@param  idAgente     Description of Parameter
	 */
	public void cancelaOperacion(int idOperacion, String idAgente) {
		Hashtable operacion;
		boolean encontrado = false;
		boolean esCompra;
		Vector cadena = new Vector();
		Posicion pi;
		if (idscompras.contains(idOperacion)) {
			operacion = this.listaCompras;
			esCompra = true;
			idscompras.remove(idOperacion);
		}
		else {
			if (idsventas.contains(idOperacion)) {
				operacion = this.listaVentas;
				idsventas.remove(idOperacion);
				esCompra = false;
			}
			else {
				return;
			}
		}

		Enumeration claves = operacion.keys();
		while (claves.hasMoreElements() && !encontrado) {
			String elemento = (String) claves.nextElement();
			cadena = (Vector) operacion.get(elemento);
			int i = 1;
			while (!encontrado && i < cadena.size()) {
				pi = (Posicion) cadena.elementAt(i);
				if (pi.getIdOperacion() == idOperacion) {
					encontrado = true;
					//cadena.remove(i);
					Integer acciones = (Integer) cadena.firstElement();
					acciones = new Integer(acciones.intValue() - pi.getNumeroDeAcciones());
					if (acciones.toString().equals("0")) {
						cadena.removeAllElements();
						operacion.remove(elemento);

					}
					else {
						cadena.setElementAt(acciones, 0);
					}
					AlmacenMensajes.getInstance().enviaMensaje(new Mensaje(Integer.toString(idOperacion), "NOTIFICACION_CANCELACION", idAgente));
				}
				i++;
			}
		}
		if (esCompra) {
			listaCompras = operacion;
		}
		else {
			listaVentas = operacion;
		}
		// Tiene que BUSCAR la operacion a cancelar si hace falta
		// se almacenaran las cancelaciones y cuando pase un tiempo se efectuaran
		// todas
	}


	/**
	 *  Description of the Method
	 *
	 *@param  idAgente     Description of Parameter
	 *@param  idOperacion  Description of Parameter
	 *@param  numAcciones  Description of Parameter
	 *@param  precio       Description of Parameter
	 */
	public void notificaOperacion(String idAgente, int idOperacion, int numAcciones, double precio) {
		String tipo = "NOTIFICACION_OPERACION";
		String contenido = idOperacion + "," + numAcciones + "," + Fluctuaciones.redondeo(precio, 2);
		Mensaje m = new Mensaje(contenido, tipo, idAgente);
		AlmacenMensajes.getInstance().enviaMensaje(m);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  precioA        Description of Parameter
	 *@param  tick           Description of Parameter
	 *@param  precioCliente  Description of Parameter
	 *@return                Description of the Returned Value
	 */
	public static double calculaPrecio(double precioA, double tick, double precioCliente) {
		double auxTick = tick;
		if (precioA > precioCliente) {
			auxTick = -auxTick;
			while (!(precioA + auxTick < precioCliente)) {
				auxTick -= tick;
			}
			return (auxTick + precioA);
		}
		else {
			while (!(precioA + auxTick > precioCliente)) {
				auxTick += tick;
			}
			return (auxTick + precioA);
		}
	}
}
