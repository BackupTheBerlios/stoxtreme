package stoxtreme.herramienta_agentes.agentes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.HashSet;

import stoxtreme.interfaz_remota.Operacion;

public class OperacionesPendientes {
	Hashtable<Integer, String> empresasPendientes;
	Hashtable<Integer, OperacionLocal> comprasPendientes;
	Hashtable<Integer, OperacionLocal> ventasPendientes;
	HashSet<Integer> cancelacionPendiente;
	
	public OperacionesPendientes(){
		empresasPendientes = new Hashtable<Integer, String>();
		comprasPendientes = new Hashtable<Integer, OperacionLocal>();
		ventasPendientes = new Hashtable<Integer, OperacionLocal>();
		cancelacionPendiente = new HashSet<Integer>();
	}
	
	public void insertaOperacionPendiente(int operacion, String empresa, int tipoOp, int numAcciones, double precio){
		empresasPendientes.put(operacion, empresa);
		Hashtable<Integer, OperacionLocal> tabla;
		if(tipoOp == Operacion.COMPRA){
			tabla = comprasPendientes;
		}
		else{ // Operacion.VENTA
			tabla = ventasPendientes;
		}
		tabla.put(operacion, new OperacionLocal(empresa, numAcciones, precio));
	}
	
	public void insertarCancelacionPendiente(int nCancelacion){
		cancelacionPendiente.add(nCancelacion);
	}
	
	public boolean isPendienteCancelacion(int nCancelacion){
		return cancelacionPendiente.contains(nCancelacion);
	}
	public String getEmpresaOperacion(int idOp) {
		return empresasPendientes.get(idOp);
	}

	public int getTipoOperacion(int idOp){
		if(comprasPendientes.containsKey(idOp)){
			return Operacion.COMPRA;
		}
		else {
			return Operacion.VENTA;
		}
	}

	public void cancelaOp(int idOp) {
		comprasPendientes.remove(idOp);
		ventasPendientes.remove(idOp);
		empresasPendientes.remove(idOp);
		cancelacionPendiente.remove(idOp);
	}

	public boolean hayOperacionesPendientes(String empresa) {
		return empresasPendientes.contains(empresa);
	}

	public void restaAcciones(int idOp, int cantidad) {
		if(comprasPendientes.containsKey(idOp)){
			comprasPendientes.get(idOp).setCantidad(comprasPendientes.get(idOp).getCantidad() - cantidad);
			if(comprasPendientes.get(idOp).getCantidad() == 0){
				cancelaOp(idOp);
			}
		}
		else{
			ventasPendientes.get(idOp).setCantidad(ventasPendientes.get(idOp).getCantidad() - cantidad);
			if(ventasPendientes.get(idOp).getCantidad() == 0)
				cancelaOp(idOp);
		}
	}

	public int dameOperacionAleatoria() {
		Enumeration<Integer> enu = empresasPendientes.keys();
		int n = (int)(Math.random()*(double)empresasPendientes.size());

		int actual = -1;
		for(int i=0; i<=n; i++){ // Avanza n-1
			actual = enu.nextElement();
		}
		
		return actual;
	}

	public double getPrecioOperacion(int idOp) {
		if(comprasPendientes.containsKey(idOp)){
			return comprasPendientes.get(idOp).getPrecio();
		}
		else{
			return ventasPendientes.get(idOp).getPrecio();
		}
	}
	
	private class OperacionLocal{
		private String empresa;
		private int cantidad;
		private double precio;
		
		public OperacionLocal(String empresa, int cantidad, double precio){
			setEmpresa(empresa);
			setCantidad(cantidad);
			setPrecio(precio);
		}
		
		public void setEmpresa(String empresa) {
			this.empresa = empresa;
		}
		
		public String getEmpresa() {
			return empresa;
		}
		
		private void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		
		private int getCantidad() {
			return cantidad;
		}
		
		public void setPrecio(double precio) {
			this.precio = precio;
		}
		
		public double getPrecio() {
			return precio;
		}
	}

	public boolean estaOperacion(int id) {
		boolean estaC = comprasPendientes.containsKey(id);
		boolean estaV = ventasPendientes.containsKey(id);
		return estaC || estaV;
	}

	public ArrayList<Integer> dameOperacionesPendientes() {
		ArrayList<Integer> lista = new ArrayList<Integer>();
		Enumeration<Integer> idsc = comprasPendientes.keys();
		Enumeration<Integer> idsv = ventasPendientes.keys();
		
		while(idsc.hasMoreElements()){
			lista.add(idsc.nextElement());
		}
		while(idsc.hasMoreElements()){
			lista.add(idsv.nextElement());
		}
		return lista;
	}
}
