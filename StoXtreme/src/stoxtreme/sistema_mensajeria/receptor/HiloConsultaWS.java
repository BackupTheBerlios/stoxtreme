package stoxtreme.sistema_mensajeria.receptor;

import javax.xml.rpc.ServiceException;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
import stoxtreme.servicio_web.StoxtremeMensajesServiceLocator;
import stoxtreme.servicio_web.StoxtremeServiceLocator;

public class HiloConsultaWS extends Thread{
	ReceptorMensajes receptor;
	
	public HiloConsultaWS(ReceptorMensajes mensajesWS){
		receptor = mensajesWS;
	}
	
	public void run(){
		try{
			StoxtremeMensajesServiceLocator locator = new StoxtremeMensajesServiceLocator();
			StoxtremeMensajes stoxtreme = locator.getStoXtremeMsg();
			while(true){
				Mensaje m = stoxtreme.getSiguienteMensaje(receptor.getUsuario());
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
		}
	}
}
