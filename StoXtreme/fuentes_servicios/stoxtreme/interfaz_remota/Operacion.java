package stoxtreme.interfaz_remota;

public class Operacion {
	public static final int COMPRA=1;
	public static final int VENTA=2;
	
	private int tipoOp;
	private int cantidad;
    private String empresa;
    private float precio;
    private String idEmisor;
    
    private boolean valido = false;
    
    public Operacion(){
    	valido = false;
    }
    
    public Operacion(String idEmisor, String empresa, float precio, int tipoOp, int cantidad){
		this.tipoOp = tipoOp;
		this.idEmisor = idEmisor;
		this.empresa = empresa;
		this.precio = precio;
		this.tipoOp = tipoOp;
		this.cantidad = cantidad;
	}
    
	public void setTipoOp(int tipoOp) {
		this.tipoOp = tipoOp;
	}

	public int getTipoOp() {
		return tipoOp;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getPrecio() {
		return precio;
	}

	public void setIdEmisor(String idEmisor) {
		this.idEmisor = idEmisor;
	}

	public String getIdEmisor() {
		return idEmisor;
	}

	public boolean isValido() {
		// TODO Para validarlo habria que ver si la empresa existe
		valido = idEmisor != null && !idEmisor.equals("") &&
			empresa != null && !empresa.equals("") &&
			(tipoOp == 1 || tipoOp==2) &&
			precio > 0.0 && cantidad > 0; 
		return valido;
	}
}
