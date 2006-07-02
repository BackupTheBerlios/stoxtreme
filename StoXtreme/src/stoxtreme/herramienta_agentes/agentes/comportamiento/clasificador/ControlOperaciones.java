package stoxtreme.herramienta_agentes.agentes.comportamiento.clasificador;

import java.util.Hashtable;

public class ControlOperaciones {
	private Hashtable<Regla, Elemento> listaCompras;
	
	public ControlOperaciones() {
		listaCompras = new Hashtable<Regla,Elemento>();
	}
	public void insertaCompraEfectuada(int ciclo, Regla r, double precio, int cantidad){
		// Guardamos la compra
		listaCompras.put(r, new Elemento(precio,cantidad));
	}
	
	public void insertaVentaEfectuada(int ciclo, Regla r, double precio, int cantidad){
		double dinero = precio*(double)cantidad;
		// Insertamos la venta y repartimos el dinero
		// 50% para la venta
		r.addBeneficio(dinero*0.5);
		// Resto para las compras teniendo en cuenta el tiempo
		// La que tenga tiempo inferior será la que tiene más beneficio
		
	}
	
	private static class Elemento{
		private double beneficio;
		private double cicloInsercion;
		
		public Elemento(double precio, double cantidad) {
			this.beneficio = -precio*(double)cantidad;
		}
		
		public void insertaGanancia(double cantidad){
			beneficio += cantidad;
		}
	}
}
