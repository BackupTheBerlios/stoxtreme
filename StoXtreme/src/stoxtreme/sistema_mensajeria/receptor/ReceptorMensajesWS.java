package stoxtreme.sistema_mensajeria.receptor;

import java.util.Iterator;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;
import stoxtreme.interfaz_remota.Mensaje;

public class ReceptorMensajesWS extends ReceptorMensajes{
	private HiloConsultaWS hiloConsulta;
	private String usuario;
	
	public ReceptorMensajesWS(String usuario){
		super();
		this.usuario = usuario;
		hiloConsulta = new HiloConsultaWS(this);
		hiloConsulta.start();
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public synchronized void notifica(Mensaje m) {
		Iterator it = listaOyentes.iterator();
		
		while(it.hasNext()){
			((IMensajeriaListener)it.next()).onMensaje(m);
		}
	}
	
}
