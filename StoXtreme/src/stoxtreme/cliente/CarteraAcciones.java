package stoxtreme.cliente;

import java.awt.Component;

import stoxtreme.cliente.gui.ModeloCartera;
import stoxtreme.interfaz_remota.Operacion;

/**
 *  Total de valores de empresas que el cliente dispone
 *
 *@author    Iván Gómez Edo, Itziar Pérez García, Alonso Javier Torres
 */
public class CarteraAcciones {
	private ModeloCartera mCartera;


	/**
	 *  Constructor de CarteraAcciones
	 */
	public CarteraAcciones() {
		mCartera = new ModeloCartera();
	}


	/**
	 *  Sutituimos MCartera por un nuevo objeto
	 *
	 *@param  cartera  Nuevo valor de MCartera
	 */
	public void setMCartera(ModeloCartera cartera) {
		mCartera = cartera;
	}


	/**
	 *  obtenemos el valor de MCartera
	 *
	 *@return    Valor de MCartera
	 */
	public ModeloCartera getMCartera() {
		return mCartera;
	}


	/**
	 *  Actualiza nuestra cartera con una operación sobre alguna de las empresas que hay en el mercado
	 *
	 *@param  o         Detalle de la operacion
	 *@param  cantidad  Cantidad de acciones a operar
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
