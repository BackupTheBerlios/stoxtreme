package stoxtreme.servidor.objeto_bolsa.fluctuaciones;

import java.util.*;

import javax.swing.JOptionPane;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.objeto_bolsa.ObjetoBolsa;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;
/**
 *  <p>
 *
 *  Título: </p> <p>
 *
 *  Descripción: </p> <p>
 *
 *  Copyright: Copyright (c) 2005</p> <p>
 *
 *  Empresa: </p>
 *
 *@author     Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 *@version    1.0
 */
//OJO EL PRIMER ELEMENTO QUE LLEVA EL NUMERO DE ACCIONES ES UN STRING AUNK SEA UN NUMERO
public class Fluctuaciones {
	/**
	 *  Description of the Field
	 */
	protected double tick;
	//o intervalo?
			/**
	 *  Description of the Field
	 */
	protected double pActual;
	//protected Hashtable entorno=new Hashtable();
	//protected Hashtable compraT,ventaT;
	private SistemaOperaciones sisOp;
	private VariablesSistema varS;
	private String empresa;


	/**
	 *  Constructor for the Fluctuaciones object
	 *
	 *@param  varSis  Description of Parameter
	 *@param  sO      Description of Parameter
	 *@param  nt      Description of Parameter
	 *@param  pa      Description of Parameter
	 *@param  emp     Description of Parameter
	 */
	public Fluctuaciones(VariablesSistema varSis, SistemaOperaciones sO, double nt, double pa, String emp) {
		sisOp = sO;
		tick = nt;
		pActual = pa;
		varS = varSis;
		empresa = emp;
	}


	/**
	 *  Constructor for the Fluctuaciones object
	 *
	 *@param  sO   Description of Parameter
	 *@param  nt   Description of Parameter
	 *@param  pa   Description of Parameter
	 *@param  emp  Description of Parameter
	 */
	public Fluctuaciones(SistemaOperaciones sO, double nt, double pa, String emp) {
		sisOp = sO;
		tick = nt;
		pActual = pa;
		empresa = emp;
	}


	/**
	 *  Sets the PrecioActual attribute of the Fluctuaciones object
	 *
	 *@param  actual  The new PrecioActual value
	 */
	public void setPrecioActual(double actual) {
		pActual = actual;
	}


	/**
	 *  Sets the Tick attribute of the Fluctuaciones object
	 *
	 *@param  tick  The new Tick value
	 */
	public void setTick(double tick) {
		this.tick = tick;
	}


	/**
	 *  Sets the SisOp attribute of the Fluctuaciones object
	 *
	 *@param  sisOp  The new SisOp value
	 */
	public void setSisOp(SistemaOperaciones sisOp) {
		this.sisOp = sisOp;
	}


	/**
	 *  Gets the PrecioActual attribute of the Fluctuaciones object
	 *
	 *@return    The PrecioActual value
	 */
	public double getPrecioActual() {
		return pActual;
	}


	/**
	 *  Gets the Tick attribute of the Fluctuaciones object
	 *
	 *@return    The Tick value
	 */
	public double getTick() {
		return tick;
	}


