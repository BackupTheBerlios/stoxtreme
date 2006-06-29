package stoxtreme.cliente;

import javax.swing.JOptionPane;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;

public class ManejadorMensajes implements IMensajeriaListener{
	private Cliente cliente;
	public ManejadorMensajes(Cliente cl){
		this.cliente = cl;
	}
	
	public synchronized void onMensaje(Mensaje m){
		System.out.println("RECIBIDO MENSAJE " +m.getDestinatario()+" "+ m.getContenido());
		
		if(m.getTipoMensaje().equals("NOTIFICACION_OPERACION")){
			String[] mss = m.getContenido().split(",");
			int idOp = Integer.parseInt(mss[0]);
			int cantidad = Integer.parseInt(mss[1]);
			double precio = Double.parseDouble(mss[2]);
			cliente.notificaOperacion(m.getDestinatario(), idOp, cantidad, precio);
		}
		else if(m.getTipoMensaje().equals("NOTIFICACION_CANCELACION")){
			int idOp = Integer.parseInt(m.getContenido());
			cliente.notificaCancelacion(m.getDestinatario(), idOp);
		}
		else if(m.getTipoMensaje().equals("CAMBIO_PRECIO")){
			String[] valores = m.getContenido().split(",");
			String empresa = valores[0];
			double nuevoPrecio = Double.parseDouble(valores[1]);
			EstadoBolsa eBolsa = cliente.getEstadoBolsa();
			eBolsa.cambiaValor(empresa, nuevoPrecio);
		}
		else if(m.getTipoMensaje().equals("INFORMACION")||m.getTipoMensaje().equals("RUMOR_AGENTES")){
			cliente.informar(m.getContenido());
		}
		else if(m.getTipoMensaje().equals("FINSESION")){
			cliente.finSimulacion();
		}
		else{
			System.out.println("Mensaje("+m.getTipoMensaje()+"): "+ m.getContenido());
		}
	}
}
