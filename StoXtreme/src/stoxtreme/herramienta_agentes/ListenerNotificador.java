package stoxtreme.herramienta_agentes;

/**
 *  Description of the Interface
 *
 *@author    Chris Seguin
 */
public interface ListenerNotificador {
	//public void onCambioPrecioAccion(String empresa, double nuevoPrecio);
	/**
	 *  Description of the Method
	 *
	 *@param  idOp      Description of Parameter
	 *@param  cantidad  Description of Parameter
	 *@param  precio    Description of Parameter
	 */
	public void onNotificacionOp(int idOp, int cantidad, double precio);


	/**
	 *  Description of the Method
	 *
	 *@param  idOp  Description of Parameter
	 */
	public void onCancelacionOp(int idOp);
}
