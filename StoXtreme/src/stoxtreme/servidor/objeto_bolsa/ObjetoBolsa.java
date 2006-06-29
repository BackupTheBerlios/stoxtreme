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

public class ObjetoBolsa implements RelojListener{
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
	public ObjetoBolsa(String nombreEmpresa, double cotizacion, String informacion){
		this.nombreEmpresa=nombreEmpresa;
		this.cotizacion=cotizacion;
		this.infoXML=new InformacionXML(informacion,nombreEmpresa);
		//Se le pasa null xq el balance y las cuentas de momento no estan hechos
		this.informacion=new Informacion(infoXML.getDatosBursatiles());
		this.nAccionesInicial = new Integer(this.informacion.getIBursatil().getCapitalSocial().elementAt(2).toString());
	}
	
	double v = 0.0;
	public void paso(){
		if(sistemaOperaciones != null && fluctuaciones !=null){
			// Se calcula el nuevo precio de la empresa
			double nPrecio = fluctuaciones.paso();
		}
	}
	
	public double  getCotizacion(){
		return this.cotizacion;
	}
	
	public void setCotizacion(double cotiz){
		this.cotizacion=cotiz;
	}
	
	public String getNombreEmpresa(){
		return this.nombreEmpresa;
	}
	
	public Informacion getInformacion(){
		return this.informacion;
	}
	
	private Hashtable<Integer, String> mapaOpsAgentes = new Hashtable<Integer,String>(); 
	public void insertaOperacion(String IDAgente,int idOperacion, Operacion op){
		System.err.println(IDAgente+" "+idOperacion+" "+op);
		mapaOpsAgentes.put(idOperacion, IDAgente);
		if(op.getTipoOp()==Operacion.COMPRA)
			sistemaOperaciones.introduceCompra(idOperacion, IDAgente, op.getPrecio(), op.getCantidad(),fluctuaciones.getPrecioActual(),fluctuaciones.getTick());
		if(op.getTipoOp()==Operacion.VENTA)
			sistemaOperaciones.introduceVenta(idOperacion, IDAgente, op.getPrecio(), op.getCantidad(),fluctuaciones.getPrecioActual(),fluctuaciones.getTick());
	}
	
	public void cancelarOperacion(int idOperacion){
		sistemaOperaciones.cancelaOperacion(idOperacion, mapaOpsAgentes.get(idOperacion));
		mapaOpsAgentes.remove(idOperacion);
	}
	public void setVariablesSistema(VariablesSistema variables, ParametrosServidor parametros) {
		sistemaOperaciones = new SistemaOperaciones(nAccionesInicial);
		fluctuaciones = new Fluctuaciones(variables,sistemaOperaciones, parametros.getTick(),this.cotizacion,nombreEmpresa);
	}
}
