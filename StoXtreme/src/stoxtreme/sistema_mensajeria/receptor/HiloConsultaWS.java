package stoxtreme.sistema_mensajeria.receptor;

import java.net.URL;

import javax.xml.rpc.ServiceException;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.Stoxtreme;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
import stoxtreme.servicio_web.StoxtremeMensajesServiceLocator;
import stoxtreme.servicio_web.StoxtremeServiceLocator;

public class HiloConsultaWS extends Thread{
	private ReceptorMensajes receptor;
	private String url;
	private boolean parar = false;
	
	public HiloConsultaWS(ReceptorMensajes mensajesWS, String url){
		receptor = mensajesWS;
		this.url = url;
	}
	
	public void run(){
		try{
			StoxtremeMensajesServiceLocator locator = new StoxtremeMensajesServiceLocator();
			StoxtremeMensajes stoxtreme;
			if(url!=null){
				stoxtreme = locator.getStoXtremeMsg(new URL(url));
			}
			else{
				stoxtreme = locator.getStoXtremeMsg();
			}
				
			while(!parar){
				Mensaje m = stoxtreme.getSiguienteMensaje(receptor.getUsuario());
				if("FIN".equals(m.getTipoMensaje())){
					parar = true;
				}
				if(m!=null){
					receptor.notifica(m);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
