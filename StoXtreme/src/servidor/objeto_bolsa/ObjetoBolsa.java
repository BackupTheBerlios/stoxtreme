package servidor.objeto_bolsa;
import interfaz_remota.Operacion;
import servidor.eventos.SistemaEventos;
import servidor.objeto_bolsa.contenedor.Contenedor;
import servidor.objeto_bolsa.fluctuaciones.Fluctuaciones;
import servidor.objeto_bolsa.informacion.Informacion;
import servidor.objeto_bolsa.operaciones.SistOperaciones;
import sist_mensajeria.emisor.*;

public class ObjetoBolsa {
	String nombreEmpresa;
	
	Informacion informacion;
	SistOperaciones sistemaOperaciones;
	Fluctuaciones fluctuaciones;
	Contenedor contenedor;
	
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
