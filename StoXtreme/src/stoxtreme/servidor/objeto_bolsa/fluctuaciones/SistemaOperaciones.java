package stoxtreme.servidor.objeto_bolsa.fluctuaciones;

import java.util.*;

import stoxtreme.interfaz_remota.Mensaje;
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
 *  Clase que guarda las operaciones sobre una empresa que llegan al sistema
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class SistemaOperaciones {
	private Hashtable listaCompras;
	private Hashtable listaVentas;
	private int nAccionesVenta;
	private double precioEstimado;
	private TreeSet idscompras;
	private TreeSet idsventas;


	/**
	 *  Constructor del objeto SistemaOperaciones
	 *
	 *@param  nAccionesVenta  Número de acciones a la venta de la empresa
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
	 *  Constructor del objeto SistemaOperaciones
	 *
	 *@param  lC  Lista de operaciones de compra
	 *@param  lV  Lista de operaciones de venta
	 */
	public SistemaOperaciones(Hashtable lC, Hashtable lV) {
		this.listaCompras = lC;
		this.listaVentas = lV;
	}


	/**
	 *  Mutador del atributo AccionesVenta
	 *
	 *@param  accionesVenta  El nuevo valor de AccionesVenta
	 */
	public void setAccionesVenta(int accionesVenta) {
		nAccionesVenta = accionesVenta;
	}


	/**
	 *  Mutador del atributo PrecioEstimado
	 *
	 *@param  precioEstimado  El nuevo valor de PrecioEstimado
	 */
	public void setPrecioEstimado(double precioEstimado) {
		this.precioEstimado = precioEstimado;
	}


	/**
	 *  Mutador del atributo ListaCompras
	 *
	 *@param  listaCompras  El nuevo valor de ListaCompras
	 */
	public void setListaCompras(Hashtable listaCompras) {
		this.listaCompras = listaCompras;
	}


	/**
	 *  Mutador del atributo ListaVentas
	 *
	 *@param  listaVentas  El nuevo valor de ListaVentas
	 */
	public void setListaVentas(Hashtable listaVentas) {
		this.listaVentas = listaVentas;
	}


	/**
	 *  Accesor del atributo Compras
	 *
	 *@return    El valor de Compras
	 */
	public Hashtable getCompras() {
		return listaCompras;
	}


	/**
	 *  Accesor del atributo Ventas
	 *
	 *@return    El valor de Ventas
	 */
	public Hashtable getVentas() {
		return listaVentas;
	}


	/**
	 *  Accesor del atributo AccionesVenta
	 *
	 *@return    El valor de AccionesVenta
	 */
	public int getAccionesVenta() {
		return nAccionesVenta;
	}


	/**
	 *  Accesor del atributo PrecioEstimado
	 *
	 *@return   El valor de PrecioEstimado
	 */
	public double getPrecioEstimado() {
		return precioEstimado;
	}


	/**
	 *  Introduce una operación de compra en el sistema de operaciones
	 *
	 *@param  idOperacion  Id de la operación
	 *@param  agente       Id del usuario que envía la operación
	 *@param  precio       Precio de compra
	 *@param  numAcciones  Número de acciones a comprar
	 *@param  precioA      Precio actual de la acción
	 *@param  tick         Tick
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
	 *  Introduce una operación de venta en el sistema de operaciones
	 *
	 *@param  idOperacion  Id de operación
	 *@param  agente       Id de usuario que envía la operación
	 *@param  precio       Precio de venta
	 *@param  numAcciones  Número de acciones a vender
	 *@param  precioA      Precio actual de la acción
	 *@param  tick         Tick
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
	 *  Cancela una operación previamente introducida
	 *
	 *@param  idOperacion  Id de operación
	 *@param  idAgente     Id de usuario que cancela la operación
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
	 *  Genera un mensaje para enviar al usuario cuando su petición ha sido atendida
	 *
	 *@param  idAgente     Id de usuario
	 *@param  idOperacion  Id de operación
	 *@param  numAcciones  Número de acciones tramitadas
	 *@param  precio       Precio al que se ha comprado/vendido
	 */
	public void notificaOperacion(String idAgente, int idOperacion, int numAcciones, double precio) {
		String tipo = "NOTIFICACION_OPERACION";
		String contenido = idOperacion + "," + numAcciones + "," + Fluctuaciones.redondeo(precio, 2);
		Mensaje m = new Mensaje(contenido, tipo, idAgente);
		AlmacenMensajes.getInstance().enviaMensaje(m);
	}


	/**
	 *  Calcula el precio de cruce de operaciones
	 *
	 *@param  precioA        Precio actual
	 *@param  tick           Tick
	 *@param  precioCliente  Precio enviado por el cliente
	 *@return                Devuelve el precio de cruce de operaciones
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
