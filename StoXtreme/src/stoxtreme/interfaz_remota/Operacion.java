package stoxtreme.interfaz_remota;
import java.lang.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Operacion {
	public static final String COMPRA="COMPRA";
	public static final String VENTA="VENTA";
	private String nombreEmpresa;
	private int numAcciones;
	private float precio;
	private String tipoOp;
	
	public Operacion(){
		
	}
	public Operacion(String tipoOp, String nombreEmpresa, 
			int numAcciones, float precio){
		this.setNombreEmpresa(nombreEmpresa);
		this.setNumAcciones(numAcciones);
		this.setPrecio(precio);
		this.setTipoOp(tipoOp);
	}
	
	public Operacion (Hashtable h){
		Enumeration contenido = h.elements();
		setNombreEmpresa((String)contenido.nextElement());
		setNumAcciones(Integer.parseInt(contenido.nextElement().toString()));
		setPrecio(new Float(contenido.nextElement().toString()).floatValue());
		setTipoOp((String)contenido.nextElement());
	}
	
	public Hashtable toHashtable(){
		Hashtable h= new Hashtable();
		h.put("nombreEmpresa", this.getNombreEmpresa());
		h.put("numAcciones",new Integer(this.getNumAcciones()).toString());
		h.put("precio",new Float (this.getPrecio()).toString());
		h.put("tipoOp",this.getTipoOp());
		return h;
	}

	public void setTipoOp(String tipoOp) {
		this.tipoOp = tipoOp;
	}

	public String getTipoOp() {
		return tipoOp;
	}

	public void setNumAcciones(int numAcciones) {
		this.numAcciones = numAcciones;
	}

	public int getNumAcciones() {
		return numAcciones;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getPrecio() {
		return precio;
	}
}
