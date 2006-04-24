package stoxtreme.cliente;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;

public class ManejadorMensajes implements IMensajeriaListener{
	private Cliente cliente;
	public ManejadorMensajes(Cliente cl){
		this.cliente = cl;
	}
	
	public synchronized void onMensaje(Mensaje m){
		System.out.println("RECIBIDO MENSAJE " + m.getContenido());
		
		if(m.getTipoMensaje().equals("NOTIFICACION_OPERACION")){
			String[] valores = m.getContenido().split(",");
			int idOp = Integer.parseInt(valores[0]);
			int cantidad = Integer.parseInt(valores[1]);
			cliente.notificaOperacion(idOp, cantidad);
		}
		else if(m.getTipoMensaje().equals("NOTIFICACION_CANCELACION")){
			int idOp = Integer.parseInt(m.getContenido());
			cliente.notificaCancelacion(idOp);
		}
		else if(m.getTipoMensaje().equals("CAMBIO_PRECIO")){
			String[] valores = m.getContenido().split(",");
			String empresa = valores[0];
			double nuevoPrecio = Double.parseDouble(valores[1]);
			EstadoBolsa eBolsa = cliente.getEstadoBolsa();
			eBolsa.cambiaValor(empresa, nuevoPrecio);
		}
		else if(m.getTipoMensaje().equals("INFORMACION")){
			cliente.informar(m.getContenido());
		}
		else{
			System.out.println("Mensaje("+m.getTipoMensaje()+"): "+ m.getContenido());
		}
	}
}
