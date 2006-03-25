package stoxtreme.sistema_mensajeria.receptor;

import javax.xml.rpc.ServiceException;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
import stoxtreme.servicio_web.StoxtremeMensajesServiceLocator;
import stoxtreme.servicio_web.StoxtremeServiceLocator;

public class HiloConsultaWS extends Thread{
	private ReceptorMensajes receptor;
	private String url;
	
	public HiloConsultaWS(ReceptorMensajes mensajesWS, String url){
		receptor = mensajesWS;
		this.url = url;
	}
	
	public void run(){
		try{
			StoxtremeMensajesServiceLocator locator = new StoxtremeMensajesServiceLocator();
			if(url!=null)
				locator.setEndpointAddress("8080", url);
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
