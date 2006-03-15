package stoxtreme.sistema_mensajeria.receptor;
import java.util.ArrayList;
import java.util.Iterator;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;

public class ReceptorMensajes{
	public static final int LOCAL = 1;
	public static final int WEB_SERVICE = 2;
	private ArrayList<IMensajeriaListener> listaOyentes;
	private HiloConsultaWS hiloConsultaWS;
	private HiloConsultaLocal hiloConsultaLocal;
	private String usuario;
	
	public ReceptorMensajes(String usuario, int tipo){
		listaOyentes = new ArrayList();
		this.usuario = usuario;
		if(tipo==LOCAL){
			hiloConsultaLocal = new HiloConsultaLocal(this);
			hiloConsultaLocal.start();
		}
		else{
			hiloConsultaWS = new HiloConsultaWS(this);
			hiloConsultaWS.start();
		}

	}
	
	public void addListener(IMensajeriaListener l){
		listaOyentes.add(l);	
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
