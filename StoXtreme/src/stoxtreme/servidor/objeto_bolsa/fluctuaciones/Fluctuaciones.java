package stoxtreme.servidor.objeto_bolsa.fluctuaciones;

import java.util.*;

import stoxtreme.servidor.VariablesSistema;
/**
 *	Clase que realiza el cruce de las operaciones y calcula el nuevo valor de las acciones
 *
 *@author     Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 *@version    1.0
 */
//OJO EL PRIMER ELEMENTO QUE LLEVA EL NUMERO DE ACCIONES ES UN STRING AUNQUE SEA UN NUMERO
public class Fluctuaciones {
	/**
	 *  Máximo cambio de precio en cada paso
	 */
	protected double tick;
	
	/**
	 *  Precio actual de la acción
	 */
	protected double pActual;
	private SistemaOperaciones sisOp;
	private VariablesSistema varS;
	private String empresa;


	/**
	 *  Constructor del objeto Fluctuaciones
	 *
	 *@param  varSis  Variables del sistema
	 *@param  sO      Sistema de operaciones
	 *@param  nt      Tick
	 *@param  pa      Precio actual
	 *@param  emp     Nombre de la empresa
	 */
	public Fluctuaciones(VariablesSistema varSis, SistemaOperaciones sO, double nt, double pa, String emp) {
		sisOp = sO;
		tick = nt;
		pActual = pa;
		varS = varSis;
		empresa = emp;
	}


	/**
	 *  Constructor del objeto Fluctuaciones
	 *
	 *@param  sO   Sistema de operaciones
	 *@param  nt   Tick
	 *@param  pa   PrecioActual
	 *@param  emp  Nombre de la empresa
	 */
	public Fluctuaciones(SistemaOperaciones sO, double nt, double pa, String emp) {
		sisOp = sO;
		tick = nt;
		pActual = pa;
		empresa = emp;
	}


	/**
	 *  Mutador del atributo PrecioActual
	 *
	 *@param  actual  El nuevo valor de PrecioActual
	 */
	public void setPrecioActual(double actual) {
		pActual = actual;
	}


	/**
	 *  Mutador del atributo Tick
	 *
	 *@param  tick  El nuevo valor de Tick
	 */
	public void setTick(double tick) {
		this.tick = tick;
	}


	/**
	 *  Mutador del atributo SisOp
	 *
	 *@param  sisOp  El nuevo valor de SisOp
	 */
	public void setSisOp(SistemaOperaciones sisOp) {
		this.sisOp = sisOp;
	}


	/**
	 *  Accesor del atributo PrecioActual
	 *
	 *@return    El nuevo valor de PrecioActual
	 */
	public double getPrecioActual() {
		return pActual;
	}


	/**
	 *  Accesor del atributo Tick
	 *
	 *@return    El nuevo valor de Tick
	 */
	public double getTick() {
		return tick;
	}


	/**
	 *  Accesor del atributo SisOp
	 *
	 *@return    El nuevo valor de SisOp
	 */
	public SistemaOperaciones getSisOp() {
		return sisOp;
	}


	/**
	 *  Extrae de la tabla de operaciones una subtabla de precios que no 
	 *  sobrepasan un umbral del precio actual.
	 *
	 *@param  vTotal  Tabla total
	 *@return         La subtabla de precios filtrados
	 */
	@SuppressWarnings("unchecked")
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
			if (Double.parseDouble(claveI) <= max && Double.parseDouble(claveI) >= min) {
				listaValores = (Vector) vTotal.get(claveI);
				if (!vFinal.containsKey(claveI)) {
					vFinal.put(claveI, listaValores);
				}
			}
		}
		return vFinal;
	}

	//cambiar para el proyecto total

	/**
	 *  Implementación del método paso() de RelojListener.
	 *  Obtiene el nuevo precio de un título y cambia su valor en las variables del sistema
	 *
	 *@return    El nuevo precio
	 */
	public double paso() {
		pActual = calculaValorTitulo();
		String nomEmpresa = "PRECIO_" + empresa.toUpperCase();
		varS.cambiaVariable(nomEmpresa, new Double(pActual));
		return pActual;
	}


	/**
	 *  Realiza el cruce de operaciones y devuelve el precio de cruce
	 *
	 *@return    El precio de cruce
	 */
	@SuppressWarnings("unchecked")
	public double calculaValorTitulo() {
		boolean hayOperacion = false;
		Hashtable compra;
		Hashtable venta;
		Vector preciosCompra;
		Vector preciosVenta;
		Enumeration claves;
		String clave;
		String claveFinal;
		compra = filtrayOrdena(sisOp.getCompras());
		venta = filtrayOrdena(sisOp.getVentas());
		claves = compra.keys();
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
				//atencion esto se queda con el primer minimo mirar a ver si queremos otros minimos
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
		return redondeo(precioM, 2);
	}


	/**
	 *  Calcula el mínimo entre un precio de compra y otro de venta
	 *
	 *@param  ordC  Precio Compra
	 *@param  ordV  Precio Venta
	 *@return       Mínimo
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
	 *  Redondea un double para que tenga menos decimales
	 *
	 *@param  numero      Número a redondear
	 *@param  nDecimales  Número máximo de decimales
	 *@return             El número redondeado
	 */
	public static double redondeo(double numero, int nDecimales) {
		return Math.floor((Math.pow(10, nDecimales) * numero) + 0.5) / Math.pow(10, nDecimales);
	}


	/**
	 *  Busca el precio (clave de la tabla compras), al que se tramita mayor volumen de acciones
	 *
	 *@param  compra  Tabla de compras
	 *@param  claves  Precios de compra
	 *@return         Precio de compra al que se tramita mayour volumen
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