	/**
	 *  Gets the SisOp attribute of the Fluctuaciones object
	 *
	 *@return    The SisOp value
	 */
	public SistemaOperaciones getSisOp() {
		return sisOp;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  vTotal  Description of Parameter
	 *@return         Description of the Returned Value
	 */
	public Hashtable filtrayOrdena(Hashtable vTotal) {
		Hashtable vFinal = new Hashtable();
		Vector listaValores;
		Enumeration listaClaves;
		double max;
		double min;
		String claveI;
		max = pActual + 5;
		min = pActual - 5;
		listaClaves = vTotal.keys();
		while (listaClaves.hasMoreElements()) {
			claveI = (String) listaClaves.nextElement();
			//}
			if (Double.parseDouble(claveI) <= max && Double.parseDouble(claveI) >= min) {
				listaValores = (Vector) vTotal.get(claveI);
				if (!vFinal.containsKey(claveI)) {
					vFinal.put(claveI, listaValores);
				}
			}
			//listaClaves.nextElement();
		}
		return vFinal;
	}

	//cambiar para el proyecto total

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public double paso() {
		pActual = calculaValorTitulo();
		String nomEmpresa = "PRECIO_" + empresa.toUpperCase();
		varS.cambiaVariable(nomEmpresa, new Double(pActual));
		return pActual;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public double calculaValorTitulo(
	/*
	 *  String nombreTitulo,double precioA
	 */
			) {
		boolean hayOperacion = false;
		Hashtable compra;
		Hashtable venta;
		Vector preciosCompra;
		Vector preciosVenta;
		Enumeration claves;
		//Posicion aux,aux2;
		String clave;
		//Posicion aux,aux2;
		String claveFinal;
		compra = filtrayOrdena(sisOp.getCompras());
		venta = filtrayOrdena(sisOp.getVentas());
		claves = compra.keys();
		//if (compra.size() == venta.size() ){
		int ordenesM = 0;
		int ordParcial;
		double precioM = pActual;
		claveFinal = "";
		while (claves.hasMoreElements()) {
			clave = (String) claves.nextElement();
			if (venta.containsKey(clave)) {
				preciosCompra = (Vector) compra.get(clave);
				preciosVenta = (Vector) venta.get(clave);
				ordParcial = minimo(Integer.parseInt((String) preciosCompra.elementAt(0).toString()),
						Integer.parseInt((String) preciosVenta.elementAt(0).toString()));
				//atencion esto se queda kon el primer minimo mirar a ver si keremos otros minimos
				if (ordParcial > ordenesM) {
					hayOperacion = true;
					ordenesM = ordParcial;
					precioM = Double.parseDouble(clave);
					claveFinal = clave;

				}
			}
		}
		if (hayOperacion) {
			preciosCompra = (Vector) compra.get(claveFinal);
			preciosVenta = (Vector) venta.get(claveFinal);
			int auxC = ordenesM;
			int auxV = ordenesM;
			int indiceC = 1;
			int indiceV = 1;
			Integer numAccionesC = (Integer) preciosCompra.elementAt(0);
			numAccionesC = Integer.valueOf(numAccionesC.intValue() - auxC);
			Integer numAccionesV = (Integer) preciosVenta.elementAt(0);
			numAccionesV = Integer.valueOf(numAccionesV.intValue() - auxV);
			preciosCompra.setElementAt(numAccionesC, 0);
			preciosVenta.setElementAt(numAccionesV, 0);
			while (auxC > 0 && indiceC < preciosCompra.size() && indiceV < preciosVenta.size()) {
				Posicion pC = (Posicion) preciosCompra.elementAt(indiceC);
				Posicion pV = (Posicion) preciosVenta.elementAt(indiceV);
				if (pC.getNumeroDeAcciones() <= auxC) {
					auxC -= pC.getNumeroDeAcciones();
					sisOp.notificaOperacion(pC.getIDAgente(), pC.getIdOperacion(), pC.getNumeroDeAcciones(), precioM);
					preciosCompra.remove(indiceC);
					indiceC++;
				}
				else {
					pC.setNumeroDeAcciones(pC.getNumeroDeAcciones() - auxC);
					sisOp.notificaOperacion(pC.getIDAgente(), pC.getIdOperacion(), auxC, precioM);
					auxC = 0;
				}
				if (pV.getNumeroDeAcciones() <= auxV) {
					auxV -= pV.getNumeroDeAcciones();
					sisOp.notificaOperacion(pV.getIDAgente(), pV.getIdOperacion(), pV.getNumeroDeAcciones(), precioM);
					preciosVenta.remove(indiceV);
					indiceV++;
				}
				else {
					pV.setNumeroDeAcciones(pV.getNumeroDeAcciones() - auxV);
					sisOp.notificaOperacion(pV.getIDAgente(), pV.getIdOperacion(), auxV, precioM);
					auxV = 0;
				}
			}
			if (numAccionesC.intValue() == 0) {
				compra.remove(claveFinal);
			}
			if (numAccionesV.intValue() == 0) {
				venta.remove(claveFinal);
			}
		}
		else {
			claveFinal = buscaMayorVolumen(compra, compra.keys());
			if (!claveFinal.equals("") && sisOp.getAccionesVenta() > 0) {
				boolean acabado = sisOp.getAccionesVenta() == 0;
				int i = 1;
				//	    	Vector auxV=(Vector)compra.get(Double.toString
				//	    			(SistemaOperaciones.calculaPrecio
				//	    					(pActual,tick,pActual+sisOp.getPrecioEstimado())));
				Vector auxC = (Vector) compra.get(claveFinal);
				Integer accionesTotales = (Integer) auxC.elementAt(0);
				if (sisOp.getAccionesVenta() > accionesTotales.intValue()) {
					accionesTotales = new Integer(0);
				}
				else {
					accionesTotales = Integer.valueOf(accionesTotales.intValue() - sisOp.getAccionesVenta());
					auxC.set(0, accionesTotales);
				}
				if (sisOp.getAccionesVenta() > 0 && auxC != null && auxC.size() > 0) {
					i = 1;
					while (auxC.size() > i && !acabado) {
						Posicion pC = (Posicion) auxC.get(i);
						if (pC.getNumeroDeAcciones() > sisOp.getAccionesVenta()) {
							pC.setNumeroDeAcciones(pC.getNumeroDeAcciones() - sisOp.getAccionesVenta());
							sisOp.notificaOperacion(pC.getIDAgente(), pC.getIdOperacion(), sisOp.getAccionesVenta(), Double.parseDouble(claveFinal));
							sisOp.setAccionesVenta(0);
							acabado = true;
						}
						else {
							sisOp.setAccionesVenta(sisOp.getAccionesVenta() - pC.getNumeroDeAcciones());
							sisOp.notificaOperacion(pC.getIDAgente(), pC.getIdOperacion(), pC.getNumeroDeAcciones(), Double.parseDouble(claveFinal));
							auxC.remove(i);
							//i++;
						}

					}
					if (accionesTotales.intValue() == 0) {
						auxC = null;
					}
					Hashtable aux = sisOp.getCompras();
					aux.remove(claveFinal);
					if (auxC != null) {
						aux.put(claveFinal, auxC);
					}
					sisOp.setListaCompras(aux);
					precioM = Double.parseDouble(claveFinal);
					return redondeo(precioM, 2);
				}
			}
		}
		//sisOp.setListaCompras(compra);
		//sisOp.setListaVentas(venta);
		return redondeo(precioM, 2);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  ordC  Description of Parameter
	 *@param  ordV  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	private int minimo(int ordC, int ordV) {
		if (ordC < ordV) {
			return ordC;
		}
		else {
			return ordV;
		}
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


	/**
	 *  Description of the Method
	 *
	 *@param  compra  Description of Parameter
	 *@param  claves  Description of Parameter
	 *@return         Description of the Returned Value
	 */
	private static String buscaMayorVolumen(Hashtable compra, Enumeration claves) {
		String claveActual = "";
		String claveSalida = "";
		int volumen = 0;
		while (claves.hasMoreElements()) {
			claveActual = (String) claves.nextElement();
			if (((Integer) ((Vector) compra.get(claveActual)).elementAt(0)).intValue() > volumen) {
				claveSalida = claveActual;
			}
		}
		return claveSalida;
	}

}
