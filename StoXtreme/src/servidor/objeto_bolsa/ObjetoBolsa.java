package servidor.objeto_bolsa;
import interfaz_remota.IInformacion;
import interfaz_remota.Operacion;
import servidor.VariablesSistema;
import servidor.eventos.SistemaEventos;
import servidor.objeto_bolsa.fluctuaciones.Fluctuaciones;
import servidor.objeto_bolsa.informacion.*;
import servidor.objeto_bolsa.operaciones.SistOperaciones;
import sist_mensajeria.emisor.*;

public class ObjetoBolsa {
	/**
	 * @uml.property  name="nombreEmpresa"
	 */
	String nombreEmpresa;
	
	/**
	 * @uml.property  name="informacion"
	 * @uml.associationEnd  readOnly="true"
	 */
	IInformacion informacion;
	/**
	 * @uml.property  name="sistemaOperaciones"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	SistOperaciones sistemaOperaciones;
	/**
	 * @uml.property  name="fluctuaciones"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Fluctuaciones fluctuaciones;
	
	/**
	 * @uml.property  name="sisEventos"
	 * @uml.associationEnd  readOnly="true"
	 */
	SistemaEventos sisEventos;
	/**
	 * @uml.property  name="sisMensajes"
	 * @uml.associationEnd  readOnly="true"
	 */
	SistemaMensajesEmisor sisMensajes;
	
	public ObjetoBolsa(String nombreEmpresa, 
			SistemaEventos sistEventos, SistemaMensajesEmisor smg, VariablesSistema var){
		sistemaOperaciones = new SistOperaciones();
		fluctuaciones = new Fluctuaciones(sistemaOperaciones, var.getTick(), var.getPrecioInicial(nombreEmpresa));
	}
	
	public void paso(){
		sistemaOperaciones.paso();
		fluctuaciones.paso();
	}
	
	public void insertaOperacion(String IDAgente,int idOperacion, Operacion op){
		if(op.getTipoOp()==Operacion.COMPRA)
			sistemaOperaciones.introduceCompra(idOperacion, IDAgente, op.getPrecio(), op.getNumAcciones());
		if(op.getTipoOp()==Operacion.VENTA)
			sistemaOperaciones.introduceVenta(idOperacion, IDAgente, op.getPrecio(), op.getNumAcciones());
	}
	
	public void cancelarOperacion(int idOperacion, String tipoOp){
		sistemaOperaciones.cancelaOperacion(idOperacion, tipoOp);
	}
}
