package stoxtreme.sistema_mensajeria.receptor;

import javax.xml.rpc.ServiceException;

import stoxtreme.cliente.StoxtremeServiceLocator;
import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.Stoxtreme;

public class HiloConsultaWS extends Thread{
	ReceptorMensajesWS receptor;
	public HiloConsultaWS(ReceptorMensajesWS mensajesWS){
		receptor = mensajesWS;
	}
	
	public void run(){
		try{
			StoxtremeServiceLocator locator = new StoxtremeServiceLocator();
			Stoxtreme stoxtreme = locator.getStoxtreme();
			while(true){
				Mensaje m = stoxtreme.siguienteMensaje(receptor.getUsuario());
				if(m!=null){
					receptor.notifica(m);
				}
				else{
					Thread.sleep(500);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			this.stop();
		}
	}
}
