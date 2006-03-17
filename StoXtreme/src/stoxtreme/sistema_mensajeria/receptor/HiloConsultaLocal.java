package stoxtreme.sistema_mensajeria.receptor;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.interfaz_remota.StoxtremeMensajes;
import stoxtreme.sistema_mensajeria.emisor.AlmacenMensajes;

public class HiloConsultaLocal extends Thread{
	ReceptorMensajes receptor;
	
	public HiloConsultaLocal(ReceptorMensajes mensajes){
		receptor = mensajes;
	}
	
	public void run(){
		try{
			StoxtremeMensajes stoxtreme = AlmacenMensajes.getInstance();
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
