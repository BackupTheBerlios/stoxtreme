package stoxtreme.sistema_mensajeria.receptor;
import java.util.ArrayList;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;

public class ReceptorMensajes{
	protected ArrayList listaOyentes;
	
	public ReceptorMensajes(){
		listaOyentes = new ArrayList();
	}
	
	public void addListener(IMensajeriaListener l){
		listaOyentes.add(l);	
	}
}
