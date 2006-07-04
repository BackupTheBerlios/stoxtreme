package stoxtreme.servidor.objeto_bolsa;
import java.util.Hashtable;

import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
//import stoxtreme.servidor.Reloj;
import stoxtreme.servidor.ParametrosServidor;
import stoxtreme.servidor.RelojListener;
import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.eventos.SistemaEventos;
import stoxtreme.servidor.objeto_bolsa.fluctuaciones.Fluctuaciones;
import stoxtreme.servidor.objeto_bolsa.fluctuaciones.SistemaOperaciones;
import stoxtreme.servidor.objeto_bolsa.informacion.Informacion;
import stoxtreme.servidor.objeto_bolsa.informacion.informacion_XML.InformacionXML;

/**
 *  Clase que representa a una empresa que cotiza en la simulacion actual
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ObjetoBolsa implements RelojListener {
	String nombreEmpresa;
	Informacion informacion;
	SistemaOperaciones sistemaOperaciones;
	Fluctuaciones fluctuaciones;
	SistemaEventos sisEventos;
	StoxtremeMensajes sisMensajes;
	double cotizacion;
	InformacionXML infoXML;
	//El numero de acciones inicial son las acciones que todavia no posee ningún agente
	int nAccionesInicial;

	double v = 0.0;

	private Hashtable<Integer, String> mapaOpsAgentes = new Hashtable<Integer, String>();


	/**
	 *  Constructor del objeto ObjetoBolsa
	 *  
	 *@param  nombreEmpresa  Nombre de la empresa
	 *@param  cotizacion     Valor inicial de las acciones
	 *@param  informacion    Ruta del fichero que contiene la información de la empresa
	 */
	public ObjetoBolsa(String nombreEmpresa, double cotizacion, String informacion) {
		this.nombreEmpresa = nombreEmpresa;
		this.cotizacion = cotizacion;
		this.infoXML = new InformacionXML(informacion, nombreEmpresa);
		//Se le pasa null xq el balance y las cuentas de momento no estan hechos
		this.informacion = new Informacion(infoXML.getDatosBursatiles());
		this.nAccionesInicial = new Integer(this.informacion.getIBursatil().getCapitalSocial().elementAt(2).toString());
	}


	/**
	 *  Mutador del atributo Cotizacion
	 *
	 *@param  cotiz  El nuevo valor de Cotizacion
	 */
	public void setCotizacion(double cotiz) {
		this.cotizacion = cotiz;
	}


	/**
	 *  Inicializa el sistema de operaciones y las fluctuaciones del ObjetoBolsa
	 *
	 *@param  variables   Variables del sistema
	 *@param  parametros  Parámetros del servidor
	 */
	public void setVariablesSistema(VariablesSistema variables, ParametrosServidor parametros) {
		sistemaOperaciones = new SistemaOperaciones(nAccionesInicial);
		fluctuaciones = new Fluctuaciones(variables, sistemaOperaciones, parametros.getTick(), this.cotizacion, nombreEmpresa);
	}


	/**
	 *  Accesor del atributo Cotizacion
	 *
	 *@return    El valor de Cotizacion
	 */
	public double getCotizacion() {
		return this.cotizacion;
	}


	/**
	 *  Accesor del atributo NombreEmpresa
	 *
	 *@return     El valor de NombreEmpresa
	 */
	public String getNombreEmpresa() {
		return this.nombreEmpresa;
	}


	/**
	 *  Accesor del atributo Informacion 
	 *
	 *@return    El valor de Informacion
	 */
	public Informacion getInformacion() {
		return this.informacion;
	}


	/**
	 *  Implementación del metodo paso() de la interfaz RelojListener.
	 *  En cada paso se calcula el nuevo precio de las acciones de la empresa
	 *  representada por este ObjetoBolsa
	 */
	public void paso() {
		if (sistemaOperaciones != null && fluctuaciones != null) {
			// Se calcula el nuevo precio de la empresa
			double nPrecio = fluctuaciones.paso();
		}
	}


	/**
	 *  Añade una operación al sistema de operaciones
	 *
	 *@param  IDAgente     Agente que emitió la operación
	 *@param  idOperacion  ID de la operación
	 *@param  op           Operación
	 */
	public void insertaOperacion(String IDAgente, int idOperacion, Operacion op) {
		mapaOpsAgentes.put(idOperacion, IDAgente);
		if (op.getTipoOp() == Operacion.COMPRA) {
			sistemaOperaciones.introduceCompra(idOperacion, IDAgente, op.getPrecio(), op.getCantidad(), fluctuaciones.getPrecioActual(), fluctuaciones.getTick());
		}
		if (op.getTipoOp() == Operacion.VENTA) {
			sistemaOperaciones.introduceVenta(idOperacion, IDAgente, op.getPrecio(), op.getCantidad(), fluctuaciones.getPrecioActual(), fluctuaciones.getTick());
		}
	}


	/**
	 *  Cancela una operación introducida previamente
	 *
	 *@param  idOperacion  Id de la operación a cancelar
	 */
	public void cancelarOperacion(int idOperacion) {
		sistemaOperaciones.cancelaOperacion(idOperacion, mapaOpsAgentes.get(idOperacion));
		mapaOpsAgentes.remove(idOperacion);
	}
}
