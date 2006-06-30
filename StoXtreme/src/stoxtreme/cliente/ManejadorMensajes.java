package stoxtreme.cliente;

import javax.swing.JOptionPane;

import stoxtreme.interfaz_remota.Mensaje;
import stoxtreme.sistema_mensajeria.IMensajeriaListener;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class ManejadorMensajes implements IMensajeriaListener {
	private Cliente cliente;


	/**
	 *  Constructor for the ManejadorMensajes object
	 *
	 *@param  cl  Description of Parameter
	 */
	public ManejadorMensajes(Cliente cl) {
		this.cliente = cl;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  m  Description of Parameter
	 */
	public synchronized void onMensaje(Mensaje m) {

		if (m.getTipoMensaje().equals("NOTIFICACION_OPERACION")) {
			String[] mss = m.getContenido().split(",");
			int idOp = Integer.parseInt(mss[0]);
			int cantidad = Integer.parseInt(mss[1]);
			double precio = Double.parseDouble(mss[2]);
			cliente.notificaOperacion(m.getDestinatario(), idOp, cantidad, precio);
		}
		else if (m.getTipoMensaje().equals("NOTIFICACION_CANCELACION")) {
			int idOp = Integer.parseInt(m.getContenido());
			cliente.notificaCancelacion(m.getDestinatario(), idOp);
		}
		else if (m.getTipoMensaje().equals("CAMBIO_PRECIO")) {
			String[] valores = m.getContenido().split(",");
			String empresa = valores[0];
			double nuevoPrecio = Double.parseDouble(valores[1]);
			EstadoBolsa eBolsa = cliente.getEstadoBolsa();
			eBolsa.cambiaValor(empresa, nuevoPrecio);
		}
		else if (m.getTipoMensaje().equals("INFORMACION") || m.getTipoMensaje().equals("RUMOR_AGENTES")) {
			cliente.informar(m.getContenido());
		}
		else if (m.getTipoMensaje().equals("FINSESION")) {
			cliente.finSimulacion();
		}
		else {
		}
	}
}
