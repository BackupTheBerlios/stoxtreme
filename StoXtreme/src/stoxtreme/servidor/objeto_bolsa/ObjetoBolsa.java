/*
 * TODO:
 * 	- Falta el sistema de cruzar las operaciones
 * 	- Conectar esto con el sistema de eventos y las variables del sistema
 * 	- 
 */


package stoxtreme.servidor.objeto_bolsa;
import stoxtreme.interfaz_remota.IInformacion;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.eventos.SistemaEventos;
import stoxtreme.servidor.objeto_bolsa.fluctuaciones.Fluctuaciones;
import stoxtreme.servidor.objeto_bolsa.fluctuaciones.SistemaOperaciones;
import stoxtreme.servidor.objeto_bolsa.informacion.Informacion;
import stoxtreme.servidor.objeto_bolsa.informacion.informacion_XML.InformacionXML;
import stoxtreme.sistema_mensajeria.emisor.EmisorMensajes;

public class ObjetoBolsa {
	String nombreEmpresa;
	Informacion informacion;
	SistemaOperaciones sistemaOperaciones;
	Fluctuaciones fluctuaciones;
	SistemaEventos sisEventos;
	EmisorMensajes sisMensajes;
	float cotizacion;
	InformacionXML infoXML;
	
	/*public ObjetoBolsa(String nombreEmpresa, float cotizacion, String Informacion,
			SistemaEventos sistEventos, EmisorMensajes smg, VariablesSistema var){
		sistemaOperaciones = new SistOperaciones();
		fluctuaciones = new Fluctuaciones(sistemaOperaciones, var.getTick(), var.getPrecioInicial(nombreEmpresa));
	}*/
	public ObjetoBolsa(String nombreEmpresa, float cotizacion, String informacion, VariablesSistema var){
		sistemaOperaciones = new SistemaOperaciones();
		fluctuaciones = new Fluctuaciones(sistemaOperaciones, var.getTick(), var.getPrecioInicial(nombreEmpresa));
		this.nombreEmpresa=nombreEmpresa;
		this.cotizacion=cotizacion;
		this.infoXML=new InformacionXML(informacion,nombreEmpresa);
		this.informacion=new Informacion(null,infoXML.getDatosBursatiles(),null);

	}
	public void paso(){
		sistemaOperaciones.paso();
		fluctuaciones.paso();
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
}
