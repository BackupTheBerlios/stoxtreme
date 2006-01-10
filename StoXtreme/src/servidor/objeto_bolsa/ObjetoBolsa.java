package servidor.objeto_bolsa;
import interfaz_remota.IInformacion;
import interfaz_remota.Operacion;
import servidor.eventos.SistemaEventos;
import servidor.objeto_bolsa.fluctuaciones.Fluctuaciones;
import servidor.objeto_bolsa.informacion.*;
import servidor.objeto_bolsa.operaciones.SistOperaciones;
import sist_mensajeria.emisor.*;

public class ObjetoBolsa {
	String nombreEmpresa;
	
	IInformacion informacion;
	SistOperaciones sistemaOperaciones;
	Fluctuaciones fluctuaciones;
	
	SistemaEventos sisEventos;
	SistemaMensajesEmisor sisMensajes;
	
	public ObjetoBolsa(String nombreEmpresa, 
			SistemaEventos sistEventos, SistemaMensajesEmisor smg){
		
	}
	
	public void paso(){
		
	}
	
	public void insertaOperacion(String IDAgente, 
			int idOperacion, Operacion op){
		
	}
	
	public void cancelarOperacion(int idOperacion){
		
	}
	
	
}
