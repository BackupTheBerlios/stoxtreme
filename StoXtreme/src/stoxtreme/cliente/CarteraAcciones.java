package stoxtreme.cliente;

import stoxtreme.cliente.gui.ModeloCartera;
import stoxtreme.interfaz_remota.Operacion;

public class CarteraAcciones {
	private ModeloCartera mCartera;
	
	public CarteraAcciones(){
		mCartera = new ModeloCartera();
	}

	public ModeloCartera getMCartera() {
		return mCartera;
	}

	public void setMCartera(ModeloCartera cartera) {
		mCartera = cartera;
	}

	public void actualiza(Operacion o, int cantidad) {
		if(o.getTipoOp()==Operacion.COMPRA){
			mCartera.insertarAcciones(o.getEmpresa(), cantidad);
		}
		else if(o.getTipoOp()==Operacion.VENTA){
			mCartera.restaAcciones(o.getEmpresa(), cantidad);
		}
	}
}
