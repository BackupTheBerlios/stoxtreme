package stoxtreme.cliente;

import java.awt.Component;

import stoxtreme.cliente.gui.ModeloCartera;
import stoxtreme.interfaz_remota.Operacion;

/**
 *  Description of the Class
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class CarteraAcciones {
	private ModeloCartera mCartera;


	/**
	 *  Constructor for the CarteraAcciones object
	 */
	public CarteraAcciones() {
		mCartera = new ModeloCartera();
	}


	/**
	 *  Sets the MCartera attribute of the CarteraAcciones object
	 *
	 *@param  cartera  The new MCartera value
	 */
	public void setMCartera(ModeloCartera cartera) {
		mCartera = cartera;
	}


	/**
	 *  Gets the MCartera attribute of the CarteraAcciones object
	 *
	 *@return    The MCartera value
	 */
	public ModeloCartera getMCartera() {
		return mCartera;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  o         Description of Parameter
	 *@param  cantidad  Description of Parameter
	 */
	public void actualiza(Operacion o, int cantidad) {
		if (o.getTipoOp() == Operacion.COMPRA) {
			mCartera.insertarAcciones(o.getEmpresa(), cantidad, o.getPrecio());
		}
		else if (o.getTipoOp() == Operacion.VENTA) {
			mCartera.restaAcciones(o.getEmpresa(), cantidad);
		}
	}
}
