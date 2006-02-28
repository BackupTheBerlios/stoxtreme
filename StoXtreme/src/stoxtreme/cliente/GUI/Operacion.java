package stoxtreme.cliente.GUI;

public class Operacion {
	public static final int COMPRA = 1;
	public static final int VENTA = 2;
	
	private String IDusr;
	private String IDempresa;
	private int cantidad;
	private float precio;
	private int tipo;
	
	public Operacion(String IDusr, String IDempresa, int cantidad, float precio, int tipo){
		this.IDusr = IDusr;
		this.IDempresa = IDempresa;
		this.cantidad = cantidad;
		this.precio = precio;
		this.tipo = tipo;
		
		System.out.println(
				"Usuario: " + IDusr + " " +
				"Empresa: " + IDempresa + " " +
				"Cantidad: " + cantidad + " " +
				"Precio: " + precio + " " +
				"Tipo: " + (tipo==Operacion.COMPRA?"Compra":"Venta")
		);
	}
	public void setIDusr(String iDusr) {
		IDusr = iDusr;
	}
	public String getIDusr() {
		return IDusr;
	}
	public void setIDempresa(String iDempresa) {
		IDempresa = iDempresa;
	}
	public String getIDempresa() {
		return IDempresa;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public float getPrecio() {
		return precio;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getTipo() {
		return tipo;
	}
	
}
