package stoxtreme.herramienta_agentes;

public class Operacion {
	enum TipoOP {CABECERA, COMPRA, VENTA}; 
	public static final int COMPRA=1;
	public static final int VENTA=2;
	public static final int CABECERA=0;
	private int tipo;
	private int numeroAcciones;
	private double precio;
	private String empresa;
	private String IDAgente;
	private int idOp;
	
	public static double redondeo(double numero, int nDecimales){
		return Math.floor((Math.pow(10,nDecimales)*numero)+0.5)/Math.pow(10,nDecimales);
	}
	
	public Operacion(String IDAgente, String empresa, int tipo, int numeroAcciones, double precio){
		setTipo(tipo);
		setNumeroAcciones(numeroAcciones);
		precio = redondeo(precio, 2);
		setPrecio(precio);
		setEmpresa(empresa);
		setIDAgente(IDAgente);
		setIDOp(-1);
	}

	public void setNumeroAcciones(int numeroAcciones) {
		this.numeroAcciones = numeroAcciones;
	}

	public int getNumeroAcciones() {
		return numeroAcciones;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getPrecio() {
		return precio;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setIDAgente(String iDAgente) {
		IDAgente = iDAgente;
	}

	public String getIDAgente() {
		return IDAgente;
	}
	
	public void setIDOp(int idOp) {
		this.idOp = idOp;
	}
	
	public int getIDOp() {
		return idOp;
	}
	
	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append(idOp); b.append(": ");
		b.append(IDAgente);
		b.append("(");
		if(tipo == COMPRA)
			b.append("COMPRA");
		else
			b.append("VENTA");
		b.append(")[");
		
		b.append(empresa); b.append(",");
		
		b.append(numeroAcciones); b.append(",");
		b.append(precio); 
		b.append("]");
		return b.toString();
	}
	
	public String datosImportantes(){
		StringBuffer b = new StringBuffer();
		b.append("[");
		if(tipo == COMPRA)
			b.append("COMPRA");
		else
			b.append("VENTA");
		b.append("");
		
		b.append(empresa); b.append(",");
		
		b.append(numeroAcciones); b.append(",");
		b.append(precio); 
		b.append("]");
		return b.toString();
	}
}
