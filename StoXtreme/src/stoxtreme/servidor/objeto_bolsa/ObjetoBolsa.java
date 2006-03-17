/*
 * TODO:
 * 	- Conectar esto con el sistema de eventos y las variables del sistema
 * 	
 */


package stoxtreme.servidor.objeto_bolsa;
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
	
	/*public ObjetoBolsa(String nombreEmpresa, double cotizacion, String Informacion,
			SistemaEventos sistEventos, EmisorMensajes smg, VariablesSistema var){
		sistemaOperaciones = new SistOperaciones();
		fluctuaciones = new Fluctuaciones(sistemaOperaciones, var.getTick(), var.getPrecioInicial(nombreEmpresa));
	}*/
	public ObjetoBolsa(String nombreEmpresa, double cotizacion, String informacion){
		this.nombreEmpresa=nombreEmpresa;
		this.cotizacion=cotizacion;
		this.infoXML=new InformacionXML(informacion,nombreEmpresa);
		//Se le pasa null xq el balance y las cuentas de momento no estan hechos
		this.informacion=new Informacion(null,infoXML.getDatosBursatiles(),null);
		
	}
	public void paso(){
		if(sistemaOperaciones != null && fluctuaciones !=null){
			sistemaOperaciones.paso();
			fluctuaciones.paso();
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
	
	public void insertaOperacion(String IDAgente,int idOperacion, Operacion op){
		if(op.getTipoOp()==Operacion.COMPRA)
			sistemaOperaciones.introduceCompra(idOperacion, IDAgente, op.getPrecio(), op.getCantidad());
		if(op.getTipoOp()==Operacion.VENTA)
			sistemaOperaciones.introduceVenta(idOperacion, IDAgente, op.getPrecio(), op.getCantidad());
	}
	
	public void cancelarOperacion(int idOperacion, String tipoOp){
		sistemaOperaciones.cancelaOperacion(idOperacion, tipoOp);
	}
	public void setVariablesSistema(VariablesSistema variables, ParametrosServidor parametros) {
		sistemaOperaciones = new SistemaOperaciones();
		fluctuaciones = new Fluctuaciones(variables,sistemaOperaciones, parametros.getTick(),this.cotizacion,nombreEmpresa);
	}
}
