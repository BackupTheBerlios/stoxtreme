package stoxtreme.servidor.objeto_bolsa;
import stoxtreme.interfaz_remota.IInformacion;
import stoxtreme.interfaz_remota.Operacion;
import stoxtreme.servidor.VariablesSistema;
import stoxtreme.servidor.eventos.SistemaEventos;
import stoxtreme.servidor.objeto_bolsa.fluctuaciones.Fluctuaciones;
import stoxtreme.servidor.objeto_bolsa.operaciones.SistOperaciones;
import stoxtreme.sistema_mensajeria.emisor.EmisorMensajes;

public class ObjetoBolsa {
	String nombreEmpresa;
	IInformacion informacion;
	SistOperaciones sistemaOperaciones;
	Fluctuaciones fluctuaciones;
	SistemaEventos sisEventos;
	EmisorMensajes sisMensajes;
	
	public ObjetoBolsa(String nombreEmpresa, 
			SistemaEventos sistEventos, EmisorMensajes smg, VariablesSistema var){
		sistemaOperaciones = new SistOperaciones();
		fluctuaciones = new Fluctuaciones(sistemaOperaciones, var.getTick(), var.getPrecioInicial(nombreEmpresa));
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
